package com.vip8.trade.bizspi.core.api;

/**
 * SPI功能点配置,定义了该SPI功能点执行优先级、是叠加执行 还是 互斥执行
 *
 * @author ..
 * @version : SpiConfig.java, v 0.1 2020年09月16日 17:03:13 .. Exp $
 */
public class SpiConfig {

    /**
     * 名称
     */
    private String name;

    /**
     * spi执行顺序，按照值从小到大往后执行
     * <p>
     * priority 值越小越先执行，值越大越靠后执行
     */
    private int priority;

    public SpiConfig() {
    }

    public SpiConfig(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public static SpiConfig create() {
        return new SpiConfig();
    }

    public static SpiConfig create(String name, int priority) {
        return new SpiConfig(name, priority);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "SpiConfig{"
                + "name='" + name + '\''
                + ", priority=" + priority +
                '}';
    }
}

