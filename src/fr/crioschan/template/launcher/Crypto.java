package fr.crioschan.template.launcher;

import fr.theshark34.openlauncherlib.minecraft.util.GameDirGenerator;
import fr.theshark34.openlauncherlib.util.Saver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
    public static final File N_DIR = GameDirGenerator.createGameDir("SERVER NAME");

    static void fileProcessor(int cipherMode,String key,File inputFile,File outputFile){
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void Encrypt() {
        String key = "ps\\\"9hg#<QtJX\\\\HSN";
        File inputFile = new File(N_DIR + "/Prop.properties");
        File encryptedFile = new File(N_DIR + "/Prop.properties");

        try {
            Crypto.fileProcessor(Cipher.ENCRYPT_MODE,key,inputFile,encryptedFile);
            System.out.println("Sucess Encrypt");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void Decrypt() {
        String key = "ps\\\"9hg#<QtJX\\\\HSN";
        File inputFile = new File(N_DIR + "/Prop.properties");
        File decryptedFile = new File(N_DIR + "/Prop.properties");

        try {
            Crypto.fileProcessor(Cipher.DECRYPT_MODE,key,inputFile,decryptedFile);
            System.out.println("Sucess Decrypt");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

}