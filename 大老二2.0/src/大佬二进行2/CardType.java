package 大佬二进行2;

public enum CardType {
	/*
	 * 梅花三先斗，第一次出牌必须见梅花三。
	 * 花色  ： 梅花 《 方片 《 红桃 《 黑桃
	 * 顺子 《同花 《 三代二 《 四代一 《 同花顺  A2345最小，23456最大。只到10JQKA
	 */
	c1,//单牌。
	c2,//对子。
	c3,//3不带。
	c4,//炸弹。
	c123,//顺子。
	c11111,//同花
	c32,//3带2。
	c41,//4带1
	c111111123,//同花顺
	c1122,//连队。
	c111222,//飞机。
	c11122234,//飞机带单排.
	c1112223344,//飞机带对子.
	c0//不能出牌
}
