/**
 * 功能：好友界面(包括我的好友，群聊)
 */
package com.qq.client;

import java.awt.*;
import java.awt.event.*;
import java.io.ObjectOutputStream;
import javax.swing.*;

import com.qq.common.Message;
import com.qq.common.MessageType;

public class QQFriendList extends JFrame implements ActionListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7933056442552732399L;

	// 卡片布局
	CardLayout c1;

	// 第一张卡片
	JPanel jphy1, jphy2, jphy3;
	JButton jphy_jb1, jphy_jb2;
	JScrollPane jphy_jsp;

	// 第二张卡片
	JPanel jpmsr1, jpmsr2, jpmsr3;
	JButton jpmsr_jb1, jpmsr_jb2;
	JScrollPane jpmsr_jsp;


	String ownerId;

	JLabel[] jbls1, jbls2;
	// 标记好友是否在线
	Boolean[] jbls1Flag,jbls2Flag;//标记群成员在线情况

	String MultichatID = "qunliao";

	// 更新在线好友
	public void updateFriend(Message m) {
		String onLineFriend[] = m.getContent().split(" ");

		for (int i = 0; i < onLineFriend.length; i++) {

			jbls1[Integer.parseInt(onLineFriend[i]) - 1].setEnabled(true);
			jbls1Flag[Integer.parseInt(onLineFriend[i]) - 1] = true;
			jbls2[Integer.parseInt((onLineFriend[i]))-1].setEnabled(true);
			jbls2Flag[Integer.parseInt(onLineFriend[i])-1] = true;
			System.out.println("已更新 "+ onLineFriend[i] +" 的状态");
		}

	}

	// 构造方法
	public QQFriendList(String ownerId) {
		this.ownerId = ownerId;
		// 处理第一张卡片(好友列表)
		jphy_jb1 = new JButton("我的好友");
		jphy_jb2 = new JButton("群聊");
		jphy_jb2.addActionListener(this);
		jphy1 = new JPanel(new BorderLayout());
		// 假定有50个好友
		jphy2 = new JPanel(new GridLayout(50, 1, 4, 4));

		// 初始化好友
		jbls1 = new JLabel[50];
		jbls1Flag = new Boolean[50];
		for (int i = 0; i < jbls1.length; i++) {
			jbls1[i] = new JLabel(i + 1 + "", new ImageIcon("images/touxiang.png"),
					JLabel.LEFT);
			jbls1[i].setEnabled(false);
			jbls1Flag[i] = false;
			if (jbls1[i].getText().equals(ownerId)) {
				jbls1[i].setEnabled(true);
				jbls1Flag[i] = true;
			}
			jbls1[i].addMouseListener(this);
			// jphy2初始化50个好友
			jphy2.add(jbls1[i]);
		}
		jphy_jsp = new JScrollPane(jphy2);
		jphy3 = new JPanel(new GridLayout(1, 1));

		// 按钮加入jphy3
		jphy3.add(jphy_jb2);

		// 加入jphy1,对jphy1初始化
		jphy1.add(jphy_jb1, "North");
		jphy1.add(jphy_jsp, "Center");
		jphy1.add(jphy3, "South");

		// 处理第二张卡片
		jpmsr_jb1 = new JButton("我的好友");
		jpmsr_jb1.addActionListener(this);
		jpmsr_jb2 = new JButton("群聊");
		jpmsr1 = new JPanel(new BorderLayout());
		// 假定有群聊中有20人
		jpmsr2 = new JPanel(new GridLayout(20, 1, 4, 4));

		// 初始化群聊成员
		jbls2 = new JLabel[20];
		jbls2Flag = new Boolean[20];
		for (int i = 0; i < jbls2.length; i++) {
			jbls2[i] = new JLabel(""+(i + 1), new ImageIcon(
					"images/touxiang.png"), JLabel.LEFT);
			jbls2[i].setEnabled(false);
			jbls2Flag[i] = false;
			if (jbls2[i].getText().equals(ownerId)) {
				jbls2[i].setEnabled(true);
				jbls2Flag[i] = true;
			}
			jbls2[i].addMouseListener(this);
			jpmsr2.add(jbls2[i]);
		}
		jpmsr_jsp = new JScrollPane(jpmsr2);
		// jpmsr2初始化20个群聊成员
		jpmsr3 = new JPanel(new GridLayout(2, 1));

		// 按钮加入jpmsr3
		jpmsr3.add(jpmsr_jb1);
		jpmsr3.add(jpmsr_jb2);

		// 加入jpmsr1,对jpmsr1初始化
		jpmsr1.add(jpmsr3, "North");
		jpmsr1.add(jpmsr_jsp, "Center");



		// 把JFrame设置为CardLayout布局
		c1 = new CardLayout();
		setLayout(c1);
		// 加入JFrame
		add(jphy1, "1");//第一种布局
		add(jpmsr1, "2");//第二种布局

		//设置窗口关闭后给服务器发送离开的信息
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				Message m =new Message();
				m.setMesType(MessageType.MESSAGE_EXIT);
				m.setSender(ownerId);
				try{
					ObjectOutputStream oos = new ObjectOutputStream(ManageClientConServerThread.getClientServerThread(ownerId).getS().getOutputStream());
					oos.writeObject(m);

				}catch (Exception e1){
					e1.printStackTrace();
				}
			}
		});
		// 设置窗体
		setTitle("山寨QQ--" + ownerId);
		setIconImage(new ImageIcon("images/icon.jpg").getImage());
		setSize(250, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	// 测试
	 //public static void main(String[] args) {
	 //QQFriendList qqfl = new QQFriendList("1");
	//
	//}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jphy_jb2 ) {
			// 点击了群聊按钮，显示第二张卡片，并弹出群聊窗口
			c1.show(this.getContentPane(), "2");
			if (ManageMultiChat.getMultiChat(this.ownerId+" "+this.MultichatID) == null){
				MultiChatView multiChat = new MultiChatView(ownerId,MultichatID);
				ManageMultiChat.addMultiChat(this.ownerId+" "+this.MultichatID,multiChat);
			}

		} else if (e.getSource() == jpmsr_jb1 ) {
			// 点击了好友按钮，显示第一张卡片
			c1.show(this.getContentPane(), "1");
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// 相应用户双击的事件，并得到好友的编号
		if (e.getClickCount() == 2) {
			// 得到该好友的编号
			String friendNo = ((JLabel) e.getSource()).getText();
			// 如果聊天的人不在线
			if (!jbls1Flag[Integer.parseInt(friendNo) - 1]) {
				JOptionPane.showMessageDialog(this, "不能和不在线的人聊天");
			} else if (!friendNo.equals(ownerId)
					&& jbls1Flag[Integer.parseInt(friendNo) - 1]) {
				// 如果不是自己并且在线
				ClientChatView qqChat = new ClientChatView(ownerId, friendNo);
				// 把聊天界面加入到管理类
				ManageQQChat.addQQChat(this.ownerId + " " + friendNo, qqChat);
			} else {
				//如果是自己
				JOptionPane.showMessageDialog(this, "不能和自己聊天");
			}

		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		JLabel jl = (JLabel) e.getSource();
		jl.setForeground(Color.red);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		JLabel jl = (JLabel) e.getSource();
		jl.setForeground(Color.black);
	}
}
