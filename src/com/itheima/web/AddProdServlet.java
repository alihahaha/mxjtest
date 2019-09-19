package com.itheima.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.itheima.domain.Product;
import com.itheima.factory.BasicFactory;
import com.itheima.service.ProdService;
import com.itheima.utils.PicUtils;

public class AddProdServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Map<String,String> map = new HashMap<String, String>();
			//1.将商品图片上传到服务器
			String upload = "/WEB-INF/upload";
			String temp = "/WEB-INF/temp";
			
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1024*100);
			factory.setRepository(new File(this.getServletContext().getRealPath(temp)));
			
			ServletFileUpload fileUpload = new ServletFileUpload(factory);
			
			if(!fileUpload.isMultipartContent(request)){
				throw new RuntimeException("请使用正确的表单进行上传!");
			}
			
			fileUpload.setFileSizeMax(1024*1024*10);
			fileUpload.setSizeMax(1024*1024*10);
			fileUpload.setHeaderEncoding("utf-8");
			
			List<FileItem> list = fileUpload.parseRequest(request);
			
			for(FileItem item : list){
				if(item.isFormField()){
					map.put(item.getFieldName(), item.getString("utf-8"));
				}else{
					String realname = item.getName();
					if(realname.contains("\\")){
						realname = realname.substring(realname.lastIndexOf("\\")+1);
					}
					String uuidname = UUID.randomUUID().toString()+"_"+realname;
					String hash = Integer.toHexString(uuidname.hashCode());
					for(char c : hash.toCharArray()){
						upload = upload + "/" +c;
					}
					File dir = new File(this.getServletContext().getRealPath(upload));
					dir.mkdirs();
					map.put("imgurl", upload+"/"+uuidname);
					
					InputStream in = item.getInputStream();
					OutputStream out = new FileOutputStream(new File(dir,uuidname));
					
					int i = 0;
					byte [] bs = new byte[1024];
					while((i = in.read(bs))!=-1){
						out.write(bs, 0, i);
					}
					
					in.close();
					out.close();
					
					item.delete();
				
					//--生成缩略图
					PicUtils utils = new PicUtils(dir+"/"+uuidname);
					utils.resizeByHeight(100);
				}
			}
			//2.调用Service添加商品信息
			ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
			Product prod = new Product();
			BeanUtils.populate(prod, map);
			prod.setId(UUID.randomUUID().toString());
			service.addProd(prod);
			
			//3.回到主页
			response.getWriter().write("添加商品成功!");
			response.setHeader("Refresh", "1;url="+request.getContextPath()+"/index.jsp");
		}catch (FileSizeLimitExceededException e) {
			throw new RuntimeException("图片不允许超过10M!");
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
