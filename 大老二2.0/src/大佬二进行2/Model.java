package 大佬二进行2;

import java.util.ArrayList;
import java.util.List;

/*
 * 取出list a123 中的每一个，都是个顺子。
 */
public class Model {
	//一组牌
		int value; //权值
		int num;// 手数 (几次能够走完，没有挡的情况下)
		List a1=new ArrayList(); //单张
		List a2=new ArrayList(); //对子
		List a3=new ArrayList(); //3带
		List a4=new ArrayList(); //4张
		
		List a123=new ArrayList(); //连子
		List a11111 = new ArrayList();//同花
		List a32 = new ArrayList();//3带2
		List a41 = new ArrayList();//4带1
		List a111111123 = new ArrayList();//同花顺
		List a112233=new ArrayList(); //连对
		List a111222=new ArrayList(); //飞机
		List a11122234 = new ArrayList();//飞机带单排
		List a1112223344 = new ArrayList();//飞机带双排
}
