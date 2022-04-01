package com.online_shopping.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author yeye
 * @date 2022/4/1  23:21
 */

@Documented
@Target({PARAMETER})
@Retention(RUNTIME)
public @interface ParseUser {


    boolean required() default true;
}
