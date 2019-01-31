package com.demo.hystrixclient.hystrix.common;


import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.sun.org.apache.regexp.internal.RE;

import java.util.Collection;
import java.util.Map;

/**
 * MyHystrixCollapser
 * 批处理请求 MyHystrixCollapser
 * 就是合并处理器
 * @author 10905 2019/1/31
 * @version 1.0
 */
public class MyHystrixCollapser extends HystrixCollapser<Map<String, ClloapserCommand.Person>, ClloapserCommand.Person, String> {
    String personName;

    /**
     * 一参数构造
     * @param personName
     */
    public MyHystrixCollapser(String personName) {
        this.personName = personName;
    }

    @Override
    public String getRequestArgument() {
        return personName;
    }

    @Override
    protected HystrixCommand<Map<String, ClloapserCommand.Person>> createCommand(Collection<CollapsedRequest<ClloapserCommand.Person, String>> collapsedRequests) {
        /**
         * 批处理
         */
        return new ClloapserCommand(collapsedRequests);
    }

    @Override
    protected void mapResponseToRequests(Map<String, ClloapserCommand.Person> batchResponse, Collection<CollapsedRequest<ClloapserCommand.Person, String>> collapsedRequests) {
//让结果请求进行关联
        for (CollapsedRequest<ClloapserCommand.Person, String> request : collapsedRequests) {

//            获取单个响应
            ClloapserCommand.Person SingleResult = batchResponse.get(request.getArgument());
//        关联到请求中
            request.setResponse(SingleResult);
        }
    }
}
