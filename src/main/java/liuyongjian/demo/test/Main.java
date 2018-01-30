package liuyongjian.demo.test;

public class Main {
	public static void test(Super su) {
		su.aa();
	}
	
	public static void test1() {
		new Super(){
			@Override
			public void aa() {
				System.err.println("override Super aa");
			}
		};
	}
	
	public static void main(String[] args) {
		AExtendsSuper su = new AExtendsSuper();
		test(su);
	}
}
