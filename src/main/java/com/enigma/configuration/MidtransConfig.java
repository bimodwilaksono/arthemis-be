package com.enigma.configuration;

import com.midtrans.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MidtransConfig  {

    @Value("${midtrans.serverKey}")
    private String serverKey;

    @Value("${midtrans.clientKey}")
    private String clientKey;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

   @Bean
    public Config config() {
       return new Config(serverKey, clientKey, false);
   }
}
