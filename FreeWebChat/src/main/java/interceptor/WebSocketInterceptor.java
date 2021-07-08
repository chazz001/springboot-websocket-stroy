package interceptor;

import cn.hutool.json.JSONUtil;
import com.xnpe.fchat.data.Message;
import com.xnpe.fchat.data.MessageKey;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class WebSocketInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            String INFO = serverHttpRequest.getURI().getPath().split("INFO=")[1];
            if (INFO != null && INFO.length() > 0) {
                Message message = JSONUtil.toBean(INFO,Message.class);
                String command = message.getCommand();
                if (command != null && MessageKey.ENTER_COMMAND.equals(command)) {
                    System.out.println("当前session的ID="+ message.getName());
                    ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
                    HttpSession session = request.getServletRequest().getSession();
                    map.put(MessageKey.KEY_WEBSOCKET_USERNAME, message.getName());
                    map.put(MessageKey.KEY_ROOM_ID, message.getRoomId());
                }
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
        System.out.println("进来webSocket的afterHandshake拦截器！");
    }
}
