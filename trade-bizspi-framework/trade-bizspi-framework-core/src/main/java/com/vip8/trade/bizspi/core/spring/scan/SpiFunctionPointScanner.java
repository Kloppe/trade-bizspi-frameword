package com.vip8.trade.bizspi.core.spring.scan;

import com.vip8.trade.bizspi.core.annotation.SpiFunctionPoint;
import com.vip8.trade.bizspi.core.spring.BizSpiUtil;
import com.vip8.trade.bizspi.core.spring.scan.scanner.AbstractClassCandidateScanner;
import com.vip8.trade.bizspi.core.spring.scan.scanner.FastClassPathScanner;
import com.vip8.trade.bizspi.core.spring.scan.scanner.IBaseSpiClassTypeFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ..
 * @version : SpiFunctionPointScanner.java, v 0.1 2020年09月16日 17:58:25 .. Exp $
 */
public class SpiFunctionPointScanner {

    private Logger LOGGER = LoggerFactory.getLogger(SpiFunctionPointScanner.class);

    private ClassLoader classLoader;

    private BeanDefinitionRegistry registry;

    SpiFunctionPointScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * scan packages and register {@linkplain SpiFactoryBean}
     *
     * @param scanPackages the scan packages
     */
    void scan(String... scanPackages) {

        Set<BeanDefinitionHolder> beanDefinitions = doScan(scanPackages);

        BizSpiUtil.registerBeanDefinitions(beanDefinitions, registry);

        this.processBeanDefinitions(beanDefinitions);
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        //noop
    }

    private Set<BeanDefinitionHolder> doScan(String... scanPackages) {
        if (scanPackages == null || scanPackages.length <= 0) {
            LOGGER.warn("【BizSpiEngine】scan packages,scanPackages is empty");
            return null;
        }
        LOGGER.info("【BizSpiEngine】scan packages path: {}", Arrays.toString(scanPackages));
        Set<Class<?>> spiClasses = scanSpiFunctionPointInterfaces(scanPackages);
        if (spiClasses == null || spiClasses.isEmpty()) {
            logScannedSpiInterfacesEmpty(scanPackages);
            return null;
        }
        logScannedSpiInterfaces(spiClasses);
        Set<BeanDefinitionHolder> beanDefHolders = new HashSet<>(spiClasses.size());
        for (Class<?> spiClass : spiClasses) {
            beanDefHolders.add(BizSpiUtil.createSpiFactoryBeanBeanDefinitionHolder(spiClass));
        }
        return beanDefHolders;
    }

    private Set<Class<?>> scanSpiFunctionPointInterfaces(String... scanPackages) {
        AbstractClassCandidateScanner fastClassPathScanner = new FastClassPathScanner();
        fastClassPathScanner.addClassTypeFilter(new IBaseSpiClassTypeFilter());
        fastClassPathScanner.addClassLoader(this.classLoader);
        return fastClassPathScanner.scan(SpiFunctionPoint.class, scanPackages);
    }

    private void logScannedSpiInterfaces(Set<Class<?>> spiClasses) {
        for (Class spiClass : spiClasses) {
            LOGGER.info("【BizSpiEngine】scanned class annotated with @SpiFunctionPoint of [{}]", spiClass);
        }
    }

    private void logScannedSpiInterfacesEmpty(String[] scanPackages) {
        LOGGER.warn("【BizSpiEngine】scanned packages path:{},not any interfaces which annotated with @SpiFunctionPoint present", Arrays.toString(scanPackages));
    }

}
