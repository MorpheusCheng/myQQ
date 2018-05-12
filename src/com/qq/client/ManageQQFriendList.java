/**
 * 管理好友、陌生人、黑名单界面
 */
package com.qq.client;

import java.util.*;

public class ManageQQFriendList {
	private static HashMap<String, QQFriendList> hm = new HashMap<String, QQFriendList>();

	// 将界面添加到集合中
	public static void addQQFriendList(String uid, QQFriendList qqFriendList) {
		hm.put(uid, qqFriendList);
	}

	// 从集合中获取界面
	public static QQFriendList getQQFriendList(String uid) {
		return (QQFriendList) hm.get(uid);
	}
}
