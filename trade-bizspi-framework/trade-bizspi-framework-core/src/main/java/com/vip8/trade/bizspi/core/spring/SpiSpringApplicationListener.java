package com.vip8.trade.bizspi.core.spring;

import com.vip8.trade.bizspi.core.annotation.BizSpiImpl;
import com.vip8.trade.bizspi.core.core.container.SpiContainer;
import com.vip8.trade.bizspi.core.core.container.SpiRegister;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * @author ..
 * @version : SpiSpringApplicationListener.java, v 0.1 2020年09月16日 17:47:17 .. Exp $
 */
public class SpiSpringApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    static final String BEAN_NAME = "bizSpiSpringApplicationListener";

    private SpiContainer container = SpiContainer.DEFAULT_INSTANCE;

    @Override
    @SuppressWarnings( "unchecked" )
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Map<String, Object> spiBeanMap = event.getApplicationContext().getBeansWithAnnotation(BizSpiImpl.class);

        if (CollectionUtils.isEmpty(spiBeanMap)) {
            return;
        }
        registerBizSpiBeans(spiBeanMap);
    }

    private void registerBizSpiBeans(Map<String, Object> spiBeanMap) {
        SpiRegister spiRegister = new SpiRegister();
        spiRegister.setContainer(container);
        spiRegister.setBizSpiBeans(spiBeanMap.values());
        spiRegister.register();
    }
}
