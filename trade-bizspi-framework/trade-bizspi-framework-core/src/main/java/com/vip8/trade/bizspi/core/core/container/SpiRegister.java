package com.vip8.trade.bizspi.core.core.container;

import com.vip8.trade.bizspi.core.api.IBaseSpi;
import org.springframework.aop.support.AopUtils;

import java.util.Collection;

/**
 * @author ..
 * @version : SpiRegister.java, v 0.1 2020年09月16日 17:21:07 .. Exp $
 */
public class SpiRegister {

    private SpiContainer container;

    private Collection<Object> bizSpiBeans;

    public void setContainer(SpiContainer container) {
        this.container = container;
    }

    public void setBizSpiBeans(Collection<Object> bizSpiBeans) {
        this.bizSpiBeans = bizSpiBeans;
    }

    public void register() {
        if (container == null) {
            return;
        }
        if (bizSpiBeans == null) {
            return;
        }
        bizSpiBeans.forEach(this::addToContainer);
    }

    @SuppressWarnings("unchecked")
    private void addToContainer(Object bean) {
        if (!(bean instanceof IBaseSpi)) {
            return;
        }
        IBaseSpi bizSpiBean = (IBaseSpi) bean;

        Class<? extends IBaseSpi> spiClass =
                (Class<? extends IBaseSpi>) AopUtils.getTargetClass(bizSpiBean).getInterfaces()[0];

        container.addToContainer(spiClass, bizSpiBean);
    }
}
