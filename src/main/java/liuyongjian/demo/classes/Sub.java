package liuyongjian.demo.classes;

public class Sub implements Super1, Super2{

	public static void main(String[] args) {
		new Sub().print();
	}

	// 必须显示表示出来，优先级
	// 1. 父类显示声明；2.具体提供默认方法的接口；3.显示声明具体用到的方法；
	@Override
	public void print() {
		Super1.super.print();
		Super2.super.print();
	}
}
