import models.RSAPrivateKey;
import models.RSAPublicKey;

import java.math.BigInteger;

public class RSA {


    public static String crypt(String message, RSAPublicKey publicKey) {
        BigInteger m = new BigInteger(message.getBytes());
        BigInteger c = m.modPow(publicKey.getE(), publicKey.getN());
        return c.toString();
    }

    public static String decrypt(String message, RSAPrivateKey privateKey) {
        BigInteger c = new BigInteger(message);
        BigInteger m = c.modPow(privateKey.getD(), privateKey.getN());
        return new String(m.toByteArray());
    }


}
