package com.vip8.trade.bizspi.core.annotation;

import com.vip8.trade.bizspi.core.api.IBaseSpi;
import org.springframework.stereotype.Service;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 业务SPI功能点实现标记
 *
 * @author ..
 * @version : BizSpiImpl.java, v 0.1 2020年09月16日 16:58:32 .. Exp $
 */
@Service
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BizSpiImpl {

    /**
     * @return 描述信息
     */
    String desc() default IBaseSpi.DEFAULT_SPI_DESCRIPTION;
}
