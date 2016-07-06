package 大佬二进行2;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 * 对牌的各种操作类。
 */
public class Common
{
	
	
	/*
	 * 实现思路：在一个数组中，数组下标存放牌的Value，里面存放的是他的张数。
	 * a[getValue(list2.get(i))]++;
		然后找到最多牌数的牌。
		加进一个待返回集合。
	 */
	// 1 按照重复次数排序    在list中重复次数多的排在前面。
	public static List<Card> getOrder2(List<Card> list)
	{

		List<Card> list3 = new ArrayList<Card>();//待返回的序列集合。
		int len = list.size();
		
		//数组下标存放牌的Value，里面存放的是他的张数。
		int a[] = new int[20];
		for(int i = 0; i < 20; i++)
		{
			a[i] = 0;
		}
		for (int i = 0; i < len; i++)
		{	
			a[getValue(list.get(i))]++;
		}
		//用于存放最大牌数的牌。
		int max = 0;
		for (int i = 0; i < 20; i++)
		{
			max = 0;
			//找到最大的牌数，
			for (int j = 20 - 1; j >= 0; j--)
			{
				if (a[j] > a[max])
					max = j;//存放
			}
			//在list集合中，找到所有的（最大牌数的牌），然后加进返回集合
			for (int k = 0; k < len; k++)
			{
				if (getValue(list.get(k)) == max)
				{
					list3.add(list.get(k));
				}
			}
			//清空最大牌数。
			a[max] = 0;
		}
		return list3;
	}

	/*
	 * 实现思路：把牌集合，分好过后，（即对子，三张，分别存放）。
	 * 取出list3中的连续三张，和对子中的前两张。加进一个集合。
	 */
	// 2返回三代二。
	public static List get3带2(List<Card> list)
	{
		//带返回的所有，三代二的集合。
		List lists = new ArrayList();
		
		//按张数分牌
		Card_index card_index = new Card_index();
		getMax(card_index, list);

		List<Card> list2 = card_index.list2;
		List<Card> list3 = card_index.list3;
		
		if (list2.size() > 0 && list3.size() > 0)
		{
			//取出并加进去。
			for (int i = 0; i < list3.size(); i += 3)
			{
				List<Card> lista = new ArrayList<Card>();
				lista.add(list3.get(i));
				lista.add(list3.get(i + 1));
				lista.add(list3.get(i + 2));
				lista.add(list2.get(0));
				lista.add(list2.get(1));

				lists.add(lista);
			}
		}
		return lists;
	}

	/*
	 * 实现思路: 先把list集合中的牌按相同张数分集合。取出list1,然后排序。
	 * 如果符合，相隔五张牌的数值差，和序号差相同。说明它是顺子。
	 */
	// 3 返回一个list集合中的顺子。
	public static List getS(List<Card> list)
	{
		//分牌。
		Card_index card_index = new Card_index();
		getMax(card_index, list);
		List<Card> list1 = card_index.list1;
		//大小排序
		order(list1);

		List lists = new ArrayList();

		int length = list1.size();

		//查找到顺子的下标。k , i 
		for (int i = 0; i < list1.size(); i++)
		{
			for (int k = length - 1; k > 2; k--)
			{
				List<Card> cardList = new ArrayList<Card>();

				int len = k - i;
				if ((len == 4)
						&& ((k - i) == (getValue(list1.get(k)) - getValue(list1
								.get(i)))))
				{
					//把找到的顺子组成一个集合。
					for (int a = i; a <= k; a++)
					{
						cardList.add(list1.get(a));

					}
					//加进待返回的集合。
					lists.add(cardList);
				}
			}
		}
		return lists;
	}

	/*
	 * 实现思路： 把list集合，分张。然后得到，对子集合。
	 * 两张加进一个集合。
	 */
	// 4返回单对子。
	public static List get对子(List<Card> list)
	{
		List lists = new ArrayList();
		//分牌。
		Card_index card_index = new Card_index();
		getMax(card_index, list);

		List<Card> list2 = card_index.list2;
		
		if (list2.size() > 0)
		{	//加进集合。
			for (int i = 0; i < list2.size(); i += 2)
			{
				List<Card> lista = new ArrayList<Card>();
				lista.add(list2.get(i));
				lista.add(list2.get(i + 1));

				lists.add(lista);

			}
		}
		return lists;

	}
	
	/*
	 * 实现思路： 分牌，然后得到单张集合。
	 */
	// 5返回单只
	public static List get单只(List<Card> list)
	{

		Card_index card_index = new Card_index();
		getMax(card_index, list);

		List<Card> list1 = card_index.list1;
		return list1;

	}
	
