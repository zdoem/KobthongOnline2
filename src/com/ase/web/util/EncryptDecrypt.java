package com.ase.web.util;

import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

public class EncryptDecrypt {
	/**********
	 * 2015.10.23
	 *  by pradoem
	 *  project : DB_ASE
	 */
	private static byte[] sharedvector = {0x01, 0x02, 0x03, 0x05, 0x07, 0x0B, 0x0D, 0x11 };
	private final static String key = "1234567890";
	
	public static String EncryptText(String RawText){
        String EncText = "";
        byte[] keyArray = new byte[24];
        byte[] temporaryKey;
       
        byte[] toEncryptArray = null;
  
        try{
            toEncryptArray =  RawText.getBytes("UTF-8");        
            MessageDigest m = MessageDigest.getInstance("MD5");
            temporaryKey = m.digest(key.getBytes("UTF-8"));
 
            if(temporaryKey.length < 24) {// DESede require 24 byte length key
                int index = 0;
                for(int i=temporaryKey.length;i< 24;i++){                   
                    keyArray[i] =  temporaryKey[index];
                }
            }        
 
            Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");            
            c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyArray, "DESede"), new IvParameterSpec(sharedvector));            
            byte[] encrypted = c.doFinal(toEncryptArray);            
            EncText = Base64.encodeBase64String(encrypted);
 
        }catch(Exception e){
            System.out.println("Error EncryptText :"+e.toString());
        }
        return EncText;        
    }
	
	public static String DecryptText(String EncText){
        String RawText = "";
        byte[] keyArray = new byte[24];
        byte[] temporaryKey;
       // byte[] toEncryptArray = null;
 
        try{
        	
            MessageDigest m = MessageDigest.getInstance("MD5");
            temporaryKey = m.digest(key.getBytes("UTF-8"));           
 
            if(temporaryKey.length < 24) {// DESede require 24 byte length key
                int index = 0;
                for(int i=temporaryKey.length;i< 24;i++) {                  
                    keyArray[i] =  temporaryKey[index];
                }
            }
            
            Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyArray, "DESede"), new IvParameterSpec(sharedvector));
            byte[] decrypted = c.doFinal(Base64.decodeBase64(EncText));   
 
            RawText = new String(decrypted, "UTF-8");                    
        } catch(Exception e){
        	 System.out.println("Error DecryptText :"+e.toString());
        }      
 
        return RawText; 
 
    }
	
	
}
