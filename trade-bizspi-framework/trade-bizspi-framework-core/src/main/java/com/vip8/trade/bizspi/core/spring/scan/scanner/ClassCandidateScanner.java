package com.vip8.trade.bizspi.core.spring.scan.scanner;

import java.util.Set;

/**
 * @author ..
 * @version : ClassCandidateScanner.java, v 0.1 2020年09月16日 18:01:03 .. Exp $
 */
public interface ClassCandidateScanner {

    /**
     * scan the specified packages classes which match annotation
     *
     * @param annotation   the class annotation to match.
     * @param scanPackages the packages
     * @return the matched class
     */
    Set<Class<?>> scan(Class<?> annotation, String... scanPackages);
}