	/*
	 * 实现思路：分牌，然后，得到对子集合。排序，然后取出奇数集合。
	 * 如果它是顺子，说明它是连对。
	 */
	// 6 返回连对。
	public static List getS2(List<Card> listu)
	{
		//分牌
		Card_index card_index = new Card_index();
		getMax(card_index, listu);
		List<Card> list = card_index.list2;
		//排序。
		order(list);

		List lists = new ArrayList();

		List<Card> listd = new ArrayList<Card>();

		//取出奇数牌。
		for (int i = 0; i < list.size(); i += 2)
		{
			listd.add(list.get(i));
		}

		int length = listd.size();
		//判断奇数牌是不是顺子。
		for (int i = 0; i < listd.size(); i++)
		{
			for (int k = length - 1; k > 0; k--)
			{
				List<Card> cardList = new ArrayList<Card>();

				int len = k - i;
				if ((len >= 2)
						&& ((k - i) == (getValue(listd.get(k)) - getValue(listd
								.get(i)))))
				{
					for (int a = i; a <= k; a++)
					{
						cardList.add(listd.get(a));

					}
					lists.add(cardList);
				}
			}
		}
		//得到顺子，然后遍历集合，和顺子里牌值相同的牌，加进集合。
		List listr = new ArrayList();
		for (int i = 0; i < lists.size(); i++)
		{
			List<Card> listq = new ArrayList<Card>();
			List listw = (List) lists.get(i);

			for (int k = 0; k < listw.size(); k++)
			{
				for (int j = 0; j < list.size(); j++)
				{
					if (getValue((Card) listw.get(k)) == getValue(list.get(j)))
					{
						listq.add(list.get(j));
					}
				}
			}
			listr.add(listq);

		}
		return listr;
	}

	/*
	 * 实现思路。直接分牌，就可得到。
	 */
	// 7返回三不带
	public static List get三不带(List<Card> list)
	{
		List lists = new ArrayList();
		//分牌
		Card_index card_index = new Card_index();
		getMax(card_index, list);

		List<Card> list3 = card_index.list3;
		//加进集合
		if (list3.size() > 0)
		{
			for (int i = 0; i < list3.size(); i += 3)
			{
				List<Card> lista = new ArrayList<Card>();
				lista.add(list3.get(i));
				lista.add(list3.get(i + 1));
				lista.add(list3.get(i + 2));

				lists.add(lista);

			}
		}
		return lists;

	}
	
	/*
	 * 实现思路：用的穷举法： 分牌等操作过后，得到三秃集合。
	 * 如果只有六张，则判断是不是牌值差一。
	 * 九张，十二张，都这样。
	 */
	// 8返回双飞三飞
	public static List get双飞(List<Card> list)
	{
		List lists = new ArrayList();
		//分牌
		Card_index card_index = new Card_index();
		getMax(card_index, list);
		List<Card> list3 = card_index.list3;

		order(list3);

		//六张时判断是不是连张。
		if (list3.size() == 6)
		{
			Card card1 = list3.get(0);
			Card card2 = list3.get(3);
			if (getValue(card2) - getValue(card1) == 1)
			{
				List<Card> lista = new ArrayList<Card>();
				for (int i = 0; i < 6; i++)
				{
					lista.add(list3.get(i));
				}

				lists.add(lista);
			}

		}

		if (list3.size() == 9)
		{
			Card card1 = list3.get(0);
			Card card2 = list3.get(3);
			Card card3 = list3.get(6);
			if (getValue(card2) - getValue(card1) == 1)
			{
				List<Card> lista = new ArrayList<Card>();
				for (int i = 0; i < 6; i++)
				{
					lista.add(list3.get(i));
				}

				lists.add(lista);
			}
			if (getValue(card3) - getValue(card2) == 1)
			{
				List<Card> lista = new ArrayList<Card>();
				for (int i = 3; i < 9; i++)
				{
					lista.add(list3.get(i));
				}

				lists.add(lista);
			}

			if ((getValue(card2) - getValue(card1) == 1)
					&& (getValue(card3) - getValue(card2)) == 1)
			{
				List<Card> lista = new ArrayList<Card>();
				for (int i = 0; i < 9; i++)
				{
					lista.add(list3.get(i));
				}

				lists.add(lista);
			}

		}
		if (list3.size() > 9)
		{
			Card card1 = list3.get(0);
			Card card2 = list3.get(3);
			Card card3 = list3.get(6);
			Card card4 = list3.get(9);
			if (getValue(card2) - getValue(card1) == 1)
			{
				List<Card> lista = new ArrayList<Card>();
				for (int i = 0; i < 6; i++)
				{
					lista.add(list3.get(i));
				}

				lists.add(lista);
			}
			if (getValue(card3) - getValue(card2) == 1)
			{
				List<Card> lista = new ArrayList<Card>();
				for (int i = 3; i < 9; i++)
				{
					lista.add(list3.get(i));
				}

				lists.add(lista);
			}
			if (getValue(card4) - getValue(card3) == 1)
			{
				List<Card> lista = new ArrayList<Card>();
				for (int i = 6; i < 12; i++)
				{
					lista.add(list3.get(i));
				}

				lists.add(lista);
			}

			if ((getValue(card2) - getValue(card1) == 1)
					&& (getValue(card3) - getValue(card2)) == 1)
			{
				List<Card> lista = new ArrayList<Card>();
				for (int i = 0; i < 9; i++)
				{
					lista.add(list3.get(i));
				}

				lists.add(lista);
			}

			if ((getValue(card4) - getValue(card3) == 1)
					&& (getValue(card3) - getValue(card2)) == 1)
			{
				List<Card> lista = new ArrayList<Card>();
				for (int i = 3; i < 12; i++)
				{
					lista.add(list3.get(i));
				}

				lists.add(lista);
			}
		}
		return lists;
	}

