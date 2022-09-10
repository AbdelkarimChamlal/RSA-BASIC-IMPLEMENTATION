package models;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RSAPublicKey {
    private BigInteger e;
    private BigInteger n;

    public RSAPublicKey(BigInteger e, BigInteger n) {
        this.e = e;
        this.n = n;
    }

    public BigInteger getE() {
        return this.e;
    }

    public BigInteger getN() {
        return this.n;
    }

    public static RSAPublicKey getPublicKeyFromPrivateKey(RSAPrivateKey privateKey) {
        return new RSAPublicKey(privateKey.getD(), privateKey.getN());
    }

    public static RSAPublicKey loadPublicKeyFromFile(String path) throws IOException {

        String content = new String(Files.readAllBytes(Paths.get(path)));
        if(content.isEmpty()) {
            throw new IOException("File is empty");
        }
        String[] numbers = content.split("::");
        if(numbers.length != 2) {
            throw new IOException("File is not valid");
        }
        BigInteger e = new BigInteger(numbers[0]);
        BigInteger n = new BigInteger(numbers[1]);
        return new RSAPublicKey(e, n);
    }

    public static void savePublicKeyToFile(String path, RSAPublicKey publicKey) throws IOException {
        String content = publicKey.getE().toString() + "::" + publicKey.getN().toString();
        Files.write(Paths.get(path), content.getBytes());
    }
}
