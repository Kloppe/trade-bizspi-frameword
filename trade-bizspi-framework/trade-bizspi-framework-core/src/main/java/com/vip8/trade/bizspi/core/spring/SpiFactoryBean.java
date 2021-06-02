package com.vip8.trade.bizspi.core.spring;

import com.vip8.trade.bizspi.core.api.IBaseSpi;
import com.vip8.trade.bizspi.core.core.container.SpiContainer;
import com.vip8.trade.bizspi.core.core.proxy.SpiProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author ..
 * @version : SpiFactoryBean.java, v 0.1 2020年09月16日 17:47:58 .. Exp $
 */
public class SpiFactoryBean<T extends IBaseSpi> implements FactoryBean<T>, InitializingBean {

    @SuppressWarnings("unchecked")
    public static final String FIELD_SPI_FUNCTION_POINT_CLASS = "spiFunctionPointClass";

    private Class<T> spiFunctionPointClass;

    public SpiFactoryBean() {

    }

    public SpiFactoryBean(Class<T> spiFunctionPointClass) {
        this.spiFunctionPointClass = spiFunctionPointClass;
    }

    public void setSpiFunctionPointClass(Class<T> spiFunctionPointClass) {
        this.spiFunctionPointClass = spiFunctionPointClass;
    }


    @Override
    public T getObject() {
        return SpiProxyFactory.newInstance(spiFunctionPointClass);
    }

    @Override
    public Class<T> getObjectType() {
        return spiFunctionPointClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        SpiContainer.DEFAULT_INSTANCE.setSpiMutex(
                spiFunctionPointClass, BizSpiUtil.isSpiFunctionPointMutex(spiFunctionPointClass));
    }
}
