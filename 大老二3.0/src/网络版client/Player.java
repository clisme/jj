package �����client;

import java.util.ArrayList;

import ���ж�����2.Main;
import �����server.Card;

public class Player {
	
	private int id = 0;
	private ArrayList<Card> cards = new ArrayList<Card>();
	private Main widowsMain;
	
	
	
	
	
	
	
	
	
	
	@Override
	public String toString() {
		return "����" + id + "�����";
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
