package com.tool.document.services.watch;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AESEncrypt
{

    private static String        transformation = "AES";
    private static SecretKeySpec secretKeySpec;
    private static byte[]        key;

    static
    {
        try
        {
            key = "Qwer1234!@".getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKeySpec = new SecretKeySpec(key, transformation);
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }

    public static void encrypt(Serializable object, OutputStream os) throws IOException
    {
        ObjectOutputStream outputStream = null;
        try
        {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            SealedObject sealedObject = new SealedObject(object, cipher);

            // Wrap the output stream
            CipherOutputStream cos = new CipherOutputStream(os, cipher);
            outputStream = new ObjectOutputStream(cos);
            outputStream.writeObject(sealedObject);
            outputStream.close();
        }
        catch (IllegalBlockSizeException | IOException | NoSuchAlgorithmException
                | NoSuchPaddingException | InvalidKeyException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (outputStream != null)
                outputStream.close();
        }
    }


    public static <T> T decrypt(InputStream is) throws IOException
    {
        ObjectInputStream inputStream = null;
        try
        {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

            CipherInputStream cipherInputStream = new CipherInputStream(is, cipher);
            inputStream = new ObjectInputStream(cipherInputStream);
            SealedObject sealedObject;
            sealedObject = (SealedObject) inputStream.readObject();
            return (T) sealedObject.getObject(cipher);
        }
        catch (ClassNotFoundException | IllegalBlockSizeException | BadPaddingException
                | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            if (inputStream != null)
                inputStream.close();
        }
    }
}
