package com.example.demo.web.grpc;


import com.example.demo.Hello;
import com.example.demo.ReactorHelloServiceGrpc;
import com.salesforce.grpc.contrib.spring.GrpcService;
import reactor.core.publisher.Mono;

@GrpcService
public class HelloServiceImpl extends ReactorHelloServiceGrpc.HelloServiceImplBase {

    @Override
    public Mono<Hello.HelloReply> hello(Mono<Hello.HelloRequest> request) {
        return request.map(r -> Hello.HelloReply.newBuilder().setMessage("Hello " + r.getName()).build());
    }

}
