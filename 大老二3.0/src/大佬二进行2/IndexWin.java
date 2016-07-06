package ���ж�����2;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

public class IndexWin extends JFrame {
	/**
	 * ѡʹ����
	 */
	
	public static  int flag = 0;//��������������ģʽ�����ǵ���ģʽ��
	private static final long serialVersionUID = 1L;

	public IndexWin() {
		init();
	}
	//����ĳ�ʼ����
	private void init() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(650, 520);
		setContentPane(createPanel());
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension d = toolkit.getScreenSize();
		setLocation((d.width - getWidth()) / 2, (d.height - getHeight()) / 2);
	}

	private Container createPanel() {

		JLayeredPane main = new JLayeredPane();
		main.setVisible(true);
		//����ͼƬ
		ImageIcon background = new ImageIcon(
				IndexWin.class.getResource("/plantvsplant/index/b.jpg"));
		JLabel lbl = new JLabel(background);
		//����λ��
		lbl.setBounds(0, 0, background.getIconWidth(),
				background.getIconHeight());
		main.add(lbl, 20);
		//����ģʽͼƬ
		final ImageIcon img1 = new ImageIcon(
				IndexWin.class
						.getResource("/plantvsplant/index/SelectorScreen_Vasebreaker_button.png"));
		//��ʼ��ϷͼƬ
		final ImageIcon img11 = new ImageIcon(
				IndexWin.class
						.getResource("/plantvsplant/index/SelectorScreen_StartAdventure_Highlight.png"));
		//Ц������ͼƬ
		ImageIcon img2 = new ImageIcon(
				IndexWin.class.getResource("/plantvsplant/index/role.png"));
		//����ģʽͼƬ
		final ImageIcon img3 = new ImageIcon(
				IndexWin.class
						.getResource("/plantvsplant/index/SelectorScreen_Challenges_button.png"));
		final JLabel lbl1 = new JLabel(img1);
		lbl1.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				//�������֪ͨ���̡߳���ʼ���ƵĽ��档
				setVisible(false);
				synchronized ("��") {
					flag = 1;
					"��".notify();
				}
			}

			public void mouseEntered(MouseEvent e) {
				lbl1.setIcon(img11);

			}

			public void mouseExited(MouseEvent e) {
				lbl1.setIcon(img1);

			}
		});
		JLabel lbl2 = new JLabel(img2);
		final JLabel lbl3 = new JLabel(img3);

		lbl3.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {

				setVisible(false);
				synchronized ("��") {
					flag = 2;
					"��".notify();}
			}

			public void mouseEntered(MouseEvent e) {
				lbl3.setIcon(img11);

			}

			public void mouseExited(MouseEvent e) {
				lbl3.setIcon(img3);

			}
		});
		//����λ��
		lbl1.setBounds(20, 50, img1.getIconWidth(), img1.getIconHeight());
		lbl2.setBounds(330, 3, img2.getIconWidth(), img2.getIconHeight());
		lbl3.setBounds(20, 160, img3.getIconWidth(), img3.getIconHeight());
		main.add(lbl1, 0);
		main.add(lbl2, 0);
		main.add(lbl3, 0);
		//�������˳���ͼƬ��
		final ImageIcon help1 = new ImageIcon(
				IndexWin.class
						.getResource("/plantvsplant/index/SelectorScreen_Help1.png"));
		final ImageIcon quit1 = new ImageIcon(
				IndexWin.class
						.getResource("/plantvsplant/index/SelectorScreen_Quit1.png"));
		final ImageIcon help2 = new ImageIcon(
				IndexWin.class
						.getResource("/plantvsplant/index/SelectorScreen_Help2.png"));
		final ImageIcon quit2 = new ImageIcon(
				IndexWin.class
						.getResource("/plantvsplant/index/SelectorScreen_Quit2.png"));
		final JLabel help = new JLabel(help1);
		final JLabel quit = new JLabel(quit1);
		help.setBounds(50, 320, help1.getIconWidth(), help1.getIconHeight());
		quit.setBounds(200, 350, quit1.getIconWidth(), quit1.getIconHeight());
		help.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				//��ӹ���ͼƬ
				final ImageIcon ���� = new ImageIcon(IndexWin.class
						.getResource("/plantvsplant/index/11.png"));
				JOptionPane.showMessageDialog(null, null, "����",
						JOptionPane.NO_OPTION, ����);
			}

			public void mouseEntered(MouseEvent e) {
				help.setIcon(help2);

			}

			public void mouseExited(MouseEvent e) {
				help.setIcon(help1);

			}
		});
		quit.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				System.exit(0);

			}

			public void mouseEntered(MouseEvent e) {
				quit.setIcon(quit2);

			}

			public void mouseExited(MouseEvent e) {
				quit.setIcon(quit1);

			}
		});
		main.add(help, 0);
		main.add(quit, 0);
		return main;
	}

	//���ڽ�����ʾ���
	public void ToShow() {
		setVisible(true);

	}

	public static void main(String[] args) {
		IndexWin test = new IndexWin();
		test.ToShow();
		
		Music music = new Music("./sounds/wuniu.mp3",true);
		music.start();

		

		while (true) {
			synchronized ("��") {
				try {
					"��".wait();
					
					if(flag == 1)
					{
						new Main(test);
						System.out.println("���ǵ���ģʽ");
					}
					
					if(flag == 2)
					{
						new Main(test);
						System.out.println("��������ģʽ");
					}
				} catch (InterruptedException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			}
		}

	}

}
