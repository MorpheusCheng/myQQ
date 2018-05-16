package com.qq.client;

import java.util.HashMap;

public class ManageMultiChat {
    private static HashMap<String, MultiChatView> hm = new HashMap<String, MultiChatView>();

    // 加入一个聊天界面
    public static void addMultiChat(String loginIdAndMultiChatId, MultiChatView multiChat) {
        hm.put(loginIdAndMultiChatId, multiChat);
    }

    // 获取一个聊天界面
    public static MultiChatView getMultiChat(String loginIdAndMultiChatId) {
        return (MultiChatView) hm.get(loginIdAndMultiChatId);
    }
    //删除一个群聊界面
    public static void removeMultiChat(String loginIdAndMultiChatId){
        hm.remove(loginIdAndMultiChatId);
    }
}
