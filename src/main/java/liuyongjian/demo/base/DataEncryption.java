package liuyongjian.demo.base;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataEncryption {
	private final static String DES = "DES";
	private final static String RSA = "RSA";
	private final static String CHARSET = "UTF-8";
	private final static String SHA256 = "SHA-256"; // MD5、SHA256

	@Test
	public void aa() {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(SHA256);
			byte[] bytes = messageDigest.digest("abc".getBytes());
			for (int i = 0; i < bytes.length; i++) {
				System.err.println(bytes[i]);
			}
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
	@Test
	public void ab() {
		String data = "des test";
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建DESKeySpec对象
		DESKeySpec dks;
		try {
			dks = new DESKeySpec("12345678".getBytes());
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey securekey = keyFactory.generateSecret(dks);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance(DES);
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
			// 执行加密操作
			byte[] b = cipher.doFinal(data.getBytes());
			
			StringBuffer sb = new StringBuffer();
			for (int n = 0; n < b.length; n++) {
				sb.append(java.lang.Integer.toHexString(b[n] & 0XFF));
			}
			String result = sb.toString().toUpperCase();
			
			assertEquals("59B98E3C84535FA1FEB959B7D4642FCB", result);
			
			cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		} catch (InvalidKeyException | NoSuchAlgorithmException | 
			InvalidKeySpecException | NoSuchPaddingException | 
			IllegalBlockSizeException | BadPaddingException exception) {
			exception.printStackTrace();
		}
	}
	
	@Test
	public void ac() {
		int keysize = 512;
		String privateSendData = "RSA encryption";
		String publicSendData = "Ok, I know";
		
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
			keyPairGenerator.initialize(keysize);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			
			// 公钥
			Key publicKey = keyPair.getPublic();
			String publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
			
	        // 私钥
	        Key privateKey = keyPair.getPrivate();
	        String privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
	        
	        Cipher cipher = Cipher.getInstance(RSA);
	        
	        // 私钥加密
	        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
	        String privateEncryptionStr = Base64.encodeBase64URLSafeString(cipher.doFinal(privateSendData.getBytes()));
	        
	        System.err.println("publicKeyStr: " + publicKeyStr);
	        System.err.println("privateKeyStr: " + privateKeyStr);
	        System.err.println("privateEncryptionStr: " + privateEncryptionStr);
	        
	        // 公钥解密
	        cipher.init(Cipher.DECRYPT_MODE, publicKey);
	        String publicDecryptionStr = new String(cipher.doFinal(Base64.decodeBase64(privateEncryptionStr)), CHARSET);
	        
	        assertEquals("RSA encryption", publicDecryptionStr);
	        
	        // 公钥加密
	        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
	        String publicEncryptionStr = Base64.encodeBase64URLSafeString(cipher.doFinal(publicSendData.getBytes()));
	        
	        System.err.println("publicEncryptionStr: " + publicEncryptionStr);
	        
	        // 私钥解密
	        cipher.init(Cipher.DECRYPT_MODE, privateKey);
	        String privateDecryptionStr = new String(cipher.doFinal(Base64.decodeBase64(publicEncryptionStr)));
	        
	        assertEquals("Ok, I know", privateDecryptionStr);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException 
			| InvalidKeyException | IllegalBlockSizeException 
			| BadPaddingException | UnsupportedEncodingException exception) {
			exception.printStackTrace();
		}
	}
	
}
