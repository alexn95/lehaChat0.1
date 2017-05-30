package app.messenger;

import app.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Date;


@Controller
public class MessageController {

    @Autowired
    ServiceImpl service;

    @MessageMapping("/mainRoom")
    @SendTo("/topic/mainRoom")
    public message mainRoom(message message) throws Exception {
        service.saveMessage(message.getContent(),"mainRoom", message.getUsername());
        return new message( message.getContent(), message.getUsername(), new Date());
    }

    @MessageMapping("/customRoom")
    @SendTo("/topic/customRoom")
    public message customRoom(message message) throws Exception {
        service.saveMessage(message.getContent(),"customRoom", message.getUsername());
        return new message( message.getContent(), message.getUsername(), new Date());
    }

    @MessageMapping("/moderRoom")
    @SendTo("/topic/moderRoom")
    public message adminRoom(message message) throws Exception {
        service.saveMessage(message.getContent(),"moderRoom", message.getUsername());
        return new message( message.getContent(), message.getUsername(), new Date());
    }

    @MessageMapping("/guestRoom")
    @SendTo("/topic/guestRoom")
    public message guestRoom(message message) throws Exception {
        service.saveMessage(message.getContent(),"guestRoom", message.getUsername());
        return new message( message.getContent(), message.getUsername(), new Date());
    }

    @MessageMapping("primeRoom")
    @SendTo("/topic/primeRoom")
    public message primeRoom(message message) throws Exception {
        service.saveMessage(message.getContent(),"primeRoom", message.getUsername());
        return new message( message.getContent(), message.getUsername(), new Date());
    }

}