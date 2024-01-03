package nl.craftsmen.company;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@SpringBootConfiguration
public class Configuration {

    @Bean
    public RestClient restClient(){
        return RestClient.create();
    }
}
