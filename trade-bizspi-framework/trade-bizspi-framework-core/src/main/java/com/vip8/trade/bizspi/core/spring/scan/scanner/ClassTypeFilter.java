package com.vip8.trade.bizspi.core.spring.scan.scanner;

/**
 * @author ..
 * @version : ClassTypeFilter.java, v 0.1 2020年09月16日 18:00:12 .. Exp $
 */
public interface ClassTypeFilter {

    /**
     * @param clazz the class
     * @return true if matched
     */
    boolean match(Class<?> clazz);
}