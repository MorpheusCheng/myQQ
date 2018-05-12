/**
 * 功能：登录后台实现
 */
package com.qq.client;

import com.qq.common.User;

public class QQClientUser {
	
	// 检验用户合法性
	public boolean checkUser(User u) {
		return new QQClientConServer().SendLoginInfoTOServer(u);
	}
}
