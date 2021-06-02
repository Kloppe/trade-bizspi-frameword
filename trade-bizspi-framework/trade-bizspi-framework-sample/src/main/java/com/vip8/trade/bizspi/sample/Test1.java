package com.vip8.trade.bizspi.sample;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author .. on 2020/11/15.
 */
public class Test1 {

    @Test
    public void t1() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        BizService bizService = ctx.getBean(BizService.class);
        bizService.execute();
    }

}
