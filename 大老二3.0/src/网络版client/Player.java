package 网络版client;

import java.util.ArrayList;

import 大佬二进行2.Main;
import 网络版server.Card;

public class Player {
	
	private int id = 0;
	private ArrayList<Card> cards = new ArrayList<Card>();
	private Main widowsMain;
	
	
	
	
	
	
	
	
	
	
	@Override
	public String toString() {
		return "我是" + id + "号玩家";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<Card> getCards() {
		return cards;
	}
	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}
	public Main getWidowsMain() {
		return widowsMain;
	}
	public void setWidowsMain(Main widowsMain) {
		this.widowsMain = widowsMain;
		
	}
	
	
	
	
	public static void main(String[] args) {
		
	}
	
}
