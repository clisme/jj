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
	// ����ͼƬ
	ImageIcon background = new ImageIcon(
			Main.class.getResource("/plantvsplant/index/89.jpg"));
	JLabel lbl = new JLabel(background);

	JMenuItem start, exit, about;// ����˵���ť
	JButton landlord[] = new JButton[2];// ��÷������ť
	JButton publishCard[] = new JButton[2];// ���ư�ť
	JButton sort;// ֽ������ť
	int m3Flag;//÷������־
	int turn;// ���ڿ�����ת
	List<Card> currentList[] = new ArrayList[3]; // ��ǰ�ĳ��ƣ������Ʒ��������档
	List<Card> playerList[] = new ArrayList[3]; // ����3����ұ�
	List<Card> lordList;// ÷���ƴ�Ŵ���
	Card card[] = new Card[54];// ����52���ơ�
	JTextField time[] = new JTextField[3]; // ��ʱ��
	Time t; // ��ʱ�����̣߳�
	ClockThread clock;// �ӱ��߳�
	boolean nextPlayer = false; // ת����ɫ

	IndexWin test;// ��һ������

	public Main(IndexWin test) {
		this.test = test;
		Init();// ��ʼ��
		SetMenu();// �����˵� ��ť(��÷����������,��ʱ��)
		this.setVisible(true);
		CardInit();// ����
		getLord(); // �����ƿ�ʼ��÷����
		time[1].setVisible(true);
		// �̰߳�ȫ��,�ѷ����̵߳�UI���Ʒŵ�����
		SwingUtilities.invokeLater(new NewTimer(this, 10));
		new ClockThread(this).start();// ��ʼ�ӱ��߳�
	}

	// ��÷������ť��
	public void getLord() {
		for (int i = 0; i < 2; i++)
			landlord[i].setVisible(true);
	}

	// ��ʼ����
	// ����ϴ��
	public void CardInit() {

		int count = 1;
		// ��ʼ���� iΪ��ɫ�� j Ϊ��ֵ��
		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j <= 13; j++) {
				card[count] = new Card(this, i + "-" + j, false);
				card[count].setLocation(350, 50);
				lbl.add(card[count]);
				count++;

			}
		}

		// ����˳��

		for (int i = 0; i < 100; i++) {
			Random random = new Random();
			int a = random.nextInt(52) + 1;
			int b = random.nextInt(52) + 1;
			Card k = card[a];
			card[a] = card[b];
			card[b] = k;
		}
		// ��ʼ����
		for (int i = 0; i < 3; i++)
			playerList[i] = new ArrayList<Card>(); // �����
		lordList = new ArrayList<Card>();// ÷����һ�š�
		int t = 0;// ���ڷ���ȷ����ҡ�
		for (int i = 1; i <= 52; i++) {
			// �ҵ�÷��3
			int k = 0;// ���÷�������±ꡣ
			Card carda = new Card(this, 4 + "-" + 3, false);

			if (carda.name.equals(card[i].name)) {
				k = i;
				Common.move(card[52], card[52].getLocation(),
						new Point(350, 50));
				lordList.add(card[i]);
			}

			if (i != k) {
				//(t++) % 3�������ҵ�������ҡ�
				switch ((t++) % 3) {
				case 0:
					// ������
					Common.move(card[i], card[i].getLocation(), new Point(150,
							44 + i * 5));
					playerList[0].add(card[i]);
					break;
				case 1:
					// ��
					Common.move(card[i], card[i].getLocation(), new Point(
							190 + i * 7, 450));
					playerList[1].add(card[i]);
					card[i].turnFront(); // ��ʾ����
					break;
				case 2:
					// �ұ����
					Common.move(card[i], card[i].getLocation(), new Point(600,
							44 + i * 5));
					playerList[2].add(card[i]);
					break;
				}
				//��ָ������ƶ���������ָ���� z ˳�����������ڻ��Ƶ�˳��
				lbl.setComponentZOrder(card[i], 0);
			}
		}
		// ���������򣬴Ӵ�С
		for (int i = 0; i < 3; i++) {
			Common.order(playerList[i]);
			Common.rePosition(this, playerList[i], i);// ���¶�λ
		}

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

		//���ñ���ͼƬ
		lbl.setBounds(0, 0, background.getIconWidth(),
				background.getIconHeight());
		container.add(lbl);

		//������ҵ�����ͼƬ
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

		//������һҳ����ͼƬ��
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

	// �����˵� ���ܰ�ť
	public void SetMenu() {
		JMenuBar jMenuBar = new JMenuBar();
		JMenu game = new JMenu("��Ϸ");
		JMenu help = new JMenu("����");

		start = new JMenuItem("����Ϸ");
		exit = new JMenuItem("�˳�");
		about = new JMenuItem("����");

		start.addActionListener(this);
		exit.addActionListener(this);
		about.addActionListener(this);

		game.add(start);
		game.add(exit);
		help.add(about);

		jMenuBar.add(game);
		jMenuBar.add(help);
		this.setJMenuBar(jMenuBar);

		landlord[0] = new JButton("��÷����");
		landlord[1] = new JButton("��     ��");
		sort = new JButton("����");
		sort.setBounds(650, 450, 60, 20);
		lbl.add(sort);
		sort.addActionListener(this);
		sort.setVisible(true);
		//����ͼƬ
		final ImageIcon help1 = new ImageIcon(
				Main.class.getResource("/plantvsplant/index/Ok.gif"));
		publishCard[0] = new JButton(help1);
		
		publishCard[1] = new JButton("��Ҫ");
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
			time[i] = new JTextField("����ʱ:");
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

	boolean sortflag = false;//���ڿ�������

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == exit) {
			this.dispose();
		}
		
		if (e.getSource() == about) {
			
		}
		//�����߼���ʵ��
		if (e.getSource() == sort) {

			if (sortflag) {
				sortflag = false;
				Common.order��ɫ(playerList[1]);
				Common.rePosition(this, playerList[1], 1);// ���¶�λ
			} else {
				sortflag = true;
				Common.order(playerList[1]);
				Common.rePosition(this, playerList[1], 1);// ���¶�λ
			}
		}
		//����Ϸ��֪ͨ���̣߳����¿�ʼ��
		if (e.getSource() == start) {
			this.setVisible(false);
			synchronized ("��") {
				"��".notify();
			}
		}
		if (e.getSource() == landlord[0]) {

			time[1].setText("��÷����");
			t.isRun = false; // ʱ���ս�
		}
		if (e.getSource() == landlord[1]) {
			time[1].setText("����");
			t.isRun = false; // ʱ���ս�
		}
		// ����ǲ�Ҫ
		if (e.getSource() == publishCard[1]) {
			this.nextPlayer = true;
			currentList[1].clear();
			time[1].setText("��Ҫ");
		}
		// ����ǳ��ư�ť
		if (e.getSource() == publishCard[0]) {
			List<Card> c = new ArrayList<Card>();
			// ��ѡ����
			for (int i = 0; i < playerList[1].size(); i++) {
				Card card = playerList[1].get(i);
				if (card.clicked) {
					c.add(card);
				}
			}
			boolean flag = false;//���ڿ����Ƿ���Գ��ơ�

			// �������������
			if (time[0].getText().equals("��Ҫ")
					&& time[2].getText().equals("��Ҫ")) {

				if (Common.jugdeType(c) != CardType.c0)
					flag = true;// ��ʾ���Գ���
			}// ����Ҹ���
			else {

				flag = Common.checkCards(c, currentList);
			}
			// �ж��Ƿ���ϳ���
			if (flag == true) {
				currentList[1] = c;
				playerList[1].removeAll(currentList[1]);// �Ƴ��ߵ���
				// ��λ����
				Point point = new Point();
				point.x = (770 / 2) - (currentList[1].size() + 1) * 15 / 2;
				point.y = 290;
				for (int i = 0, len = currentList[1].size(); i < len; i++) {
					Card card = currentList[1].get(i);
					Common.move(card, card.getLocation(), point);
					point.x += 15;
				}
				// �����ƺ�����������
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

		main.t = new Time(main, 10);// ��10��ʼ����ʱ
		main.t.start();

	}

}
