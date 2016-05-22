package com.gv.web.iLearn.commands.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class CircuitBreakerCommand extends HystrixCommand<Boolean> {

    public CircuitBreakerCommand(String groupKey ,String key,boolean openCircuitBreaker){
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
        		.andCommandKey(HystrixCommandKey.Factory.asKey(key))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                                .withCircuitBreakerForceClosed(openCircuitBreaker)));
    }

    @Override
    public Boolean run(){return Boolean.TRUE;}

    @Override
    public Boolean getFallback() {return Boolean.FALSE;}
}

