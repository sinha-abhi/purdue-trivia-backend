package com.abhisinha.purduetrivia.game.init;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Component
public class WebServerCustomization implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Override
    public void customize(ConfigurableServletWebServerFactory container) {
        try {
            container.setAddress(InetAddress.getByName("192.168.2.7"));
        } catch (Exception e) {
            // do nothing
        }
        container.setPort(1031);
    }
}
