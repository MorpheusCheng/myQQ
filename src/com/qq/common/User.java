/**
 * 用户信息类
 */
package com.qq.common;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8433144405811117163L;
	private String userId; // 账号
	private String passwd; // 密码
	private Boolean flag;//是否注册

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Boolean getFlag(){
		return  flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
}
