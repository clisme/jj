package ���ж�����2;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
/*
 * �ӱ��̡߳�
 */
public class ClockThread extends Thread{
	Main main;//�ӱ�Ҫ��ʾ�Ľ��档
	public ClockThread(Main main)
	{
		this.main = main;
	}

	/*
	 * ʵ��˼·��ÿһ�룬�ڹ�������ʾһ������ͼƬ��Ȼ��ɾ�����������µ�����ͼƬ��Ȼ����¡�
	 */
	@Override
	public void run() {
		
		ImageIcon[] js = new ImageIcon[10];
		ImageIcon background = new ImageIcon(
				ClockThread.class.
				getResource(
			"/Times/ClockPlate.png"));
				JLabel lbl = new JLabel(background);
				lbl.setBounds(380, 250, background.getIconWidth(),
						background.getIconHeight());
			
				
				while(true){
					for (int i = 0; i <= 9; i++) {
						//repaint��������¡�
						main.lbl.repaint();
						lbl.repaint();
						js[i] = new ImageIcon(
								ClockThread.class.
								getResource("/Times/blue" + i + ".png"));
						JLabel lb2 = new JLabel(js[i]);
						lb2.setBounds(380+24, 250+30, js[i].getIconWidth(),
								js[i].getIconHeight());
						main.lbl.add(lb2);
						main.lbl.add(lbl);
						main.lbl.repaint();
						main.lbl.repaint();
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO �Զ����ɵ� catch ��
							e.printStackTrace();
						}
						main.lbl.remove(lb2);

						main.lbl.repaint();
						lbl.repaint();

						
					}
					main.setVisible(true);

				}

	}
	
}




