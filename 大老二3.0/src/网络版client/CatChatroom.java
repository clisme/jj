package 网络版client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

import 大佬二进行2.Main;
import 网络版function.CatBean;

public class CatChatroom {

	private static final long serialVersionUID = 6129126482250125466L;

	private static Socket clientSocket;
	private static ObjectOutputStream oos;
	private static ObjectInputStream ois;
	private static String u_name;
	private static ArrayList onlines;
	private static Player Myplayer;

	/**
	 * Create the frame.
	 */

	public CatChatroom(String u_name, Socket client, Player player) {
		// 赋值
		u_name = u_name;
		clientSocket = client;
		Myplayer = player;
		onlines = new ArrayList();

		try {
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			// 记录上线客户的信息在catbean中，并发送给服务器
			CatBean bean = new CatBean();
			bean.setType(2);
			bean.setName(u_name);
			oos.writeObject(bean);
			oos.flush();

			// 启动客户接收线程
			new ClientInputThread().start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	class ClientInputThread extends Thread {

		@Override
		public void run() {
			try {
				
				// 不停的从服务器接收信息
				while (true) {
					ois = new ObjectInputStream(clientSocket.getInputStream());
					final CatBean bean = (CatBean) ois.readObject();
					
					switch (bean.getType()) {
					case 3: {
						// 实现给玩家更新头像。
						if (bean.getId() % 3 == 2) {
							Myplayer.getWidowsMain().face0.setVisible(true);
							Myplayer.getWidowsMain().face1.setVisible(true);
						}
						if (bean.getId() % 3 == 0) {
							Myplayer.getWidowsMain().face0.setVisible(true);
							Myplayer.getWidowsMain().face1.setVisible(true);
							Myplayer.getWidowsMain().face2.setVisible(true);
							// 表明人数已凑够，可已开始了。
							// 通知所有的玩家不用等了。组桌已完成。
							//上面是三个玩家接收的，所以下面被执行了三次。
							bean.setType(4);// 去得到牌，和去掉等待图片。
							bean.setId(Myplayer.getId());//用于给服务器便于发送给哪个玩家。
							sendMessage(bean);

						}
						break;
					}
					case 4: {
						System.out.println("client   1"+Myplayer.getId());
							//去掉组桌图片。
							Myplayer.getWidowsMain().text.setVisible(false);
							//玩家得到牌。	
							Myplayer.setCards(bean.getCards());
							System.out.println(Myplayer.getCards());
//						}

						break;
					}
					case 2: { // 开启玩家界面，然后给玩家设置编号，窗体，和头标。。
						Myplayer.setId(bean.getId());
						Main wm = new Main();
						Myplayer.setWidowsMain(wm);
						Myplayer.getWidowsMain().setTitle(Myplayer.toString());
						Myplayer.getWidowsMain().setVisible(true);
						if (bean.getId() % 3 != 1) {// 如果玩家不是第一个。
							bean.setType(3);// 给玩家更新头像。
							
								sendMessage(bean);
							
							
						}

						break;
					}

					default: {
						break;
					}
					}

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (clientSocket != null) {
					try {
						clientSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.exit(0);
			}
			}
	}

	
	
	private synchronized void  sendMessage(CatBean clientBean) {
		try {
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			oos.writeObject(clientBean);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
