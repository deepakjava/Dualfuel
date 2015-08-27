package com.test.encryptor;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.hibernate3.encryptor.HibernatePBEStringEncryptor;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.assertEquals;

public class TestEncyptor {

    private ApplicationContext context = null;
    @Before
    public void init(){
        context =
                new ClassPathXmlApplicationContext(new String[] {"spring-beans_def.xml"});


    }

    @Test
    public void decryptString(){
        String decryptMe = "Y7ZJpt+HeK1wSPrBLY/S6w==";
        HibernatePBEStringEncryptor hibernateStringEncryptor = context.getBean("hibernateStringEncryptor", HibernatePBEStringEncryptor.class);
        String decryptedSting = hibernateStringEncryptor.decrypt(decryptMe);

        System.out.println(decryptedSting);
//		assertEquals( "GOOD", decryptMe, hibernateStringEncryptor.encrypt(decryptedSting) );
    }

    @Test
    public void encryptString(){
        String encryptMe = "test";
        HibernatePBEStringEncryptor hibernateStringEncryptor = context.getBean("hibernateStringEncryptor", HibernatePBEStringEncryptor.class);
        String encryptedSting = hibernateStringEncryptor.encrypt(encryptMe);

        System.out.println(encryptedSting);
        assertEquals( "GOOD", encryptMe, hibernateStringEncryptor.decrypt(encryptedSting) );
    }

    @Test
    public void encryptStringDBUser(){
        String encryptMe = "o+nd1wr2KqsyYgHU0wm2N3taTzzc08ce";
        StandardPBEStringEncryptor hibernateStringEncryptor = context.getBean("configurationEncryptor", StandardPBEStringEncryptor.class);
        String encryptedSting = hibernateStringEncryptor.encrypt(encryptMe);

        System.out.println(encryptedSting);
        assertEquals( "GOOD", encryptMe, hibernateStringEncryptor.decrypt(encryptedSting) );
    }

}
