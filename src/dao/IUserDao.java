package dao;

import entity.User;

public interface IUserDao {
	// 注册用户
	Boolean registe(User u);

	// 使用密码登录
	User login(String phone, String password);

	// 使用验证码和人脸识别登录
	User login(String phone);

	// 检查是否是第一次登录
	Boolean isFirst(String phone);

	// 检查用户是否存在
	Boolean isRegistered(String phone);
}
