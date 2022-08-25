package com.zdk.blog.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @Description
 * @Author zdk
 * @Date 2022/8/25 11:00
 * 加密
 */
public class RsaUtils {
    private static final Logger logger = LoggerFactory.getLogger(RsaUtils.class);
    private static final int DEFAULT_KEY_SIZE = 2048;
    private static final String ALGORITHMS = "RSA";
    private static final String SIGN_ALGORITHMS = "SHA256withRSA";

    public static PublicKey getPublicKey(byte[] bytes) {
        try {
            bytes = Base64.getDecoder().decode(bytes);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(ALGORITHMS);
            return factory.generatePublic(spec);
        } catch (Exception var3) {
            logger.error("获取RSA公钥异常", var3);
            return null;
        }
    }

    public static PublicKey getPublicKey(String publicKey) {
        try {
            byte[] bytes = Base64.getDecoder().decode(publicKey);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(ALGORITHMS);
            return factory.generatePublic(spec);
        } catch (Exception var4) {
            logger.error("获取RSA公钥异常", var4);
            return null;
        }
    }

    public static PrivateKey getPrivateKey(byte[] bytes) {
        try {
            bytes = Base64.getDecoder().decode(bytes);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(ALGORITHMS);
            return factory.generatePrivate(spec);
        } catch (Exception var3) {
            logger.error("获取RSA私钥异常", var3);
            return null;
        }
    }

    public static PrivateKey getPrivateKey(String privateKey) {
        try {
            byte[] bytes = Base64.getDecoder().decode(privateKey);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
            KeyFactory factory = KeyFactory.getInstance(ALGORITHMS);
            return factory.generatePrivate(spec);
        } catch (Exception var4) {
            var4.printStackTrace();
            logger.error("获取RSA私钥异常", var4);
            return null;
        }
    }

