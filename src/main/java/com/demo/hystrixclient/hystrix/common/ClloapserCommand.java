package com.demo.hystrixclient.hystrix.common;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.*;

/**
 * ClloapserCommand
 * 多个命令合N为一
 *
 * @author 10905 2019/1/31
 * @version 1.0
 */
public class ClloapserCommand extends HystrixCommand<Map<String, ClloapserCommand.Person>> {
    /**
     * 请求集合,第一个String 是请求的返回类型,第二个是请求的类型,封装成下面的Collection的
     */

    Collection<HystrixCollapser.CollapsedRequest<Person, String>> requests;

    protected ClloapserCommand(Collection<HystrixCollapser.CollapsedRequest<Person, String>> requests) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup")));
        this.requests=requests;
    }

    @Override
    public Map<String, Person> run() throws Exception {
        System.out.println("收集的参数后执行命令,参数数量:"+requests.size());
//        处理参数
        List<String> personNames=new ArrayList<>();
        for (HystrixCollapser.CollapsedRequest<Person, String> request : requests) {
            personNames.add(request.getArgument());
        }
//        调用服务(模拟调用),根据名字获取person的map
        Map<String,Person> result=callService(personNames);

        return result;
    }
    private Map<String,Person> callService(List<String> personNames){
        Map<String,Person> result=new HashMap<>();
        /**
         * 模拟数据库数据
         */
        for (String personName : personNames) {
            Person person=new Person();
            person.id=UUID.randomUUID().toString();
            person.name=personName;
            person.age=new Random().nextInt(30);
            result.put(personName,person);
        }
        return result;

    }


    /**
     * 实体类person
     */
    static class Person {
        private String id;
        private String name;
        private Integer age;

        public Person(String id, String name, Integer age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public Person() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

}
