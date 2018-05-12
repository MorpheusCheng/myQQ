package com.qq.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;

import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.common.User;

public class ClientLogin extends JFrame implements ActionListener {
    JPanel jp1,jp2,jp3;
    JLabel jlb1,jlb2;
    JButton jb1,jb2,jb3;
    JTextField jtf;
    JPasswordField jpf;

    public ClientLogin(){
        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();

        jlb1 = new JLabel("用户名");
        jlb2 = new JLabel("密 码");

        jb1 = new JButton("登录");
        jb2 = new JButton("注册");
        jb3 = new JButton("取消");

        jtf = new JTextField(20);
        jpf = new JPasswordField(20);

        jb1.addActionListener(this);
        jb2.addActionListener(this);

        setLayout(new GridLayout(3,1));

        jp1.add(jlb1);
        jp1.add(jtf);
        jp2.add(jlb2);
        jp2.add(jpf);
        jp3.add(jb1);
        jp3.add(jb2);
        jp3.add(jb3);

        add(jp1);
        add(jp2);
        add(jp3);

        setTitle("登录界面");
        setSize(600,300);
        setLocation(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == jb1){
            //登录验证
            QQClientUser qqClientUser = new QQClientUser();
            User u = new User();
            u.setUserId(jtf.getText().trim());
            u.setPasswd(new String(jpf.getPassword()));

            if(qqClientUser.checkUser(u)){
                try{
                    QQFriendList qqlist = new QQFriendList(u.getUserId());
                    ManageQQFriendList.addQQFriendList(u.getUserId(),qqlist);

                    ObjectOutputStream oos = new ObjectOutputStream(ManageClientConServerThread.getClientServerThread(u.getUserId()).getS().getOutputStream());
                    Message m = new Message();
                    m.setMesType(MessageType.MESSAGE_GET_ONLINEFRIEND);
                    m.setSender(u.getUserId());
                    oos.writeObject(m);
                }catch (Exception e1)
                {
                    e1.printStackTrace();
                }
                dispose();
            }

        else{
            JOptionPane.showMessageDialog(this,"用户名或密码错误");
        }
        }
    }

    public static void main(String[] args){
        ClientLogin cl = new ClientLogin();
    }
}
