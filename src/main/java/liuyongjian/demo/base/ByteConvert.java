package liuyongjian.demo.base;

import static org.junit.Assert.*;
import org.junit.Test;

public class ByteConvert {
	@Test
	public void aa() {
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
		// 2. int & 0xFF 去除前24位
		// 11111111 11111111 11111111 11100100 & 00000000 00000000 00000000 11111111
		// 3. result: 00000000 00000000 00000000 11100100
		assertEquals(b & 0xFF, a);
	}
	
	@Test
	public void ad() {
		int a = 130; // int截断24高位, 判断剩余8位首位，若为1则为负数，计算后7位补码，若为0不计算后7位
		assertEquals((byte)a, -126);
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
		long l = 100000000000000l;
		int i = (int) l;
		assertEquals(276447232, i);
		
		int a = 10;
		long b = a;
		assertEquals(10, b);
	}
}
