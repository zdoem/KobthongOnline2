package com.test.testz;

import org.apache.commons.codec.binary.Base64;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import javax.swing.JOptionPane;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class TestMD5EncrypDecrypt {

	private static byte[] sharedvector = {
	    0x01, 0x02, 0x03, 0x05, 0x07, 0x0B, 0x0D, 0x11
	    };
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 String EncrypText = EncryptText("1234");
		 System.out.println("Encrypt :"+EncrypText);
		 
		 String decrypt = DecryptText("8GG736v0K3c=");
		 System.out.println("decrypt :"+decrypt);
		 
	}
	
	public static String EncryptText(String RawText)
    {
        String EncText = "";
        byte[] keyArray = new byte[24];
        byte[] temporaryKey;
        String key = "developersnotedotcom";
        byte[] toEncryptArray = null;
  
        try
        {
 
            toEncryptArray =  RawText.getBytes("UTF-8");        
            MessageDigest m = MessageDigest.getInstance("MD5");
            temporaryKey = m.digest(key.getBytes("UTF-8"));
 
            if(temporaryKey.length < 24) // DESede require 24 byte length key
            {
                int index = 0;
                for(int i=temporaryKey.length;i< 24;i++)
                {                   
                    keyArray[i] =  temporaryKey[index];
                }
            }        
 
            Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");            
            c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyArray, "DESede"), new IvParameterSpec(sharedvector));            
            byte[] encrypted = c.doFinal(toEncryptArray);            
            EncText = Base64.encodeBase64String(encrypted);
 
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
 
        return EncText;        
    }
	
	public static String DecryptText(String EncText)
    {
 
        String RawText = "";
        byte[] keyArray = new byte[24];
        byte[] temporaryKey;
        String key = "developersnotedotcom";
        byte[] toEncryptArray = null;
 
        try{
            MessageDigest m = MessageDigest.getInstance("MD5");
            temporaryKey = m.digest(key.getBytes("UTF-8"));           
 
            if(temporaryKey.length < 24) // DESede require 24 byte length key
            {
                int index = 0;
                for(int i=temporaryKey.length;i< 24;i++)
                {                  
                    keyArray[i] =  temporaryKey[index];
                }
            }
            
            Cipher c = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyArray, "DESede"), new IvParameterSpec(sharedvector));
            byte[] decrypted = c.doFinal(Base64.decodeBase64(EncText));   
 
            RawText = new String(decrypted, "UTF-8");                    
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
        }      
 
        return RawText; 
 
    }

}
