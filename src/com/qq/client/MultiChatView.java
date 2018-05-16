package com.qq.client;

import com.qq.common.Message;
import com.qq.common.MessageType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectOutputStream;
import java.util.Date;

public class MultiChatView  extends JFrame implements ActionListener {

    //private static final long serialVersionUID = 582207281703443438L;
    JTextArea jta;
    JTextField jtf;
    JButton jb;
    JPanel jp;
    JScrollPane jsp;
    private String ownerID;
    private String MultiChatID;
    public String getOwnerID(){
        return ownerID;
    }
    public String getMultiChatID(){
        return  MultiChatID;
    }
    public void setOwnerID(String ownerID){
        this.ownerID = ownerID;
    }
    public void setMultiChatID(String MultiChatID){
        this.MultiChatID = MultiChatID;
    }

    public void showMessages(Message m){
        String info = m.getSender()+"说："+m.getContent()+"\r\n";
        System.out.println("Multichat:"+info);
        jta.append(info);
    }

    public MultiChatView(String ownerID ,String MultiChatID){
        this.ownerID = ownerID;
        this.MultiChatID = MultiChatID;

        jta = new JTextArea();
        jsp = new JScrollPane(jta);
        jtf = new JTextField(20);
        jb = new JButton("发送");
        jb.addActionListener(this);
        jp = new JPanel();

        jp.add(jtf);
        jp.add(jb);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                ManageMultiChat.removeMultiChat(ownerID+" "+MultiChatID);
            }
        });

        add(jsp,"Center");
        add(jp,"South");
        setTitle(MultiChatID+" ");
        setSize(400,300);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == jb){
            Message m = new Message();
            m.setSender(this.ownerID);
            m.setMultiChat(this.MultiChatID);
            m.setContent(jtf.getText());
            m.setMesType(MessageType.MESSAGE_MULTI);
            m.setSendTime(new Date().toString());
            String info = m.getSender()+"说："+m.getContent()+"\r\n";
            System.out.println(info);

            jta.append(info);
            jtf.setText("");

            try{
                ObjectOutputStream oos = new ObjectOutputStream(ManageClientConServerThread.getClientServerThread(this.ownerID).getS().getOutputStream());
                oos.writeObject(m);
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }
    }
}
