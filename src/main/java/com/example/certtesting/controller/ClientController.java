package com.example.certtesting.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.security.cert.X509Certificate;


@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    WebClient webClient;

    private final HttpServletRequest request;

    public ClientController(HttpServletRequest request) {
        this.request = request;
    }


    @GetMapping("/send")
    public Mono<ResponseEntity<String>> getMsData() {
//        String cn = "";
//        X509Certificate[] clientCertificates = (X509Certificate[]) request.getAttribute("jakarta.servlet.request.X509Certificate");
//        if (clientCertificates != null) {
//        TODO:validate certificate with trust-store
//            System.out.println(clientCertificates.length);
//            System.out.println(clientCertificates[0]);
//            String commonName = String.valueOf(clientCertificates[0].getSubjectX500Principal());
//            if (commonName != null) {
//                int startIndex = commonName.indexOf("CN=") + 3;
//                int endIndex = commonName.indexOf(',', startIndex);
//                if (endIndex == -1) {
//                    endIndex = commonName.length();
//                }
//                cn = commonName.substring(startIndex, endIndex);
//                System.out.println("Common Name (CN): " + cn);
//            }
//        }
//        System.out.println("Hello");
       // if (cn.equals("Ritik Khadgi")) {
            return webClient.get()
                    .uri("/server/data")
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(result -> ResponseEntity.ok("From Client: " + result))
                    .onErrorReturn(ResponseEntity.status(HttpStatus.BAD_GATEWAY).build());
      /*  } else {
            System.out.println("Im here");
            return null;
        }*/
    }


        @GetMapping("/hello")
        public String getHello () {
            String cn = "";
            X509Certificate[] clientCertificates = (X509Certificate[]) request.getAttribute("jakarta.servlet.request.X509Certificate");
            if (clientCertificates != null) {
//        TODO:validate certificate with trust-store
                System.out.println(clientCertificates.length);
                System.out.println(clientCertificates[0]);
                String commonName = String.valueOf(clientCertificates[0].getSubjectX500Principal());
                if (commonName != null) {
                    int startIndex = commonName.indexOf("CN=") + 3;
                    int endIndex = commonName.indexOf(',', startIndex);
                    if (endIndex == -1) {
                        endIndex = commonName.length();
                    }
                    cn = commonName.substring(startIndex, endIndex);
                    System.out.println("Common Name (CN): " + cn);
                }
            }
            if (cn.equals("nt ms")){
                return "Hello";
            }
            else{
                return null;
            }

        }

    }