    public static void generateKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHMS);
            keyPairGenerator.initialize(DEFAULT_KEY_SIZE);
            KeyPair keyPair = keyPairGenerator.genKeyPair();
            byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
            String publicKey = Base64.getEncoder().encodeToString(publicKeyBytes);
            byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
            String privateKey = Base64.getEncoder().encodeToString(privateKeyBytes);
        } catch (NoSuchAlgorithmException var6) {
            logger.error("生成RSA公私钥异常", var6);
        }

    }

    public static PublicKey getPublicKeyFromFile(String filename) throws Exception {
        byte[] bytes = readFile(filename);
        return getPublicKey(bytes);
    }

    public static PrivateKey getPrivateKeyFromFile(String filename) throws Exception {
        byte[] bytes = readFile(filename);
        return getPrivateKey(bytes);
    }

    public static void generateKey(String publicKeyFilename, String privateKeyFilename, String secret, int keySize) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHMS);
        SecureRandom secureRandom = new SecureRandom(secret.getBytes());
        keyPairGenerator.initialize(Math.max(keySize, DEFAULT_KEY_SIZE), secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        publicKeyBytes = Base64.getEncoder().encode(publicKeyBytes);
        writeFile(publicKeyFilename, publicKeyBytes);
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        privateKeyBytes = Base64.getEncoder().encode(privateKeyBytes);
        writeFile(privateKeyFilename, privateKeyBytes);
    }

    private static byte[] readFile(String fileName) throws Exception {
        return Files.readAllBytes((new File(fileName)).toPath());
    }

    private static void writeFile(String destPath, byte[] bytes) throws IOException {
        File dest = new File(destPath);
        if (!dest.exists()) {
            dest.createNewFile();
        }

        Files.write(dest.toPath(), bytes, new OpenOption[0]);
    }

    public static String decryptByPublicKey(String publicKey, String text) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHMS);
        PublicKey publicKeyObj = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(ALGORITHMS);
        cipher.init(2, publicKeyObj);
        byte[] result = cipher.doFinal(Base64.getDecoder().decode(text));
        return new String(result);
    }

    public static String encryptByPrivateKey(String privateKey, String text) {
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHMS);
            PrivateKey privateKeyObj = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance(ALGORITHMS);
            cipher.init(1, privateKeyObj);
            byte[] result = cipher.doFinal(text.getBytes());
            return Base64.getEncoder().encodeToString(result);
        } catch (Exception var7) {
            var7.printStackTrace();
            return null;
        }
    }

    public static String decryptByPrivateKey(String privateKey, String text) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHMS);
        PrivateKey privateKeyObj = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);
        Cipher cipher = Cipher.getInstance(ALGORITHMS);
        cipher.init(2, privateKeyObj);
        byte[] result = cipher.doFinal(Base64.getDecoder().decode(text));
        return new String(result);
    }

    public static String encryptByPublicKey(String publicKey, String text) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHMS);
        PublicKey publicKeyObj = keyFactory.generatePublic(x509EncodedKeySpec2);
        Cipher cipher = Cipher.getInstance(ALGORITHMS);
        cipher.init(1, publicKeyObj);
        byte[] result = cipher.doFinal(text.getBytes());
        return Base64.getEncoder().encodeToString(result);
    }

    public static String sign(String privateKey, String text) {
        try {
            PrivateKey key = getPrivateKey(privateKey);
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(key);
            signature.update(text.getBytes(StandardCharsets.UTF_8));
            byte[] signedData = signature.sign();
            return Base64.getEncoder().encodeToString(signedData);
        } catch (Exception var5) {
            var5.printStackTrace();
            return "";
        }
    }

    public static boolean verify(String publicKeyString, String source, String signature) {
        if (source != null && signature != null && publicKeyString != null) {
            try {
                PublicKey publicKey = getPublicKey(publicKeyString);
                Signature sign = Signature.getInstance(SIGN_ALGORITHMS);
                sign.initVerify(publicKey);
                sign.update(source.getBytes(StandardCharsets.UTF_8));
                return sign.verify(Base64.getDecoder().decode(signature));
            } catch (Exception var5) {
                var5.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyPyM/KjQ7C7e0LbHFpaGQKDMLXpTt0g50J+Ec0cEOOTLItdNXcHfIIsQaChBQnlDpvGh6V60uN1ZH9wYMWX/5b/qIe4MumN2INRgMkldZEF3sB25B66KfCq45R+3HaW7UwhE62aoOGo+zj/7KvMilT0gpCuc6hVyDm8XFBfo/hpZjZzPbm47cOE+gceCGezKohRoBVlracoBjGyC+xac3twLFdDsBzQuoeRzvpOlLh6Aaottx85mBg9rkOwaRmsj05tkibav8tjdBVXBqQ7oTK8MpTe6hFoGCAx6TDIy085cS8jri634MJMdtIrQMUA1YSgIhLR7fGHjBbPJEAIUYwIDAQAB";
        String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDI/Iz8qNDsLt7QtscWloZAoMwtelO3SDnQn4RzRwQ45Msi101dwd8gixBoKEFCeUOm8aHpXrS43Vkf3BgxZf/lv+oh7gy6Y3Yg1GAySV1kQXewHbkHrop8KrjlH7cdpbtTCETrZqg4aj7OP/sq8yKVPSCkK5zqFXIObxcUF+j+GlmNnM9ubjtw4T6Bx4IZ7MqiFGgFWWtpygGMbIL7Fpze3AsV0OwHNC6h5HO+k6UuHoBqi23HzmYGD2uQ7BpGayPTm2SJtq/y2N0FVcGpDuhMrwylN7qEWgYIDHpMMjLTzlxLyOuLrfgwkx20itAxQDVhKAiEtHt8YeMFs8kQAhRjAgMBAAECggEAfMfPAE4O55Nx4kKX9/4b+6PZH6MCtpewzUU8BhXhGTFyrH7fGYZz8NBlr0TOv2ZqCguaajjSGrPR0WXj30dkSE+OHOKeJFn7zQYNcyr5QHUkycKqTYqfj9c2U4oNNerfEf3gTAXACsZrl8FBPrkUj3MUANjS4sHNvghaW688iATaSE4RdJWksuyHlQ2H2aFoqQQqWwfZGcD8PUboFr2VnT0EfsnNEo25F3ffTwHCU8wTkC+A/R4kSe7OVziA0KLYr9gRQcS0r58s3SIe/OmpJrdsiehj77L5IKvXddr+rSiFV2lABwGTvwF8dCYJQswME951EZw0SWT9hg1VgzOGAQKBgQDlRR3kpeKuF5CYeORVwvjKTHaEJ1xj369Dw15fIkEGZviHJCvySvWmo3U1OS/taCs9u6ZjRrL0b/FIXN/BwXygTQwF+lcgT52sjbMUhBjMNjWIsQyJEVdxGo/Ck+teseIlQWcPPtdZG/IPhSM7FVfWyI124YunSFH6bxrlIwS9UQKBgQDga0ZHrwgFSrXa4C9mFA0NqGLgjNXxrzALnPFQqMeeJtWC9rBGxQkAw2/OO3375A9mvvt7k4j2UT2yA5Vn3THnK6bRdissQCYr9eFRCe2DxiM42MGBOn3YWkdP64Bh7FJgt74GrhL0cZ0obJhsSF6pfnbgQk7x90z4hmwFJJo5cwKBgQCvSYqzV8jZhZa9nCAnGawRLyilnVpb2ZcsXeB3J79DX9K/r/4RfxIVV3+zM4fbIPt+dUufN1x22mdFBgzlHENmY8G/iEi4oWZRmPzDwMMczo1bdtG4shOCm+Yxw9n+Sk42Yayj0U3gCSemigoHkCfaRPczXvng6cATkoKjkYR8IQKBgQCdaYF9bH9Svum6If/9ONlDGDZqzT6P7NLlgmsdqZWDEDg5SXGx4ikDeRJmdQJUec53wkRBlOW/6JI+2BanKNcFEHBNgfacbk6YDTrDDhSklolEKX3peC6nYAOfQk/l386Ueq12oAk5nquenDV51nVXrBYUDU60mENMG3WPUfI/kwKBgHW33OgZQvgpqDUqCzsl06RPpNnWRfp9+R8qAVPFHGERXglzedY2xRan/EDTrIXuZ6m6iQ1eyTTw8bNfOEUC1CK27xZz6HoDJ62Ho1OzCzViGCr/IgrUQ6KRCCwXLIvvcAidbmhzofaKxf+JDHcDQ8KAgYb2Gte07ToZne90+bJq";
        PublicKey publicKeyStr = getPublicKey(publicKey);
        PrivateKey privateKeyStr = getPrivateKey(privateKey);
        String text = "这是要加密的明文";
        String encrypt = encryptByPrivateKey(privateKey, text);
        System.out.println("私钥加密后的密文：" + encrypt);
        String decrypt = decryptByPublicKey(publicKey, encrypt);
        System.out.println("公钥解密后的明文：" + decrypt);
        encrypt = encryptByPublicKey(publicKey, text);
        System.out.println("公钥加密后的密文：" + encrypt);
        decrypt = decryptByPrivateKey(privateKey, encrypt);
        System.out.println("私钥解密后的明文：" + decrypt);
        String sign = sign(privateKey, text);
        System.out.println("签名后字符串：" + sign);
        boolean verify = verify(publicKey, text, sign);
        System.out.println("验签结果：" + verify);
    }
}
