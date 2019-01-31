package com.demo.hystrixclient.hystrix;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * FallbackTest
 *
 * @author 10905 2019/1/31
 * @version 1.0
 */
public class FallbackTest {
    static class  FallbackCommand extends HystrixCommand<String>{
        public FallbackCommand(){
            super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        }

        @Override
        protected String run() throws Exception {
            System.out.println("命令执行!");
            return "";
        }

        @Override
        protected String getFallback() {
            System.out.println("执行回退方法");
            return "fallback";
        }
    }
    public static void main(String[] args) {

//        断路器强制被打开
        ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.circuitBreaker.forceOpen","true");
       FallbackCommand command=new FallbackCommand();
       command.execute();
//      创建第二个命令,断路器关闭
        ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.circuitBreaker.forceOpen","false");
        FallbackCommand command2=new FallbackCommand();
        command2.execute();
    }
}
