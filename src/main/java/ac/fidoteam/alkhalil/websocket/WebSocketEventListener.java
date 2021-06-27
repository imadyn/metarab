package ac.fidoteam.alkhalil.websocket;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
    
    private final Collection<String> sessions = Collections.synchronizedList(new ArrayList<>());

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection {}", event);

        String username = ((User)((UsernamePasswordAuthenticationToken)event.getUser()).getPrincipal()).getUsername() ;
        if (username != null) {
        	sessions.add(username);
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    	logger.info("Disconnect a web socket connection {}", event);
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
        	sessions.remove(username);
        }
    }

	/**
	 * @return the sessions
	 */
	public Collection<String> getSessions() {
		return sessions;
	}
}