package dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import entity.User;
import utils.DaoUtils;

public class UserDaoImp implements IUserDao {

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

}
