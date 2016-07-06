package ���ж�����2;

import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Main extends JFrame implements ActionListener {

	public Container container = null;// ��������

	JLabel lbl;// ���ڴ�ű���ͼƬ���Ժ����е�ͼƬ�����������֮�ϵ��ӡ�
	public JLabel face0, face1, face2;
	// ����ͼƬ��塣
	public JLabel text;
	public JButton kaishi;
	JMenuItem start, exit, about;// ����˵���ť

	public Main() {
		Init();// ��ʼ��
		SetMenu();// �����˵� ��ť(��÷����������,��ʱ��)
		this.setVisible(false);
	}

	// ��ʼ������
	public void Init() {

		this.setTitle("ֽ����Ϸ֮---JJ ���ж�");
		this.setSize(830, 620);
		setResizable(false);
		setLocationRelativeTo(getOwner()); // ��Ļ����
		container = this.getContentPane();
		container.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ����ͼƬ
		ImageIcon background = new ImageIcon(
				Main.class.getResource("/plantvsplant/index/89.jpg"));
		lbl = new JLabel(background);

		// ���ñ���ͼƬ
		lbl.setBounds(0, 0, background.getIconWidth(),
				background.getIconHeight());
		container.add(lbl);

		// ������ҵ�����ͼƬ
		face0 = new JLabel(new ImageIcon(
				Main.class.getResource("/plantvsplant/index/face0.gif")));
		face0.setBounds(0, 50, 75, 75);
		face0.setVisible(false);
		lbl.add(face0);

		face1 = new JLabel(new ImageIcon(
				Main.class.getResource("/plantvsplant/index/face1.gif")));
		face1.setBounds(0, 450, 75, 75);
		// face1.setVisible(false);
		lbl.add(face1);

		face2 = new JLabel(new ImageIcon(
				Main.class.getResource("/plantvsplant/index/face2.gif")));
		face2.setBounds(679, 50, 75, 75);
		face2.setVisible(false);
		lbl.add(face2);

		text = new JLabel(new ImageIcon(
				Main.class.getResource("/plantvsplant/index/text.png")));
		text.setBounds(250, 300, 315, 41);
		text.setVisible(true);
		lbl.add(text);
		
		

	}

	// �����˵� ���ܰ�ť
	public void SetMenu() {
		JMenuBar jMenuBar = new JMenuBar();
		JMenu game = new JMenu("��Ϸ");
		JMenu help = new JMenu("����");

		start = new JMenuItem("����Ϸ");
		exit = new JMenuItem("�˳�");
		about = new JMenuItem("����");

		game.add(start);
		game.add(exit);
		help.add(about);

		jMenuBar.add(game);
		jMenuBar.add(help);
		this.setJMenuBar(jMenuBar);
		
//		//��ʼ��ť
//		kaishi = new JButton("��ʼ");
//		kaishi.setBounds(650, 450, 60, 20);
//		lbl.add(kaishi);
//		kaishi.addActionListener(this);
//		kaishi.setVisible(false);

	}

	public static void main(String[] args) {
		new Main();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
//		if(e.getSource() == kaishi)
//		{
//			kaishi.setVisible(false);
//			//ʵ�����緢�ơ�
//			System.out.println("����һ��ʼ");
//		}
		
	}

}
