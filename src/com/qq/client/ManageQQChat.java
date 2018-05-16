/**
 * 功能：管理用户聊天界面
 */
package com.qq.client;

import java.util.*;


public class ManageQQChat {
	private static HashMap<String, ClientChatView> hm = new HashMap<String, ClientChatView>();

	// 加入一个聊天界面
	public static void addQQChat(String loginIdAndFriendId, ClientChatView qqChat) {
		hm.put(loginIdAndFriendId, qqChat);
	}

	// 获取一个聊天界面
	public static ClientChatView getQQChat(String loginIdAndFriendId) {
		return (ClientChatView) hm.get(loginIdAndFriendId);
	}

	public static void removeQQChat(String loginIdAndFriend){
		hm.remove(loginIdAndFriend);
	}
}
