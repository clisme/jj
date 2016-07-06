package �����client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

import ���ж�����2.Main;
import �����function.CatBean;

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
		// ��ֵ
		u_name = u_name;
		clientSocket = client;
		Myplayer = player;
		onlines = new ArrayList();

		try {
			oos = new ObjectOutputStream(clientSocket.getOutputStream());
			// ��¼���߿ͻ�����Ϣ��catbean�У������͸�������
			CatBean bean = new CatBean();
			bean.setType(2);
			bean.setName(u_name);
			oos.writeObject(bean);
			oos.flush();

			// �����ͻ������߳�
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
				
				// ��ͣ�Ĵӷ�����������Ϣ
				while (true) {
					ois = new ObjectInputStream(clientSocket.getInputStream());
					final CatBean bean = (CatBean) ois.readObject();
					
					switch (bean.getType()) {
					case 3: {
						// ʵ�ָ���Ҹ���ͷ��
						if (bean.getId() % 3 == 2) {
							Myplayer.getWidowsMain().face0.setVisible(true);
							Myplayer.getWidowsMain().face1.setVisible(true);
						}
						if (bean.getId() % 3 == 0) {
							Myplayer.getWidowsMain().face0.setVisible(true);
							Myplayer.getWidowsMain().face1.setVisible(true);
							Myplayer.getWidowsMain().face2.setVisible(true);
							// ���������Ѵչ������ѿ�ʼ�ˡ�
							// ֪ͨ���е���Ҳ��õ��ˡ���������ɡ�
							//������������ҽ��յģ��������汻ִ�������Ρ�
							bean.setType(4);// ȥ�õ��ƣ���ȥ���ȴ�ͼƬ��
							bean.setId(Myplayer.getId());//���ڸ����������ڷ��͸��ĸ���ҡ�
							sendMessage(bean);

						}
						break;
					}
					case 4: {
						System.out.println("client   1"+Myplayer.getId());
							//ȥ������ͼƬ��
							Myplayer.getWidowsMain().text.setVisible(false);
							//��ҵõ��ơ�	
							Myplayer.setCards(bean.getCards());
							System.out.println(Myplayer.getCards());
//						}

						break;
					}
					case 2: { // ������ҽ��棬Ȼ���������ñ�ţ����壬��ͷ�ꡣ��
						Myplayer.setId(bean.getId());
						Main wm = new Main();
						Myplayer.setWidowsMain(wm);
						Myplayer.getWidowsMain().setTitle(Myplayer.toString());
						Myplayer.getWidowsMain().setVisible(true);
						if (bean.getId() % 3 != 1) {// �����Ҳ��ǵ�һ����
							bean.setType(3);// ����Ҹ���ͷ��
							
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
