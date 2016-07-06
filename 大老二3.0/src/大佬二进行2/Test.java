package 大佬二进行2;

public class Test {
	public static String i;
	public void seti()
	{
		if(i == null)
		{
			i = "zhangsan";
			return;
		}
		else if("zhangsan".equals(i))
		{
			i = "lisi";
			return;
		}
		else if ("lisi".equals(i))
		{
			i = "wangwu";
			return;
		}
	}
	public static void main(String[] args) {
		Test test = new Test();
		test.seti();
		System.out.println(Test.i);
		test.seti();
		System.out.println(Test.i);

	}
}
