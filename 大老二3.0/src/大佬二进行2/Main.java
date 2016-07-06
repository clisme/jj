package 大佬二进行2;

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

	public Container container = null;// 定义容器

	JLabel lbl;// 用于存放背景图片。以后所有的图片，都在这个里之上叠加。
	public JLabel face0, face1, face2;
	// 组桌图片面板。
	public JLabel text;
	public JButton kaishi;
	JMenuItem start, exit, about;// 定义菜单按钮

	public Main() {
		Init();// 初始化
		SetMenu();// 创建菜单 按钮(抢梅花三，发牌,计时器)
		this.setVisible(false);
	}

	// 初始化窗体
	public void Init() {

		this.setTitle("纸牌游戏之---JJ 大佬二");
		this.setSize(830, 620);
		setResizable(false);
		setLocationRelativeTo(getOwner()); // 屏幕居中
		container = this.getContentPane();
		container.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 背景图片
		ImageIcon background = new ImageIcon(
				Main.class.getResource("/plantvsplant/index/89.jpg"));
		lbl = new JLabel(background);

		// 设置背景图片
		lbl.setBounds(0, 0, background.getIconWidth(),
				background.getIconHeight());
		container.add(lbl);

		// 三个玩家的脸的图片
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

	// 创建菜单 功能按钮
	public void SetMenu() {
		JMenuBar jMenuBar = new JMenuBar();
		JMenu game = new JMenu("游戏");
		JMenu help = new JMenu("帮助");

		start = new JMenuItem("新游戏");
		exit = new JMenuItem("退出");
		about = new JMenuItem("关于");

		game.add(start);
		game.add(exit);
		help.add(about);

		jMenuBar.add(game);
		jMenuBar.add(help);
		this.setJMenuBar(jMenuBar);
		
//		//开始按钮
//		kaishi = new JButton("开始");
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
//			//实现网络发牌。
//			System.out.println("发牌一开始");
//		}
		
	}

}
