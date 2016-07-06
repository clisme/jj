package 网络版server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import 网络版function.CatBean;
import 网络版function.ClientBean;

public class CatServer {
	private static ServerSocket ss;

	// 用于存放在线用户
	public static ArrayList<ClientBean> onlines;
	ArrayList<Card> player1Cards,player2Cards,player3Cards;
	static {
		try {
			ss = new ServerSocket(5000);
			onlines = new ArrayList<ClientBean>();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	class CatClientThread extends Thread {
		private Socket socket;
		private CatBean bean;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;

		public CatClientThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				// 不停的从客户端接收信息
				while (true) {
					// 读取从客户端接收到的catbean信息
					ois = new ObjectInputStream(socket.getInputStream());
					bean = (CatBean) ois.readObject();

					// 分析catbean中，type是那样一种类型
					switch (bean.getType()) {

					case 0: { // 上线
						// 记录上线客户的用户名和端口在clientbean中
						ClientBean cbean = new ClientBean();
						cbean.setName(bean.getName());
						cbean.setSocket(socket);
						// 添加在线用户
						onlines.add(cbean);
						// 创建服务器的catbean，并发送给客户端
						CatBean serverBean = new CatBean();
						serverBean.setType(0);
						// 通知所有客户有人上线
						// 客户昵称
						sendAll(serverBean);
						break;
					}
					case -1: { // 下线
						// 创建服务器的catbean，并发送给客户端
						CatBean serverBean = new CatBean();
						serverBean.setType(-1);

						try {
							oos = new ObjectOutputStream(
									socket.getOutputStream());
							oos.writeObject(serverBean);
							oos.flush();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						onlines.remove(bean.getId());

						// 向剩下的在线用户发送有人离开的通知
						CatBean serverBean2 = new CatBean();
						serverBean2.setType(0);

						sendAll(serverBean2);
						return;
					}
					case 3: { //计算出要更新的玩家，是那些。
						int chu = bean.getId()/3;
						ArrayList<ClientBean> clients = new ArrayList<ClientBean>();
						if (bean.getId() % 3 == 2) {
							
							int p1 = chu*3;
							int p2 = chu*3+1;
							clients .add(onlines.get(p1));
							clients.add(onlines.get(p2));
						}
						
						if(bean.getId()%3 == 0)
						{
							int p1 = chu*3-3;
							int p2 = chu*3-2;
							int p3 = chu*3-1;
							clients.add(onlines.get(p1));
							clients.add(onlines.get(p2));
							clients.add(onlines.get(p3));
						}
						// 创建服务器的catbean，并发送给客户端
						bean.setType(3);
						// 向选中的客户发送数据
						sendMessage(clients,bean);
						break;
					}
					case 2: { // 给登陆的用户编号。

						/*
						 * 下面是记录登陆用户的信息。
						 */
						ClientBean cbean = new ClientBean();
						cbean.setName(bean.getName());
						cbean.setSocket(socket);
						// 添加在线用户
						onlines.add(cbean);

						/*
						 * 这才是对信息处理。的模块。
						 */

						// 创建服务器的catbean，并发送给客户端
						CatBean serverBean = new CatBean();
						serverBean.setType(2);
						index++;
						serverBean.setId(index);

						/*
						 * 下面是把信息，发送。
						 */

						ArrayList<ClientBean> clients = new ArrayList<ClientBean>();

						clients.add(onlines.get(index - 1));

						sendMessage(clients,serverBean);
						
						//得到牌。当够三个人时，生成牌。
						if(index %3 == 0)
						{
							发牌生成牌.CardInit();
							player1Cards = 发牌生成牌.playerList[0];
							player2Cards = 发牌生成牌.playerList[1];
							player3Cards = 发牌生成牌.playerList[2];
						}
						break;
					}
					case 4: { //会连续被执行三次。每次发送的是一个玩家。
						      // 每个玩家根据自己的id号，得到对应的牌。
						
						ArrayList<ClientBean> clients = new ArrayList<ClientBean>();
						int playerid = bean.getId();//因为传过来的就是玩家Id;
					
						clients.add(onlines.get(playerid - 1));//取出要发送的玩家。
						
						CatBean bean1 = new CatBean();
						if(playerid%3 == 1)
						{
							bean1.setCards(player1Cards);
						}
						if(playerid%3 == 2)
						{
							bean1.setCards(player2Cards);
						}
						if(playerid%3 == 0)
						{
							bean1.setCards(player3Cards);
						}
						
						bean1.setType(4);
						sendMessage(clients,bean1);
						
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
				close();
			}
		}

		// 向选中的用户发送数据
		public void sendMessage(ArrayList<ClientBean> clients,CatBean serverBean) {
			Iterator<ClientBean> it = clients.iterator();
			ObjectOutputStream oos;
			while (it.hasNext()) {
				Socket c = it.next().getSocket();
				try {
					oos = new ObjectOutputStream(c.getOutputStream());
					oos.writeObject(serverBean);
					oos.flush();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		// 向所有的用户发送数据
		public void sendAll(CatBean serverBean) {
			Iterator<ClientBean> it = onlines.iterator();
			ObjectOutputStream oos;
			while (it.hasNext()) {
				Socket c = it.next().getSocket();
				try {
					oos = new ObjectOutputStream(c.getOutputStream());
					oos.writeObject(serverBean);
					oos.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		private void close() {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private int index = 0;// 用于给客户编号。

	public void start() {
		try {
			while (true) {
				Socket socket = ss.accept();

				new CatClientThread(socket).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new CatServer().start();
	}

}
