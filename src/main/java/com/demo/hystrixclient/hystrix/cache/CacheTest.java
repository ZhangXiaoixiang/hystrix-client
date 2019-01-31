package com.demo.hystrixclient.hystrix.cache;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

/**
 * CacheTest
 * 测试请求缓存
 *
 * @author 10905 2019/1/31
 * @version 1.0
 */
public class CacheTest {
    public static void main(String[] args) {
//        初始化上下文
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
//        请求正常的服务
        String key = "cache-key";
        MyCommand c1 = new MyCommand(key);
        MyCommand c2 = new MyCommand(key);
        MyCommand c3 = new MyCommand(key);
        System.out.println(c1.execute() + "C1是否读取缓存:" + c1.isResponseFromCache());
        System.out.println(c2.execute() + "C2是否读取缓存:" + c2.isResponseFromCache());
        System.out.println(c3.execute() + "C3是否读取缓存:" + c3.isResponseFromCache());
//        获取缓存实例
        HystrixRequestCache cache = HystrixRequestCache
                .getInstance(HystrixCommandKey
                        .Factory.asKey("MyCommandKey"), HystrixConcurrencyStrategyDefault.getInstance());
//清空缓存key
        cache.clear(key);
        System.out.println("-------------清空缓存key------------");
        MyCommand c4=new MyCommand(key);
        System.out.println(c4.execute() + "C4是否读取缓存:" + c4.isResponseFromCache());
        System.out.println("\n\n记得关闭上下文,   this.key=key;必须在命令设置哈");
        context.shutdown();
    }
}
