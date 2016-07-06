package ���ж�����2;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Card extends JLabel implements MouseListener{

	Main main;//Main�������
	public String name;//ͼƬurl����
	boolean up;//�Ƿ�������
	boolean canClick=false;//�Ƿ�ɱ����
	boolean clicked=false;//�Ƿ�����
	public Card(Main m,String name,boolean up){
		this.main=m;
		this.name=name;
		this.up=up;
	    if(this.up)
	    	this.turnFront();
	    else {
			this.turnRear();
		}
		this.setSize(71, 96);
		this.setVisible(true);
		this.addMouseListener(this);
	}

	//����
	public void turnFront() {
		/*
		 * setIcon()�Ƕ���JLabel���Ҫ��ʾ��ͼ�ꡣ
		 */
		this.setIcon(new ImageIcon(
				Card.class.
				getResource("/images/" + name + ".gif")));
		this.up = true;
	}
	//����
	public void turnRear() {
		this.setIcon(new ImageIcon(
				Card.class.
				getResource("/images/rear.gif")));
		this.up = false;
	}
	@Override
	public void mouseClicked(MouseEvent e) {

		if(canClick)
		{
			/*
			 * getLocation()��ÿ�Ƭ���������λ�á�
			 */
			Point from=this.getLocation();
			int step; //�ƶ��ľ���
			if(clicked)
				step=-20;
			else {
				step=20;
			}
			/*
			 * ֽ�Ʊ����һ�ε�ʱ����ǰһ����Ȼ�����÷����ٵ�һ�ξͻ����һ����
			 */
			clicked=!clicked; //����
			//����ѡ�е�ʱ����ǰ�ƶ�һ��/����һ��
			Common.move(this,from,new Point(from.x,from.y-step));
		}
		
		
	}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}

}
