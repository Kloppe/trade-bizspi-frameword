package com.vip8.trade.bizspi.core.spring.scan.scanner;

import com.vip8.trade.bizspi.core.api.IBaseSpi;

/**
 * @author ..
 * @version : IBaseSpiClassTypeFilter.java, v 0.1 2020年09月16日 18:00:40 .. Exp $
 */
public class IBaseSpiClassTypeFilter implements ClassTypeFilter {

    @Override
    public boolean match(Class<?> clazz) {
        return IBaseSpi.class.isAssignableFrom(clazz);
    }
}
