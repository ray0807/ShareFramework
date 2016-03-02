package com.corelibs.api;

import java.util.HashMap;

/**
 * 通过定义好的Manager接口以及Retrofit来生成具体Manager实例.
 * <br/>
 * Created by Ryan on 2015/12/30.
 */
public class ManagerFactory {
    private static ManagerFactory factory;
    private static HashMap<String, Object> serviceMap = new HashMap<>();

    public static ManagerFactory getFactory() {
        if (factory == null) {
            synchronized (ManagerFactory.class) {
                if (factory == null)
                    factory = new ManagerFactory();
            }
        }
        return factory;
    }

    public <T> T getManager(Class<T> clz) {
        Object service = serviceMap.get(clz.getName());
        if(service == null) {
            service = RetrofitFactory.getRetrofit().create(clz);
            serviceMap.put(clz.getName(), service);
        }
        return (T) service;
    }
}
