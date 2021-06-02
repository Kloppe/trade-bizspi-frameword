package com.vip8.trade.bizspi.sample.spi;

import com.vip8.trade.bizspi.core.annotation.SpiFunctionPoint;
import com.vip8.trade.bizspi.core.api.IBaseSpi;
import com.vip8.trade.bizspi.sample.Context;

/**
 * @author .. on 2020/11/15.
 */
@SpiFunctionPoint( desc = "SpiAction2", spiBeanName = SpiAction2.BEAN_NAME )
public interface SpiAction2 extends IBaseSpi<Context, SpiAction2Result> {

    String BEAN_NAME = "spiAction2";

}