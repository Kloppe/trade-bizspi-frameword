package com.vip8.trade.bizspi.sample;

import com.vip8.trade.bizspi.sample.spi.MultiSpiAction;
import com.vip8.trade.bizspi.sample.spi.SpiAction1;
import com.vip8.trade.bizspi.sample.spi.SpiAction1Result;
import com.vip8.trade.bizspi.sample.spi.SpiAction2;
import com.vip8.trade.bizspi.sample.spi.SpiAction2Result;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author .. on 2020/11/15.
 */
@Component
public class BizService {

    /**
     * 业务扩展点1
     */
    @Resource( name = SpiAction1.BEAN_NAME )
    private SpiAction1 spiAction1;

    /**
     * 业务扩展点2
     */
    @Resource( name = SpiAction2.BEAN_NAME )
    private SpiAction2 spiAction2;

    /**
     * 业务扩展点3，带有个多个实现
     */
    @Resource( name = MultiSpiAction.BEAN_NAME )
    private MultiSpiAction multiSpiAction;

    /**
     * 主业务流执行方法
     */
    public void execute() {
        // 统一的执行上下文
        Context context = new Context();
        
        // 扩展点1执行
        List<SpiAction1Result> result1 = spiAction1.invoke(context);
        
        // 扩展点2执行
        List<SpiAction2Result> result2 = spiAction2.invoke(context);
        
        // 扩展点3执行
        List<String> result = multiSpiAction.invoke(context);
        
        System.out.println(result);
    }

}
