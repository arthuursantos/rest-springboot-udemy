package org.example.restspringbootudemy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
//
//import java.util.HashMap;
//import java.util.Map;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

//        Pbkdf2PasswordEncoder pbkdf2encoder = new Pbkdf2PasswordEncoder("", 8, 185000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
//        Map<String, PasswordEncoder> encoders = new HashMap<>();
//        encoders.put("pbkdf2", pbkdf2encoder);
//        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
//        passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2encoder);
//        String hash1 = passwordEncoder.encode("sacola07");
//        System.out.println("My hash1 " + hash1);

    }

}
