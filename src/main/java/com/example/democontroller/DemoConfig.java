package com.example.democontroller;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(DynamicRestBuilderProperties.class)
public class DemoConfig {

    private static final Logger logger = LoggerFactory.getLogger(DemoConfig.class);
    @Autowired
    private DynamicRestBuilderProperties dynamicRestBuilderProperties;

    @Autowired
    private ConfigurableApplicationContext configurableApplicationContext;

    @Autowired
    private RestClient.Builder restClientBuilder;

    public DemoConfig() {
        logger.info("DemoConfig Initialized!!!!");
    }

    @PostConstruct
    public void init() {
        ConfigurableListableBeanFactory beanFactory = this.configurableApplicationContext.getBeanFactory();
        // iterate over properties and register new beans'
        for (DynamicRestBuilderProperties.CustomClient client : dynamicRestBuilderProperties.clients()) {
            RestClient tempClient = restClientBuilder.clone().requestFactory(getClientHttpRequestFactory(client.connectionTimeout(), client.responseTimeout())).defaultHeader("user-agent", client.userAgent()).build();
            beanFactory.autowireBean(tempClient);
            beanFactory.initializeBean(tempClient, client.clientName());
            beanFactory.registerSingleton(client.clientName(), tempClient);
            logger.info("{} bean created", client.clientName());
        }
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory(int connectionTimeout, int responseTimeout) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(responseTimeout);
        factory.setConnectTimeout(connectionTimeout);
        return factory;
    }
}
