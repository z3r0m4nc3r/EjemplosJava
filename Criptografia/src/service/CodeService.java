package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CodeService {
	private Key aesKey;
	
	public CodeService() {
		
	}
	
	public void setaesKey (String clave) {
		this.aesKey = claveAES(clave);
	}

	private Key claveAES (String clave) {
		byte[] claveEncriptacion;
		try {
			claveEncriptacion = clave.getBytes("UTF-8");
			MessageDigest sha = MessageDigest.getInstance("SHA-1");

			claveEncriptacion = sha.digest(claveEncriptacion);
			claveEncriptacion = Arrays.copyOf(claveEncriptacion, 32);

			Key aesKey = new SecretKeySpec(claveEncriptacion, "AES");
			return aesKey;
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	 
	public String encript(String text) throws Exception { 
	 
	    Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.ENCRYPT_MODE, aesKey);
	 
	    byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
	         
	    return Base64.getEncoder().encodeToString(encrypted);
	}
	
	public String decrypt(String encrypted) throws Exception {
	    
		  byte[] encryptedBytes=Base64.getDecoder().decode(encrypted);
			 
		    Cipher cipher = Cipher.getInstance("AES");
		    cipher.init(Cipher.DECRYPT_MODE, aesKey);
		   
		    String decrypted = new String(cipher.doFinal(encryptedBytes));
		    return decrypted;
	    
	    }
	
	public void encryptFile (File file1) throws Exception{
		String fileName = Base64.getEncoder().encodeToString(file1.getName().getBytes("UTF-8"));
		BufferedReader reader =null;
		FileOutputStream fout = new FileOutputStream(System.getProperty("user.dir")+System.getProperty("file.separator")+"Encrypted"+System.getProperty("file.separator")+fileName,true);
		PrintStream out = new PrintStream(fout);
		String lineaActual;
		
		reader = new BufferedReader(new FileReader(file1.getAbsolutePath()));
		while((lineaActual=reader.readLine()) !=null) {
			out.println(encript(lineaActual));
			
		}
		
		reader.close();
		out.close();
		
	}
	
	public void decryptFile (File file1) throws Exception{
		String fileName = new String (Base64.getDecoder().decode(file1.getName()));
		BufferedReader reader =null;
		FileOutputStream fout = new FileOutputStream(System.getProperty("user.dir")+System.getProperty("file.separator")+"Decrypted"+System.getProperty("file.separator")+fileName,true);
		PrintStream out = new PrintStream(fout);
		String lineaActual, resultado;
		
		reader = new BufferedReader(new FileReader(file1.getAbsolutePath()));
		while((lineaActual=reader.readLine()) !=null) {
			resultado = decrypt(lineaActual);
			out.println(resultado);
		}

		reader.close();
		out.close();
		
	}

}
