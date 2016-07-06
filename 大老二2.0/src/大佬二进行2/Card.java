package 大佬二进行2;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Card extends JLabel implements MouseListener{

	Main main;//Main类的引用
	public String name;//图片url名字
	boolean up;//是否正反面
	boolean canClick=false;//是否可被点击
	boolean clicked=false;//是否点击过
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

	//正面
	public void turnFront() {
		/*
		 * setIcon()是定义JLabel组件要显示的图标。
		 */
		this.setIcon(new ImageIcon(
				Card.class.
				getResource("/images/" + name + ".gif")));
		this.up = true;
	}
	//反面
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
			 * getLocation()获得卡片组件的坐标位置。
			 */
			Point from=this.getLocation();
			int step; //移动的距离
			if(clicked)
				step=-20;
			else {
				step=20;
			}
			/*
			 * 纸牌被点击一次的时候，向前一步，然后设置反向。再点一次就会后退一步。
			 */
			clicked=!clicked; //反向
			//当被选中的时候，向前移动一步/后退一步
			Common.move(this,from,new Point(from.x,from.y-step));
		}
		
		
	}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}

}
