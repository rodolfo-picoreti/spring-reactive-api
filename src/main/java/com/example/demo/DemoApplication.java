package com.example.demo;

import com.example.demo.web.grpc.HelloServiceImpl;
import com.salesforce.grpc.contrib.spring.GrpcServerHost;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean(initMethod = "start")
	public GrpcServerHost grpcServerHost() {
		final int port = 9090;
		log.info("Listening for gRPC on port " + port);
		return new GrpcServerHost(port);
	}

	@Bean
	public ReactorHelloServiceGrpc.HelloServiceImplBase chatImpl() {
		return new HelloServiceImpl();
	}

}
