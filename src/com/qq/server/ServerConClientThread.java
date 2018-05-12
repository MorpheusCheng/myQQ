package com.qq.server;

import com.qq.common.Message;
import com.qq.common.MessageType;

import java.net.*;
import java.util.*;
import java.io.*;

public class ServerConClientThread extends Thread {
    Socket s;

    public ServerConClientThread(Socket s){
        this.s = s;
    }

    public void notifyAllOtherFriends(String iam){
        HashMap<String,?> hm = ManageClientThread.hm;
        Iterator<String> it = hm.keySet().iterator();

        while( it.hasNext()){
            Message m = new Message();
            m.setContent(iam);
            m.setMesType(MessageType.MESSAGE_RET_ONLINEFRIEND);
            String onId = it.next();

            if(hm.get(onId) != null) {
                try {

                    ObjectOutputStream oos = new ObjectOutputStream(ManageClientThread.getClientThread(onId).s.getOutputStream());
                    m.setGetter(onId);
                    oos.writeObject(m);
                    System.out.println("要发送给"+onId+" "+iam+"刚上线的消息");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void run(){
        while (true){
            try{
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                Message m = (Message) ois.readObject();
                System.out.println(m.getSender()+"给"+m.getGetter()+"内容为："+m.getContent());
                if (m.getMesType().equals(MessageType.MESSAGE_COMM))
                {
                    ServerConClientThread sc = ManageClientThread.getClientThread(m.getGetter());
                    ObjectOutputStream oos = new ObjectOutputStream(sc.s.getOutputStream());
                    oos.writeObject(m);
                    System.out.println("消息发送成功");
                }
                else if (m.getMesType().equals(MessageType.MESSAGE_GET_ONLINEFRIEND)){
                    System.out.println(m.getSender()+" 要他的好友");
                    String res = ManageClientThread.getAllOnLineUserId();
                    System.out.println(m.getSender()+" 的好友有 "+ res);
                    Message m2 = new Message();
                    m2.setMesType((MessageType.MESSAGE_RET_ONLINEFRIEND));
                    m2.setContent(res);
                    m2.setGetter(m.getSender());
                    ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                    oos.writeObject(m2);
                }
                else if (m.getMesType().equals(MessageType.MESSAGE_EXIT)){
                    ManageClientThread.removeClientThread(m.getSender());
                    break;
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
