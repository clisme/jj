package �����cat.login;

import java.net.Socket;
import java.util.HashMap;

import �����client.CatChatroom;
import �����client.Player;
import �����function.ClientBean;

public class CatLogin {

	public static HashMap<String, ClientBean> onlines;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		Socket client;
		try {
			client = new Socket("localhost", 5000);
			String u_name = "zhangsan";
			Player player = new Player();
			CatChatroom frame = new CatChatroom(u_name, client,player);

			
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}

		
	}
}