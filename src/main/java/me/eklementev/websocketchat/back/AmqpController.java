package me.eklementev.websocketchat.back;

import me.eklementev.websocketchat.data.Message;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class AmqpController {

    @Autowired
    private BlockingQueue<Message> incoming;

    @Autowired
    private BlockingQueue<Message> outgoing;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private FanoutExchange exchange;

    private ExecutorService executorService;

    public void handleMessage(Message message) throws InterruptedException {
        outgoing.put(message);
    }

    @PostConstruct
    public void init() {
        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            while (true) {
                try {
                    Message message = incoming.take();
                    rabbitTemplate.convertAndSend(exchange.getName(), "", message);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {
        executorService.shutdownNow();
        executorService.awaitTermination(3, TimeUnit.SECONDS);
    }
}
