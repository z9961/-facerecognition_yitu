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

@WebServlet("/passwordLoginCheck")
public class PasswordLoginCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PasswordLoginCheck() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 获取当前用户名跟用户密码
		String password = request.getParameter("password");
		String phone = ((User) request.getSession().getAttribute("user")).getPhone();
		// 使用数据库查找对象 看密码是否正确

		IUserDao userdao = UserDaoImp.getInstance();

		User user = userdao.login(phone, password);

		if (user != null) {
           
			request.getRequestDispatcher("welcome.jsp").forward(request, response);

		} else {
			// 密码不正确
			request.getSession().setAttribute("msg", "密码错误!");
			request.getRequestDispatcher("msg.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
