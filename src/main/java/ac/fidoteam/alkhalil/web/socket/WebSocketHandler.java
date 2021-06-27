/**
 * 
 */
package ac.fidoteam.alkhalil.websocket;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author DELL
 *
 */
@Component
public class WebSocketHandler extends TextWebSocketHandler {

  private final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

  List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
    logger.info("Sending message: " + message.getPayload() + " to " + sessions.size() + " sessions.");
    for(WebSocketSession webSocketSession : sessions) {
      webSocketSession.sendMessage(message);
    }
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    sessions.add(session);
    logger.info("Added Websocket session, total number of sessions are " + sessions.size());
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    sessions.remove(session);
    logger.info("Removed Websocket session, total number of sessions are " + sessions.size());
  }
}
