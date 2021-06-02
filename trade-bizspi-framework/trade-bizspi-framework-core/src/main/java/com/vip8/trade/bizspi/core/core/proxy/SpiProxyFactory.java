package com.vip8.trade.bizspi.core.core.proxy;


import com.vip8.trade.bizspi.core.api.IBaseSpi;

import java.lang.reflect.Proxy;

/**
 * @author ..
 * @version : SpiProxyFactory.java, v 0.1 2020年09月16日 17:31:28 .. Exp $
 */
public class SpiProxyFactory {

    @SuppressWarnings( "unchecked" )
    private static <T extends IBaseSpi> T newInstance(SpiProxy<T> spiProxy) {
        final Class<T> bizSpiClass = spiProxy.getSpiClass();
        return (T) Proxy.newProxyInstance(bizSpiClass.getClassLoader(), new Class[]{bizSpiClass}, spiProxy);
    }


    public static <T extends IBaseSpi> T newInstance(Class<T> spiClass) {
        final SpiProxy<T> spiProxy = new SpiProxy<>(spiClass);
        return newInstance(spiProxy);
    }
}
