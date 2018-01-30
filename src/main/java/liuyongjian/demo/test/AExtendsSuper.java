package liuyongjian.demo.test;

public class AExtendsSuper extends Super {
	@Override
	public void aa() {
		System.err.println("AExtendsSuper aa");
	}
	
	public void ab() {
		bb();
	}
}
