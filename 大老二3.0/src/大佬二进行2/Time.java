package ���ж�����2;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Time extends Thread
{
	Main main;
	boolean isRun = true;
	int i = 10;

	public Time(Main m, int i)
	{
		this.main = m;
		this.i = i;
	}

	@Override
	public void run()
	{

		while (i > -1 && isRun)
		{
			main.time[1].setText("����ʱ:" + i--);
			second(1);// ��һ��
		}
		if (i == -1)// �����սᣬ˵����ʱ
			main.time[1].setText("����");
		main.landlord[0].setVisible(false);
		main.landlord[1].setVisible(false);
		for (Card card2 : main.playerList[1])
			card2.canClick = true;// �ɱ����
		// ����Լ�����÷����
		if (main.time[1].getText().equals("��÷����"))
		{
			// �õ�÷����
			main.playerList[1].addAll(main.lordList);
			openlord(true);
			second(2);// �ȴ�����
			Common.order(main.playerList[1]);
			Common.rePosition(main, main.playerList[1], 1);
			setlord(1);
		}
		else
		{
			// ����ѡ��
			if (Common.getScore(main.playerList[0]) < Common
					.getScore(main.playerList[2]))
			{
				main.time[2].setText("���������ң��ٺ�");
				main.time[2].setVisible(true);
				setlord(2);// �趨��һ������
				openlord(true);
				second(3);
				main.playerList[2].addAll(main.lordList);
				Common.order(main.playerList[2]);
				Common.rePosition(main, main.playerList[2], 2);
				openlord(false);

			}
			else
			{
				main.time[0].setText("����");
				main.time[0].setVisible(true);
				setlord(0);// �趨����
				openlord(true);
				second(3);
				main.playerList[0].addAll(main.lordList);
				Common.order(main.playerList[0]);
				Common.rePosition(main, main.playerList[0], 0);
				openlord(false);

			}
		}
		// ѡ��÷��3�� �ر����ư�ť
		main.landlord[0].setVisible(false);
		main.landlord[1].setVisible(false);
		turnOn(false);
		for (int i = 0; i < 3; i++)
		{
			main.time[i].setText("��Ҫ");
			main.time[i].setVisible(false);
		}
		// ��ʼ��Ϸ �����ȳ��ƵĲ�ͬ˳��ͬ
		main.turn = main.m3Flag;
		while (true)
		{
			if (main.turn == 1) // ��
			{
				turnOn(true);// ���ư�ť --�ҳ���
				timeWait(30, 1);// ���Լ��Ķ�ʱ��
				turnOn(false);// ѡ��ر�
				//(main.turn + 1) % 3   ���ڳ���ѭ��
				main.turn = (main.turn + 1) % 3;
				if (win())// �ж���Ӯ
					break;
			}
			if (main.turn == 0)
			{
				computer0();
				main.turn = (main.turn + 1) % 3;
				if (win())// �ж���Ӯ
					break;
			}
			if (main.turn == 2)
			{
				computer2();
				main.turn = (main.turn + 1) % 3;
				if (win())// �ж���Ӯ
					break;
			}
		}
	}

	// �ȴ�i��
	public void second(int i)
	{
		try
		{
			Thread.sleep(i * 1000);
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ÷�����Ʒ���
	public void openlord(boolean is)
	{

		if (is)
			main.lordList.get(0).turnFront(); // ÷�����Ʒ���
		else
		{
			main.lordList.get(0).turnRear(); // ÷�����Ʊպ�
		}
		main.lordList.get(0).canClick = true;// �ɱ����
	}

	// �趨÷��������
	public void setlord(int i)
	{
		Point point = new Point();
		if (i == 1)// �ҵõ�
		{
			point.x = 80;
			point.y = 430;
			main.m3Flag = 1;// �趨
		}
		if (i == 0)
		{
			point.x = 80;
			point.y = 20;
			main.m3Flag = 0;
		}
		if (i == 2)
		{
			point.x = 700;
			point.y = 20;
			main.m3Flag = 2;
		}

	}

	// �򿪳��ư�ť
	public void turnOn(boolean flag)
	{
		main.publishCard[0].setVisible(flag);
		main.publishCard[1].setVisible(flag);
	}

	// ����0����(�Ҵ���1)
	public void computer0()
	{
		timeWait(1, 0); // ��ʱ
		ShowCard(0); // ����

	}

	// ����2����(�Ҵ���1)
	public void computer2()
	{
		timeWait(1, 2); // ��ʱ
		ShowCard(2); // ����

	}

	/*
	 * �ѵ㡣���ܻ��ĵڶ������֡�
	 */
	// ���� role��ʾ˭�ȳ��ơ�
	public void ShowCard(int role)
	{
		Model model = new Model();

		Common.getModel(main.playerList[role], model);
		// // ���ߵ���
		List list = new ArrayList();
		// �������������
		if (main.time[(role + 1) % 3].getText().equals("��Ҫ")
				&& main.time[(role + 2) % 3].getText().equals("��Ҫ"))
		{
			// �е�����
			if (model.a1.size() > 0)
			{
				for (int i = 0; i < model.a1.size(); i++)
				{
					List<Card> cards = new ArrayList<Card>();
					Card card = (Card) model.a1.get(i);
					cards.add(card);
					list.add(cards);
				}
			}// �ж��ӳ�����
			else if (model.a2.size() > 0)
			{
				for (int i = 0; i < model.a2.size(); i++)
				{
					List<Card> cards = (List<Card>) model.a2.get(i);
					list.add(cards);
				}
			}// ��˳�ӳ�˳��
			else if (model.a123.size() > 0)
			{
				for (int i = 0; i < model.a123.size(); i++)
				{
					List<Card> cards = (List<Card>) model.a123.get(i);
					list.add(cards);
				}
			}
			// ��ͬ���ͳ�ͬ����
			else if (model.a11111.size() > 0)
			{

				for (int i = 0; i < model.a11111.size(); i++)
				{
					List<Card> cards = (List<Card>) model.a11111.get(i);
					list.add(cards);
				}
			}// ���������ͳ���������
			else if (model.a32.size() > 0)
			{
				for (int i = 0; i < model.a32.size(); i++)

				{
					List<Card> cards = (List<Card>) model.a32.get(i);
					list.add(cards);
				}
			}
			// ���Ĵ�1�ͳ��Ĵ�1
			else if (model.a41.size() > 0)
			{
				for (int i = 0; i < model.a41.size(); i++)

				{
					List<Card> cards = (List<Card>) model.a41.get(i);
					list.add(cards);
				}
			}

			// ��ͬ��˳�ͳ�ͬ��˳
			else if (model.a111111123.size() > 0)
			{
				for (int i = 0; i < model.a111111123.size(); i++)

				{
					List<Card> cards = (List<Card>) model.a111111123.get(i);
					list.add(cards);
				}
			}
			// ����ͺ�ͳ���ͺ��
			else if (model.a3.size() > 0)
			{
				for (int i = 0; i < model.a3.size(); i++)

				{
					List<Card> cards = (List<Card>) model.a3.get(i);
					list.add(cards);
				}
			}
			// �����žͳ�����
			else if (model.a4.size() > 0)
			{
				for (int i = 0; i < model.a4.size(); i++)

				{
					List<Card> cards = (List<Card>) model.a4.get(i);
					list.add(cards);
				}
			}
			// �����Ӿͳ�����
			else if (model.a112233.size() > 0)
			{
				for (int i = 0; i < model.a112233.size(); i++)

				{
					List<Card> cards = (List<Card>) model.a112233.get(i);
					list.add(cards);
				}
			}
			// ��˫��˫�����ͳ�
			else if (model.a1112223344.size() > 0)
			{
				for (int i = 0; i < model.a1112223344.size(); i++)

				{
					List<Card> cards = (List<Card>) model.a1112223344.get(i);
					list.add(cards);
				}
			}
			// ��˫�ɵ������ͳ�
			else if (model.a11122234.size() > 0)
			{
				for (int i = 0; i < model.a11122234.size(); i++)

				{
					List<Card> cards = (List<Card>) model.a11122234.get(i);
					list.add(cards);
				}
			}
			// ��˫�ɾͳ�˫��
			else if (model.a111222.size() > 0)
			{
				for (int i = 0; i < model.a111222.size(); i++)

				{
					List<Card> cards = (List<Card>) model.a111222.get(i);
					list.add(cards);
				}
			}

		}// ����Ǹ���
		else
		{
			List<Card> player = main.currentList[(role + 2) % 3].size() > 0 ? main.currentList[(role + 2) % 3]
					: main.currentList[(role + 1) % 3];

			CardType cType = Common.jugdeType(player);
			// ����ǵ���
			if (cType == CardType.c1)
			{
				if (model.a1.size() > 0)
				{
					for (int i = 0; i < model.a1.size(); i++)
					{
						List<Card> cards = new ArrayList<Card>();
						Card card = (Card) model.a1.get(i);
						cards.add(card);

						if (checkCards(cards, player))
						{
							list.add(cards);
							break;
						}
					}
				}
			}// ����Ƕ���
			else if (cType == CardType.c2)
			{
				if (model.a2.size() > 0)
				{
					for (int i = 0; i < model.a2.size(); i++)
					{
						List<Card> cards = (List<Card>) model.a2.get(i);

						if (checkCards(cards, player))
						{
							list.add(cards);
							break;
						}
					}
				}
			}// 3��
			else if (cType == CardType.c3)
			{

				if (model.a3.size() > 0)
				{
					for (int i = 0; i < model.a3.size(); i++)
					{
						List<Card> cards = (List<Card>) model.a3.get(i);

						if (checkCards(cards, player))
						{
							list.add(cards);
							break;
						}
					}
				}
			}// 4��
			else if (cType == CardType.c4)
			{

				if (model.a4.size() > 0)
				{
					for (int i = 0; i < model.a4.size(); i++)
					{
						List<Card> cards = (List<Card>) model.a4.get(i);

						if (checkCards(cards, player))
						{
							list.add(cards);
							break;
						}
					}
				}
			}// ���������

			else if (cType == CardType.c1122)
			{

				if (model.a112233.size() > 0)
				{
					for (int i = 0; i < model.a112233.size(); i++)
					{
						List<Card> cards = (List<Card>) model.a112233.get(i);

						if (checkCards(cards, player))
						{
							list.add(cards);
							break;
						}
					}
				}
			}// �����˫��
			else if (cType == CardType.c111222)
			{

				if (model.a111222.size() > 0)
				{
					for (int i = 0; i < model.a111222.size(); i++)
					{
						List<Card> cards = (List<Card>) model.a111222.get(i);

						if (checkCards(cards, player))
						{
							list.add(cards);
							break;
						}
					}
				}
			}
			// ˫�ɴ���
			else if (cType == CardType.c11122234)
			{

				if (model.a11122234.size() > 0)
				{
					for (int i = 0; i < model.a11122234.size(); i++)
					{
						List<Card> cards = (List<Card>) model.a11122234.get(i);

						if (checkCards(cards, player))
						{
							list.add(cards);
							break;
						}
					}
				}
			}
			// ˫�ɴ�˫
			else if (cType == CardType.c1112223344)
			{

				if (model.a1112223344.size() > 0)
				{
					for (int i = 0; i < model.a1112223344.size(); i++)
					{
						List<Card> cards = (List<Card>) model.a1112223344
								.get(i);

						if (checkCards(cards, player))
						{
							list.add(cards);
							break;
						}
					}
				}
			}
			// ˳��
			else if (cType == CardType.c123)
			{
				int k = 0;// k == 0;��ʾû�ҵ��ܸ����ơ�
				if (model.a123.size() > 0)
				{
					for (int i = 0; i < model.a123.size(); i++)
					{
						List<Card> cards = (List<Card>) model.a123.get(i);

						if (checkCards(cards, player))
						{
							k = 1;
							list.add(cards);
							break;
						}
					}
				}
				if (k == 0 && model.a11111.size() > 0)
				{
					if (model.a11111.size() > 0)
					{
						for (int i = 0; i < model.a11111.size(); i++)
						{
							List<Card> cards = (List<Card>) model.a11111.get(i);

							if (checkCards(cards, player))
							{
								k = 1;
								list.add(cards);
								break;
							}
						}
					}
				}
				if (k == 0 && model.a32.size() > 0)
				{
					if (model.a32.size() > 0)
					{
						for (int i = 0; i < model.a32.size(); i++)
						{
							List<Card> cards = (List<Card>) model.a32.get(i);

							if (checkCards(cards, player))
							{
								k = 1;
								list.add(cards);
								break;
							}
						}
					}
				}

				if (k == 0 && model.a41.size() > 0)
				{
					if (model.a41.size() > 0)
					{
						for (int i = 0; i < model.a41.size(); i++)
						{
							List<Card> cards = (List<Card>) model.a41.get(i);

							if (checkCards(cards, player))
							{
								k = 1;
								list.add(cards);
								break;
							}
						}
					}
				}
				if (k == 0 && model.a111111123.size() > 0)
				{
					if (model.a111111123.size() > 0)
					{
						for (int i = 0; i < model.a111111123.size(); i++)
						{
							List<Card> cards = (List<Card>) model.a111111123
									.get(i);

							if (checkCards(cards, player))
							{
								k = 1;
								list.add(cards);
								break;
							}
						}
					}
				}
			}
			// �����ͬ����
			//
			else if (cType == CardType.c11111)
			{
				int k = 0;// k ==
							// 0;��ʾû�ҵ��ܸ����ơ�

				if (k == 0 && model.a11111.size() > 0)
				{
					if (model.a11111.size() > 0)
					{
						for (int i = 0; i < model.a11111.size(); i++)
						{
							List<Card> cards = (List<Card>) model.a11111.get(i);

							if (checkCards(cards, player))
							{
								k = 1;
								list.add(cards);
								break;
							}
						}
					}
				}
				if (k == 0 && model.a32.size() > 0)
				{
					if (model.a32.size() > 0)
					{
						for (int i = 0; i < model.a32.size(); i++)
						{
							List<Card> cards = (List<Card>) model.a32.get(i);

							if (checkCards(cards, player))
							{
								k = 1;
								list.add(cards);
								break;
							}
						}
					}
				}

				if (k == 0 && model.a41.size() > 0)
				{
					if (model.a41.size() > 0)
					{
						for (int i = 0; i < model.a41.size(); i++)
						{
							List<Card> cards = (List<Card>) model.a41.get(i);

							if (checkCards(cards, player))
							{
								k = 1;
								list.add(cards);
								break;
							}
						}
					}
				}
				if (k == 0 && model.a111111123.size() > 0)
				{
					if (model.a111111123.size() > 0)
					{
						for (int i = 0; i < model.a111111123.size(); i++)
						{
							List<Card> cards = (List<Card>) model.a111111123
									.get(i);

							if (checkCards(cards, player))
							{
								k = 1;
								list.add(cards);
								break;
							}
						}
					}
				}
			}
			// �������������

			else if (cType == CardType.c32)
			{
				int k = 0;// k ==
							// 0;��ʾû�ҵ��ܸ����ơ�

				if (k == 0 && model.a32.size() > 0)
				{
					if (model.a32.size() > 0)
					{
						for (int i = 0; i < model.a32.size(); i++)
						{
							List<Card> cards = (List<Card>) model.a32.get(i);

							if (checkCards(cards, player))
							{
								k = 1;
								list.add(cards);
								break;
							}
						}
					}
				}

				if (k == 0 && model.a41.size() > 0)
				{
					if (model.a41.size() > 0)
					{
						for (int i = 0; i < model.a41.size(); i++)
						{
							List<Card> cards = (List<Card>) model.a41.get(i);

							if (checkCards(cards, player))
							{
								k = 1;
								list.add(cards);
								break;
							}
						}
					}
				}
				if (k == 0 && model.a111111123.size() > 0)
				{
					if (model.a111111123.size() > 0)
					{
						for (int i = 0; i < model.a111111123.size(); i++)
						{
							List<Card> cards = (List<Card>) model.a111111123
									.get(i);

							if (checkCards(cards, player))
							{
								k = 1;
								list.add(cards);
								break;
							}
						}
					}
				}
			}
			// ������Ĵ�һ
			//
			else if (cType == CardType.c41)
			{
				int k = 0;// k ==
							// 0;��ʾû�ҵ��ܸ����ơ�

				if (k == 0 && model.a41.size() > 0)
				{
					if (model.a41.size() > 0)
					{
						for (int i = 0; i < model.a41.size(); i++)
						{
							List<Card> cards = (List<Card>) model.a41.get(i);

							if (checkCards(cards, player))
							{
								k = 1;
								list.add(cards);
								break;
							}
						}
					}
				}
				if (k == 0 && model.a111111123.size() > 0)
				{
					if (model.a111111123.size() > 0)
					{
						for (int i = 0; i < model.a111111123.size(); i++)
						{
							List<Card> cards = (List<Card>) model.a111111123
									.get(i);

							if (checkCards(cards, player))
							{
								k = 1;
								list.add(cards);
								break;
							}
						}
					}
				}
			}
			// �����ͬ��˳
			// ˳��
			else if (cType == CardType.c111111123)
			{
				int k = 0;// k
							// ==
							// 0;��ʾû�ҵ��ܸ����ơ�

				if (k == 0 && model.a111111123.size() > 0)
				{
					if (model.a111111123.size() > 0)
					{
						for (int i = 0; i < model.a111111123.size(); i++)
						{
							List<Card> cards = (List<Card>) model.a111111123
									.get(i);

							if (checkCards(cards, player))
							{
								k = 1;
								list.add(cards);
								break;
							}
						}
					}
				}
			}

		}

		// ����������ơ�
		main.currentList[role].clear();
		//��λ�����ơ�
		if (list.size() > 0)
		{
			Point point = new Point();
			if (role == 0)
				point.x = 265;
			if (role == 2)
				point.x = 480;
			point.y = (400 / 2) - (list.size() + 1) * 15 / 2 ;// ��Ļ�в�
			
				List<Card> cards = (List<Card>) list.get(0);
				for (Card card : cards)
				{
					Common.move(card, card.getLocation(), point);
					point.y += 15;
					main.currentList[role].add(card);
					main.playerList[role].remove(card);
				}
				list.remove(0);
				Common.rePosition(main, main.playerList[role], role);
			
			
		}
		else
		{
			main.time[role].setVisible(true);
			main.time[role].setText("��Ҫ");
		}
		//�����Ҫ��������ʾ�������ơ�
		for (Card card : main.currentList[role])
			card.turnFront();
	}

	// // ��ʱ��ģ��ʱ��
	public void timeWait(int n, int player)
	{

		if (main.currentList[player].size() > 0)
			Common.hideCards(main.currentList[player]);
		if (player == 1)// ������ң�10�뵽��ֱ����һ�ҳ���
		{
			int i = n;

			while (main.nextPlayer == false && i >= 0)
			{
				main.time[player].setText("����ʱ:" + i);
				main.time[player].setVisible(true);
				second(1);
				i--;
			}
			if (i == -1)
			{
				main.time[player].setText("��ʱ");
			}
			main.nextPlayer = false;
		}
		else
		{
			for (int i = n; i >= 0; i--)
			{
				second(1);
				main.time[player].setText("����ʱ:" + i);
				main.time[player].setVisible(true);
			}
		}
		main.time[player].setVisible(false);
	}

	// �ж���Ӯ
	public boolean win()
	{
		for (int i = 0; i < 3; i++)
		{
			if (main.playerList[i].size() == 0)
			{
				String s;
				if (i == 1)
				{
					s = "��ϲ�㣬ʤ����!";
				}
				else
				{
					s = "��ϲ����" + i + ",Ӯ��! ��������д����Ŷ";
				}
				JOptionPane.showMessageDialog(main, s);
				return true;
			}
		}
		return false;
	}

	public static boolean checkCards(List<Card> c, List<Card> player)
	{

		// �ҳ���ǰ���������ĸ����Գ���,c�ǵ�ѡ����
		List<Card> currentlist = player;

		Common.order(currentlist);
		Common.order(c);
		CardType cType = Common.jugdeType(c);

		CardType currentType = Common.jugdeType(currentlist);

		// ���������ֱͬ�ӹ���
		if (c.size() != currentlist.size())
		{
			return false;
		}

		// �������������ж�
		if (c.size() == 5 && currentlist.size() == 5)
		{

			if (currentType == CardType.c123)
			{
				if (cType == CardType.c123)
				{
					// ������С����ǰ�棬����ں��档
					Common.order(currentlist);
					Common.order(c);

					// ȡ��˳��������һ�������бȽϣ���Ĵ�
					Card card1 = c.get(4);
					Card card2 = currentlist.get(4);
					int value = Common.getValue(card1) - Common.getValue(card2);
					// ��� �� 0 ������ c ��
					if (value > 0)
					{
						return true;
					}
					if (value < 0)
					{
						return false;
					}
				}

				if (cType == CardType.c11111 || cType == CardType.c32
						|| cType == CardType.c41
						|| cType == CardType.c111111123)
				{
					return true;
				}
			}

			if (currentType == CardType.c11111)
			{
				if (cType == CardType.c11111)
				{

					// ������С����ǰ�棬����ں��档
					Common.order(currentlist);
					Common.order(c);

					Card card1 = c.get(4);
					Card card2 = currentlist.get(4);

					// ���������ɫ��ͬ��
					if (Common.getColor(card1) == Common.getColor(card2))
					{
						int value = Common.getValue(card1)
								- Common.getValue(card2);
						if (value > 0)
						{
							return true;
						}
						if (value < 0)
						{
							return false;
						}
					}
					// �����ɫ����ͬ����ɫ��ֵС���Ǹ���
					if (Common.getColor(card1) != Common.getColor(card2))
					{
						int value = Common.getColor(card1)
								- Common.getColor(card2);
						if (value > 0)
						{
							return false;
						}
						if (value < 0)
						{
							return true;
						}
					}

				}
				if (cType == CardType.c32 || cType == CardType.c41
						|| cType == CardType.c111111123)
				{
					return true;
				}

			}

			if (currentType == CardType.c32)
			{
				if (cType == CardType.c32)
				{
					c = Common.getOrder2(c);
					currentlist = Common.getOrder2(currentlist);

					// ȡ����һ����ֵ���бȽϡ�
					Card card1 = c.get(0);
					Card card2 = currentlist.get(0);
					int value = Common.getValue(card1) - Common.getValue(card2);
					// ��� �� 0 ������ c ��
					if (value > 0)
					{
						return true;
					}
					if (value < 0)
					{
						return false;
					}
				}

				if (cType == CardType.c41 || cType == CardType.c111111123)
				{
					return true;
				}
			}
			if (currentType == CardType.c41)
			{
				if (cType == CardType.c41)
				{
					c = Common.getOrder2(c);
					currentlist = Common.getOrder2(currentlist);

					// ȡ����һ����ֵ���бȽϡ�
					Card card1 = c.get(0);
					Card card2 = currentlist.get(0);
					int value = Common.getValue(card1) - Common.getValue(card2);
					// ��� �� 0 ������ c ��
					if (value > 0)
					{
						return true;
					}
					if (value < 0)
					{
						return false;
					}
				}

				if (cType == CardType.c111111123)
				{
					return true;
				}
			}

			if (currentType == CardType.c111111123)
			{
				if (cType == CardType.c111111123)
				{
					// ������С����ǰ�棬����ں��档
					Common.order(currentlist);
					Common.order(c);

					Card card1 = c.get(4);
					Card card2 = currentlist.get(4);

					// ���������ɫ��ͬ��
					if (Common.getColor(card1) == Common.getColor(card2))
					{
						int value = Common.getValue(card1)
								- Common.getValue(card2);
						if (value > 0)
						{
							return true;
						}
						if (value < 0)
						{
							return false;
						}
					}
					// �����ɫ����ͬ����ɫ��ֵС���Ǹ���
					if (Common.getColor(card1) != Common.getColor(card2))
					{
						int value = Common.getColor(card1)
								- Common.getColor(card2);
						if (value > 0)
						{
							return false;
						}
						if (value < 0)
						{
							return true;
						}
					}
				}
			}
		}
		// �������������ġ�
		if (c.size() != 5 && currentlist.size() != 5
				&& c.size() == currentlist.size())
		{
			// ����������Ͳ�һ����ֱ��return��
			if (cType != currentType)
			{
				return false;
			}

			if (cType == currentType)
			{
				// ��ֻ�Ͷ���
				if (cType == CardType.c1 || cType == CardType.c2)
				{
					// ������С����ǰ�棬����ں��档
					Common.order(currentlist);
					Common.order(c);

					Card card1 = c.get(c.size() - 1);
					Card card2 = currentlist.get(currentlist.size() - 1);

					int value = Common.getValue(card1) - Common.getValue(card2);

					if (value > 0)
					{
						return true;
					}
					if (value < 0)
					{
						return false;
					}
					if (value == 0)
					{
						// ���ֵ��ͬ���Ȼ�ɫ��

						// �����ɫ����ͬ����ɫ��ֵС���Ǹ���
						if (Common.getColor(card1) - Common.getColor(card2) > 0)
						{
							return false;
						}
						else
							return true;
					}
				}

				// 3��,4��,���ӣ��ɻ���������Ϊ֮ǰ������ˣ�����ֱ�ӱȽϵ�һ�����С�
				if (cType == CardType.c3 || cType == CardType.c4
						|| cType == CardType.c111222 || cType == CardType.c1122)
				{
					if (Common.getValue(c.get(0)) <= Common
							.getValue(currentlist.get(0)))
					{
						return false;
					}
					else
					{
						return true;
					}
				}

				if (cType == CardType.c1112223344
						|| cType == CardType.c11122234)
				{
					c = Common.getOrder2(c);
					currentlist = Common.getOrder2(currentlist);

					// ȡ����һ����ֵ���бȽϡ�
					Card card1 = c.get(0);
					Card card2 = currentlist.get(0);
					int value = Common.getValue(card1) - Common.getValue(card2);
					// ��� �� 0 ������ c ��
					if (value > 0)
					{
						return true;
					}
					if (value < 0)
					{
						return false;
					}
				}
			}
		}
		return false;

	}
}
