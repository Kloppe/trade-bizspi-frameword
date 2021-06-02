package com.vip8.trade.bizspi.core.api;

import java.util.List;

/**
 * SPI功能接口定义必须继承该接口
 *
 * @author ..
 * @version : IBaseSPI.java, v 0.1 2020年09月16日 17:03:04 .. Exp $
 */
public interface IBaseSpi<In, Out> {

    /**
     * spi默认描述
     */
    String DEFAULT_SPI_DESCRIPTION = "";

    /**
     * spi执行过滤
     *
     * @param ctx spi上下文
     * @return true:执行spi，false:跳过执行
     */
    boolean filter(In ctx);

    /**
     * spi执行内容
     *
     * @param ctx spi上下文
     * @return spi执行结果组合
     */
    List<Out> invoke(In ctx);

    /**
     * spi配置信息
     *
     * @param ctx spi上下文
     * @return spi配置
     */
    default SpiConfig config(In ctx) {
        return SpiConfig.create();
    }
}
