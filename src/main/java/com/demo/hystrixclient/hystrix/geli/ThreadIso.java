package com.demo.hystrixclient.hystrix.geli;

import com.netflix.config.ConfigurationManager;

/**
 * ThreadIso
 * 隔离2种策略之 1线程隔离
 *
 * @author 10905 2019/1/31
 * @version 1.0
 */
public class ThreadIso {
    public static void main(String[] args) throws InterruptedException {
//        配置线程池大小为3
        ConfigurationManager.getConfigInstance().setProperty("hystrix.threadpool.default.coreSize", 3);
        for (int i = 0; i < 6; i++) {
            MyCommand myCommand = new MyCommand(i);
            myCommand.queue();

        }
        Thread.sleep(5000);
    }
}
