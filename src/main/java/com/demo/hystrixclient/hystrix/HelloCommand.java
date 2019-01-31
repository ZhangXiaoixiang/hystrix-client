package com.demo.hystrixclient.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * HelloCommand
 * 需要构造器
 *
 * @author 10905 2019/1/31
 * @version 1.0
 */
public class HelloCommand extends HystrixCommand<String> {
    private String url;
    CloseableHttpClient httpClient;

    public HelloCommand(String url) {
//        调父类的构造器,设置命令组,默认用来作为线程池的key
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
//        调用Http客户端
        this.httpClient = HttpClients.createDefault();
        this.url = url;
    }

    @Override
    protected String run() throws Exception {

        try {
//        调用GET方法请求服务
            HttpGet httpGet = new HttpGet(url);
//        得到响应的结果
            HttpResponse response = httpClient.execute(httpGet);
//        解析并执行返回命令
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";

    }

    @Override
    protected String getFallback() {
        System.out.println("执行了HelloCommand回退方法...");
        return"error";
    }
}
