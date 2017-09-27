package homework;

import java.util.ArrayList;
import java.util.Random;
import java.util.Comparator;

public class Outer {
	private ArrayList<Double> random_array;
	class Inner {
		public void initArray(int high,int sum) { 
			/**
			 * high:��Χ
			 * sum:���ɵ���������ܸ���
			 */
			random_array = new ArrayList<Double>();
			Random rand = new Random();
			for(int i=0;i<sum;++i) 
				random_array.add(rand.nextDouble()*high);
		}
	}
	public void showArray() {
		if (random_array.size()!=0) // �����Ѿ���ʼ��
			System.out.println(random_array);
		else //������û�г�ʼ��
			System.out.println("������ڲ����ʼ���������");
	}
	protected void sortArray() { // ʹ�ÿ�������ʱ�临�Ӷ���O(logN)��������ڴ�ͳ��һλλ����
		random_array.sort(new Comparator<Double> () {
			@Override 
			public int compare(Double a,Double b) {
				return (a<b)?-1:1;
			}
		});
	}
	public Double getMax() {
		return random_array.get(random_array.size()-1);
	}
	public Double getMin() {
		return random_array.get(0);
	}
}
