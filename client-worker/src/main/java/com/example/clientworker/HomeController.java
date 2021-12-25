package com.example.clientworker;


import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController implements ApplicationListener<WebServerInitializedEvent> {

//    @Value("${eureka.instance.instance-id}")
//    private String instanceId;

    int serverPort;

    @GetMapping("/home")
    public String home() {
        return String.valueOf(serverPort);
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        serverPort = event.getSource().getPort();
    }
}