	/*
	 * 先把集合中的双飞取出来，在加上单只。
	 */
	// 9返回双飞带单只
	public static List get双飞带单只(List<Card> list)
	{
		List lists = new ArrayList();

		Card_index card_index = new Card_index();
		getMax(card_index, list);

		List<Card> list1 = card_index.list1;
		
		List listd = get双飞(list);
		if (listd.size() != 0 && list1.size() >= 2)
		{
			for (int i = 0; i < listd.size(); i++)
			{
				List lista = (List) listd.get(i);
				// 如果长度是6，表明要加2个单只。
				if (lista.size() == 6)
				{
					lista.add(list1.get(0));
					lista.add(list1.get(1));
					lists.add(lista);
				}
				if (lista.size() == 9 && list1.size() >= 3)
				{
					lista.add(list1.get(0));
					lista.add(list1.get(1));
					lista.add(list1.get(2));
					lists.add(lista);
				}

			}
		}
		return lists;
	}

	/*
	 * 先把集合中的双飞取出来在加上对子。
	 */
	// 10返回双飞带对子
	public static List get双飞带对只(List<Card> list)
	{
		List lists = new ArrayList();

		Card_index card_index = new Card_index();
		getMax(card_index, list);

		List<Card> list1 = card_index.list2;
		
		List listd = get双飞(list);
		if (listd.size() != 0 && list1.size() >= 4)
		{
			for (int i = 0; i < listd.size(); i++)
			{
				List lista = (List) listd.get(i);
				// 如果长度是6，表明要加2个单只。
				if (lista.size() == 6)
				{
					lista.add(list1.get(0));
					lista.add(list1.get(1));
					lista.add(list1.get(2));
					lista.add(list1.get(3));
					lists.add(lista);
				}
				if (lista.size() == 9 && list1.size() >= 6)
				{
					lista.add(list1.get(0));
					lista.add(list1.get(1));
					lista.add(list1.get(2));
					lista.add(list1.get(3));
					lista.add(list1.get(4));
					lista.add(list1.get(5));
					lists.add(lista);
				}

			}
		}
		return lists;
	}

	/*
	 * 分过牌后，从list4中，取四个，从list1中取一个。
	 */
	// 11返回四代1
	public static List get四代一(List<Card> list)
	{
		List lists = new ArrayList();
		//分牌
		Card_index card_index = new Card_index();
		getMax(card_index, list);

		List<Card> list1 = card_index.list1;
		List<Card> list4 = card_index.list4;
		

		if (list1.size() > 0 && list4.size() > 0)
		{
			//从list4中把牌，加进去。
			for (int i = 0; i < list4.size(); i += 4)
			{
				List<Card> lista = new ArrayList<Card>();
				lista.add(list4.get(i));
				lista.add(list4.get(i + 1));
				lista.add(list4.get(i + 2));
				lista.add(list4.get(i + 3));
				//从list1中把牌，加进去。
				lista.add(list1.get(0));

				lists.add(lista);

			}
		}
		return lists;

	}

