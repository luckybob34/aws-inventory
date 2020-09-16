package cloud.operations.service.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

	public String encrypt(String value ) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
		//Create Encrypt/Decrypt Object
		EncryptDecrypt encryption = new EncryptDecrypt();
		
		return encryption.encrypt(value);
	}
	
	public String decrypt(String value) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
		//Create Encrypt/Decrypt Object
		EncryptDecrypt encryption = new EncryptDecrypt();
		
		return encryption.decrypt(value);
	}
}