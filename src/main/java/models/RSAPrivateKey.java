package models;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RSAPrivateKey {
    private BigInteger d;
    private BigInteger n;

    public RSAPrivateKey(BigInteger d, BigInteger n) {
        this.d = d;
        this.n = n;
    }

    public BigInteger getD() {
        return this.d;
    }

    public BigInteger getN() {
        return this.n;
    }

    public static RSAPrivateKey loadPrivateKeyFromFile(String path) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(path)));
        if(content.isEmpty()) {
            throw new IOException("File is empty");
        }
        String[] numbers = content.split("::");
        if(numbers.length != 2) {
            throw new IOException("File is not valid");
        }
        BigInteger d = new BigInteger(numbers[0]);
        BigInteger n = new BigInteger(numbers[1]);
        return new RSAPrivateKey(d, n);
    }

    public static RSAPrivateKey getPrivateKeyFromPublicKey(RSAPublicKey publicKey) {
        return new RSAPrivateKey(publicKey.getE(), publicKey.getN());
    }

    public static void savePrivateKeyToFile(String path, RSAPrivateKey privateKey) throws IOException {
        String content = privateKey.getD().toString() + "::" + privateKey.getN().toString();
        // create the file if it doesn't exist
        Files.write(Paths.get(path), content.getBytes());
    }

}