	/*
	 * 分过牌后，直接从list4中取。
	 */
	// 12返回四张
	public static List get四张(List<Card> list)
	{
		List lists = new ArrayList();
		//分牌
		Card_index card_index = new Card_index();
		getMax(card_index, list);

		List<Card> list4 = card_index.list4;
		

		if (list4.size() > 0)
		{

			//加进。
			for (int i = 0; i < list4.size(); i += 4)
			{
				List<Card> lista = new ArrayList<Card>();
				lista.add(list4.get(i));
				lista.add(list4.get(i + 1));
				lista.add(list4.get(i + 2));
				lista.add(list4.get(i + 3));

				lists.add(lista);

			}
		}
		return lists;

	}

	/*
	 * 按花色分牌过后，从各种花色中取出5张。取的是前4张，和最后一张。
	 */
	// 13返回同花
	public static List get同花(List<Card> list)
	{
		List lists = new ArrayList();
		//按花色分牌。
		Card_color card_color = new Card_color();
		colorList(card_color, list);

		int blackTlength = card_color.blackTlist.size();
		//如果一种花色牌>5张。加进。加前4个，和最后一个。
		if (blackTlength >= 5)
		{
			List<Card> list1 = new ArrayList();

			for (int i = 0; i < blackTlength; i++)
			{
				if (i < 4 || (i == blackTlength - 1))
				{
					Card card = card_color.blackTlist.get(i);
					list1.add(card);
				}
			}
			lists.add(list1);
		}

		int redTlength = card_color.redTlist.size();

		if (redTlength >= 5)
		{
			List<Card> list1 = new ArrayList();

			for (int i = 0; i < redTlength; i++)
			{
				if (i < 4 || (i == redTlength - 1))
				{
					Card card = card_color.redTlist.get(i);
					list1.add(card);
				}
			}
			lists.add(list1);
		}

		int blackMlength = card_color.blackMlist.size();

		if (blackMlength >= 5)
		{
			List<Card> list1 = new ArrayList();

			for (int i = 0; i < blackMlength; i++)
			{
				if (i < 4 || (i == blackMlength - 1))
				{
					Card card = card_color.blackMlist.get(i);
					list1.add(card);
				}
			}
			lists.add(list1);
		}

		int redFlength = card_color.redFlist.size();

		if (redFlength >= 5)
		{
			List<Card> list1 = new ArrayList();

			for (int i = 0; i < redFlength; i++)
			{
				if (i < 4 || (i == redFlength - 1))
				{
					Card card = card_color.redFlist.get(i);
					list1.add(card);
				}
			}
			lists.add(list1);
		}
		return lists;
	}

	/*
	 * 先按牌的花色分牌。然后，在判断每个集合是不是顺子。
	 */
	// 14返回同花顺
	public static List get同花顺(List<Card> list)
	{
		List lists = new ArrayList();
		//按花色分牌
		Card_color card_color = new Card_color();
		colorList(card_color, list);
		//如果每个集合大于0，再去判断是不是顺子。
		if ((getS(card_color.blackMlist)).size() != 0)
		{
			lists.addAll(getS(card_color.blackMlist));
		}
		if ((getS(card_color.blackTlist)).size() != 0)
		{
			lists.addAll(getS(card_color.blackTlist));
		}
		if ((getS(card_color.redFlist)).size() != 0)
		{
			lists.addAll(getS(card_color.redFlist));
		}
		if ((getS(card_color.redTlist)).size() != 0)
		{
			lists.addAll(getS(card_color.redTlist));
		}
		return lists;
	}

	/*
	 * 遍历list集合中的每一张牌，取出牌的花色。加进对应的集合。
	 */
	// 15给牌分花色。
	public static void colorList(Card_color card_color, List<Card> list)
	{
		for (Card card : list)
		{
			int color = Common.getColor(card);
			switch (color)
			{
			case 1:
				card_color.blackTlist.add(card);
				break;
			case 2:
				card_color.redTlist.add(card);
				break;
			case 3:
				card_color.redFlist.add(card);
				break;
			case 4:
				card_color.blackMlist.add(card);
				break;
			}
		}
	}

	/*
	 * 牌的名字是 花色-数值。所以直接截取字符串就可以了。
	 * 然后转换成整型数值。
	 */
	// 16 返回花色
	public static int getColor(Card card)
	{
		//parseInt()  数字字符串转化成整形函数。
		return Integer.parseInt(card.name.substring(0, 1));
	}

