package plus.ojbk.converter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class PasswordConverter {

	private static final String[] options = {"-e", "-d"};
	public static final String KEY = "e793104vg726apo6c293428bce4efc65d";
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, IOException {
		if (args.length < 2) {
			System.err.println("The number of parameters should be greater than 1");
			System.exit(1);
		}
		String option = args[0];
		boolean valid = false;
		for (String op : options) {
			if (op.equals(option)) {
				valid = true;
				break;
			}
		}
		if (!valid) {
			System.err.println("Invalid option : " + option);
			System.exit(1);
		}
		String data = args[1];
		switch(option) {
		  case "-e" : encrypt(data);break;
		  case "-d" : decrypt(data);break;
		}
		
	}
	
	private static void encrypt(String data) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, IOException {
		TDESUtils threeDES = new TDESUtils();
	    String value_encrypt = threeDES.encryptThreeDESECB(URLEncoder.encode(data, "UTF-8"), KEY);	
		System.out.println(value_encrypt);
	}
	
	private static void decrypt(String data) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
		TDESUtils threeDES = new TDESUtils();
		String value_decrypt = threeDES.decryptThreeDESECB(data, KEY);
		System.out.println(value_decrypt);
	}
}
