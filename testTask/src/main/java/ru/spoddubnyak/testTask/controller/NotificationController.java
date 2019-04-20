package ru.spoddubnyak.testTask.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.spoddubnyak.testTask.domain.SocketMessage;

import java.util.concurrent.TimeUnit;

@Controller
public class NotificationController {

    @MessageMapping("/message")
    @SendTo("/topic/message")
    public SocketMessage getMessage(final SocketMessage message) throws Exception {
        TimeUnit.SECONDS.sleep(10);
        return new SocketMessage("Cообщение отправлено при помощи технологии, websocket");
    }
}
