package com.test.testz;

import com.ase.web.util.EncryptDecrypt;

public class TestCallEncrypt {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		EncryptDecrypt objEnc = new EncryptDecrypt();
		
		 String EncrypText = objEnc.EncryptText("user");
		 System.out.println("Encrypt :"+EncrypText);
		 
		 String decrypt = objEnc.DecryptText("");
		 System.out.println("decrypt :"+decrypt);
		 
	}
}
