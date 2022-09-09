package com.example.nplusone.config;

import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.test.context.TestConfiguration;

import javax.sql.DataSource;

import static net.ttddyy.dsproxy.listener.logging.CommonsLogLevel.DEBUG;
import static net.ttddyy.dsproxy.listener.logging.CommonsLogLevel.INFO;

@TestConfiguration
public class MyTestConfig implements BeanPostProcessor {
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException
    {

        if (bean instanceof DataSource)
        {
            return ProxyDataSourceBuilder
                    .create((DataSource) bean)
                    .countQuery()
//                    .logQueryByCommons(INFO)
//                    .multiline()
                    .name("MyDS")
                    .build();
            // @formatter:on

        }
        return bean; // you can return any other object as well
    }
}
