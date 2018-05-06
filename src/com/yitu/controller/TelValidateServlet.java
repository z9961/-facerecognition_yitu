package com.yitu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

import com.yitu.entity.User;

/**
 * Servlet implementation class TelValidateServlet
 */
@WebServlet("/TelValidateServlet")
public class TelValidateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TelValidateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
//		String tel = request.getParameter("tel");
		User user = (User)request.getSession().getAttribute("user");
		String tel = user.getPhone();
		String number = request.getParameter("number");
		int appid = 1400086205; 
		String appkey = "d7bb95f0669c3a75fe865d2c1abeabd2";
		// 短信模板ID，需要在短信应用中申请
		int templateId = 140615;
//		// 签名
		String smsSign = "境界的彼方";
//		String smsSign = ""
//		System.out.println("2222222222222222222");
		try {
			int code = (int)(Math.random()*9000+1000);
			System.err.println(code);
			request.getSession().setAttribute("code", code);
			//单发短信
//			String[] params = {"5678","1234"};
//		    SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
//		    SmsSingleSenderResult result = ssender.sendWithParam("86", tel, templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
//		    		
//		    		
////		    		sendWithParam("86", tel,
////		    		        templateId, params[0], smsSign, "", "");
//		    System.out.print(result);
		    
		    
			
			
			
			//指定模板单发短信；
		    SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
		    SmsSingleSenderResult result = ssender.send(0, "86", tel,
		        "境界的彼方"+code+"为您的登录验证码，请于1分钟内填写。如非本人操作，请忽略本短信。", "", "");
		    System.out.print(result);
		    response.setCharacterEncoding("UTF-8");// 编码
			response.getWriter().write("success");// 传值
		    
		    //判断验证码是否正确；
//		    if(number.equals(params[0])){
//		    	response.sendRedirect("success.jsp");
//		    }else{
//		    	request.setAttribute("msg", "验证码有误");
//		    	response.sendRedirect("message_login.jsp");
//		    }
		    
//		    response.sendRedirect("success.jsp");
		} catch (HTTPException e) {
		    // HTTP响应码错误
		    e.printStackTrace();
		} catch (JSONException e) {
		    // json解析错误
		    e.printStackTrace();
		} catch (IOException e) {
		    // 网络IO错误
		    e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
