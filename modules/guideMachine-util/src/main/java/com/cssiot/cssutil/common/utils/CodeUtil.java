package com.cssiot.cssutil.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.commons.codec.binary.Hex;

import com.cssiot.cssutil.common.exception.ResultException;

public class CodeUtil {
	
	public static String hexMD5(String... arr) {
		if (ChkUtil.isEmptyAllObject(arr)) {
			throw new ResultException(-2, "非法请求参数，有部分参数为空 : " + Arrays.toString(arr), null, null, null);
		}
	    Arrays.sort(arr);
	    StringBuilder sb = new StringBuilder();
	    for (String a : arr) {
	      sb.append(a);
	    }
		return encode("MD5", sb.toString());
	}
	
	public static String hexSHA1(String... arr) {
		if (ChkUtil.isEmptyAllObject(arr)) {
			throw new ResultException(-2, "非法请求参数，有部分参数为空 : " + Arrays.toString(arr), null, null, null);
		}
		Arrays.sort(arr);
		StringBuilder sb = new StringBuilder();
		for (String a : arr) {
			sb.append(a);
		}
		return encode("SHA-1", sb.toString());
	}
	
	public static String encode(String algorithm, String value) {
		byte[] digest = null;
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(value.getBytes("utf-8"));
			digest = md.digest();
		} catch (NoSuchAlgorithmException e) {
			throw new ResultException(-2, e.getMessage(), null, null, null);
		} catch (UnsupportedEncodingException e) {
			throw new ResultException(-2, e.getMessage(), null, null, null);
		}
		return byteToHexString(digest);
	}
	
	public static String byteToHexString(byte[] bytes) {
		return String.valueOf(Hex.encodeHex(bytes));
	}

}

