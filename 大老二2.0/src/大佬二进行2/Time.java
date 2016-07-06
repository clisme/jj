package 大佬二进行2;

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
			main.time[1].setText("倒计时:" + i--);
			second(1);// 等一秒
		}
		if (i == -1)// 正常终结，说明超时
			main.time[1].setText("不抢");
		main.landlord[0].setVisible(false);
		main.landlord[1].setVisible(false);
		for (Card card2 : main.playerList[1])
			card2.canClick = true;// 可被点击
		// 如果自己抢到梅花三
		if (main.time[1].getText().equals("抢梅花三"))
		{
			// 得到梅花三
			main.playerList[1].addAll(main.lordList);
			openlord(true);
			second(2);// 等待五秒
			Common.order(main.playerList[1]);
			Common.rePosition(main, main.playerList[1], 1);
			setlord(1);
		}
		else
		{
			// 电脑选抢
			if (Common.getScore(main.playerList[0]) < Common
					.getScore(main.playerList[2]))
			{
				main.time[2].setText("抢到的是我，嘿嘿");
				main.time[2].setVisible(true);
				setlord(2);// 设定第一出牌人
				openlord(true);
				second(3);
				main.playerList[2].addAll(main.lordList);
				Common.order(main.playerList[2]);
				Common.rePosition(main, main.playerList[2], 2);
				openlord(false);

			}
			else
			{
				main.time[0].setText("抢到");
				main.time[0].setVisible(true);
				setlord(0);// 设定地主
				openlord(true);
				second(3);
				main.playerList[0].addAll(main.lordList);
				Common.order(main.playerList[0]);
				Common.rePosition(main, main.playerList[0], 0);
				openlord(false);

			}
		}
		// 选完梅花3后 关闭抢牌按钮
		main.landlord[0].setVisible(false);
		main.landlord[1].setVisible(false);
		turnOn(false);
		for (int i = 0; i < 3; i++)
		{
			main.time[i].setText("不要");
			main.time[i].setVisible(false);
		}
		// 开始游戏 根据先出牌的不同顺序不同
		main.turn = main.m3Flag;
		while (true)
		{
			if (main.turn == 1) // 我
			{
				turnOn(true);// 出牌按钮 --我出牌
				timeWait(30, 1);// 我自己的定时器
				turnOn(false);// 选完关闭
				//(main.turn + 1) % 3   用于出牌循环
				main.turn = (main.turn + 1) % 3;
				if (win())// 判断输赢
					break;
			}
			if (main.turn == 0)
			{
				computer0();
				main.turn = (main.turn + 1) % 3;
				if (win())// 判断输赢
					break;
			}
			if (main.turn == 2)
			{
				computer2();
				main.turn = (main.turn + 1) % 3;
				if (win())// 判断输赢
					break;
			}
		}
	}

	// 等待i秒
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

	// 梅花三牌翻看
	public void openlord(boolean is)
	{

		if (is)
			main.lordList.get(0).turnFront(); // 梅花三牌翻看
		else
		{
			main.lordList.get(0).turnRear(); // 梅花三牌闭合
		}
		main.lordList.get(0).canClick = true;// 可被点击
	}

	// 设定梅花三得主
	public void setlord(int i)
	{
		Point point = new Point();
		if (i == 1)// 我得到
		{
			point.x = 80;
			point.y = 430;
			main.m3Flag = 1;// 设定
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

	// 打开出牌按钮
	public void turnOn(boolean flag)
	{
		main.publishCard[0].setVisible(flag);
		main.publishCard[1].setVisible(flag);
	}

	// 电脑0走牌(我代表1)
	public void computer0()
	{
		timeWait(1, 0); // 定时
		ShowCard(0); // 出牌

	}

	// 电脑2走牌(我代表1)
	public void computer2()
	{
		timeWait(1, 2); // 定时
		ShowCard(2); // 出牌

	}

	/*
	 * 难点。智能化的第二个表现。
	 */
	// 走牌 role表示谁先出牌。
	public void ShowCard(int role)
	{
		Model model = new Model();

		Common.getModel(main.playerList[role], model);
		// // 待走的牌
		List list = new ArrayList();
		// 如果是主动出牌
		if (main.time[(role + 1) % 3].getText().equals("不要")
				&& main.time[(role + 2) % 3].getText().equals("不要"))
		{
			// 有单出单
			if (model.a1.size() > 0)
			{
				for (int i = 0; i < model.a1.size(); i++)
				{
					List<Card> cards = new ArrayList<Card>();
					Card card = (Card) model.a1.get(i);
					cards.add(card);
					list.add(cards);
				}
			}// 有对子出对子
			else if (model.a2.size() > 0)
			{
				for (int i = 0; i < model.a2.size(); i++)
				{
					List<Card> cards = (List<Card>) model.a2.get(i);
					list.add(cards);
				}
			}// 有顺子出顺子
			else if (model.a123.size() > 0)
			{
				for (int i = 0; i < model.a123.size(); i++)
				{
					List<Card> cards = (List<Card>) model.a123.get(i);
					list.add(cards);
				}
			}
			// 有同花就出同花。
			else if (model.a11111.size() > 0)
			{

				for (int i = 0; i < model.a11111.size(); i++)
				{
					List<Card> cards = (List<Card>) model.a11111.get(i);
					list.add(cards);
				}
			}// 有三代二就出三带二。
			else if (model.a32.size() > 0)
			{
				for (int i = 0; i < model.a32.size(); i++)

				{
					List<Card> cards = (List<Card>) model.a32.get(i);
					list.add(cards);
				}
			}
			// 有四代1就出四代1
			else if (model.a41.size() > 0)
			{
				for (int i = 0; i < model.a41.size(); i++)

				{
					List<Card> cards = (List<Card>) model.a41.get(i);
					list.add(cards);
				}
			}

			// 有同花顺就出同花顺
			else if (model.a111111123.size() > 0)
			{
				for (int i = 0; i < model.a111111123.size(); i++)

				{
					List<Card> cards = (List<Card>) model.a111111123.get(i);
					list.add(cards);
				}
			}
			// 有三秃就出三秃。
			else if (model.a3.size() > 0)
			{
				for (int i = 0; i < model.a3.size(); i++)

				{
					List<Card> cards = (List<Card>) model.a3.get(i);
					list.add(cards);
				}
			}
			// 有四张就出四张
			else if (model.a4.size() > 0)
			{
				for (int i = 0; i < model.a4.size(); i++)

				{
					List<Card> cards = (List<Card>) model.a4.get(i);
					list.add(cards);
				}
			}
			// 有连队就出连对
			else if (model.a112233.size() > 0)
			{
				for (int i = 0; i < model.a112233.size(); i++)

				{
					List<Card> cards = (List<Card>) model.a112233.get(i);
					list.add(cards);
				}
			}
			// 有双飞双待，就出
			else if (model.a1112223344.size() > 0)
			{
				for (int i = 0; i < model.a1112223344.size(); i++)

				{
					List<Card> cards = (List<Card>) model.a1112223344.get(i);
					list.add(cards);
				}
			}
			// 有双飞单带，就出
			else if (model.a11122234.size() > 0)
			{
				for (int i = 0; i < model.a11122234.size(); i++)

				{
					List<Card> cards = (List<Card>) model.a11122234.get(i);
					list.add(cards);
				}
			}
			// 有双飞就出双飞
			else if (model.a111222.size() > 0)
			{
				for (int i = 0; i < model.a111222.size(); i++)

				{
					List<Card> cards = (List<Card>) model.a111222.get(i);
					list.add(cards);
				}
			}

		}// 如果是跟牌
		else
		{
			List<Card> player = main.currentList[(role + 2) % 3].size() > 0 ? main.currentList[(role + 2) % 3]
					: main.currentList[(role + 1) % 3];

			CardType cType = Common.jugdeType(player);
			// 如果是单牌
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
			}// 如果是对子
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
			}// 3带
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
			}// 4张
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
			}// 如果是连队

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
			}// 如果是双飞
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
			// 双飞带单
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
			// 双飞带双
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
			// 顺子
			else if (cType == CardType.c123)
			{
				int k = 0;// k == 0;表示没找到能跟的牌。
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
			// 如果是同花。
			//
			else if (cType == CardType.c11111)
			{
				int k = 0;// k ==
							// 0;表示没找到能跟的牌。

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
			// 如果是三代二。

			else if (cType == CardType.c32)
			{
				int k = 0;// k ==
							// 0;表示没找到能跟的牌。

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
			// 如果是四代一
			//
			else if (cType == CardType.c41)
			{
				int k = 0;// k ==
							// 0;表示没找到能跟的牌。

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
			// 如果是同花顺
			// 顺子
			else if (cType == CardType.c111111123)
			{
				int k = 0;// k
							// ==
							// 0;表示没找到能跟的牌。

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

		// 清理出过的牌。
		main.currentList[role].clear();
		//定位出的牌。
		if (list.size() > 0)
		{
			Point point = new Point();
			if (role == 0)
				point.x = 265;
			if (role == 2)
				point.x = 480;
			point.y = (400 / 2) - (list.size() + 1) * 15 / 2 ;// 屏幕中部
			
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
			main.time[role].setText("不要");
		}
		//如果不要，保存显示出过的牌。
		for (Card card : main.currentList[role])
			card.turnFront();
	}

	// // 延时，模拟时钟
	public void timeWait(int n, int player)
	{

		if (main.currentList[player].size() > 0)
			Common.hideCards(main.currentList[player]);
		if (player == 1)// 如果是我，10秒到后直接下一家出牌
		{
			int i = n;

			while (main.nextPlayer == false && i >= 0)
			{
				main.time[player].setText("倒计时:" + i);
				main.time[player].setVisible(true);
				second(1);
				i--;
			}
			if (i == -1)
			{
				main.time[player].setText("超时");
			}
			main.nextPlayer = false;
		}
		else
		{
			for (int i = n; i >= 0; i--)
			{
				second(1);
				main.time[player].setText("倒计时:" + i);
				main.time[player].setVisible(true);
			}
		}
		main.time[player].setVisible(false);
	}

	// 判断输赢
	public boolean win()
	{
		for (int i = 0; i < 3; i++)
		{
			if (main.playerList[i].size() == 0)
			{
				String s;
				if (i == 1)
				{
					s = "恭喜你，胜利了!";
				}
				else
				{
					s = "恭喜电脑" + i + ",赢了! 你的智商有待提高哦";
				}
				JOptionPane.showMessageDialog(main, s);
				return true;
			}
		}
		return false;
	}

	public static boolean checkCards(List<Card> c, List<Card> player)
	{

		// 找出当前最大的牌是哪个电脑出的,c是点选的牌
		List<Card> currentlist = player;

		Common.order(currentlist);
		Common.order(c);
		CardType cType = Common.jugdeType(c);

		CardType currentType = Common.jugdeType(currentlist);

		// 如果张数不同直接过滤
		if (c.size() != currentlist.size())
		{
			return false;
		}

		// 带有类型升级判断
		if (c.size() == 5 && currentlist.size() == 5)
		{

			if (currentType == CardType.c123)
			{
				if (cType == CardType.c123)
				{
					// 排序是小的在前面，大的在后面。
					Common.order(currentlist);
					Common.order(c);

					// 取出顺子中最大的一个数进行比较，大的大。
					Card card1 = c.get(4);
					Card card2 = currentlist.get(4);
					int value = Common.getValue(card1) - Common.getValue(card2);
					// 如果 》 0 ，表明 c 大。
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

					// 排序是小的在前面，大的在后面。
					Common.order(currentlist);
					Common.order(c);

					Card card1 = c.get(4);
					Card card2 = currentlist.get(4);

					// 如果两个花色相同。
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
					// 如果花色不相同，花色数值小的那个大。
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

					// 取出第一个数值进行比较。
					Card card1 = c.get(0);
					Card card2 = currentlist.get(0);
					int value = Common.getValue(card1) - Common.getValue(card2);
					// 如果 》 0 ，表明 c 大。
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

					// 取出第一个数值进行比较。
					Card card1 = c.get(0);
					Card card2 = currentlist.get(0);
					int value = Common.getValue(card1) - Common.getValue(card2);
					// 如果 》 0 ，表明 c 大。
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
					// 排序是小的在前面，大的在后面。
					Common.order(currentlist);
					Common.order(c);

					Card card1 = c.get(4);
					Card card2 = currentlist.get(4);

					// 如果两个花色相同。
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
					// 如果花色不相同，花色数值小的那个大。
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
		// 不含类型升级的。
		if (c.size() != 5 && currentlist.size() != 5
				&& c.size() == currentlist.size())
		{
			// 如果它们类型不一样就直接return。
			if (cType != currentType)
			{
				return false;
			}

			if (cType == currentType)
			{
				// 单只和对子
				if (cType == CardType.c1 || cType == CardType.c2)
				{
					// 排序是小的在前面，大的在后面。
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
						// 如果值相同，比花色。

						// 如果花色不相同，花色数值小的那个大。
						if (Common.getColor(card1) - Common.getColor(card2) > 0)
						{
							return false;
						}
						else
							return true;
					}
				}

				// 3带,4张,连队，飞机不带，因为之前排序过了，所以直接比较第一个就行。
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

					// 取出第一个数值进行比较。
					Card card1 = c.get(0);
					Card card2 = currentlist.get(0);
					int value = Common.getValue(card1) - Common.getValue(card2);
					// 如果 》 0 ，表明 c 大。
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
