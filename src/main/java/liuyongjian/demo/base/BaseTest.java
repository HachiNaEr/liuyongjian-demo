package liuyongjian.demo.base;

import static org.junit.Assert.*;

import org.junit.Test;

public class BaseTest extends Object{
	@Test
	public void aa() {
		// 二进制，八进制，十进制，十六进制比较
		int a = 0b1011;
		int b = 013;
		int c = 11;
		int d = 0xb;

		assertEquals(a, b);
		assertEquals(a, c);
		assertEquals(a, d);
		assertEquals(b, c);
		assertEquals(b, d);
		assertEquals(c, d);
	}
	
	@Test
	public void ab() {
		assertEquals(28, 0b00000000000000000000000000011100);
		assertEquals(-28, 0b11111111111111111111111111100100);
		// 负整数表示方式: 正整数的补码, 先取反码, 然后加1.
	}
        
	@Test
	public void ac() {
		int a = 228; // 00000000 00000000 00000000 11100100
		byte b = -28;
		// 1. byte转换为int 11111111 11111111 11111111 11100100
		// 2. 11111111 11111111 11111111 11100100 & 00000000 00000000 00000000 11111111 
		// 3. result: 00000000 00000000 00000000 11100100
		assertEquals(a, b & 0xFF);
	}
	
	@Test
	public void ad() {
		int a = 130; // int截断24高位, 判断剩余8位首位，若为1则为负数，计算后7位补码，若为0不计算后7位
		assertEquals(-126, (byte)a);
	}
	
	@Test
	public void ae() {
		// 任一中文
		String chineseChar = "昺";
		byte[] bytes = chineseChar.getBytes();

		assertEquals("111", Integer.toBinaryString(bytes[0] & 0xFF).substring(0, 3));
		assertEquals("10",  Integer.toBinaryString(bytes[1] & 0xFF).substring(0, 2));
		assertEquals("10",  Integer.toBinaryString(bytes[2] & 0xFF).substring(0, 2));
	}
	
	@Test
	public void af() {
		// 强制类型转换
		long l = 100000000000000l;
		int i = (int) l;
		assertEquals(276447232, i);
		
		int a = 10;
		long b = a;
		assertEquals(10, b);
	}
	
	@Test
	public void ag() {
		// 移位操作符
		int number = 10;
		number >>= 1;
		assertEquals(5, number);
		
		number <<= 1;
		assertEquals(10, number);
	}
	
	@Test
	public void ah() {
		assertEquals("str11",  "str" + 1 + 1);
		assertEquals("2str",   1 + 1 + "str");
		assertEquals("3str12", 1 + 2 + "str" + 1 + 2);
	}
	
	@Test
	public void ai() {
		// E为指数基数10
		assertEquals(200000, 20e4, 0);
	}
	
	@Test
	public void aj() {
		// 数组初始化
		int[] ints = {0, 1};
		int[] intss = new int[2];
		int[] intsss = new int[]{0, 1};
		intss[0] = 0;
		intss[1] = 1;
		assertArrayEquals(intss, ints);
		assertArrayEquals(intsss, intss);
	}
	
	@Test
	public void ak() {
		
	}
	
	@Test
	public void al() {
		
	}
	
	@Test
	public void am() {
		
	}
	
	@Test
	public void an() {
		
	}
}
