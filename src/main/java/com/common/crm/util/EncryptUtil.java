package com.common.crm.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
/**
 * 加密工具类   des md5
 * @author huizi
 *
 */
public class EncryptUtil {
	/**
	 * AES对称加密 加密
	 * @param context 明文
	 * @param skey 秘钥
	 * @return 16进制密文
	 * @throws Exception
	 */
	public static String toAesPwd(String context,String skey) throws Exception{
		Cipher cipher = Cipher.getInstance("AES");
		byte[] byteContent = context.getBytes("utf-8");
		KeyGenerator kg = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom =  SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(skey.getBytes());
		kg.init(128, secureRandom);
		SecretKey secretKey = kg.generateKey();
		SecretKeySpec key = new SecretKeySpec(secretKey.getEncoded(), "AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] result = cipher.doFinal(byteContent);// 加密
		return parseByte2HexStr(result);
	}
	/**
	 * AES对称加密 解密
	 * @param pwdCon 16进制密文
	 * @param skey 秘钥
	 * @return 明文
	 * @throws Exception
	 */
	
	public static String toAesContext(String pwdCon,String skey) throws Exception{
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		KeyGenerator kg = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom =  SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(skey.getBytes());
		kg.init(128, secureRandom);
		SecretKey secretKey = kg.generateKey();
		SecretKeySpec key = new SecretKeySpec(secretKey.getEncoded(), "AES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] result = cipher.doFinal(parseHexStr2Byte(pwdCon));
		return new String(result);
	}
	/**
	 * 	字节数组转为16进制字符串
	 * @param buf 字节数组
	 * @return 16进制字符串
	 */
	public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
	/**
	 * 	16进制字符串转为字节数组
	 * @param hexStr 6进制字符串
	 * @return 字节数组
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
        	return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
	
	/**
	 * 	md5编码
	 * @param context 明文
	 * @return 密文
	 */
	public static String md5(String context)  {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] bb = digest.digest(context.getBytes());
		return parseByte2HexStr(bb);
	}
}
