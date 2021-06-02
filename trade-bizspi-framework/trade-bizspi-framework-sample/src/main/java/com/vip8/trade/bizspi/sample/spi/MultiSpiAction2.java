package com.vip8.trade.bizspi.sample.spi;

import com.vip8.trade.bizspi.core.annotation.BizSpiImpl;
import com.vip8.trade.bizspi.core.api.SpiConfig;
import com.vip8.trade.bizspi.sample.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * @author .. on 2020/11/15.
 */
@BizSpiImpl
public class MultiSpiAction2 implements MultiSpiAction {

    @Override
    public boolean filter(Context ctx) {
        return true;
    }

    @Override
    public List<String> invoke(Context ctx) {
        List<String> list = new ArrayList<>();
        list.add("multi action result 2");
        System.out.println("MultiSpiAction impl 2 executed");
        return list;
    }

    @Override
    public SpiConfig config(Context ctx) {
        return SpiConfig.create("MultiSpiAction2", 2);
    }

}
