package com.vip8.trade.bizspi.sample.spi;

import com.vip8.trade.bizspi.core.annotation.BizSpiImpl;
import com.vip8.trade.bizspi.sample.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * @author .. on 2020/11/15.
 */
@BizSpiImpl
public class SpiAction1Impl implements SpiAction1 {

    @Override
    public boolean filter(Context ctx) {
        return true;
    }

    @Override
    public List<SpiAction1Result> invoke(Context ctx) {
        List<SpiAction1Result> list = new ArrayList<>();
        list.add(new SpiAction1Result());
        System.out.println("SpiAction1Impl executed");
        return list;
    }

}
