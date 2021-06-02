package com.vip8.trade.bizspi.sample.spi;

import com.vip8.trade.bizspi.core.annotation.SpiFunctionPoint;
import com.vip8.trade.bizspi.core.api.IBaseSpi;
import com.vip8.trade.bizspi.sample.Context;

/**
 * @author .. on 2020/11/15.
 */
@SpiFunctionPoint( desc = "MultiSpiAction", spiBeanName = MultiSpiAction.BEAN_NAME, mutex = false )
public interface MultiSpiAction extends IBaseSpi<Context, String> {

    String BEAN_NAME = "multiSpiAction";

}