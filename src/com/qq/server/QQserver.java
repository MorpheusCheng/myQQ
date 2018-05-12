package com.qq.server;

import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class QQserver {

    public void close(){
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public QQserver(){

        try{
            ServerSocket ss = new ServerSocket(9999);
            while (true){
               Socket s = ss.accept();
               ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
               User u = (User) ois.readObject();
               System.out.println("账号： "+u.getUserId()+" 密码 ："+u.getPasswd());
               //测试登录
                Message m = new Message();
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
               // System.out.println(m.getSender()+"已经连上");
               if(u.getUserId().equals("22") || u.getUserId().equals("33") || u.getUserId().equals("44"))
               { //开启一个新的线程连接
                   m.setMesType(MessageType.MESSAGE_SUCCEED);
                   oos.writeObject(m);
                   ServerConClientThread scct = new ServerConClientThread(s);
                   ManageClientThread.addClientThread(u.getUserId(),scct);
                   scct.start();
                   System.out.println("测试是否开始下一个： 用户: "+u.getUserId());
                   scct.notifyAllOtherFriends(u.getUserId());

               }
               else {
                   m.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                   oos.writeObject(m);
                   s.close();
               }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        QQserver qq = new QQserver();
    }
}
