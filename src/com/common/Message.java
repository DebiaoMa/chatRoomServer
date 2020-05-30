package com.common;

/**
 *
 * msg1 登陆成功
 * msg2 失败
 * msg3
 */
public class Message implements java.io.Serializable{


    private static final long serialVersionUID = -1894460174037487429L;
    private String mesType;

    private String sender;
    private String getter;
    private String con;             //内容
    private String sendTime;

    public String getMesType() {
        return mesType;
    }

    public String getSender() {
        return sender;
    }

    public String getGetter() {
        return getter;
    }

    public String getCon() {
        return con;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "mesType='" + mesType + '\'' +
                ", sender='" + sender + '\'' +
                ", getter='" + getter + '\'' +
                ", con='" + con + '\'' +
                ", sendTime='" + sendTime + '\'' +
                '}';
    }
}
