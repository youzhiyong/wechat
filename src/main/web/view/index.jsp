<%--
  Created by IntelliJ IDEA.
  User: zfibs020
  Date: 2018/3/21
  Time: 17:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    String url = (String)request.getAttribute("url");
%>
<div id="content">hello</div>
<div >测试分享回调</div>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.1.0.js"></script>
<script type="text/javascript">
    var text = document.getElementById("content1");
    wx.config({
        debug: true,
        appId : '${appId}',
        timestamp : '${timestamp}',
        nonceStr : '${nonceStr}',
        signature : '${signature}',
        jsApiList : [ 'checkJsApi', 'onMenuShareTimeline', 'onMenuShareAppMessage',
            'onMenuShareQQ', ]
    });
    wx.hideAllNonBaseMenuItem();
    wx.ready(function() {

        wx.checkJsApi({
            jsApiList: ['chooseImage'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
            success: function(res) {
                // 以键值对的形式返回，可用的api值true，不可用为false
                // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
            }
        });

        /* 分享 文档  https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115*/
        /* 发送给朋友 */
        document.getElementById("bt").onclick = function () {
            alert("分享");
            wx.onMenuShareAppMessage({
                title: '123',
                desc: 'desc',
                link: 'https://www.baidu.com',
                imgUrl: 'null',
                trigger: function (res) {
                    alert('用户点击发送给朋友');
                },
                success: function (res) {
                    text.innerHTML = "已分享";
                    alert('已分享');
                },
                cancel: function (res) {
                    text.innerHTML = "已取消";
                    alert('已取消');
                },
                fail: function (res) {
                    alert(JSON.stringify(res));
                }
            });
        }

        /* 分享到朋友圈 */
        wx.onMenuShareTimeline({
            title: '', // 分享标题
            link: '', // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
            imgUrl: '', // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        })
        /* 分享到QQ */
        wx.onMenuShareQQ({
            title: '', // 分享标题
            desc: '', // 分享描述
            link: '', // 分享链接
            imgUrl: '', // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
        /* 分享到腾讯微博 */
        wx.onMenuShareWeibo({
            title: '', // 分享标题
            desc: '', // 分享描述
            link: '', // 分享链接
            imgUrl: '', // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
        /* 分享到QQ空间 */
        wx.onMenuShareQZone({
            title: '', // 分享标题
            desc: '', // 分享描述
            link: '', // 分享链接
            imgUrl: '', // 分享图标
            success: function () {
                // 用户确认分享后执行的回调函数
            },
            cancel: function () {
                // 用户取消分享后执行的回调函数
            }
        });
    });
</script>
</body>
</html>
