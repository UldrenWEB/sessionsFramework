package sessionFramework.utils;

import org.mindrot.jbcrypt.BCrypt;

public class Encryptor {
    
    public static String encrypt(String plainText){
        return BCrypt.hashpw(plainText, BCrypt.gensalt());
    }

    public static boolean compare(String plainText, String hashText) {
        if (BCrypt.checkpw(plainText, hashText))
        	return true;
        else
            return false;
    }
}
