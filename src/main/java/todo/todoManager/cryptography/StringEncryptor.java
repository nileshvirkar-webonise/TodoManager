package todo.todoManager.cryptography;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;

public class StringEncryptor {

	String key1 = "Bar12345Bar12345"; // 128 bit key
	Key aesKey = null;;
	Cipher cipher = null;

    PBEKeySpec pbeKeySpec;
    PBEParameterSpec pbeParamSpec;
    SecretKeyFactory keyFac;
    SecretKey pbeKey;
    
	private static StringEncryptor stringEncryptor = null;

	private StringEncryptor() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException {
	    byte[] salt =  "webonise".getBytes();
	    int count = 20;
	    pbeParamSpec = new PBEParameterSpec(salt, count);
	    pbeKeySpec = new PBEKeySpec(key1.toCharArray(), salt, count);
	    keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
	    pbeKey = keyFac.generateSecret(pbeKeySpec);
	    
	    try {
			cipher = Cipher.getInstance(pbeKey.getAlgorithm());
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

	public String getEncryptedString(String source) throws IllegalBlockSizeException,
			BadPaddingException, InvalidAlgorithmParameterException {
		byte[] encrypted = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);
			encrypted = cipher.doFinal(source.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return Base64.encodeBase64String(encrypted);
	}

	public String getDecryptedString(String source) throws IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException {
		String decrypted = null;
			try {
				cipher.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);
			} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			}
			byte[] bytes = Base64.decodeBase64(source);
			decrypted = new String(cipher.doFinal(bytes));
		return decrypted.toString();
	}
}
