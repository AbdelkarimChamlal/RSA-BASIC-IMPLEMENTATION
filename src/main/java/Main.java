import helpers.KeyGenerator;
import models.RSAPrivateKey;
import models.RSAPublicKey;

import java.math.BigInteger;
import java.security.Key;
import java.util.ArrayList;
import java.util.Random;

/**
 * Main class is a port for using the app
 * @author Abdelkarim CHAMLAL
 * @version 1.0
 * @since 1.0
 * @see KeyGenerator
 * @see RSAPrivateKey
 * @see RSAPublicKey
 * @see BigInteger
 */
public class Main {

    // takes an array of args and acts accordingly
    // supported args:
    // -g: generate a new key pair and saves it to <path>
    // -m: encrypts the message -m "message" with the public key and displays the result
    // -d: decrypts the message -d "message" with the private key and displays the result
    // -p: loads the public key from -p <path>
    // -s: loads the private key from -s <path>
    // -h: displays the help
    public static void main(String[] args) {

//        args = new String[]{"-g", "C:/Users/<USER>/Desktop/fun projects/RSA-BASIC-IMPLEMENTATION/demo", "-m", "HEllO WORLD"};
//        args = new String[]{"-p", "C:/Users/<USER>/Desktop/fun projects/RSA-BASIC-IMPLEMENTATION/demo/public.key", "-m", "HEllO WORLD"};
//        args = new String[]{"-s", "C:/Users/<USER>/Desktop/fun projects/RSA-BASIC-IMPLEMENTATION/demo/private.key", "-d", "<encrypted msg>"};


        for (String arg : args) {
            if (arg.equals("-h")) {
                System.out.println("Usage: java -jar rsa.jar [options] [args] [options] [args] ...");
                System.out.println("Options:");
                System.out.println("-g: generate a new key pair and saves it to <path>");
                System.out.println("-m: encrypts the message -m \"message\" with the public key and displays the result");
                System.out.println("-d: decrypts the message -d \"message\" with the private key and displays the result");
                System.out.println("-p: loads the public key from -p <path>");
                System.out.println("-s: loads the private key from -s <path>");
                System.out.println("-h: displays the help");
                System.exit(0);
            }
        }

        KeyGenerator keyGenerator;
        RSAPrivateKey privateKey = null;
        RSAPublicKey publicKey = null;

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equals("-g")) {
                if (i + 1 < args.length) {
                    keyGenerator = new KeyGenerator();
                    privateKey = keyGenerator.getPrivateKey();
                    publicKey = keyGenerator.getPublicKey();
                    try {
                        RSAPrivateKey.savePrivateKeyToFile(args[i + 1] + "/private.key", privateKey);
                        RSAPublicKey.savePublicKeyToFile(args[i + 1] + "/public.key", publicKey);
                    } catch (Exception e) {
                        System.out.println("Failed to save the key pair to " + args[i + 1]);
                        System.exit(1);
                    }
                } else {
                    System.out.println("Error: -g option requires a path");
                    System.exit(1);
                }
            }
        }

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equals("-p")) {
                if (i + 1 < args.length) {
                    try {
                        publicKey = RSAPublicKey.loadPublicKeyFromFile(args[i + 1]);
                    } catch (Exception e) {
                        System.out.println("Failed to load the public key from " + args[i + 1]);
                        System.exit(1);
                    }
                } else {
                    System.out.println("Error: -p option requires a file path");
                    System.exit(1);
                }
            }
        }

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equals("-s")) {
                if (i + 1 < args.length) {
                    try {
                        privateKey = RSAPrivateKey.loadPrivateKeyFromFile(args[i + 1]);
                    } catch (Exception e) {
                        System.out.println("Failed to load the private key from " + args[i + 1]);
                        System.exit(1);
                    }
                } else {
                    System.out.println("Error: -s option requires a file path");
                    System.exit(1);
                }
            }
        }

        for(int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equals("-m")) {
                if(publicKey == null) {
                    System.out.println("Error: -m option requires a public key");
                    System.exit(1);
                }
                if (i + 1 < args.length) {
                    String message = args[i + 1];
                    String encryptedMessage = RSA.crypt(message, publicKey);
                    System.out.println("Encrypted message: " + encryptedMessage);
                } else {
                    System.out.println("Error: -m option requires a message");
                    System.exit(1);
                }
            }
        }

        for(int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equals("-d")) {
                if(privateKey == null) {
                    System.out.println("Error: -d option requires a private key");
                    System.exit(1);
                }
                if (i + 1 < args.length) {
                    String message = args[i + 1];
                    String decryptedMessage = RSA.decrypt(message, privateKey);
                    System.out.println("Decrypted message: " + decryptedMessage);
                } else {
                    System.out.println("Error: -d option requires a message");
                    System.exit(1);
                }
            }
        }

        System.out.println("Done");
        System.exit(0);
    }

}