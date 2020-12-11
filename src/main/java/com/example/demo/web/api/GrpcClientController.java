package com.example.demo.web.api;

import com.example.demo.Hello;
import com.example.demo.ReactorHelloServiceGrpc;
import io.grpc.ManagedChannelBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/grpc")
public class GrpcClientController {

    final ReactorHelloServiceGrpc.ReactorHelloServiceStub client = ReactorHelloServiceGrpc.newReactorStub(
            ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build()
    );

    @GetMapping
    Mono<String> get() {
        return client.hello(Hello.HelloRequest.newBuilder().setName("hodorrrrr").build())
                .map(res -> res.getMessage());
    }

}
