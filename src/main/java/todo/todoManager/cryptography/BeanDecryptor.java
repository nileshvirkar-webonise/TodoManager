package todo.todoManager.cryptography;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidAlgorithmParameterException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import todo.todoManager.Annotations.Encrypted;
import todo.todoManager.dao.TodoDAO;

public class BeanDecryptor {

    final static Logger logger = Logger.getLogger(TodoDAO.class);

    public BeanDecryptor() {
        super();
    }

    public static Object encryptBean(Object object, Class objClass) {

        for ( Field field : objClass.getDeclaredFields() ) {
            Class<?> type = field.getType();
            String name = field.getName();
            Annotation annotation = field.getAnnotation(Encrypted.class);
            if ( annotation != null ) {
                logger.info(type + "," + name + "," + annotation);

                try {
                    String valueForEncrypt = (String) object.getClass().getMethod("get" + StringUtils.capitalize(name)).invoke(object);
                    StringEncryptor stringEncryptor = StringEncryptor.getInstance();
                    String encryptedValue = stringEncryptor.getEncryptedString(valueForEncrypt);
                    logger.info(valueForEncrypt + " and " + encryptedValue);
                    object.getClass().getMethod("set" + StringUtils.capitalize(name), type).invoke(object, encryptedValue);
                    logger.info(object);

                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                }
            }

        }
        return object;
    }

    public static Object decryptBean(Object encreptedObject, Class objClass) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

        String decryptedText = "text1";
        for ( Field field : objClass.getDeclaredFields() ) {
            Class<?> type = field.getType();
            String name = field.getName();
            Annotation annotation = field.getAnnotation(Encrypted.class);
            if ( annotation != null ) {
                logger.info(type + "," + name + "," + annotation);
                String valueForDecrypt = (String) encreptedObject.getClass().getMethod("get" + StringUtils.capitalize(name)).invoke(encreptedObject);
                decryptedText = StringEncryptor.getInstance().getDecryptedString(valueForDecrypt);
                try {
                    encreptedObject.getClass().getMethod("set" + StringUtils.capitalize(name), type).invoke(encreptedObject, decryptedText);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        return encreptedObject;

    }
}
