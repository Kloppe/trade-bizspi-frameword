package com.vip8.trade.bizspi.sample.spi;

import com.vip8.trade.bizspi.core.annotation.SpiFunctionPoint;
import com.vip8.trade.bizspi.core.api.IBaseSpi;
import com.vip8.trade.bizspi.sample.Context;

/**
 * @author .. on 2020/11/15.
 */
@SpiFunctionPoint( desc = "SpiAction1", spiBeanName = SpiAction1.BEAN_NAME )
public interface SpiAction1 extends IBaseSpi<Context, SpiAction1Result> {

    String BEAN_NAME = "spiAction1";

}