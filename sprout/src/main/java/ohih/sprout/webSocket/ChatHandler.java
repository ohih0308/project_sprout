package ohih.sprout.webSocket;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import ohih.sprout.domain.Utilities;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ChatHandler extends TextWebSocketHandler {
    private static Map<WebSocketSession, String> map = new HashMap<>();
    private String userName = "";

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        JsonArray userList = new JsonArray();

        String uuid = Utilities.createUUID();
        userName = uuid.substring(0, 5);

        map.put(session, userName);


        for (String value : map.values()) {
            userList.add(value);
        }


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userName", userName);
        jsonObject.addProperty("userCount", map.size());
        jsonObject.add("userList", userList);
        jsonObject.addProperty("message", "\"" + map.get(session) + "\"" + " has joined chat.");


        for (WebSocketSession webSocketSession : map.keySet()) {
            webSocketSession.sendMessage(new TextMessage(jsonObject.toString()));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JsonArray userList = new JsonArray();

        String sender = map.get(session);
        String payload = message.getPayload();


        for (String value : map.values()) {
            userList.add(value);
        }


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userName", userName);
        jsonObject.addProperty("userCount", map.size());
        jsonObject.add("userList", userList);
        jsonObject.addProperty("message", sender + ": " + payload);


        for (WebSocketSession webSocketSession : map.keySet()) {
            webSocketSession.sendMessage(new TextMessage(jsonObject.toString()));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String left = map.get(session);

        map.remove(session);

        JsonArray userList = new JsonArray();

        for (String value : map.values()) {
            userList.add(value);
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("userName", userName);
        jsonObject.addProperty("userCount", map.size());
        jsonObject.add("userList", userList);
        jsonObject.addProperty("message", "\"" + left + "\"" + " has left chat.");


        for (WebSocketSession webSocketSession : map.keySet()) {
            webSocketSession.sendMessage(new TextMessage(jsonObject.toString()));
        }
    }
}
