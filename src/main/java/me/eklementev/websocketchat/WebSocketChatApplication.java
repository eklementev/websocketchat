package me.eklementev.websocketchat;

import me.eklementev.websocketchat.data.Message;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@SpringBootApplication
@EnableScheduling
public class WebSocketChatApplication {

	@Bean("incoming")
	BlockingQueue<Message> incoming() {
		return new LinkedBlockingQueue<>();
	}

	@Bean("outgoing")
	BlockingQueue<Message> outgoing() {
		return new LinkedBlockingQueue<>();
	}

	public static void main(String[] args) {
		SpringApplication.run(WebSocketChatApplication.class, args);
	}
}
