package homework;

import homework.Outer;

public class TestOuter {
	public static final int HIGH = 10; // ����������鷶Χ[0,HIGH)
	public static final int SUM = 10; // ������������С
	public static void showInfo() {
		System.out.println("����Ĵ�С�ǣ�"+SUM);
		System.out.println("����ķ�Χ�ǣ�[0,"+HIGH+")");
	}
	public static void main(String[]args) {
		Outer outer = new Outer();
		Outer.Inner in = outer.new Inner();
		in.initArray(HIGH, SUM);
		showInfo();
		System.out.print("ԭʼ�����ǣ�");
		outer.showArray();
		outer.sortArray(); // ������п�������
		System.out.print("�����������ǣ�");
		outer.showArray();
		System.out.println("�������ǣ�"+outer.getMax());
		System.out.println("��С�����ǣ�"+outer.getMin());
	}
}
