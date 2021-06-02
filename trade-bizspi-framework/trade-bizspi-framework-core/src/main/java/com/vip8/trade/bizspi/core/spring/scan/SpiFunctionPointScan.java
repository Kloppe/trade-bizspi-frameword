package com.vip8.trade.bizspi.core.spring.scan;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ..
 * @version : SpiFunctionPointScan.java, v 0.1 2020年09月16日 17:51:18 .. Exp $
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(SpiFunctionPointScannerRegistrar.class)
public @interface SpiFunctionPointScan {

    /**
     * SPI功能点扫描包路径
     */
    @AliasFor("basePackages")
    String[] value() default {};

    /**
     * SPI功能点扫描包路径
     */
    @AliasFor("value")
    String[] basePackages() default {};
}
