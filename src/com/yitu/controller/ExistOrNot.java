package com.yitu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yitu.dao.IUserDao;
import com.yitu.dao.UserDaoImp;
import com.yitu.entity.User;

/**
 * Servlet implementation class ExistOrNot
 */
@WebServlet("/existOrNot")
public class ExistOrNot extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ExistOrNot() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取手机号 判断是不是已经注册过
		String phone = request.getParameter("phone");
		IUserDao userdao = UserDaoImp.getInstance();
		User user = new User();
		user.setPhone(phone);
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		session.setAttribute("phone", phone);
		if (userdao.isRegistered(phone)) {
			// 用户已经存在 将用户放在session中 并 跳转登录界面
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} else {
			// 用户不存在 跳转注册界面
			request.getRequestDispatcher("register.jsp").forward(request, response);
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
