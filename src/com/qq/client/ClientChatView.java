package com.qq.client;


import com.qq.common.Message;
import  com.qq.common.MessageType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;


public class ClientChatView  extends JFrame  implements ActionListener{

    JTextArea jta;
    JTextField jtf;
    JButton jb;
    JPanel jp;
    JScrollPane jsp;

    private String ownerId;
    private String friendId;

    public String getOwnerId(){
        return ownerId;
    }

    public void setOwnerId(String ownerId){
        this.ownerId = ownerId;
    }

    public String getFriendId(){
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public void showMessage(Message m){
        String info = m.getSender()+"对"+m.getGetter()+"说："+m.getContent()+"\r\n";
        jta.append(info);
    }

    public ClientChatView(String ownerId,String friendId){
        this.ownerId = ownerId;
        this.friendId = friendId;
        jta = new JTextArea();
        jtf = new JTextField(20);
        jsp = new JScrollPane(jta);
        jb  = new JButton("发送");
        jp  = new JPanel();
        jb.addActionListener(this);

        jp.add(jtf);
        jp.add(jb);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                ManageQQChat.removeQQChat(ownerId+" "+friendId);
            }
        });

        add(jsp,"Center");
        add(jp,"South");
        setTitle(this.getOwnerId()+"和"+this.getFriendId()+"聊天");
        setSize(400,300);
        setLocationRelativeTo(null);
        setVisible(true);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == jb){
            Message m = new Message();
            m.setSender(this.ownerId);
            m.setGetter(this.friendId);
            m.setContent(jtf.getText());
            m.setMesType(MessageType.MESSAGE_COMM);
            m.setSendTime(new Date().toString());

            String info = m.getSender()+"对"+m.getGetter()+"说："+m.getContent()+"\r\n";
            jta.append(info);
            jtf.setText("");

            try{
                ObjectOutputStream oos = new ObjectOutputStream(ManageClientConServerThread.getClientServerThread(this.ownerId).getS().getOutputStream());
                oos.writeObject(m);
            } catch (IOException e1){
                e1.printStackTrace();
            }


        }
    }
    //public static void main(String[] args){

      //  ClientChatView c = new ClientChatView("33","22");
    //}

}
