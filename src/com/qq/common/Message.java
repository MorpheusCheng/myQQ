/**
 * 功能：消息类
 * 可以发送消息
 * Message规格
 * mesType 1 ->表示登录成功
 * mesType 2 ->表示登录失败
 * mesType 3 ->表示普通的消息包
 */
package com.qq.common;

import java.io.Serializable;

public class Message implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5160728458299623666L;

	private String mesType;	//消息种类
	
	private String sender;	//发送者
	
	private String getter;	//接收者
	
	private String content;	//内容
	
	private String sendTime;//时间

	private String MultiChat;//群聊信息
	
	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getGetter() {
		return getter;
	}

	public void setGetter(String getter) {
		this.getter = getter;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMesType() {
		return mesType;
	}

	public void setMesType(String mesType) {
		this.mesType = mesType;
	}

	public void setMultiChat(String multiChat) {
		MultiChat = multiChat;
	}

	public String getMultiChat() {
		return MultiChat;
	}
}
