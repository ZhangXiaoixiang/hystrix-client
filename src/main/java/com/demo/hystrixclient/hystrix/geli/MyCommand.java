package com.demo.hystrixclient.hystrix.geli;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * MyCommand
 * 这是共同的命令类
 * @author 10905 2019/1/31
 * @version 1.0
 */
public class MyCommand extends HystrixCommand<String> {
    int index;

    public MyCommand(int index) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup")));
        this.index=index;
    }

    @Override
    protected String run() throws Exception {
        Thread.sleep(500);
        System.out.println("run 当前的索引是:"+index);
        return "";
    }

    @Override
    protected String getFallback() {
        System.out.println("getFallback  当前的索引是:"+index);
        return "";
    }
}
