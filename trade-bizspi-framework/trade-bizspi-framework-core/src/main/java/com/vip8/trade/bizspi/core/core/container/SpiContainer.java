package com.vip8.trade.bizspi.core.core.container;

import com.vip8.trade.bizspi.core.api.IBaseSpi;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ..
 * @version : SpiContainer.java, v 0.1 2020年09月16日 17:12:53 .. Exp $
 */
public interface SpiContainer {

    SpiContainer DEFAULT_INSTANCE = DefaultSpiContainer.getInstance();

    /**
     * 获取某个SPI实现
     *
     * @return 当前SPI所有实现
     */
    List<? extends IBaseSpi> lookup(Class<? extends IBaseSpi> spiClass);

    /**
     * @return 叠加 还是 互斥执行
     */
    Boolean mutexOf(Class<? extends IBaseSpi> spiClass);

    /**
     * 设置SPI功能点执行策略(互斥执行、还是叠加执行)
     * <p>
     * mutex = true : 互斥执行，mutex = false : 叠加执行
     */
    void setSpiMutex(Class<? extends IBaseSpi> spiClass, Boolean mutex);

    /**
     * SPI实现添加到SPI容器中
     */
    void addToContainer(Class<? extends IBaseSpi> spiClass, IBaseSpi spiImpl);

    /**
     * 获取所有SPI实现
     *
     * @return 所有SPI实现
     */
    Set<Map.Entry<Class<? extends IBaseSpi>, List<IBaseSpi>>> getAllSpiImpl();
}
