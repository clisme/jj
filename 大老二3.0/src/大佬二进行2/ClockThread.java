package 大佬二进行2;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
/*
 * 钟表线程。
 */
public class ClockThread extends Thread{
	Main main;//钟表要显示的界面。
	public ClockThread(Main main)
	{
		this.main = main;
	}

	/*
	 * 实现思路。每一秒，在工表上显示一个数字图片，然后删掉它，加入新的数字图片，然后更新。
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
						//repaint。界面更新。
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
							// TODO 自动生成的 catch 块
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




