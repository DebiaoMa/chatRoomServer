package com.common;

public interface MessageType {

    //登陆成功
    String messageSucceed = "1";
    //登陆失败
    String messageLoginFail = "2";
    //普通信息包
    String messageCommMsg = "3";
    //请求好友在线的包
    String messageGetOnlineFriend = "4";
    //返回在线用户
    String messageOnlineFriend = "5";
    //广播
    String messageBroadcast = "6";
}
