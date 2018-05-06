package com.yitu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yitu.dao.IUserDao;
import com.yitu.dao.UserDaoImp;
import com.yitu.entity.User;

@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Register() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		int type = Integer.valueOf(request.getParameter("rdo1"));
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String phone = ((User) request.getSession().getAttribute("user")).getPhone();
		// String messagecode=request.getParameter("password");
		// String truecode=request.request.getSession().getAttribute("user");


		String code = request.getParameter("messagecode");
		System.out.println("chaunguolaide"+code);
		String true_code = request.getSession().getAttribute("code").toString();
		System.out.println("shiji"+true_code);
		
		 if(code.equals(true_code)){
			 User user = new User();
				user.setPassword(password);
				user.setPhone(phone);
				user.setUsername(username);
				request.getSession().setAttribute("user", user);
				IUserDao userdao = UserDaoImp.getInstance();

				
				
				try {
					userdao.registe(user);
				} catch (Exception e) {
					 request.setAttribute("exception_sql", "用户已经存在");
					 request.setAttribute("turn_index", "1");
			          request.getRequestDispatcher("register.jsp").forward(request, response);
			          
			     	
			      
					// TODO: handle exception
				}
				
				if (type == 0) {

					request.getRequestDispatcher("registersuccess.jsp").forward(request, response);

				} else {
					request.getRequestDispatcher("face_register.jsp").forward(request, response);

				}	 
		
		 }else{
			 System.out.println("验证码错误");
		
		          request.setAttribute("code_fault", "验证码错误");
		          request.getRequestDispatcher("register.jsp").forward(request, response);
		 }
		
		
		
	}

}