	/*
	 * 牌的名字是 花色-数值。所以直接截取字符串就可以了。
	 * 然后转换成整型数值。A 和 2 ，数值加13 
	 */
	// 17 返回牌值
	public static int getValue(Card card)
	{
		int i = Integer.parseInt(card.name.substring(2, card.name.length()));
		if (card.name.substring(2, card.name.length()).equals("2"))
			i = 15;
		if (card.name.substring(2, card.name.length()).equals("1"))
			i = 14;

		return i;
	}

	/*
	 * 取出每个牌值，加进对应的集合里即可。
	 */
	// 18 得到最大相同数,张数相同的牌加在一个集合里。 给牌分重数
	public static void getMax(Card_index card_index, List<Card> list)
	{

		List lists = new ArrayList();
		// 1-13各算一种
		List<Card> list0 = new ArrayList<Card>();
		List<Card> list1 = new ArrayList<Card>();
		List<Card> list2 = new ArrayList<Card>();
		List<Card> list3 = new ArrayList<Card>();
		List<Card> list4 = new ArrayList<Card>();
		List<Card> list5 = new ArrayList<Card>();
		List<Card> list6 = new ArrayList<Card>();
		List<Card> list7 = new ArrayList<Card>();
		List<Card> list8 = new ArrayList<Card>();
		List<Card> list9 = new ArrayList<Card>();
		List<Card> list10 = new ArrayList<Card>();
		List<Card> list11 = new ArrayList<Card>();
		List<Card> list12 = new ArrayList<Card>();
	
		//取出每张牌加入对应的集合。
		for (int i = 0, len = list.size(); i < len; i++)
		{
			Card card = list.get(i);
			int value = Common.getValue(card);
			switch (value)
			{
			case 3:
				list0.add(card);
				break;
			case 4:
				list1.add(card);
				break;
			case 5:
				list2.add(card);
				break;
			case 6:
				list3.add(card);
				break;
			case 7:
				list4.add(card);
				break;
			case 8:
				list5.add(card);
				break;
			case 9:
				list6.add(card);
				break;
			case 10:
				list7.add(card);
				break;
			case 11:
				list8.add(card);
				break;
			case 12:
				list9.add(card);
				break;
			case 13:
				list10.add(card);
				break;
			case 14:
				list11.add(card);
				break;
			case 15:
				list12.add(card);
				break;
			}
		}

		//用去防止list1234个集合中，是空的现象，导致错误。
		lists.add(list0);
		lists.add(list1);
		lists.add(list2);
		lists.add(list3);
		lists.add(list4);
		lists.add(list5);
		lists.add(list6);
		lists.add(list7);
		lists.add(list8);
		lists.add(list9);
		lists.add(list10);
		lists.add(list11);
		lists.add(list12);

		//把所有的相同张数的牌放在一个集合里。
		for (int i = 0; i < lists.size(); i++)
		{

			List listcard = (List) lists.get(i);

			int len = listcard.size();

			switch (len)
			{
			case 1:
				card_index.list1.addAll(listcard);
				break;
			case 2:
				card_index.list2.addAll(listcard);
				break;
			case 3:
				card_index.list3.addAll(listcard);
				break;
			case 4:
				card_index.list4.addAll(listcard);
				break;
			}

		}
	}

