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
	// 背景图片
	ImageIcon background = new ImageIcon(
			Main.class.getResource("/plantvsplant/index/89.jpg"));
	JLabel lbl = new JLabel(background);

	JMenuItem start, exit, about;// 定义菜单按钮
	JButton landlord[] = new JButton[2];// 抢梅花三按钮
	JButton publishCard[] = new JButton[2];// 出牌按钮
	JButton sort;// 纸牌排序按钮
	int m3Flag;//梅花三标志
	int turn;// 用于控制轮转
	List<Card> currentList[] = new ArrayList[3]; // 当前的出牌，出的牌放在这里面。
	List<Card> playerList[] = new ArrayList[3]; // 定义3个玩家表
	List<Card> lordList;// 梅花牌存放处。
	Card card[] = new Card[54];// 定义52张牌。
	JTextField time[] = new JTextField[3]; // 计时器
	Time t; // 定时器（线程）
	ClockThread clock;// 钟表线程
	boolean nextPlayer = false; // 转换角色

	IndexWin test;// 第一个界面

	public Main(IndexWin test) {
		this.test = test;
		Init();// 初始化
		SetMenu();// 创建菜单 按钮(抢梅花三，发牌,计时器)
		this.setVisible(true);
		CardInit();// 发牌
		getLord(); // 发完牌开始抢梅花三
		time[1].setVisible(true);
		// 线程安全性,把非主线程的UI控制放到里面
		SwingUtilities.invokeLater(new NewTimer(this, 10));
		new ClockThread(this).start();// 开始钟表线程
	}

	// 抢梅花三按钮。
	public void getLord() {
		for (int i = 0; i < 2; i++)
			landlord[i].setVisible(true);
	}

	// 初始化牌
	// 发牌洗牌
	public void CardInit() {

		int count = 1;
		// 初始化牌 i为花色， j 为数值。
		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j <= 13; j++) {
				card[count] = new Card(this, i + "-" + j, false);
				card[count].setLocation(350, 50);
				lbl.add(card[count]);
				count++;

			}
		}

		// 打乱顺序

		for (int i = 0; i < 100; i++) {
			Random random = new Random();
			int a = random.nextInt(52) + 1;
			int b = random.nextInt(52) + 1;
			Card k = card[a];
			card[a] = card[b];
			card[b] = k;
		}
		// 开始发牌
		for (int i = 0; i < 3; i++)
			playerList[i] = new ArrayList<Card>(); // 玩家牌
		lordList = new ArrayList<Card>();// 梅花三一张。
		int t = 0;// 用于发牌确定玩家。
		for (int i = 1; i <= 52; i++) {
			// 找到梅花3
			int k = 0;// 存放梅花三的下标。
			Card carda = new Card(this, 4 + "-" + 3, false);

			if (carda.name.equals(card[i].name)) {
				k = i;
				Common.move(card[52], card[52].getLocation(),
						new Point(350, 50));
				lordList.add(card[i]);
			}

			if (i != k) {
				//(t++) % 3，用于找到三个玩家。
				switch ((t++) % 3) {
				case 0:
					// 左边玩家
					Common.move(card[i], card[i].getLocation(), new Point(150,
							44 + i * 5));
					playerList[0].add(card[i]);
					break;
				case 1:
					// 我
					Common.move(card[i], card[i].getLocation(), new Point(
							190 + i * 7, 450));
					playerList[1].add(card[i]);
					card[i].turnFront(); // 显示正面
					break;
				case 2:
					// 右边玩家
					Common.move(card[i], card[i].getLocation(), new Point(600,
							44 + i * 5));
					playerList[2].add(card[i]);
					break;
				}
				//将指定组件移动到容器中指定的 z 顺序索引。用于绘制的顺序。
				lbl.setComponentZOrder(card[i], 0);
			}
		}
		// 发完牌排序，从大到小
		for (int i = 0; i < 3; i++) {
			Common.order(playerList[i]);
			Common.rePosition(this, playerList[i], i);// 重新定位
		}

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

		//设置背景图片
		lbl.setBounds(0, 0, background.getIconWidth(),
				background.getIconHeight());
		container.add(lbl);

		//三个玩家的脸的图片
		JLabel face0 = new JLabel(new ImageIcon(
				Main.class.getResource("/plantvsplant/index/face0.gif")));
		face0.setBounds(0, 50, 75, 75);
		lbl.add(face0);

		JLabel face1 = new JLabel(new ImageIcon(
				Main.class.getResource("/plantvsplant/index/face1.gif")));
		face1.setBounds(0, 450, 75, 75);
		lbl.add(face1);

		JLabel face2 = new JLabel(new ImageIcon(
				Main.class.getResource("/plantvsplant/index/face2.gif")));
		face2.setBounds(679, 50, 75, 75);
		lbl.add(face2);

		//返回上一页的门图片。
		final ImageIcon door1 = new ImageIcon(
				Main.class.getResource("/plantvsplant/index/door1.png"));
		final ImageIcon door2 = new ImageIcon(
				Main.class.getResource("/plantvsplant/index/door2.png"));

		final JLabel door = new JLabel(door1);
		door.setBounds(720, 470, 75, 75);
		lbl.add(door);
		door.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				test.ToShow();
			}

			public void mouseEntered(MouseEvent e) {
				door.setIcon(door2);

			}

			public void mouseExited(MouseEvent e) {
				door.setIcon(door1);

			}
		});

	}

	// 创建菜单 功能按钮
	public void SetMenu() {
		JMenuBar jMenuBar = new JMenuBar();
		JMenu game = new JMenu("游戏");
		JMenu help = new JMenu("帮助");

		start = new JMenuItem("新游戏");
		exit = new JMenuItem("退出");
		about = new JMenuItem("关于");

		start.addActionListener(this);
		exit.addActionListener(this);
		about.addActionListener(this);

		game.add(start);
		game.add(exit);
		help.add(about);

		jMenuBar.add(game);
		jMenuBar.add(help);
		this.setJMenuBar(jMenuBar);

		landlord[0] = new JButton("抢梅花三");
		landlord[1] = new JButton("不     抢");
		sort = new JButton("排序");
		sort.setBounds(650, 450, 60, 20);
		lbl.add(sort);
		sort.addActionListener(this);
		sort.setVisible(true);
		//出牌图片
		final ImageIcon help1 = new ImageIcon(
				Main.class.getResource("/plantvsplant/index/Ok.gif"));
		publishCard[0] = new JButton(help1);
		
		publishCard[1] = new JButton("不要");
		for (int i = 0; i < 2; i++) {
			publishCard[i].setBounds(320 + i * 100, 400, 60, 20);
			landlord[i].setBounds(320 + i * 100, 400, 75, 20);
			lbl.add(landlord[i]);
			landlord[i].addActionListener(this);
			landlord[i].setVisible(false);
			lbl.add(publishCard[i]);
			publishCard[i].setVisible(false);
			publishCard[i].addActionListener(this);
		}
		for (int i = 0; i < 3; i++) {
			time[i] = new JTextField("倒计时:");
			time[i].setVisible(false);
			lbl.add(time[i]);
		}
		time[0].setBounds(250, 230, 60, 20);
		time[1].setBounds(374, 360, 60, 20);
		time[2].setBounds(515, 230, 60, 20);

		for (int i = 0; i < 3; i++) {
			currentList[i] = new ArrayList<Card>();
		}
	}

	boolean sortflag = false;//用于控制排序。

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == exit) {
			this.dispose();
		}
		
		if (e.getSource() == about) {
			
		}
		//排序逻辑的实现
		if (e.getSource() == sort) {

			if (sortflag) {
				sortflag = false;
				Common.order花色(playerList[1]);
				Common.rePosition(this, playerList[1], 1);// 重新定位
			} else {
				sortflag = true;
				Common.order(playerList[1]);
				Common.rePosition(this, playerList[1], 1);// 重新定位
			}
		}
		//新游戏，通知主线程，重新开始。
		if (e.getSource() == start) {
			this.setVisible(false);
			synchronized ("锁") {
				"锁".notify();
			}
		}
		if (e.getSource() == landlord[0]) {

			time[1].setText("抢梅花三");
			t.isRun = false; // 时钟终结
		}
		if (e.getSource() == landlord[1]) {
			time[1].setText("不抢");
			t.isRun = false; // 时钟终结
		}
		// 如果是不要
		if (e.getSource() == publishCard[1]) {
			this.nextPlayer = true;
			currentList[1].clear();
			time[1].setText("不要");
		}
		// 如果是出牌按钮
		if (e.getSource() == publishCard[0]) {
			List<Card> c = new ArrayList<Card>();
			// 点选出牌
			for (int i = 0; i < playerList[1].size(); i++) {
				Card card = playerList[1].get(i);
				if (card.clicked) {
					c.add(card);
				}
			}
			boolean flag = false;//用于控制是否可以出牌。

			// 如果我主动出牌
			if (time[0].getText().equals("不要")
					&& time[2].getText().equals("不要")) {

				if (Common.jugdeType(c) != CardType.c0)
					flag = true;// 表示可以出牌
			}// 如果我跟牌
			else {

				flag = Common.checkCards(c, currentList);
			}
			// 判断是否符合出牌
			if (flag == true) {
				currentList[1] = c;
				playerList[1].removeAll(currentList[1]);// 移除走的牌
				// 定位出牌
				Point point = new Point();
				point.x = (770 / 2) - (currentList[1].size() + 1) * 15 / 2;
				point.y = 290;
				for (int i = 0, len = currentList[1].size(); i < len; i++) {
					Card card = currentList[1].get(i);
					Common.move(card, card.getLocation(), point);
					point.x += 15;
				}
				// 抽完牌后重新整理牌
				Common.rePosition(this, playerList[1], 1);
				time[1].setVisible(false);
				this.nextPlayer = true;
			}

		}
	}
}

class NewTimer implements Runnable {

	Main main;
	int i;

	public NewTimer(Main m, int i) {
		this.main = m;
		this.i = i;
	}

	@Override
	public void run() {

		main.t = new Time(main, 10);// 从10开始倒计时
		main.t.start();

	}

}
