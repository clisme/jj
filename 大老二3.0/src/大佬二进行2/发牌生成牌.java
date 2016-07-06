package 大佬二进行2;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class 发牌生成牌 {

	public static ArrayList<Card>[] playerList = new ArrayList[3];
	public static ArrayList<Card> lordList;
	public static Card[] card = new Card[52];

	public static void CardInit() {

		int count = 1;
		// 初始化牌
		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j <= 13; j++) {
				// 去除梅花三
				if (i == 4 && j == 3) {
					continue;
				}
				card[count] = new Card(i + "-" + j);
				count++;

			}
		}

		// 打乱顺序

		for (int i = 0; i < 100; i++) {
			Random random = new Random();
			int a = random.nextInt(50) + 1;
			int b = random.nextInt(50) + 1;
			Card k = card[a];
			card[a] = card[b];
			card[b] = k;
		}
		// 开始发牌
		for (int i = 0; i < 3; i++)
			playerList[i] = new ArrayList<Card>(); // 玩家牌
		int t = 0;
		for (int i = 1; i <= 51; i++) {

			switch ((t++) % 3) {
			case 0:

				playerList[0].add(card[i]);
				break;
			case 1:
				// 我

				playerList[1].add(card[i]);

				break;
			case 2:

				playerList[2].add(card[i]);
				break;
			}

		}
	}
	
	public static void main(String[] args) {
		发牌生成牌.CardInit();
		System.out.println(发牌生成牌.playerList[0]);
		System.out.println(发牌生成牌.playerList[1]);
		System.out.println(发牌生成牌.playerList[2]);
	}

}
