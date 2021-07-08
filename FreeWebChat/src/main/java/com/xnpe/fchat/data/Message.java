package com.xnpe.fchat.data;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

@Data
public class Message implements MessageKey {
    private String roomId;
    private String name;
    private String command;
    private String info;


    public JSONObject toJson() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(KEY_INFO, this.info);
            jsonObject.put(KEY_WEBSOCKET_USERNAME, this.name);
            jsonObject.put(KEY_MESSAGE_COMMAND, this.command);
            jsonObject.put(KEY_ROOM_ID, this.roomId);
            return jsonObject;
        } catch (JSONException e) {

        }
        return null;
    }

}
