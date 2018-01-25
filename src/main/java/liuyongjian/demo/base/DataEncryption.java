package liuyongjian.demo.base;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.junit.Test;

import static org.junit.Assert.*;

public class DataEncryption {
	private final static String DES = "DES";
	private final static String SHA256 = "SHA-256"; // MD5、SHA256

	@Test
	public void aa() {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(SHA256);
			byte[] bytes = messageDigest.digest("abc".getBytes());
			StringBuffer sb = new StringBuffer();
			for (int index = 0; index < bytes.length; index++) {
				sb.append(Integer.toHexString(bytes[index] & 0xFF));
			}
			assertEquals("ba7816bf8f1cfea414140de5dae2223b0361a396177a9cb410ff61f2015ad", sb.toString());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec("12345678".getBytes());
		// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		// 执行加密操作
		byte[] b = cipher.doFinal("aaa".getBytes());
		
		StringBuffer sb = new StringBuffer();
		for (int n = 0; n < b.length; n++) {
			sb.append(java.lang.Integer.toHexString(b[n] & 0XFF));
		}
		String result = sb.toString().toUpperCase();

		System.out.println(result);
	}
	
}
