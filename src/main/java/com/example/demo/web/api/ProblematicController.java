package com.example.demo.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/trouble")
public class ProblematicController {

    @GetMapping
    Mono<String> get() {
        final var random = new Random();
        final var randomInt = random.ints(0,6).findFirst().getAsInt();
        final var delay = Duration.ofSeconds(randomInt);
        final Mono<String> payload =  randomInt % 2 == 0 ?
                Mono.error(new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE)) :
                Mono.just("{\"ok\": \"nice\"}");

        return payload.delayElement(delay);
    }

}
