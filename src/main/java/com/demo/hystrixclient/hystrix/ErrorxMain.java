package com.demo.hystrixclient.hystrix;

/**
 * HystrixMain
 * 测试hystrix
 * @author 10905 2019/1/31
 * @version 1.0
 */
public class ErrorxMain {
    public static void main(String[] args) {
//        正常请求服务
        String url="http://localhost:8080/errorHello";
        HelloCommand command=new HelloCommand(url);
        String execute = command.execute();
        System.out.println("\n服务响应结果:"+execute);

    }
}
