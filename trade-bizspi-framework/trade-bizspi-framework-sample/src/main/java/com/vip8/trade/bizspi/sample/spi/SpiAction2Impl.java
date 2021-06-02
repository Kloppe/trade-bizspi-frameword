package com.vip8.trade.bizspi.sample.spi;

import com.vip8.trade.bizspi.core.annotation.BizSpiImpl;
import com.vip8.trade.bizspi.sample.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * @author .. on 2020/11/15.
 */
@BizSpiImpl
public class SpiAction2Impl implements SpiAction2 {

    @Override
    public boolean filter(Context ctx) {
        return true;
    }

    @Override
    public List<SpiAction2Result> invoke(Context ctx) {
        List<SpiAction2Result> list = new ArrayList<>();
        list.add(new SpiAction2Result());
        System.out.println("SpiAction2Impl executed");
        return list;
    }

}
