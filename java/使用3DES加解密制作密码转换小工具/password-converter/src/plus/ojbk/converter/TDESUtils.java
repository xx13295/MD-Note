package plus.ojbk.converter;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * @author 3DES 加解密
 */

public class TDESUtils {
	public static final String IV = "f1w-*%1s";
	private static final String DES_CBC_ALGORITHM = "DES/CBC/PKCS5Padding";
	private static final String DES_ECB_ALGORITHM = "DESede/ECB/PKCS5Padding";

	/**
	 * DESCBC加密
	 *
	 * @param value 数据源
	 * @param key 密钥,长度必须是8的倍数
	 * @return 返回加密后的数据
	 * @throws Exception
	 */
	public String encryptDESCBC(final String value, final String key)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		// 生成key,同时制定是des还是DESede,两者的key长度要求不同
		final DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		final SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		// 加密向量
		final IvParameterSpec iv = new IvParameterSpec(IV.getBytes("UTF-8"));
		final Cipher cipher = Cipher.getInstance(DES_CBC_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		final byte[] valueByte = cipher.doFinal(value.getBytes("UTF-8"));
		// 通过base64,将加密数组转换成字符串
		final Base64.Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(valueByte);
	}

	/**
	 * DESCBC解密
	 *
	 * @param value 数据源
	 * @param key 密钥,长度必须是8的倍数
	 * @return 返回解密后的原始数据
	 * @throws Exception
	 */
	public String decryptDESCBC(final String value, final String key)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		// 通过base64,将字符串转成byte数组
		final Base64.Decoder decoder = Base64.getDecoder();
		final byte[] textByte = value.getBytes("UTF-8"); 
		final byte[] byteValue = decoder.decode(textByte);
		final DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		final SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		final IvParameterSpec iv = new IvParameterSpec(IV.getBytes("UTF-8"));
		final Cipher cipher = Cipher.getInstance(DES_CBC_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		final byte[] retByte = cipher.doFinal(byteValue);
		return new String(retByte);

	}

	/**
	 * 3DESECB加密
	 * 
	 * @param value 数据源
	 * @param key 必须是长度大于等于 3*8 = 24 位
	 * @return 密文
	 * @throws Exception
	 */
	public String encryptThreeDESECB(final String value, final String key)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		final DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
		final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		final SecretKey securekey = keyFactory.generateSecret(dks);
		final Cipher cipher = Cipher.getInstance(DES_ECB_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, securekey);
		final byte[] valueByte = cipher.doFinal(value.getBytes());
		final Base64.Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(valueByte);
	}

	/**
	 * 3DESECB解密
	 * 
	 * @param value 数据源
	 * @param key 必须是长度大于等于 3*8 = 24 位
	 * @return 明文
	 * @throws Exception
	 */
	public String decryptThreeDESECB(final String value, final String key)
			throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		// 通过base64,将字符串转成byte数组
		final Base64.Decoder decoder = Base64.getDecoder();
		final byte[] textByte = value.getBytes("UTF-8"); 
		final byte[] byteValue = decoder.decode(textByte);
		// 解密的key
		final DESedeKeySpec dks = new DESedeKeySpec(key.getBytes("UTF-8"));
		final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		final SecretKey securekey = keyFactory.generateSecret(dks);
		// Chipher对象解密
		final Cipher cipher = Cipher.getInstance(DES_ECB_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, securekey);
		final byte[] retByte = cipher.doFinal(byteValue);
		return new String(retByte);
	}

}