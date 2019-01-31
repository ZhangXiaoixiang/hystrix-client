package com.demo.hystrixclient.hystrix.cache;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

/**
 * MyCommand
 * 需要重新父类getCacheKey
 * @author 10905 2019/1/31
 * @version 1.0
 */
public class MyCommand extends HystrixCommand<String> {
    private String key;
    protected MyCommand(String key) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
        .andCommandKey(HystrixCommandKey.Factory.asKey("MyCommandKey"))
        );
        this.key=key;
    }

    @Override
    protected String run() throws Exception {
        System.out.println("正常执行********(不是读取缓存)******");
        return "";
    }

    /**
     * 重写
     * @return
     */
    @Override
    protected String getCacheKey() {

        return this.key;
    }
}