	/*
	 * 得到电脑出的牌类型，和玩家点选要出的牌的类型。相同比较大小就可以了。
	 */
	// 19检查选牌是否能出
	public static boolean checkCards(List<Card> c, List<Card>[] current)
	{
		// 找出当前最大的牌是哪个电脑出的,c是点选的牌
		List<Card> currentlist = (current[0].size() > 0) ? current[0]
				: current[2];
		//给两个集合的牌排序。
		order(currentlist);
		order(c);
		//得出选牌的类型。
		CardType cType = jugdeType(c);
		//得出电脑牌的类型。
		CardType currentType = jugdeType(currentlist);

		// 如果张数不同直接过滤
		if (c.size() != currentlist.size())
		{
			return false;
		}

		// 带有类型升级判断
		if (c.size() == 5 && currentlist.size() == 5)
		{
			//如果电脑牌是顺子。
			if (currentType == CardType.c123)
			{	
				//如果点选牌是顺子。取出判断大小即可。
				if (cType == CardType.c123)
				{
					// 排序是小的在前面，大的在后面。
					order(currentlist);
					order(c);

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
				//如果点选牌是同花，三代二，四代一，同花顺。直接压死。
				if (cType == CardType.c11111 || cType == CardType.c32
						|| cType == CardType.c41
						|| cType == CardType.c111111123)
				{
					return true;
				}
			}
			//如果电脑牌是同花。
			if (currentType == CardType.c11111)
			{
				//如果点选牌是同花，同花色的排序后，判断大小。
				//不同花色的，花色大的小。
				if (cType == CardType.c11111)
				{

					// 排序是小的在前面，大的在后面。
					order(currentlist);
					order(c);

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
				//类型升级直接压死的。
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
					//重复次数排序。
					c = getOrder2(c);
					currentlist = getOrder2(currentlist);

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
					c = getOrder2(c);
					currentlist = getOrder2(currentlist);

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
					order(currentlist);
					order(c);

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
				// 单只和对子  	先排序。在判断数值。
				if (cType == CardType.c1 || cType == CardType.c2)
				{
					// 排序是小的在前面，大的在后面。
					order(currentlist);
					order(c);

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
						else return true;
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
				//排过续，直接比较第一个数值就行了。
				if (cType == CardType.c1112223344
						|| cType == CardType.c11122234)
				{
					//按重数排序。
					c = getOrder2(c);
					currentlist = getOrder2(currentlist);

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

	/*
	 * 记录list中每个牌的花色。然后取出一个，只要剩下的牌中花色和它全部相同。
	 * 则证明是同花。
	 */
	// 20类型判断中，用于判断是否是同花
	public static boolean 同花(List<Card> list)
	{
		int[] color = new int[list.size()];
		for (int i = 0; i < list.size(); i++)
		{
			color[i] = Common.getColor(list.get(i));
		}
		int temp = color[0];
		for (int i = 0; i < color.length; i++)
		{
			if (temp != color[i])
			{
				return false;
			}
		}
		return true;

	}

	// 20 判断牌型
	public static CardType jugdeType(List<Card> list)
	{
		//纸牌大小排序。
		order(list);
		// 因为之前排序过所以比较好判断
		int len = list.size();
		// 单牌,对子，3不带，4个一样
		if (len <= 4)
		{ // 如果第一个和最后个相同，说明全部相同
			if (list.size() > 0
					&& Common.getValue(list.get(0)) == Common.getValue(list
							.get(len - 1)))
			{
				switch (len)
				{
				case 1:
					return CardType.c1;
				case 2:
					return CardType.c2;
				case 3:
					return CardType.c3;
				case 4:
					return CardType.c4;
				}
			}

		}
		// 当5张以上时，连字，3带2，飞机，2顺，4带1等等

		// 现在按相同牌数排序。张数多的排在前面。
		Card_index card_index = new Card_index();
		getMax(card_index, list);

		if (len == 5)
		{
			// 同花顺
			if (同花(list)
					&& (card_index.list1.size() == len)
					&& (Common.getValue(list.get(len - 1))
							- Common.getValue(list.get(0)) == len - 1))
				return CardType.c111111123;

			// 顺子  第一个和第最后一个的差值。序列差等于数值差。
			if ((card_index.list1.size() == len)
					&& (Common.getValue(list.get(len - 1))
							- Common.getValue(list.get(0)) == len - 1))
				return CardType.c123;
			// 同花
			if (同花(list))
				return CardType.c11111;

			// 3带2
			if (card_index.list3.size() == 3 && card_index.list2.size() == 2
					&& len == 5)
				return CardType.c32;
			// 4带1
			if (card_index.list4.size() == 4 && card_index.list1.size() == 1
					&& len == 5)
				return CardType.c41;
		}

		if (len > 5)
		{

			// 连队
			if (card_index.list2.size() == len
					&& len % 2 == 0   //说明是对子
					&& len / 2 >= 3	  //说明对子要大于三。  数值差等于长度÷2-1
					&& (Common.getValue(list.get(len - 1))
							- Common.getValue(list.get(0)) == (len / 2 - 1)))
				return CardType.c1122;
			// 飞机
			if (card_index.list3.size() == len
					&& (len % 3 == 0)
					&& (Common.getValue(list.get(len - 1))
							- Common.getValue(list.get(0)) == (len / 3 - 1)))
				return CardType.c111222;
			// 飞机带n单,n/2对
			if (card_index.list3.size() == len / 4 * 3
					&& (Common
							.getValue((card_index.list3.get(len / 4 * 3 - 1)))
							- Common.getValue((card_index.list3.get(0))) == (len / 4 - 1))
					&& card_index.list1.size() == len / 4)
				return CardType.c11122234;

			// 飞机带n双

			if (len >= 10
					&& (card_index.list3.size() == (len / 5 * 3))
					&& (card_index.list2.size() == len / 5 * 2)
					&& (Common.getValue(card_index.list3.get(len / 5 * 3 - 1))
							- Common.getValue(card_index.list3.get(0)) == (len / 5 - 1)))
				return CardType.c1112223344;
		}
		return CardType.c0;
	}

	/*
	 * 21纸牌大小排序。比较器中，直接取出牌的数值进行比较。
	 * 如果数值相同，比较花色。
	 */
	public static void order(List<Card> list)
	{
		Collections.sort(list, new Comparator<Card>()
		{
			@Override
			public int compare(Card o1, Card o2)
			{
				// TODO Auto-generated method stub
				int a1 = Integer.parseInt(o1.name.substring(0, 1));// 花色
				int a2 = Integer.parseInt(o2.name.substring(0, 1));
				int b1 = Integer.parseInt(o1.name.substring(2, o1.name.length()));// 数值
				int b2 = Integer.parseInt(o2.name.substring(2, o2.name.length()));
				int flag = 0;

				// 如果是A或者2
				if (b1 == 1)
					b1 += 13;
				if (b2 == 1)
					b2 += 13;
				if (b1 == 2)
					b1 += 14;
				if (b2 == 2)
					b2 += 14;
				flag = b1 - b2;
				if (flag == 0)
					return a2 - a1;
				else
				{
					return flag;
				}
			}
		});

	}
	
	/*
	 * 22 比较器中，按花色大小，返回。花色相同的按数值比较。
	 */
	public static void order花色(List<Card> list)
	{
		Collections.sort(list, new Comparator<Card>()
		{
			@Override
			public int compare(Card o1, Card o2)
			{
				// TODO Auto-generated method stub
				int a1 = Integer.parseInt(o1.name.substring(0, 1));// 花色
				int a2 = Integer.parseInt(o2.name.substring(0, 1));
				int b1 = Integer.parseInt(o1.name.substring(2, o1.name.length()));// 数值
				int b2 = Integer.parseInt(o2.name.substring(2, o2.name.length()));
				int flag = 0;

				// 如果是A或者2
				if (b1 == 1)
					b1 += 13;
				if (b2 == 1)
					b2 += 13;
				if (b1 == 2)
					b1 += 14;
				if (b2 == 2)
					b2 += 14;
				flag = a2 - a1;
				if (flag == 0)
					return b1 - b2;
				else
				{
					return flag;
				}
			}
		});

	}


	// 23 移动效果的函数,用于发牌
	public static void move(Card card, Point from, Point to)
	{
		if (to.x != from.x)
		{
			double k = (1.0) * (to.y - from.y) / (to.x - from.x);
			double b = to.y - to.x * k;
			int flag = 0;// 判断向左还是向右移动步幅
			if (from.x < to.x)
				flag = 20;
			else
			{
				flag = -20;
			}
			for (int i = from.x; Math.abs(i - to.x) > 20; i += flag)
			{
				double y = k * i + b;// 这里主要用的数学中的线性函数

				card.setLocation(i, (int) y);
				try
				{
					Thread.sleep(4); // 延迟，可自己设置
				}
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// 位置校准
		card.setLocation(to);
	}

	// 24 重新定位    flag代表电脑1 ,2 或者是我
	public static void rePosition(Main m, List<Card> list, int flag)
	{
		Point p = new Point();
		if (flag == 0)
		{
			p.x = 150;
			p.y = (450 / 2 - 14) - (list.size() + 1) * 15 / 2;
		}
		if (flag == 1)
		{// 我的排序 _y=450 width=830
			p.x = (800 / 2 + 10) - (list.size() + 1) * 21 / 2;
			p.y = 450;
		}
		if (flag == 2)
		{
			p.x = 600;
			p.y = (450 / 2 - 14) - (list.size() + 1) * 15 / 2;
		}
		int len = list.size();
		for (int i = 0; i < len; i++)
		{
			Card card = list.get(i);
			Common.move(card, card.getLocation(), p);
			m.container.setComponentZOrder(card, 0);
			if (flag == 1)
				p.x += 21;
			else p.y += 15;

		}
	}

	// 25 隐藏之前出过的牌 设置不可见。
	public static void hideCards(List<Card> list)
	{
		for (int i = 0, len = list.size(); i < len; i++)
		{
			list.get(i).setVisible(false);
		}
	}

	// 抢牌权值，看是否抢抢到梅花三。A 和 2 多的人能抢到。
	public static int getScore(List<Card> list)
	{
		int count = 0;
		for (int i = 0, len = list.size(); i < len; i++)
		{
			Card card = list.get(i);

			if (card.name.substring(2, card.name.length()).equals("2"))
			{

				count += 2;
			}

			if (card.name.substring(2, card.name.length()).equals("1"))
			{

				count += 1;
			}
		}
		return count;

	}

	/*
	 * 这是是否会智能化。难点。
	 * 因为存储的每一个顺子，可能会有重复的张牌。所以应该取一次顺子后，在list中把它删掉。然后重新整理，在取。
	 * 直至为空。表明。没有顺子了。在继续取下一个。
	 *  节奏应该是：
	 *   取出顺子集，判断是否为空。 
	 *   不为空。 
	 *   取出一个顺子。并删掉他。
	 *    然后更新顺子集 重复。
	 */
	public static void getModel(List<Card> list1, Model model)
	{
		List list = new ArrayList(list1);
		List lists = get同花顺(list);

		while (lists.size() != 0)
		{
			List<Card> cards = (List<Card>) lists.get(0);
			model.a111111123.add(cards);
			list.removeAll(cards);
			lists = get同花顺(list);
		}

		lists = get四代一(list);

		while (lists.size() != 0)
		{
			List<Card> cards = (List<Card>) lists.get(0);
			model.a41.add(cards);
			list.removeAll(cards);
			lists = get四代一(list);
		}
		
		lists = get双飞带对只(list);

		while (lists.size() != 0)
		{
			List<Card> cards = (List<Card>) lists.get(0);
			model.a1112223344.add(cards);
			list.removeAll(cards);
			lists = get双飞带对只(list);
		}
		
		lists = get双飞带单只(list);

		while (lists.size() != 0)
		{
			List<Card> cards = (List<Card>) lists.get(0);
			model.a11122234.add(cards);
			list.removeAll(cards);
			lists = get双飞带单只(list);
		}
		
		lists = get双飞(list);

		while (lists.size() != 0)
		{
			List<Card> cards = (List<Card>) lists.get(0);
			model.a111222.add(cards);
			list.removeAll(cards);
			lists = get双飞(list);
		}
		
		
		
		lists = get3带2(list);

		while (lists.size() != 0)
		{
			List<Card> cards = (List<Card>) lists.get(0);
			model.a32.add(cards);
			list.removeAll(cards);
			lists = get3带2(list);
		}
		
		lists = get同花(list);

		while (lists.size() != 0)
		{
			List<Card> cards = (List<Card>) lists.get(0);
			model.a11111.add(cards);
			list.removeAll(cards);
			lists = get同花(list);
		}
		
		lists = getS(list);

		while (lists.size() != 0)
		{
			List<Card> cards = (List<Card>) lists.get(0);
			model.a123.add(cards);
			list.removeAll(cards);
			lists = getS(list);
		}
		
		lists = getS2(list);

		while (lists.size() != 0)
		{
			List<Card> cards = (List<Card>) lists.get(0);
			model.a112233.add(cards);
			list.removeAll(cards);
			lists = getS2(list);
		}
		
		lists = get四张(list);

		while (lists.size() != 0)
		{
			List<Card> cards = (List<Card>) lists.get(0);
			model.a4.add(cards);
			list.removeAll(cards);
			lists = get四张(list);
		}
		
		lists = get三不带(list);

		while (lists.size() != 0)
		{
			List<Card> cards = (List<Card>) lists.get(0);
			model.a3.add(cards);
			list.removeAll(cards);
			lists = get三不带(list);
		}
		
		lists = get对子(list);

		while (lists.size() != 0)
		{
			List<Card> cards = (List<Card>) lists.get(0);
			model.a2.add(cards);
			list.removeAll(cards);
			lists = get对子(list);
		}
		
		lists = get单只(list);
		if(lists.size() != 0)
		{
			model.a1.addAll(lists);
			list.remove(lists);
		}

	}
	
}
//封装用于存放花色分类的集合。
class Card_color
{
	List<Card> redTlist = new ArrayList<Card>();
	List<Card> redFlist = new ArrayList<Card>();
	List<Card> blackMlist = new ArrayList<Card>();
	List<Card> blackTlist = new ArrayList<Card>();

}
//封装用于存放牌数分类的集合。
class Card_index
{
	List<Card> list1 = new ArrayList<Card>();
	List<Card> list2 = new ArrayList<Card>();
	List<Card> list3 = new ArrayList<Card>();
	List<Card> list4 = new ArrayList<Card>();
}
