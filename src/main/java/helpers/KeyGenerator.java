package helpers;

import models.RSAPrivateKey;
import models.RSAPublicKey;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class KeyGenerator {

    private static final String[] numbers = new String[]{"1","2","3","4","5","6","7","8","9"};
    private static final String[] impairs = new String[]{"1","3","5","7","9"};
    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;

    public KeyGenerator() {
        BigInteger prime1 = this.generatePrimeNumber();
        BigInteger prime2 = this.generatePrimeNumber();
        BigInteger n = prime1.multiply(prime2);
        BigInteger t = prime1.subtract(new BigInteger("1")).multiply(prime2.subtract(new BigInteger("1")));
        BigInteger e = this.calculateE(n, t);
        BigInteger d = this.calculateD(n, t, e);
        this.publicKey = new RSAPublicKey(e, n);
        this.privateKey = new RSAPrivateKey(d, n);
    }

    public RSAPublicKey getPublicKey() {
        return this.publicKey;
    }

    public RSAPrivateKey getPrivateKey() {
        return this.privateKey;
    }

    private BigInteger calculateD(BigInteger n, BigInteger t, BigInteger e) {
        BigInteger k = new BigInteger("1");
        BigInteger d;
        while (true) {
            d = k.multiply(t).add(new BigInteger("1")).divide(e);
            if (d.multiply(e).subtract(k.multiply(t)).intValue() == 1) {
                return d;
            }
            k = k.add(new BigInteger("1"));
        }
    }
    private BigInteger calculateE(BigInteger n, BigInteger t){
        int e = 2;
        while (n.mod(new BigInteger(Integer.toString(e))).intValue() == 0 || t.mod(new BigInteger(Integer.toString(e))).intValue() == 0) {
            e++;
        }
        return new BigInteger(Integer.toString(e));
    }

    private BigInteger generatePrimeNumber() {
        while(true){
            StringBuilder randomNumber = new StringBuilder();
            for (int i = 1; i < 512; i++) {
                randomNumber.append(numbers[new Random().nextInt(numbers.length)]);
            }
            randomNumber.append(impairs[new Random().nextInt(impairs.length)]);
            BigInteger number = new BigInteger(randomNumber.toString());
            if(number.isProbablePrime(1)){
                return number;
            }
        }

    }
}
