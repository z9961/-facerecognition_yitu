package com.yitu.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.yitu.entity.User;
import com.yitu.utils.DaoUtils;

public class UserDaoImp implements IUserDao {

	// 单例模式
	private static UserDaoImp instance;

	public static UserDaoImp getInstance() {
		if (instance == null) {
			instance = new UserDaoImp();
		}
		return instance;
	}

	@Override
	public Boolean registe(User u) {
		QueryRunner qr = new QueryRunner(DaoUtils.getDataSource());
		String sql = "insert into users value(?,?,?,0)";
		try {
			qr.update(sql, u.getPhone(), u.getUsername(), u.getPassword());
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public User login(String phone, String password) {
		User user = null;
		QueryRunner qr = new QueryRunner(DaoUtils.getDataSource());
		String sql = "select * from users where phone=? and password=?";
		try {
			user = qr.query(sql, new BeanHandler<User>(User.class), phone, password);
		} catch (SQLException e) {
			e.printStackTrace();
			
			throw new RuntimeException(e);
		
		}
		return user;
	}

	@Override
	public User login(String phone) {
		User user = null;
		QueryRunner qr = new QueryRunner(DaoUtils.getDataSource());
		String sql = "select * from users where phone=?";
		try {
			user = qr.query(sql, new BeanHandler<User>(User.class), phone);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return user;
	}

	@Override
	public Boolean isFirst(String phone) {
		User user = null;
		QueryRunner qr = new QueryRunner(DaoUtils.getDataSource());
		String sql = "select * from users where phone=?";
		try {
			user = qr.query(sql, new BeanHandler<User>(User.class), phone);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		if (user.getState() != 0)
			return false;
		else
			return true;
	}

	@Override
	public Boolean isRegistered(String phone) {
		User user = null;
		QueryRunner qr = new QueryRunner(DaoUtils.getDataSource());
		String sql = "select * from users where phone=?";
		try {
			user = qr.query(sql, new BeanHandler<User>(User.class), phone);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		if (user == null || "".equals(user) || "".equals(user.getPhone()))
			return false;
		else
			return true;
	}

	@Override
	public void changeState(String phone) {
		QueryRunner qr = new QueryRunner(DaoUtils.getDataSource());
		String sql = "update users set state=state+1 where phone = ?";
		try {
			qr.update(sql, phone);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int countOfLogin(String phone) {
		User user = null;
		QueryRunner qr = new QueryRunner(DaoUtils.getDataSource());
		String sql = "select * from users where phone=?";
		try {
			user = qr.query(sql, new BeanHandler<User>(User.class), phone);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		if (user == null || "".equals(user) || "".equals(user.getPhone()))
			return 0;
		else
			return user.getState();
	}
}
