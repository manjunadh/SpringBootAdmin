package com.ness.hackathon.demoapp.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.SocketUtils;

@Configuration
public class CommonEmbeddedContainerConfiguration {

    @Value("${minPort:7500}")
    Integer minPort;

    @Value("${maxPort:9500}")
    Integer maxPort;

    // set the port range on the embedded container
    @Component
    public class TomcatPortsCustomizationBean implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

        @Override
        public void customize(ConfigurableServletWebServerFactory server) {

            int port = SocketUtils.findAvailableTcpPort(minPort,maxPort);
            // this is very important - Spring Boot needs it so it will show up in Eureka
            System.getProperties().put("server.port", port);
            server.setPort(port);
        }

    }


}
