package com.example.servertesting.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.security.cert.X509Certificate;

@RestController
@RequestMapping("/server")
public class ServerController {

    private final WebClient webClient;
    private final HttpServletRequest request;

    public ServerController(WebClient webClient, HttpServletRequest request) {
        this.webClient = webClient;
        this.request = request;
    }




    String cn = "";

    @GetMapping("/data")
    public String getData() {
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

        if (cn.equals("nt gateway")) {
            System.out.println("Hello");
            return "Server Side";
        }
        else{
            return "Invalid CN";
        }
    }

    @GetMapping("/check")
    public Mono<String> receiveData() {


            return webClient
                    .get()
                    .uri("/client/hello")
                    .retrieve()
                    .bodyToMono(String.class);
        }

    }

