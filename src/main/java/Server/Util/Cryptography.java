package Server.Util;

import java.math.*;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cryptography {
    public static String md5(String s){
       MessageDigest m;
        try {
            m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes(),0,s.length());
            return new BigInteger(1,m.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Cryptography.class.getName()).log(Level.SEVERE, null, ex);
            return s;
        }
    }
}
