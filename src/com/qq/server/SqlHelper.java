/**
 * 功能：用于连接数据库
 */
package com.qq.server;

import java.sql.*;

public class SqlHelper {
	// 数据库变量
	private Connection ct = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/QQdb";
	private String username = "root";
	@SuppressWarnings("unused")
	private String password = "123456";

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Connection getCt() {
		return ct;
	}

	public void setCt(Connection ct) {
		this.ct = ct;
	}

	public PreparedStatement getPs() {
		return ps;
	}

	public void setPs(PreparedStatement ps) {
		this.ps = ps;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	// 构造方法，用于初始化连接
	public SqlHelper() {
		try {
			// 连接驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 获取连接
			ct = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/QQdb", "root",
					"123456");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 查询功能
	public ResultSet queryExecute(String sql, String[] paras) {
		try {
			// 创建PreparedStatement
			ps = ct.prepareStatement(sql);
			// 给ps参数赋值
			for (int i = 0; i < paras.length; i++) {
				ps.setString(i + 1, paras[i]);
			}
			//执行操作
			rs = ps.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	// 关闭数据库资源
	public void close() {
		// 关闭资源
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (ct != null) {
				ct.close();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

//测试
	/*public static void main(String[] args) {
		SqlHelper helper = new SqlHelper();
		String sql = "select * from QQUser where 1=?";
		String[] paras = {"1"};
		ResultSet r = helper.queryExecute(sql, paras);
		try {
			while(r.next()){
			System.out.println(r.getString("QQUserId") + " " + r.getString("QQPassword"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

}
