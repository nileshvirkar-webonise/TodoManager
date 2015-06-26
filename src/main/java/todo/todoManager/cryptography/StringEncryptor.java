package todo.todoManager.cryptography;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import todo.todoManager.app.App;

public class StringEncryptor {

	private String key1 = "Bar12345Bar12345"; // 128 bit key
	private Cipher cipher = null;
	SecretKeySpec key;

	final static Logger logger = Logger.getLogger(App.class);

	private byte[] ivBytes = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05,
			0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f };
	private static StringEncryptor stringEncryptor = null;
	AlgorithmParameterSpec paramSpec = null;

	private StringEncryptor() throws NoSuchAlgorithmException,
			InvalidKeySpecException, InvalidKeyException,
			InvalidAlgorithmParameterException {

		try {
			SecretKeySpec key = new SecretKeySpec(key1.getBytes(),
					"AES");

			paramSpec = new IvParameterSpec(ivBytes);

			// Decrypt the message
			Cipher cipher = Cipher.getInstance(
					"PBEWITHSHA256AND128BITAES-CBC-BC",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());

			cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivBytes));

		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}

	public static StringEncryptor getInstance() {
		if (stringEncryptor == null) {
			try {
				stringEncryptor = new StringEncryptor();
			} catch (InvalidKeyException | NoSuchAlgorithmException
					| InvalidKeySpecException
					| InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			}
		}
		return stringEncryptor;
	}

	/*
	 * public String getEncryptedString(String source)
	 * 
	 * throws IllegalBlockSizeException, BadPaddingException,
	 * InvalidAlgorithmParameterException, InvalidKeySpecException { byte[]
	 * encrypted = null; try { cipher =
	 * Cipher.getInstance("AES/CBC/PKCS7Padding",new
	 * org.bouncycastle.jce.provider.BouncyCastleProvider());
	 * cipher.init(Cipher.DECRYPT_MODE,key,new IvParameterSpec(ivBytes));
	 * encrypted = cipher.doFinal(source.getBytes()); } catch
	 * (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
	 * e) { e.printStackTrace(); } return Base64.encodeBase64String(encrypted);
	 * }
	 */

	public String getDecryptedString(String source)
			throws IllegalBlockSizeException, BadPaddingException,
			UnsupportedEncodingException {

		Security.addProvider(new BouncyCastleProvider());
		String decrypted = null;
		try {
			cipher = Cipher.getInstance("PBEWITHSHA256AND128BITAES-CBC-BC",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			logger.info("*****************" + key);
			cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		byte[] bytes = Base64.decodeBase64(source);
		decrypted = new String(cipher.doFinal(bytes));
		return decrypted.toString();
	}
}
