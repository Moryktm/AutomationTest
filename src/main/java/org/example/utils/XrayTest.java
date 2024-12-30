package org.example.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface XrayTest {
    String key();  // La clé du test Jira Xray
    String bugLink() default "";  // Un lien optionnel vers un bug Jira, si applicable
}
