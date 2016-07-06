package ���ж�����2;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 * ���Ƶĸ��ֲ����ࡣ
 */
public class Common
{
	
	
	/*
	 * ʵ��˼·����һ�������У������±����Ƶ�Value�������ŵ�������������
	 * a[getValue(list2.get(i))]++;
		Ȼ���ҵ�����������ơ�
		�ӽ�һ�������ؼ��ϡ�
	 */
	// 1 �����ظ���������    ��list���ظ������������ǰ�档
	public static List<Card> getOrder2(List<Card> list)
	{

		List<Card> list3 = new ArrayList<Card>();//�����ص����м��ϡ�
		int len = list.size();
		
		//�����±����Ƶ�Value�������ŵ�������������
		int a[] = new int[20];
		for(int i = 0; i < 20; i++)
		{
			a[i] = 0;
		}
		for (int i = 0; i < len; i++)
		{	
			a[getValue(list.get(i))]++;
		}
		//���ڴ������������ơ�
		int max = 0;
		for (int i = 0; i < 20; i++)
		{
			max = 0;
			//�ҵ�����������
			for (int j = 20 - 1; j >= 0; j--)
			{
				if (a[j] > a[max])
					max = j;//���
			}
			//��list�����У��ҵ����еģ�����������ƣ���Ȼ��ӽ����ؼ���
			for (int k = 0; k < len; k++)
			{
				if (getValue(list.get(k)) == max)
				{
					list3.add(list.get(k));
				}
			}
			//������������
			a[max] = 0;
		}
		return list3;
	}

