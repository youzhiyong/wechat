package com.yzy.demo;

import com.jfinal.kit.HashKit;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;
import com.jfinal.weixin.sdk.jfinal.MsgControllerAdapter;
import com.jfinal.weixin.sdk.msg.in.InTextMsg;
import com.jfinal.weixin.sdk.msg.in.InVoiceMsg;
import com.jfinal.weixin.sdk.msg.in.event.InFollowEvent;
import com.jfinal.weixin.sdk.msg.in.event.InMenuEvent;
import com.jfinal.weixin.sdk.msg.out.OutTextMsg;
import com.jfinal.weixin.sdk.msg.out.OutVoiceMsg;

import java.util.UUID;

/**
 * Created by youzhiyong on 2018/3/21.
 */
public class WeixinMsgController extends MsgControllerAdapter {

    public static int follows = 0;

    protected void processInFollowEvent(InFollowEvent inFollowEvent) {
        System.out.print(inFollowEvent.toString());
        if (inFollowEvent.getEvent().equalsIgnoreCase("subscribe")) {
            follows++;

            renderOutTextMsg("hello");
        } else {
            follows--;
            renderNull();
        }

    }

    public ApiConfig getApiConfig() {
        ApiConfig ac = new ApiConfig();

        // 配置微信 API 相关常量
        ac.setToken(PropKit.get("token"));
        ac.setAppId(PropKit.get("appId"));
        ac.setAppSecret(PropKit.get("appSecret"));
        ac.setEncryptMessage(PropKit.getBoolean("encryptMessage", false));
        ac.setEncodingAesKey(PropKit.get("encodingAesKey", "setting it in config file"));
        return ac;
    }

    protected void processInTextMsg(InTextMsg inTextMsg) {
        String msgContent = inTextMsg.getContent().trim();
        System.out.println(msgContent);
        if (msgContent.equalsIgnoreCase("1")) {

            String timestamp = Long.toString(System.currentTimeMillis() / 1000);
            String nonce_str = UUID.randomUUID().toString();
            // 这里参数的顺序要按照 key 值 ASCII 码升序排序
            //注意这里参数名必须全部小写，且必须有序
            String  str = "&noncestr=" + nonce_str +
                    "&timestamp=" + timestamp;
            String signature = HashKit.sha1(str);
            setAttr("appId", ApiConfigKit.getApiConfig().getAppId());
            setAttr("nonceStr", nonce_str);
            setAttr("timestamp", timestamp);
            setAttr("signature", signature);
            renderJsp("index.html");
        } else {
            OutTextMsg outMsg = new OutTextMsg(inTextMsg);
            outMsg.setContent(msgContent);
            render(outMsg);
        }
    }

    protected void processInVoiceMsg(InVoiceMsg inVoiceMsg) {
        OutVoiceMsg outMsg = new OutVoiceMsg(inVoiceMsg);
        // 将刚发过来的语音再发回去
        outMsg.setMediaId(inVoiceMsg.getMediaId());
        render(outMsg);
    }

    protected void processInMenuEvent(InMenuEvent inMenuEvent) {

    }
}
