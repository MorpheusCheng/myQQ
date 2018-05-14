package com.qq.server;

import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;

public class QQserver {
    SqlHelper sh = null;
    //关闭资源
    public void close(){
        try{
            if(sh != null){
                sh.close();
            }
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
               //测试非数据库部分
                // if(u.getUserId().equals("22") || u.getUserId().equals("33") || u.getUserId().equals("44"))
                //数据库部分
                sh = new SqlHelper();
                String sql = "select QQPassword from QQUser where QQuserId=?";
                String[] paras = {u.getUserId()};
                ResultSet rs = null;
                String password = null;

                //注册部分
                if (u.getFlag().equals(true))
                {
                    rs= sh.queryExecute(sql,paras);
                    if (rs.next()){//注册失败，名字已被占用
                        m.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                        oos.writeObject(m);
                        s.close();
                    }
                    else {//注册成功
                        //在数据库中增加该用户信息
                        String Insertsql = "insert into QQUser values(?,?)";
                        String[] paras2 = {u.getUserId(),u.getPasswd()};
                        sh.InsertData(Insertsql,paras2);
                        m.setMesType(MessageType.MESSAGE_SUCCEED);
                        oos.writeObject(m);
                        ServerConClientThread scct = new ServerConClientThread(s);
                        ManageClientThread.addClientThread(u.getUserId(),scct);
                        scct.start();
                        System.out.println("注册用户成功，测试是否开始下一个： 用户: "+u.getUserId());
                        scct.notifyAllOtherFriends(u.getUserId());
                    }
                }
                else//登录部分
                {
                    rs = sh.queryExecute(sql,paras);
                    if (rs.next()){
                        password = rs.getString("QQPassword").trim();
                    }

                    if (u.getPasswd().equals(password) )
                    { //登录成功，开启一个新的线程连接
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
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        QQserver qq = new QQserver();
    }
}
