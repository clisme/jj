package 网络版function;

import java.io.Serializable;
import java.util.ArrayList;

import 网络版server.Card;


/*
 * 存放客户端发来，或服务器端发出的信息。  发送的一种信息的包装。
 */
public class CatBean implements Serializable {
	private int type; // 1私聊 0上下线更新 -1下线请求  
	private int id;//用于给每个客户编号。
	private ArrayList<ClientBean> clients;
	private ArrayList<ClientBean> onlines;
	private ArrayList<Card> cards;
	

	private String u_name;



	

	private int port;


	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	

	

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

	public String getName() {
		return u_name;
	}

	public void setName(String u_name) {
		this.u_name = u_name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<ClientBean> getClients() {
		return clients;
	}

	public void setClients(ArrayList<ClientBean> clients) {
		this.clients = clients;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public ArrayList<ClientBean> getOnlines() {
		return onlines;
	}

	public void setOnlines(ArrayList<ClientBean> onlines) {
		this.onlines = onlines;
	}

	
	
}
