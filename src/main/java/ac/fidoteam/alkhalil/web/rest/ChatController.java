/**
 * 
 */
package ac.fidoteam.alkhalil.web.rest;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import ac.fidoteam.alkhalil.service.dto.MessageDTO;

@Controller
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    @MessageMapping("/websocket/chat")
    @SendTo("/topic/chat")
    public MessageDTO sendMsg(@Payload MessageDTO activityDTO, StompHeaderAccessor stompHeaderAccessor, Principal principal) {
        log.debug("Sending user tracking data {}", activityDTO);
        stompHeaderAccessor.getSessionAttributes().put("username", principal.getName());
        return activityDTO;
    }
}
