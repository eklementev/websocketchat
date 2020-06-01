package me.eklementev.websocketchat.front;

import me.eklementev.websocketchat.data.Message;
import me.eklementev.websocketchat.data.MessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Controller
public class WebSocketController {

    @Autowired
    private BlockingQueue<Message> incoming;

    @Autowired
    private BlockingQueue<Message> outgoing;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private ExecutorService executorService;

    @MessageMapping("/chat")
    void handleMessage(MessageRequest request) throws InterruptedException {
        incoming.put(Message.fromRequest(request));
    }

    @PostConstruct
    public void init() {
        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            while (true) {
                try {
                    Message message = outgoing.take();
                    messagingTemplate.convertAndSend("/messages", message);
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
