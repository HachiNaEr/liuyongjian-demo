package liuyongjian.demo.base;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Base64;
import java.util.stream.Stream;

import org.junit.Test;

import liuyongjian.demo.annotation.Person;

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
		// Base64
		String str = "中文@#￥%……&*bc";
		byte[] bytes = Base64.getEncoder().encode(str.getBytes());
		String resultStr = new String(bytes);
		assertEquals("5Lit5paHQCPvv6Ul4oCm4oCmJipiYw==", resultStr);
		
		String decodeStr = new String(Base64.getDecoder().decode(resultStr));
		assertEquals(str, decodeStr);
	}
	
	@Test
	public void al() {
		// Duration 表示对于时间的区间比较
		LocalDateTime l1 = LocalDateTime.of(2018, 2, 15, 12, 10);
		LocalDateTime l2 = LocalDateTime.of(2018, 2, 15, 12, 12);
		Duration d1 = Duration.between(l1, l2);
		assertEquals(2, d1.toMinutes());
	}
	
	@Test(expected = DateTimeException.class)
	public void am() {
		// instant用于便于机器处理，LocalDateTime用于便于人阅读，不能混用
		Instant instant = Instant.now();
		LocalDateTime l1 = LocalDateTime.of(2018, 2, 15, 12, 10);
		Duration.between(instant, l1);
	}
	
	@Test
	public void an() {
		LocalDate localDate = LocalDate.of(2018, 1, 2);
		// 下周一（含当天）
		LocalDate localDate2 = localDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
		// 当月最后一天
		LocalDate localDate3 = localDate2.with(TemporalAdjusters.lastDayOfMonth());
		
		assertEquals("2018-01-08", localDate2.toString());
		assertEquals("2018-01-31", localDate3.toString());
	}
	
	@Test
	public void ao() {
		// 实现当前日期的下一个工作日时间，用TemporalAdjusters实现比较复杂的时间处理
		TemporalAdjuster nextWorkingDay = TemporalAdjusters.ofDateAdjuster(temporal -> {
			DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
			int dayToAdd = 1;
			if (dow == DayOfWeek.FRIDAY) {
				dayToAdd = 3;
			}
			if (dow == DayOfWeek.SATURDAY) {
				dayToAdd = 2;
			}
			return temporal.plus(dayToAdd, ChronoUnit.DAYS);
		});
		LocalDate localDate = LocalDate.of(2018, 1, 5);
		assertEquals("2018-01-08", localDate.with(nextWorkingDay).toString());
	}
	
	@Test
	@Person(name = "liuyongjian", age = 125)
	@Person(name = "yongjianliu", age = 101)
	@Person(name = "hello world", age = 100)
	public void ap() {
		// 测试多注解语法
		String myName = "ap";
		
		for (Method method : getClass().getMethods()) {
			if (myName.equals(method.getName())) {
				Person[] person = method.getAnnotationsByType(Person.class);
				for (int index = 0; index < person.length; index++) {
					switch (index) {
					case 0:
						assertEquals("liuyongjian", person[index].name());
						break;
					case 1:
						assertEquals("yongjianliu", person[index].name());
						break;
					case 2:
						assertEquals("hello world", person[index].name());
						break;
					}
				}
			}
		}
	}
	
	@Test
	public void aq() {
		// Java8新语法 Long::sum
		Long l = 3L;
		assertEquals(l, Stream.iterate(0L, i -> i + 1).limit(3).reduce(0L, Long::sum));
	}
	
	@Test
	public void ar() {
		
	}
	
	@Test
	public void as() {
		
	}
	
	@Test
	public void at() {
		
	}
	
	@Test
	public void au() {
		
	}
	
	@Test
	public void av() {
		
	}
	
	@Test
	public void aw() {
		
	}
	
	@Test
	public void ax() {
		
	}
	
	@Test
	public void ay() {
		
	}
	
	@Test
	public void az() {
		
	}
	
	@Test
	public void ba() {
		
	}
	
	@Test
	public void bb() {
		
	}
}
