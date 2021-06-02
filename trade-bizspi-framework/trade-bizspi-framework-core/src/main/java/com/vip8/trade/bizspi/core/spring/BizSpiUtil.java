package com.vip8.trade.bizspi.core.spring;

import com.vip8.trade.bizspi.core.annotation.SpiFunctionPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.beans.Introspector;
import java.util.Objects;
import java.util.Set;

/**
 * @author ..
 * @version : BizSpiUtil.java, v 0.1 2020年09月16日 17:52:05 .. Exp $
 */
public final class BizSpiUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(BizSpiUtil.class);

    public static void registerSpiApplicationListenerBeanDefinition(BeanDefinitionRegistry registry) {
        String beanName = SpiSpringApplicationListener.BEAN_NAME;
        GenericBeanDefinition beanDef = new GenericBeanDefinition();
        beanDef.setBeanClass(SpiSpringApplicationListener.class);
        registry.registerBeanDefinition(beanName, beanDef);
        LOGGER.info("【BizSpiEngine】 registered :Bean '{}' of type [{}]", beanName, SpiSpringApplicationListener.class.getName());
    }


    public static void registerBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions, BeanDefinitionRegistry registry) {
        if (null == beanDefinitions || beanDefinitions.isEmpty()) {
            return;
        }
        for (BeanDefinitionHolder beanDefinition : beanDefinitions) {
            BeanDefinitionReaderUtils.registerBeanDefinition(beanDefinition, registry);
            LOGGER.info("【BizSpiEngine】 spring bean registered,'{}' of type [{}] ,spi interface:[{}]",
                    beanDefinition.getBeanName(),
                    beanDefinition.getBeanDefinition().getBeanClassName(),
                    beanDefinition.getBeanDefinition().getConstructorArgumentValues().getGenericArgumentValues().get(0).getValue());
        }
    }


    public static BeanDefinitionHolder createSpiFactoryBeanBeanDefinitionHolder(Class<?> spiClass) {
        GenericBeanDefinition beanDef = new GenericBeanDefinition();
        beanDef.setBeanClass(SpiFactoryBean.class);
        beanDef.getConstructorArgumentValues().addGenericArgumentValue(spiClass);
        String beanName = generateSpiFactoryBeanName(spiClass);
        LOGGER.info("【BizSpiEngine】 spring bean created, '{}' of type [{}] for spi interface:[{}] , mutex:[{}]",
                beanName, SpiFactoryBean.class.getName(), spiClass.getName(), BizSpiUtil.isSpiFunctionPointMutex(spiClass));

        return new BeanDefinitionHolder(beanDef, beanName);
    }

    private static String generateSpiFactoryBeanName(Class<?> spiClass) {
        SpiFunctionPoint sfpAnn = spiClass.getAnnotation(SpiFunctionPoint.class);
        if (sfpAnn != null && StringUtils.hasText(sfpAnn.spiBeanName())) {
            return sfpAnn.spiBeanName();
        }
        return BizSpiUtil.generateDefaultBeanName(spiClass);
    }

    private static String generateDefaultBeanName(Class<?> clazz) {
        final String shortClassName = ClassUtils.getShortName(clazz);
        return Introspector.decapitalize(shortClassName);
    }

    public static SpiFunctionPoint getSpiFunctionPointAnnotation(Class<?> spiClass) {
        SpiFunctionPoint spiFunctionPoint = spiClass.getAnnotation(SpiFunctionPoint.class);
        Objects.requireNonNull(spiFunctionPoint,
                String.format("%s has not annotation of @SpiFunctionPoint", spiClass.getName()));
        return spiFunctionPoint;
    }

    public static boolean isSpiFunctionPointMutex(Class<?> spiClass) {
        return getSpiFunctionPointAnnotation(spiClass).mutex();
    }
}
