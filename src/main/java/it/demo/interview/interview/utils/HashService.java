package it.demo.interview.interview.utils;

import lombok.experimental.UtilityClass;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@UtilityClass
public class HashService {

    public final String DEFAULT_HASH = "SHA512";

    private MessageDigest getDigest(String hashAlgorithm) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(hashAlgorithm);
    }

    public String getFileHash(MultipartFile file) throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        try (InputStream inputStream = new ByteArrayInputStream(file.getBytes())) {
            final byte[] buffer = new byte[StreamUtils.BUFFER_SIZE];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }
            return Base64.getEncoder().encodeToString(md.digest());
        }
    }

}
