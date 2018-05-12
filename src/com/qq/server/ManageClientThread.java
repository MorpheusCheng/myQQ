package com.qq.server;

import java.util.HashMap;
import java.util.Iterator;

public class ManageClientThread {
    public static HashMap<String,ServerConClientThread> hm = new HashMap<String, ServerConClientThread>();

    public static void addClientThread(String uid,ServerConClientThread ct){
        System.out.println("在hashmap中已添加"+uid);
        hm.put(uid,ct);
    }

    public static void removeClientThread(String uid){
        hm.remove(uid);
    }

    public static ServerConClientThread getClientThread(String uid){
        return (ServerConClientThread) hm.get(uid);
    }

    // 返回当前在线的人的情况
    public static String getAllOnLineUserId() {
        // 使用迭代器进行遍历
        Iterator<String> it = hm.keySet().iterator();
        String res = "";
        //System.out.println("在while前");
        while (it.hasNext()) {
          //  System.out.println("在while中");
            res += it.next() + " ";

        }
        //System.out.println("在while后");
        return res;
    }
}
