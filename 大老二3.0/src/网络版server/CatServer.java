package �����server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import �����function.CatBean;
import �����function.ClientBean;

public class CatServer {
	private static ServerSocket ss;

	// ���ڴ�������û�
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
				// ��ͣ�Ĵӿͻ��˽�����Ϣ
				while (true) {
					// ��ȡ�ӿͻ��˽��յ���catbean��Ϣ
					ois = new ObjectInputStream(socket.getInputStream());
					bean = (CatBean) ois.readObject();

					// ����catbean�У�type������һ������
					switch (bean.getType()) {

					case 0: { // ����
						// ��¼���߿ͻ����û����Ͷ˿���clientbean��
						ClientBean cbean = new ClientBean();
						cbean.setName(bean.getName());
						cbean.setSocket(socket);
						// ��������û�
						onlines.add(cbean);
						// ������������catbean�������͸��ͻ���
						CatBean serverBean = new CatBean();
						serverBean.setType(0);
						// ֪ͨ���пͻ���������
						// �ͻ��ǳ�
						sendAll(serverBean);
						break;
					}
					case -1: { // ����
						// ������������catbean�������͸��ͻ���
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

						// ��ʣ�µ������û����������뿪��֪ͨ
						CatBean serverBean2 = new CatBean();
						serverBean2.setType(0);

						sendAll(serverBean2);
						return;
					}
					case 3: { //�����Ҫ���µ���ң�����Щ��
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
						// ������������catbean�������͸��ͻ���
						bean.setType(3);
						// ��ѡ�еĿͻ���������
						sendMessage(clients,bean);
						break;
					}
					case 2: { // ����½���û���š�

						/*
						 * �����Ǽ�¼��½�û�����Ϣ��
						 */
						ClientBean cbean = new ClientBean();
						cbean.setName(bean.getName());
						cbean.setSocket(socket);
						// ��������û�
						onlines.add(cbean);

						/*
						 * ����Ƕ���Ϣ������ģ�顣
						 */

						// ������������catbean�������͸��ͻ���
						CatBean serverBean = new CatBean();
						serverBean.setType(2);
						index++;
						serverBean.setId(index);

						/*
						 * �����ǰ���Ϣ�����͡�
						 */

						ArrayList<ClientBean> clients = new ArrayList<ClientBean>();

						clients.add(onlines.get(index - 1));

						sendMessage(clients,serverBean);
						
						//�õ��ơ�����������ʱ�������ơ�
						if(index %3 == 0)
						{
							����������.CardInit();
							player1Cards = ����������.playerList[0];
							player2Cards = ����������.playerList[1];
							player3Cards = ����������.playerList[2];
						}
						break;
					}
					case 4: { //��������ִ�����Ρ�ÿ�η��͵���һ����ҡ�
						      // ÿ����Ҹ����Լ���id�ţ��õ���Ӧ���ơ�
						
						ArrayList<ClientBean> clients = new ArrayList<ClientBean>();
						int playerid = bean.getId();//��Ϊ�������ľ������Id;
					
						clients.add(onlines.get(playerid - 1));//ȡ��Ҫ���͵���ҡ�
						
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

		// ��ѡ�е��û���������
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

		// �����е��û���������
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

	private int index = 0;// ���ڸ��ͻ���š�

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
