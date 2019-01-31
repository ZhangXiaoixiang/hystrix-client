package com.demo.hystrixclient.hystrix.close;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandMetrics;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * CloseTest
 * 测试断路器关闭
 *
 * @author 10905 2019/1/31
 * @version 1.0
 */
public class CloseTest {
    static class  MyCommand2 extends HystrixCommand<String>{
        /**
         * 设置关闭判断时间
         */
        private Boolean isTimeOut;

        /**
         * 设置超时时间500毫秒
         * @param isTimeOut
         */
        protected MyCommand2(Boolean isTimeOut) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
            .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
            .withExecutionTimeoutInMilliseconds(500))
            );
            this.isTimeOut=isTimeOut;
        }

        @Override
        protected String run() throws Exception {
//            让外部决定是否超时
            if (isTimeOut){
                Thread.sleep(800);
            }else {
                Thread.sleep(200);
            }

            return "";
        }


        @Override
        protected String getFallback() {
            System.out.println("进入getFallback...");
            return "";
        }
    }


    public static void main(String[] args) throws InterruptedException {
        /**
         * 10秒内有3个请求符合第一个条件
         */
        ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds",10000);
        ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.circuitBreaker.requestVolumeThreshold",3);
        ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.circuitBreaker.errorThresholdPercentage",50);
//设置休眠期
        ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.circuitBreaker.sleepWindowInMilliseconds",3000);
        Boolean isTimeOut=true;
        for (int i = 0; i < 10; i++) {
//            执行的命令全部会超时
            MyCommand2 myCommand2=new MyCommand2(isTimeOut);
            myCommand2.execute();
//            输出健康信息状态
            HystrixCommandMetrics.HealthCounts healthCounts = myCommand2.getMetrics().getHealthCounts();
            System.out.println("断路器的状态为:"+myCommand2.isCircuitBreakerOpen()+",请求书总和:"+healthCounts.getTotalRequests());

            if (myCommand2.isCircuitBreakerOpen()){
                //            打开断路器
                isTimeOut=false;
                System.out.println("---------------打开断路器--------");
//            休眠结束后等待4秒,确保结束
                Thread.sleep(4000);

            }


        }

    }
}
