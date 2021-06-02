package com.vip8.trade.bizspi.core.core.container;

import com.vip8.trade.bizspi.core.api.IBaseSpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author ..
 * @version : DefaultSpiContainer.java, v 0.1 2020年09月16日 17:13:07 .. Exp $
 */
@SuppressWarnings("ALL")
public class DefaultSpiContainer implements SpiContainer {

    private final Logger logger = LoggerFactory.getLogger(DefaultSpiContainer.class);

    private static final Map<Class<? extends IBaseSpi>, List<IBaseSpi>> container = new HashMap<>();

    private static final Map<Class<? extends IBaseSpi>, Boolean> mutexContainer = new HashMap<>();

    public static class DefaultSpiContainerInstance {
        static final DefaultSpiContainer INSTANCE = new DefaultSpiContainer();
    }

    public static DefaultSpiContainer getInstance() {
        return DefaultSpiContainerInstance.INSTANCE;
    }

    @Override
    public Set<Map.Entry<Class<? extends IBaseSpi>, List<IBaseSpi>>> getAllSpiImpl() {
        return Collections.unmodifiableSet(container.entrySet());
    }


    @Override
    public List<? extends IBaseSpi> lookup(Class<? extends IBaseSpi> spiClass) {
        return container.get(spiClass);
    }

    @Override
    public Boolean mutexOf(Class<? extends IBaseSpi> spiClass) {
        return Objects.requireNonNull(mutexContainer.get(spiClass),
                String.format("%s weather mutex execute is unknown", spiClass.getName()));
    }

    @Override
    public void setSpiMutex(Class<? extends IBaseSpi> spiClass, Boolean mutex) {
        if (mutex == null) {
            throw new IllegalArgumentException(String.format("%s , parameter mutex is null", spiClass.getName()));
        }
        synchronized (mutexContainer) {
            // already registered
            if (mutexContainer.get(spiClass) != null) {
                return;
            }
            mutexContainer.put(spiClass, mutex);
        }
    }

    @Override
    public void addToContainer(Class<? extends IBaseSpi> spiClass, IBaseSpi spiImpl) {
        synchronized (container) {

            List<IBaseSpi> spiImplInstances = getRegisteredSpiImplInstances(spiClass);

            if (spiImplInstances.contains(spiImpl)) {
                return;
            }
            spiImplInstances.add(spiImpl);

            spiImplInstances.sort(Comparator.comparingInt(o -> o.config(null).getPriority()));

            List<IBaseSpi> unmodifiableInstances = Collections.unmodifiableList(spiImplInstances);

            loggerSpiRegister(spiClass, unmodifiableInstances);

            container.put(spiClass, unmodifiableInstances);

        }
    }

    private List<IBaseSpi> getRegisteredSpiImplInstances(Class<? extends IBaseSpi> spiClass) {
        if (container.get(spiClass) == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(container.get(spiClass));
    }

    private void loggerSpiRegister(Class spiClass, List<IBaseSpi> spiImplList) {
        for (IBaseSpi spiImpl : spiImplList) {
            logger.info("【BizSpiEngine】 biz spi registered :spi interface:[{}] ,spi implement:[{}] ,{}",
                    spiClass.getName(),
                    spiImpl.getClass().getName(), spiImpl.config(null).toString());
        }
    }
}
