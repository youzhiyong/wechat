package com.yzy.demo;

import com.jfinal.kit.HashKit;
import com.jfinal.kit.PropKit;
import com.jfinal.weixin.sdk.api.*;
import com.jfinal.weixin.sdk.jfinal.ApiController;

import java.util.UUID;

/**
 * Created by youzhiyong on 2018/3/21.
 */
public class WeixinApiController extends BaseApiController {

    /**
     * 获取公众号菜单
     */
    public void getMenu() {
        ApiResult apiResult = MenuApi.getMenu();
        if (apiResult.isSucceed())
            renderText(apiResult.getJson());
        else
            renderText(apiResult.getErrorMsg());
    }

    /**
     * 创建菜单
     * 注意：菜单需要在部署后手动请求一次 才能生效   /api/createMenu
     */
    public void createMenu()
    {
        String str = "{\n" +
                "    \"button\": [\n" +
                "        {\n" +
                "            \"name\": \"分享测试\",\n" +
                "            \"url\": \"http://yzy.ngrok.xiaomiqiu.cn/api/getJsp\",\n" +
                "            \"type\": \"view\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"我的博客\",\n" +
                "            \"url\": \"https://www.tqblogs.cn/\",\n" +
                "            \"type\": \"view\"\n" +
                "        },\n" +
                "        {\n" +
                "\t    \"name\": \"待定\",\n" +
                "\t    \"url\": \"https://www.baidu.com\",\n" +
                "\t    \"type\": \"view\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        ApiResult apiResult = MenuApi.createMenu(str);
        if (apiResult.isSucceed())
            renderText(apiResult.getJson());
        else
            renderText(apiResult.getErrorMsg());
    }

    /**
     * 获取公众号关注用户
     */
    public void getFollowers()
    {
        ApiResult apiResult = UserApi.getFollows();
        renderText(apiResult.getJson());
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo()
    {
        //String openId = getAttr("openId");
        String openId = getPara("openId");   //从get方式的参数中获取
        if (openId == null) openId = "ozVHC1P2EMybcCdz7p-BpUTawEf8";
        ApiResult apiResult = UserApi.getUserInfo(openId);

        renderText(apiResult.getJson());
    }

    public void getJsp() {
        JsTicket jsApiTicket = JsTicketApi.getTicket(JsTicketApi.JsApiType.jsapi);
        String jsapi_ticket = jsApiTicket.getTicket();
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String nonce_str = UUID.randomUUID().toString();
        // 注意 URL 一定要动态获取，不能 hardcode.
        String url = "http://" + getRequest().getServerName() // 服务器地址
                // + ":"
                // + getRequest().getServerPort() //端口号
                + getRequest().getContextPath() // 项目名称
                + getRequest().getServletPath();// 请求页面或其他地址
        String qs = getRequest().getQueryString(); // 参数
        if (qs != null) {
            url = url + "?" + (getRequest().getQueryString());
        }
        // 签名生成规则如下：参与签名的字段包括noncestr（随机字符串）, 有效的jsapi_ticket, timestamp（时间戳）, url（当前网页的URL，不包含#及其后面部分） 。对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）后，使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串string1。这里需要注意的是所有参数名均为小写字符。对string1作sha1加密，字段名和字段值都采用原始值，不进行URL 转义。
        //#see https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115
        String  str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        String signature = HashKit.sha1(str);
        setAttr("appId", ApiConfigKit.getApiConfig().getAppId());
        setAttr("nonceStr", nonce_str);
        setAttr("url", url);
        setAttr("timestamp", timestamp);
        setAttr("signature", signature);
        renderJsp("index.jsp");
    }

    public void getHtml() {
        renderJsp("index.html");
    }
}
