package com.yitu.utils;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DaoUtils {

	// 创建数据池
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();

	// 返回数据池
	public static ComboPooledDataSource getDataSource() {
		return dataSource;
	}

	// 返回一个连接
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

}
