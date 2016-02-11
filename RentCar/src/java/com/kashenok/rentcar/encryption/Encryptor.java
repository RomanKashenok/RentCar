package com.kashenok.rentcar.encryption;

import com.kashenok.rentcar.exception.ServiceException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;

/**
 * The class Coder for hashing the password or login
 */
public class Encryptor {

    /**
     * The method for hashing password
     *
     * @param value is login or password
     * @return the hashed password
     * @throws ServiceException
     */
    public String encode(String value) throws ServiceException {
        String hashValue = null;
        byte[] array = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(value.getBytes("utf-8"));
            array = messageDigest.digest();
            BASE64Encoder baseEncoder = new BASE64Encoder();
            hashValue = baseEncoder.encode(array);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new ServiceException("Exception in method Coder.doHash", e);
        }
        return hashValue;
    }
}
