package sample;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {
	private static SecretKey geneKey(String key) throws Exception {  
	    //获取一个密钥生成器实例  
	    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");  
	    SecureRandom random = new SecureRandom();  
	    random.setSeed(key.getBytes());//设置加密用的种子，这里就是密钥  
	    keyGenerator.init(random);  
	    SecretKey secretKey = keyGenerator.generateKey();  
	    return secretKey;  
	}  
	private static SecretKey readKey(Path keyPath)  {
	    //读取存起来的密钥  
	    byte[] keyBytes;
		try {
			keyBytes = Files.readAllBytes(keyPath);
			SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");  
			return keySpec;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	    return null;  
	}  
	private static boolean saveKey(SecretKey k,Path path)
	{
		try {
			Files.write(path, k.getEncoded());  
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return false;
	}
	public static String decode(String content,String keyPath)
	{
		SecretKey key=readKey(Paths.get(keyPath));
		try {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, key);  //指定解密模式

			byte[] s = cipher.doFinal(ParseUtil.parseHexStr2Byte(content));//解密后
		
			return new String(s);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	public static String encode(String content,String keyPath)
	{
		SecretKey key=readKey(Paths.get(keyPath));

		Cipher cipher;
		try {
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, key);  //指定模式
			byte[] result = cipher.doFinal(content.getBytes());//加密后的字节数组  
			String miwen=ParseUtil.parseByte2HexStr(result);//二进制转十六进制
			return miwen;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public static boolean buildKeyFile(String key,String filePath)
	{
		try {
			SecretKey k=geneKey(key);//简短密钥
			//把上面的密钥存起来
			saveKey(k,Paths.get(filePath));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
