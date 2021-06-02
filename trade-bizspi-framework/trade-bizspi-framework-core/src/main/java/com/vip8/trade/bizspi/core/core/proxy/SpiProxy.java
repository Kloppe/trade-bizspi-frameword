package com.vip8.trade.bizspi.core.core.proxy;

import com.vip8.trade.bizspi.core.api.IBaseSpi;
import com.vip8.trade.bizspi.core.core.container.SpiContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ..
 * @version : SpiProxy.java, v 0.1 2020年09月16日 17:23:33 .. Exp $
 */
@SuppressWarnings( "ALL" )
public class SpiProxy<T extends IBaseSpi> implements InvocationHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpiProxy.class);

    private Class<T> spiClass;

    SpiProxy(Class<T> spiClass) {
        this.spiClass = spiClass;
    }

    Class<T> getSpiClass() {
        return spiClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        SpiContainer spiContainer = SpiContainer.DEFAULT_INSTANCE;

        List<? extends IBaseSpi> spiImplList = spiContainer.lookup(spiClass);

        boolean isMutexSpi = spiContainer.mutexOf(spiClass);

        if (spiImplList == null || spiImplList.isEmpty()) {
            LOGGER.warn("【BizSpiEngine】can not find any spi implementations for spi class:{}", spiClass.getName());
            return new ArrayList<>();
        }

        List compositeResult = new ArrayList<>();

        for (IBaseSpi spi : spiImplList) {

            if (null != args && spi.filter(args[0])) {
                List<?> result;
                try {
                    result = (List<?>) method.invoke(spi, args);
                } catch (InvocationTargetException e) {
                    throw e.getTargetException();
                }
                // 叠加执行
                if (result != null && !result.isEmpty()) {
                    compositeResult.addAll(result);
                }
                // 互斥执行
                if (isMutexSpi) {
                    break;
                }
            }
        }
        return compositeResult;
    }

}
