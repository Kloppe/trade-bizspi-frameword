package com.vip8.trade.bizspi.core.annotation;

import com.vip8.trade.bizspi.core.api.IBaseSpi;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 业务SPI功能点定义注解
 *
 * @author ..
 * @version : SpiFunctionPoint.java, v 0.1 2020年09月16日 17:01:44 .. Exp $
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SpiFunctionPoint {

    /**
     * 业务功能节点描述
     */
    @AliasFor("desc")
    String value() default IBaseSpi.DEFAULT_SPI_DESCRIPTION;

    /**
     * 业务功能节点描述
     */
    @AliasFor("value")
    String desc() default IBaseSpi.DEFAULT_SPI_DESCRIPTION;

    /**
     * @return 业务功能点多个实现是否互斥执行
     */
    boolean mutex() default true;

    /**
     * custom SpiFactoryBean bean name
     * <p>
     * 注入时使用 @Resource(name = "${custom definition spiBeanName}")
     */
    String spiBeanName() default "";


}
