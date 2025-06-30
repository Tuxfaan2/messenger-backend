package de.tuxfan.messengerbackend.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;

@RestController
public class SseEventController {

    private final SseEmitter emitter;

    public SseEventController(SseEmitter emitter) {
        this.emitter = emitter;
    }

    @GetMapping("/events")
    public SseEmitter eventStream() {
        return emitter;
    }

    @GetMapping(path = "/stream-flux", produces =
            MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamFlux() {
        return Flux.interval(Duration.ofSeconds(1)).map(sequence -> "Flux - " + LocalTime.now().toString());
    }

    @PostMapping("/test")
    public void test() throws IOException {
        emitter.send("test");
    }
}