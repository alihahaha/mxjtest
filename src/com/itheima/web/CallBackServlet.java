package com.itheima.web;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.factory.BasicFactory;
import com.itheima.service.OrderService;
import com.itheima.utils.PaymentUtil;

public class CallBackServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获得回调所有数据
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");	
		// 身份校验 --- 判断是不是支付公司通知你
		String hmac = request.getParameter("hmac");
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
				"keyValue");
		// 自己对上面数据进行加密 --- 比较支付公司发过来hamc
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
				r8_MP, r9_BType, keyValue);
	
		if(isValid){
			if("1".equals(r9_BType)){
				//--浏览器调用
				response.getWriter().write("支付成功!3秒后回到主页..");
				response.setHeader("Refresh", "3;url="+request.getContextPath()+"/index.jsp");

				//--真正的修改订单状态应该在下面的else中进行,但是学习阶段没有做,所以先写在这里测试一下
				OrderService service = BasicFactory.getFactory().getInstance(OrderService.class);
				service.updatePayState(r6_Order,1);
				response.getWriter().print("success");
				
				return;
			}else{
				//--易宝调用,可以相信,需要将当前订单的支付状态从0改为1
				OrderService service = BasicFactory.getFactory().getInstance(OrderService.class);
				service.updatePayState(r6_Order,1);
				response.getWriter().print("success");
			}
		}else{
			throw new RuntimeException("支付结果参数被篡改过!!");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
