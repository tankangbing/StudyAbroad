package com.app.studyabroad.util;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import org.springframework.security.providers.encoding.Md5PasswordEncoder;

/**
 * ���ܽ���
 * @author Amao
 *
 */

public class CodeSecurityUtil {

	/**
	 * ��һ��String����MD5������Ϣ
	 * @param message Ҫ���ܵ�String
	 * @return ���ɵ�MD5��Ϣ
	 * @throws NoSuchAlgorithmException 
	 */
	public static String getMD5(String message) throws NoSuchAlgorithmException {	
		Md5PasswordEncoder md5coder = new Md5PasswordEncoder();
		return md5coder.encodePassword(message,null);
	}
	  /** 
     * ʹ��ģ��ָ������RSA��Կ 
     * ע�⣺���˴�������Ĭ�ϲ�λ��ʽ��ΪRSA/None/PKCS1Padding����ͬJDKĬ�ϵĲ�λ��ʽ���ܲ�ͬ����AndroidĬ����RSA 
     * /None/NoPadding�� 
     *  
     * @param modulus 
     *            ģ 
     * @param exponent 
     *            ָ�� 
     * @return 
     */  
    public static RSAPublicKey getPublicKey(String modulus, String exponent) {  
        try {  
            BigInteger b1 = new BigInteger(modulus);  
            BigInteger b2 = new BigInteger(exponent);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);  
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
  
    /** 
     * ʹ��ģ��ָ������RSA˽Կ 
     * ע�⣺���˴�������Ĭ�ϲ�λ��ʽ��ΪRSA/None/PKCS1Padding����ͬJDKĬ�ϵĲ�λ��ʽ���ܲ�ͬ����AndroidĬ����RSA 
     * /None/NoPadding�� 
     *  
     * @param modulus 
     *            ģ 
     * @param exponent 
     *            ָ�� 
     * @return 
     */  
    public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {  
        try {  
            BigInteger b1 = new BigInteger(modulus);  
            BigInteger b2 = new BigInteger(exponent);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);  
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
    
    /**
     * ��Կ����
     * 
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String data, RSAPublicKey publicKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        // ģ��
        int key_len = publicKey.getModulus().bitLength() / 8;
        // �������ݳ��� <= ģ��-11
        String[] datas = splitString(data, key_len - 11);
        String mi = "";
        //������ĳ��ȴ���ģ��-11��Ҫ�������
        for (String s : datas) {
            mi += bcd2Str(cipher.doFinal(s.getBytes("UTF-8")));
        }
        return mi;
    }
 
    /**
     * ˽Կ����
     * 
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data, RSAPrivateKey privateKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        //ģ��
        int key_len = privateKey.getModulus().bitLength() / 8;
        byte[] bytes = data.getBytes("UTF-8");
        byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
        System.err.println(bcd.length);
        //������ĳ��ȴ���ģ����Ҫ�������
        String ming = "";
        byte[][] arrays = splitArray(bcd, key_len);
        for(byte[] arr : arrays){
            ming += new String(cipher.doFinal(arr));
        }
        return ming;
    }
    
    /**
     * ASCII��תBCD��
     * 
     */
    public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
        byte[] bcd = new byte[asc_len / 2];
        int j = 0;
        for (int i = 0; i < (asc_len + 1) / 2; i++) {
            bcd[i] = asc_to_bcd(ascii[j++]);
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
        }
        return bcd;
    }
    public static byte asc_to_bcd(byte asc) {
        byte bcd;
 
        if ((asc >= '0') && (asc <= '9'))
            bcd = (byte) (asc - '0');
        else if ((asc >= 'A') && (asc <= 'F'))
            bcd = (byte) (asc - 'A' + 10);
        else if ((asc >= 'a') && (asc <= 'f'))
            bcd = (byte) (asc - 'a' + 10);
        else
            bcd = (byte) (asc - 48);
        return bcd;
    }
    /**
     * BCDת�ַ���
     */
    public static String bcd2Str(byte[] bytes) {
        char temp[] = new char[bytes.length * 2], val;
 
        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
 
            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }
    /**
     * ����ַ���
     */
    public static String[] splitString(String string, int len) {
        int x = string.length() / len;
        int y = string.length() % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        String[] strings = new String[x + z];
        String str = "";
        for (int i=0; i<x+z; i++) {
            if (i==x+z-1 && y!=0) {
                str = string.substring(i*len, i*len+y);
            }else{
                str = string.substring(i*len, i*len+len);
            }
            strings[i] = str;
        }
        return strings;
    }
    /**
     *������� 
     */
    public static byte[][] splitArray(byte[] data,int len){
        int x = data.length / len;
        int y = data.length % len;
        int z = 0;
        if(y!=0){
            z = 1;
        }
        byte[][] arrays = new byte[x+z][];
        byte[] arr;
        for(int i=0; i<x+z; i++){
            arr = new byte[len];
            if(i==x+z-1 && y!=0){
                System.arraycopy(data, i*len, arr, 0, y);
            }else{
                System.arraycopy(data, i*len, arr, 0, len);
            }
            arrays[i] = arr;
        }
        return arrays;
    }
	
}
