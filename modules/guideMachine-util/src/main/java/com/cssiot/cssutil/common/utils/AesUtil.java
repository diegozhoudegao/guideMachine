package com.cssiot.cssutil.common.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Aes加密及解密Hex
 * 
 * @author shinry.liu
 *
 */
public class AesUtil {
	public static String pwd = "mima";
	public static final String SEED = "gongzuoliumiyao";
	/**
	 * 加密
	 * 
	 * @param seed 传入的生成密匙
	 * @param clearText 要加密的内容
	 * @return
	 */
	public static String encrypt(String seed, String clearText) {
		String resultSeed = pwd + seed;
		byte[] result = null;
		try {
			byte[] rawkey = getRawKey(resultSeed.getBytes());
			result = encrypt(rawkey, clearText.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String content = toHex(result);
		return content;

	}

	/**
	 * 解密
	 * 
	 * @param seed 传入的生成密匙
	 * @param clearText 要加密的内容
	 * @return
	 */

	public static String decrypt(String seed, String encrypted) {
		String resultSeed = pwd + seed;
		byte[] rawKey;
		try {
			rawKey = getRawKey(resultSeed.getBytes());
			byte[] enc = toByte(encrypted);
			byte[] result = decrypt(rawKey, enc);
			String coentn = new String(result);
			return coentn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 
	 * @param seed 密匙的字节数据
	 * @return
	 * @throws Exception
	 */
	private static byte[] getRawKey(byte[] seed) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		sr.setSeed(seed);
		kgen.init(128, sr);
		SecretKey sKey = kgen.generateKey();
		byte[] raw = sKey.getEncoded();
		return raw;
	}

	/**
	 * 
	 * @param raw 生成的密匙字节数据
	 * @param clear 加密的数据字节数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
		byte[] encrypted = cipher.doFinal(clear);
		return encrypted;
	}

	/**
	 * 
	 * @param raw 生成的密匙字节数据
	 * @param clear 解密的数据字节数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
		byte[] decrypted = cipher.doFinal(encrypted);
		return decrypted;
	}

	/*
	 * 转16进制
	 */
	public static String toHex(String txt) {
		return toHex(txt.getBytes());
	}

	public static String fromHex(String hex) {
		return new String(toByte(hex));
	}

	public static byte[] toByte(String hexString) {
		int len = hexString.length() / 2;
		byte[] result = new byte[len];
		for (int i = 0; i < len; i++)
			result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
		return result;
	}

	public static String toHex(byte[] buf) {
		if (buf == null)
			return "";
		StringBuffer result = new StringBuffer(2 * buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}
		return result.toString();
	}

	private static void appendHex(StringBuffer sb, byte b) {
		final String HEX = "0123456789ABCDEF";
		sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
	}

}
