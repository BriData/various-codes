/**
 * @author AsuraDong
 * @time 2017/9/19 21:09
 */
package calculator;
import java.util.*;

class Level {
	protected HashMap<String,Integer> level = new HashMap<String,Integer>();
	Level (){
		level.put("+", 1);
		level.put("-", 1);
		level.put("*", 3);
		level.put("/", 3);
		level.put("cos",5);
		level.put("sin",5);
		level.put("(", 7);
		level.put(")", 7);
	}
	public void addLevel(String op,int lev) {
		level.put(op, lev);
	}
	public HashMap<String,Integer> getLevel() {
		return level;
	}
}

public class Transform extends Level{
	private Stack<String> op_stack  = new Stack<String>(); // operator_stack
	private Stack<String> res_stack = new Stack<String>(); // result_stack
	public String calc="";
	Transform (String calc){	
		this.calc = calc.replaceAll(" ","");
		if(this.calc.substring(0, 1).equals("-")) 
			this.calc = "0" + this.calc;
	}
	public void transCalc() {
		String number = "0123456789";
		String operator = "+/-*()";
		String letter = "abcdefghijklmnopqrstuvwxyz";
		for(int i=0;i<calc.length();++i) {
			String op = calc.substring(i, i+1); //op��ǰ���Ż�����
			if(number.indexOf(op)!=-1){ // ����ֱ��������ջ
				String multi_num = op;				
				while(i+1<calc.length() &&  ( calc.substring(i+1,i+2).equals(".") ||  number.indexOf(calc.substring(i+1, i+2))!=-1 )) { //�����һ���ַ������ֵĻ�
					i = i+1;
					op = calc.substring(i, i+1); //�õ���һ���ַ�
					multi_num += op;
				}
				res_stack.push(multi_num);
			} 
			else if (operator.indexOf(op)!=-1) { // ����Ƿ���
				if(!op_stack.isEmpty())  {
					String pre_op = op_stack.peek(); // ����ջ�е���һ��������
					while(level.get(pre_op)>=level.get(op) && !pre_op.equals("(")) { // ע��Ҫ�ж������ŵ�����
						res_stack.push(op_stack.pop()); // ������ջ�е���һ��������������������ջ
						if (!op_stack.isEmpty())
							pre_op = op_stack.peek();
						else break;
					}
				}
				if (op.equals(")")) {
					String pre_op = op_stack.peek(); 
					while(!pre_op.equals("(")) {
						res_stack.push(op_stack.pop());
						pre_op = op_stack.peek(); 
					}
					op_stack.pop(); // ���һ���������ţ�����
				}
				else 
					op_stack.push(op);
			} 
			else if (letter.indexOf(op)!=-1){ // ����cos��sin��tan��sqrt�����������
				String multi_num = op; 
				while(i+1<calc.length() && letter.indexOf(calc.substring(i+1,i+2))!=-1) {
					i = i+1;
					op = calc.substring(i,i+1);
					multi_num+=op;
				}
				op_stack.push(multi_num);
			}
		}
		while(!op_stack.isEmpty()) {
			res_stack.push(op_stack.pop());
		}
	}
	public double getResult() {
		String operator = "+/-*()";
		String other_operator = "cos sin";
		Vector<String> res_vec = new Vector<String>();
		while(!res_stack.isEmpty()) {
			res_vec.add(0,res_stack.pop());
		}
		for(int i=0;i<res_vec.size() && res_vec.size()>1 ;++i) {
			if(operator.indexOf(res_vec.get(i))!=-1) { // �������������
				double  last_num = Double.parseDouble(res_vec.get(i-1));
				double last_last_num = Double.parseDouble(res_vec.get(i-2));
				double temp = operateElem(last_last_num,last_num,res_vec.get(i));
				res_vec.remove(i);
				res_vec.remove(i-1);
				res_vec.remove(i-2);
				res_vec.add(i-2,Double.toString(temp));
				i = i-2;
			}
			else if (other_operator.indexOf(res_vec.get(i))!=-1) {
				double last_num = Double.parseDouble(res_vec.get(i-1));
				double temp = moreOperateElem(last_num,res_vec.get(i));
				res_vec.remove(i);
				res_vec.remove(i-1);
				res_vec.add(i-1,Double.toString(temp));
				i = i-1;
			}
		}
		return Double.parseDouble(res_vec.get(0)); // ���Ľ��ջһ��ֻ��һ��ֵ
	}
	public void showResStack() {
		System.out.println(res_stack);
	}
	private double moreOperateElem(double last_num,String tag) {
		switch(tag) {
		case "cos":
			return Math.cos(last_num);
		case "sin":
			return Math.sin(last_num);
		default:
			return 0;
		}
	}
	private double operateElem(double a,double b,String tag) { // �������
		switch(tag) {
		case "+":
			return a+b;
		case "-":
			return a-b;
		case "/":
			return a/b;
		case "*":
			return a*b;
		default:
			return 0;
		}
	}
	public static void main(String[]args) {
		Scanner scan2 = new Scanner(System.in);
		Scanner scan = new Scanner(System.in);
		int n = scan2.nextInt();
		
		while(true) {
			if(n==0)
				break;
//			System.out.println("�����������ʽ��");
			String calc = scan.nextLine();
//			if (calc.equals("q") || calc.equals("quit"))
//				break;
			Transform mytrans  = new Transform(calc.substring(0, calc.length()-1));
			mytrans.transCalc();
//			System.out.print("��׺���ʽ��");
//			mytrans.showResStack();
			double res = mytrans.getResult();
			System.out.printf("%.4f\n", res);
			n--;
		}
	}
}