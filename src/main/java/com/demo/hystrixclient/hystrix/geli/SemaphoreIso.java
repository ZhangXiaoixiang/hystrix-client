package com.demo.hystrixclient.hystrix.geli;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * SemaphoreIso
 *SemaphoreIso 隔离2种策略之 2信号隔离策略
 * @author 10905 2019/1/31
 * @version 1.0
 */
public class SemaphoreIso {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("需要看是否执行回退,注意配置类不要写错-----");
        //        配置信号策略进行隔离
        ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.execution.isolation.strategy",
                HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE);
//    设置最大并发,默认是10,本例子为2
        ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.execution.isolation.semaphore.maxConcurrentRequests",2);
//      设置执行回退方法的最大并发数  默认是10,本例子为20

        ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.fallback.semaphore.maxConcurrentRequests",2);
//        不要显式创建线程，请使用线程池。
        for (int i = 0; i < 6; i++) {
            final  int index=i;
            Thread t=new Thread(){
                @Override
                public void  run(){
                    MyCommand myCommand=new MyCommand(index);
                    myCommand.execute();

                }
            };
            t.start();

        }
        Thread.sleep(5000);

    }
}
