package com.demo.hystrixclient.hystrix.common;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * CollapseTest
 *
 * @author 10905 2019/1/31
 * @version 1.0
 */
public class CollapseTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        收集1秒内发生的请求,合并为一个命令
        ConfigurationManager.getConfigInstance().setProperty("hystrix.collapser.default.timerDelayInMilliseconds",1000);
//        请求上下文
        HystrixRequestContext context=HystrixRequestContext.initializeContext();
//        创建请求合并处理器
        MyHystrixCollapser myHystrixCollapser=new MyHystrixCollapser("张晓祥");
        MyHystrixCollapser myHystrixCollapser1=new MyHystrixCollapser("凯");
        MyHystrixCollapser myHystrixCollapser2=new MyHystrixCollapser("鲁班七号");
        MyHystrixCollapser myHystrixCollapser3=new MyHystrixCollapser("诸葛亮");
        MyHystrixCollapser myHystrixCollapser4=new MyHystrixCollapser("王昭君");
        MyHystrixCollapser myHystrixCollapser5=new MyHystrixCollapser("百里守约");
//        异步执行
        Future<ClloapserCommand.Person> future=myHystrixCollapser.queue();
        Future<ClloapserCommand.Person> future1=myHystrixCollapser1.queue();
        Future<ClloapserCommand.Person> future2=myHystrixCollapser2.queue();
        Future<ClloapserCommand.Person> future3=myHystrixCollapser3.queue();
        Future<ClloapserCommand.Person> future4=myHystrixCollapser4.queue();
        Future<ClloapserCommand.Person> future5=myHystrixCollapser5.queue();
//        打印
        System.out.println(future.get());
        System.out.println(future1.get());
        System.out.println(future2.get());
        System.out.println(future3.get());
        System.out.println(future4.get());
        System.out.println(future5.get());
        context.shutdown();
    }
}
