package com.example.democontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class DemoController {
    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    @Qualifier("test1")
    private RestClient restClient;

    @Autowired
    @Qualifier("test2")
    private RestClient restClient2;

    public DemoController() {
    }

    @GetMapping("/test1")
    public ResponseEntity<String> test1() {
        return ResponseEntity.ok(restClient.get().uri("https://httpbin.org/user-agent").retrieve().body(String.class));
    }

    @GetMapping("/test2")
    public ResponseEntity<String> test2() {
        return ResponseEntity.ok(restClient2.get().uri("https://httpbin.org/user-agent").retrieve().body(String.class));
    }
}