	/*
	 * ʵ��˼·�����Ƽ��ϣ��ֺù��󣬣������ӣ����ţ��ֱ��ţ���
	 * ȡ��list3�е��������ţ��Ͷ����е�ǰ���š��ӽ�һ�����ϡ�
	 */
	// 2������������
	public static List get3��2(List<Card> list)
	{
		//�����ص����У��������ļ��ϡ�
		List lists = new ArrayList();
		
		//����������
		Card_index card_index = new Card_index();
		getMax(card_index, list);

		List<Card> list2 = card_index.list2;
		List<Card> list3 = card_index.list3;
		
		if (list2.size() > 0 && list3.size() > 0)
		{
			//ȡ�����ӽ�ȥ��
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
	 * ʵ��˼·: �Ȱ�list�����е��ư���ͬ�����ּ��ϡ�ȡ��list1,Ȼ������
	 * ������ϣ���������Ƶ���ֵ�����Ų���ͬ��˵������˳�ӡ�
	 */
	// 3 ����һ��list�����е�˳�ӡ�
	public static List getS(List<Card> list)
	{
		//���ơ�
		Card_index card_index = new Card_index();
		getMax(card_index, list);
		List<Card> list1 = card_index.list1;
		//��С����
		order(list1);

		List lists = new ArrayList();

		int length = list1.size();

		//���ҵ�˳�ӵ��±ꡣk , i 
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
					//���ҵ���˳�����һ�����ϡ�
					for (int a = i; a <= k; a++)
					{
						cardList.add(list1.get(a));

					}
					//�ӽ������صļ��ϡ�
					lists.add(cardList);
				}
			}
		}
		return lists;
	}

	/*
	 * ʵ��˼·�� ��list���ϣ����š�Ȼ��õ������Ӽ��ϡ�
	 * ���żӽ�һ�����ϡ�
	 */
	// 4���ص����ӡ�
	public static List get����(List<Card> list)
	{
		List lists = new ArrayList();
		//���ơ�
		Card_index card_index = new Card_index();
		getMax(card_index, list);

		List<Card> list2 = card_index.list2;
		
		if (list2.size() > 0)
		{	//�ӽ����ϡ�
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
	 * ʵ��˼·�� ���ƣ�Ȼ��õ����ż��ϡ�
	 */
	// 5���ص�ֻ
	public static List get��ֻ(List<Card> list)
	{

		Card_index card_index = new Card_index();
		getMax(card_index, list);

		List<Card> list1 = card_index.list1;
		return list1;

	}
	
	/*
	 * ʵ��˼·�����ƣ�Ȼ�󣬵õ����Ӽ��ϡ�����Ȼ��ȡ���������ϡ�
	 * �������˳�ӣ�˵���������ԡ�
	 */
	// 6 �������ԡ�
	public static List getS2(List<Card> listu)
	{
		//����
		Card_index card_index = new Card_index();
		getMax(card_index, listu);
		List<Card> list = card_index.list2;
		//����
		order(list);

		List lists = new ArrayList();

		List<Card> listd = new ArrayList<Card>();

		//ȡ�������ơ�
		for (int i = 0; i < list.size(); i += 2)
		{
			listd.add(list.get(i));
		}

		int length = listd.size();
		//�ж��������ǲ���˳�ӡ�
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
		//�õ�˳�ӣ�Ȼ��������ϣ���˳������ֵ��ͬ���ƣ��ӽ����ϡ�
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
	 * ʵ��˼·��ֱ�ӷ��ƣ��Ϳɵõ���
	 */
	// 7����������
	public static List get������(List<Card> list)
	{
		List lists = new ArrayList();
		//����
		Card_index card_index = new Card_index();
		getMax(card_index, list);

		List<Card> list3 = card_index.list3;
		//�ӽ�����
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
	 * ʵ��˼·���õ���ٷ��� ���ƵȲ������󣬵õ���ͺ���ϡ�
	 * ���ֻ�����ţ����ж��ǲ�����ֵ��һ��
	 * ���ţ�ʮ���ţ���������
	 */
	// 8����˫������
	public static List get˫��(List<Card> list)
	{
		List lists = new ArrayList();
		//����
		Card_index card_index = new Card_index();
		getMax(card_index, list);
		List<Card> list3 = card_index.list3;

		order(list3);

		//����ʱ�ж��ǲ������š�
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
	 * �ȰѼ����е�˫��ȡ�������ڼ��ϵ�ֻ��
	 */
	// 9����˫�ɴ���ֻ
	public static List get˫�ɴ���ֻ(List<Card> list)
	{
		List lists = new ArrayList();

		Card_index card_index = new Card_index();
		getMax(card_index, list);

		List<Card> list1 = card_index.list1;
		
		List listd = get˫��(list);
		if (listd.size() != 0 && list1.size() >= 2)
		{
			for (int i = 0; i < listd.size(); i++)
			{
				List lista = (List) listd.get(i);
				// ���������6������Ҫ��2����ֻ��
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
	 * �ȰѼ����е�˫��ȡ�����ڼ��϶��ӡ�
	 */
	// 10����˫�ɴ�����
	public static List get˫�ɴ���ֻ(List<Card> list)
	{
		List lists = new ArrayList();

		Card_index card_index = new Card_index();
		getMax(card_index, list);

		List<Card> list1 = card_index.list2;
		
		List listd = get˫��(list);
		if (listd.size() != 0 && list1.size() >= 4)
		{
			for (int i = 0; i < listd.size(); i++)
			{
				List lista = (List) listd.get(i);
				// ���������6������Ҫ��2����ֻ��
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
	 * �ֹ��ƺ󣬴�list4�У�ȡ�ĸ�����list1��ȡһ����
	 */
	// 11�����Ĵ�1
	public static List get�Ĵ�һ(List<Card> list)
	{
		List lists = new ArrayList();
		//����
		Card_index card_index = new Card_index();
		getMax(card_index, list);

		List<Card> list1 = card_index.list1;
		List<Card> list4 = card_index.list4;
		

		if (list1.size() > 0 && list4.size() > 0)
		{
			//��list4�а��ƣ��ӽ�ȥ��
			for (int i = 0; i < list4.size(); i += 4)
			{
				List<Card> lista = new ArrayList<Card>();
				lista.add(list4.get(i));
				lista.add(list4.get(i + 1));
				lista.add(list4.get(i + 2));
				lista.add(list4.get(i + 3));
				//��list1�а��ƣ��ӽ�ȥ��
				lista.add(list1.get(0));

				lists.add(lista);

			}
		}
		return lists;

	}

	/*
	 * �ֹ��ƺ�ֱ�Ӵ�list4��ȡ��
	 */
	// 12��������
	public static List get����(List<Card> list)
	{
		List lists = new ArrayList();
		//����
		Card_index card_index = new Card_index();
		getMax(card_index, list);

		List<Card> list4 = card_index.list4;
		

		if (list4.size() > 0)
		{

			//�ӽ���
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
	 * ����ɫ���ƹ��󣬴Ӹ��ֻ�ɫ��ȡ��5�š�ȡ����ǰ4�ţ������һ�š�
	 */
	// 13����ͬ��
	public static List getͬ��(List<Card> list)
	{
		List lists = new ArrayList();
		//����ɫ���ơ�
		Card_color card_color = new Card_color();
		colorList(card_color, list);

		int blackTlength = card_color.blackTlist.size();
		//���һ�ֻ�ɫ��>5�š��ӽ�����ǰ4���������һ����
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
	 * �Ȱ��ƵĻ�ɫ���ơ�Ȼ�����ж�ÿ�������ǲ���˳�ӡ�
	 */
	// 14����ͬ��˳
	public static List getͬ��˳(List<Card> list)
	{
		List lists = new ArrayList();
		//����ɫ����
		Card_color card_color = new Card_color();
		colorList(card_color, list);
		//���ÿ�����ϴ���0����ȥ�ж��ǲ���˳�ӡ�
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
	 * ����list�����е�ÿһ���ƣ�ȡ���ƵĻ�ɫ���ӽ���Ӧ�ļ��ϡ�
	 */
	// 15���Ʒֻ�ɫ��
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
	 * �Ƶ������� ��ɫ-��ֵ������ֱ�ӽ�ȡ�ַ����Ϳ����ˡ�
	 * Ȼ��ת����������ֵ��
	 */
	// 16 ���ػ�ɫ
	public static int getColor(Card card)
	{
		//parseInt()  �����ַ���ת�������κ�����
		return Integer.parseInt(card.name.substring(0, 1));
	}

	/*
	 * �Ƶ������� ��ɫ-��ֵ������ֱ�ӽ�ȡ�ַ����Ϳ����ˡ�
	 * Ȼ��ת����������ֵ��A �� 2 ����ֵ��13 
	 */
	// 17 ������ֵ
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
	 * ȡ��ÿ����ֵ���ӽ���Ӧ�ļ����Ｔ�ɡ�
	 */
	// 18 �õ������ͬ��,������ͬ���Ƽ���һ������� ���Ʒ�����
	public static void getMax(Card_index card_index, List<Card> list)
	{

		List lists = new ArrayList();
		// 1-13����һ��
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
	
		//ȡ��ÿ���Ƽ����Ӧ�ļ��ϡ�
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

		//��ȥ��ֹlist1234�������У��ǿյ����󣬵��´���
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

		//�����е���ͬ�������Ʒ���һ�������
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
	 * �õ����Գ��������ͣ�����ҵ�ѡҪ�����Ƶ����͡���ͬ�Ƚϴ�С�Ϳ����ˡ�
	 */
	// 19���ѡ���Ƿ��ܳ�
	public static boolean checkCards(List<Card> c, List<Card>[] current)
	{
		// �ҳ���ǰ���������ĸ����Գ���,c�ǵ�ѡ����
		List<Card> currentlist = (current[0].size() > 0) ? current[0]
				: current[2];
		//���������ϵ�������
		order(currentlist);
		order(c);
		//�ó�ѡ�Ƶ����͡�
		CardType cType = jugdeType(c);
		//�ó������Ƶ����͡�
		CardType currentType = jugdeType(currentlist);

		// ���������ֱͬ�ӹ���
		if (c.size() != currentlist.size())
		{
			return false;
		}

		// �������������ж�
		if (c.size() == 5 && currentlist.size() == 5)
		{
			//�����������˳�ӡ�
			if (currentType == CardType.c123)
			{	
				//�����ѡ����˳�ӡ�ȡ���жϴ�С���ɡ�
				if (cType == CardType.c123)
				{
					// ������С����ǰ�棬����ں��档
					order(currentlist);
					order(c);

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
				//�����ѡ����ͬ�������������Ĵ�һ��ͬ��˳��ֱ��ѹ����
				if (cType == CardType.c11111 || cType == CardType.c32
						|| cType == CardType.c41
						|| cType == CardType.c111111123)
				{
					return true;
				}
			}
			//�����������ͬ����
			if (currentType == CardType.c11111)
			{
				//�����ѡ����ͬ����ͬ��ɫ��������жϴ�С��
				//��ͬ��ɫ�ģ���ɫ���С��
				if (cType == CardType.c11111)
				{

					// ������С����ǰ�棬����ں��档
					order(currentlist);
					order(c);

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
				//��������ֱ��ѹ���ġ�
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
					//�ظ���������
					c = getOrder2(c);
					currentlist = getOrder2(currentlist);

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
					c = getOrder2(c);
					currentlist = getOrder2(currentlist);

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
					order(currentlist);
					order(c);

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
				// ��ֻ�Ͷ���  	���������ж���ֵ��
				if (cType == CardType.c1 || cType == CardType.c2)
				{
					// ������С����ǰ�棬����ں��档
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
						// ���ֵ��ͬ���Ȼ�ɫ��

						// �����ɫ����ͬ����ɫ��ֵС���Ǹ���
						if (Common.getColor(card1) - Common.getColor(card2) > 0)
						{
							return false;
						}
						else return true;
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
				//�Ź�����ֱ�ӱȽϵ�һ����ֵ�����ˡ�
				if (cType == CardType.c1112223344
						|| cType == CardType.c11122234)
				{
					//����������
					c = getOrder2(c);
					currentlist = getOrder2(currentlist);

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

	/*
	 * ��¼list��ÿ���ƵĻ�ɫ��Ȼ��ȡ��һ����ֻҪʣ�µ����л�ɫ����ȫ����ͬ��
	 * ��֤����ͬ����
	 */
	// 20�����ж��У������ж��Ƿ���ͬ��
	public static boolean ͬ��(List<Card> list)
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

	// 20 �ж�����
	public static CardType jugdeType(List<Card> list)
	{
		//ֽ�ƴ�С����
		order(list);
		// ��Ϊ֮ǰ��������ԱȽϺ��ж�
		int len = list.size();
		// ����,���ӣ�3������4��һ��
		if (len <= 4)
		{ // �����һ����������ͬ��˵��ȫ����ͬ
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
		// ��5������ʱ�����֣�3��2���ɻ���2˳��4��1�ȵ�

		// ���ڰ���ͬ�������������������ǰ�档
		Card_index card_index = new Card_index();
		getMax(card_index, list);

		if (len == 5)
		{
			// ͬ��˳
			if (ͬ��(list)
					&& (card_index.list1.size() == len)
					&& (Common.getValue(list.get(len - 1))
							- Common.getValue(list.get(0)) == len - 1))
				return CardType.c111111123;

			// ˳��  ��һ���͵����һ���Ĳ�ֵ�����в������ֵ�
			if ((card_index.list1.size() == len)
					&& (Common.getValue(list.get(len - 1))
							- Common.getValue(list.get(0)) == len - 1))
				return CardType.c123;
			// ͬ��
			if (ͬ��(list))
				return CardType.c11111;

			// 3��2
			if (card_index.list3.size() == 3 && card_index.list2.size() == 2
					&& len == 5)
				return CardType.c32;
			// 4��1
			if (card_index.list4.size() == 4 && card_index.list1.size() == 1
					&& len == 5)
				return CardType.c41;
		}

		if (len > 5)
		{

			// ����
			if (card_index.list2.size() == len
					&& len % 2 == 0   //˵���Ƕ���
					&& len / 2 >= 3	  //˵������Ҫ��������  ��ֵ����ڳ��ȡ�2-1
					&& (Common.getValue(list.get(len - 1))
							- Common.getValue(list.get(0)) == (len / 2 - 1)))
				return CardType.c1122;
			// �ɻ�
			if (card_index.list3.size() == len
					&& (len % 3 == 0)
					&& (Common.getValue(list.get(len - 1))
							- Common.getValue(list.get(0)) == (len / 3 - 1)))
				return CardType.c111222;
			// �ɻ���n��,n/2��
			if (card_index.list3.size() == len / 4 * 3
					&& (Common
							.getValue((card_index.list3.get(len / 4 * 3 - 1)))
							- Common.getValue((card_index.list3.get(0))) == (len / 4 - 1))
					&& card_index.list1.size() == len / 4)
				return CardType.c11122234;

			// �ɻ���n˫

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
	 * 21ֽ�ƴ�С���򡣱Ƚ����У�ֱ��ȡ���Ƶ���ֵ���бȽϡ�
	 * �����ֵ��ͬ���Ƚϻ�ɫ��
	 */
	public static void order(List<Card> list)
	{
		Collections.sort(list, new Comparator<Card>()
		{
			@Override
			public int compare(Card o1, Card o2)
			{
				// TODO Auto-generated method stub
				int a1 = Integer.parseInt(o1.name.substring(0, 1));// ��ɫ
				int a2 = Integer.parseInt(o2.name.substring(0, 1));
				int b1 = Integer.parseInt(o1.name.substring(2, o1.name.length()));// ��ֵ
				int b2 = Integer.parseInt(o2.name.substring(2, o2.name.length()));
				int flag = 0;

				// �����A����2
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
	 * 22 �Ƚ����У�����ɫ��С�����ء���ɫ��ͬ�İ���ֵ�Ƚϡ�
	 */
	public static void order��ɫ(List<Card> list)
	{
		Collections.sort(list, new Comparator<Card>()
		{
			@Override
			public int compare(Card o1, Card o2)
			{
				// TODO Auto-generated method stub
				int a1 = Integer.parseInt(o1.name.substring(0, 1));// ��ɫ
				int a2 = Integer.parseInt(o2.name.substring(0, 1));
				int b1 = Integer.parseInt(o1.name.substring(2, o1.name.length()));// ��ֵ
				int b2 = Integer.parseInt(o2.name.substring(2, o2.name.length()));
				int flag = 0;

				// �����A����2
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


	// 23 �ƶ�Ч���ĺ���,���ڷ���
	public static void move(Card card, Point from, Point to)
	{
		if (to.x != from.x)
		{
			double k = (1.0) * (to.y - from.y) / (to.x - from.x);
			double b = to.y - to.x * k;
			int flag = 0;// �ж������������ƶ�����
			if (from.x < to.x)
				flag = 20;
			else
			{
				flag = -20;
			}
			for (int i = from.x; Math.abs(i - to.x) > 20; i += flag)
			{
				double y = k * i + b;// ������Ҫ�õ���ѧ�е����Ժ���

				card.setLocation(i, (int) y);
				try
				{
					Thread.sleep(4); // �ӳ٣����Լ�����
				}
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// λ��У׼
		card.setLocation(to);
	}

	// 24 ���¶�λ    flag�������1 ,2 ��������
	public static void rePosition(Main m, List<Card> list, int flag)
	{
		Point p = new Point();
		if (flag == 0)
		{
			p.x = 150;
			p.y = (450 / 2 - 14) - (list.size() + 1) * 15 / 2;
		}
		if (flag == 1)
		{// �ҵ����� _y=450 width=830
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

	// 25 ����֮ǰ�������� ���ò��ɼ���
	public static void hideCards(List<Card> list)
	{
		for (int i = 0, len = list.size(); i < len; i++)
		{
			list.get(i).setVisible(false);
		}
	}

	// ����Ȩֵ�����Ƿ�������÷������A �� 2 �������������
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
	 * �����Ƿ�����ܻ����ѵ㡣
	 * ��Ϊ�洢��ÿһ��˳�ӣ����ܻ����ظ������ơ�����Ӧ��ȡһ��˳�Ӻ���list�а���ɾ����Ȼ������������ȡ��
	 * ֱ��Ϊ�ա�������û��˳���ˡ��ڼ���ȡ��һ����
	 *  ����Ӧ���ǣ�
	 *   ȡ��˳�Ӽ����ж��Ƿ�Ϊ�ա� 
	 *   ��Ϊ�ա� 
	 *   ȡ��һ��˳�ӡ���ɾ������
	 *    Ȼ�����˳�Ӽ� �ظ���
	 */
	public static void getModel(List<Card> list1, Model model)
	{
		List list = new ArrayList(list1);
		List lists = getͬ��˳(list);

		while (lists.size() != 0)
		{
			List<Card> cards = (List<Card>) lists.get(0);
			model.a111111123.add(cards);
			list.removeAll(cards);
			lists = getͬ��˳(list);
		}

		lists = get�Ĵ�һ(list);

		while (lists.size() != 0)
		{
			List<Card> cards = (List<Card>) lists.get(0);
			model.a41.add(cards);
			list.removeAll(cards);
			lists = get�Ĵ�һ(list);
		}
		
		lists = get˫�ɴ���ֻ(list);

		while (lists.size() != 0)
		{
			List<Card> cards = (List<Card>) lists.get(0);
			model.a1112223344.add(cards);
			list.removeAll(cards);
			lists = get˫�ɴ���ֻ(list);
		}
		
		lists = get˫�ɴ���ֻ(list);

		while (lists.size() != 0)
		{
			List<Card> cards = (List<Card>) lists.get(0);
			model.a11122234.add(cards);
			list.removeAll(cards);
			lists = get˫�ɴ���ֻ(list);
		}
		
		lists = get˫��(list);

		while (lists.size() != 0)
		{
			List<Card> cards = (List<Card>) lists.get(0);
			model.a111222.add(cards);
			list.removeAll(cards);
			lists = get˫��(list);
		}
		
		
		
		lists = get3��2(list);

		while (lists.size() != 0)
		{
			List<Card> cards = (List<Card>) lists.get(0);
			model.a32.add(cards);
			list.removeAll(cards);
			lists = get3��2(list);
		}
		
		lists = getͬ��(list);

		while (lists.size() != 0)
		{
			List<Card> cards = (List<Card>) lists.get(0);
			model.a11111.add(cards);
			list.removeAll(cards);
			lists = getͬ��(list);
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
		
		lists = get����(list);

		while (lists.size() != 0)
		{
			List<Card> cards = (List<Card>) lists.get(0);
			model.a4.add(cards);
			list.removeAll(cards);
			lists = get����(list);
		}
		
		lists = get������(list);

		while (lists.size() != 0)
		{
			List<Card> cards = (List<Card>) lists.get(0);
			model.a3.add(cards);
			list.removeAll(cards);
			lists = get������(list);
		}
		
		lists = get����(list);

		while (lists.size() != 0)
		{
			List<Card> cards = (List<Card>) lists.get(0);
			model.a2.add(cards);
			list.removeAll(cards);
			lists = get����(list);
		}
		
		lists = get��ֻ(list);
		if(lists.size() != 0)
		{
			model.a1.addAll(lists);
			list.remove(lists);
		}

	}
	
}
//��װ���ڴ�Ż�ɫ����ļ��ϡ�
class Card_color
{
	List<Card> redTlist = new ArrayList<Card>();
	List<Card> redFlist = new ArrayList<Card>();
	List<Card> blackMlist = new ArrayList<Card>();
	List<Card> blackTlist = new ArrayList<Card>();

}
//��װ���ڴ����������ļ��ϡ�
class Card_index
{
	List<Card> list1 = new ArrayList<Card>();
	List<Card> list2 = new ArrayList<Card>();
	List<Card> list3 = new ArrayList<Card>();
	List<Card> list4 = new ArrayList<Card>();
}
