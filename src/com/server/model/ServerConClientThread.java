package com.server.model;

import com.common.Message;
import com.common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

/**
 * 服务器和某个客户端的通信线程
 */
public class ServerConClientThread extends Thread{

    Socket socket;

    public ServerConClientThread(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {

        while (true) {

            try {
                ObjectInputStream ois = new ObjectInputStream(this.socket.getInputStream());
                Message msg = (Message) ois.readObject();

//                System.out.println(msg.getSender() + " send to " + msg.getGetter() + ": " + msg.getCon());
                System.out.println(msg.toString());

                if (msg.getMesType().equals(MessageType.messageCommMsg)) {

                    dealCommMsg(msg);
                } else if (msg.getMesType().equals(MessageType.messageGetOnlineFriend)) {

//                    dealGetOnlineMsg(msg);
                    dealGetOnlineMsg();
                } else if (msg.getMesType().equals(MessageType.messageBroadcast)) {
                    dealBroadcastMsg(msg);
                } else if (msg.getMesType().equals(MessageType.messageOut)) {
                    dealSignOut(msg);
                } else if (msg.getMesType().equals(MessageType.messageGetIp)) {
                    dealGetIpRequest(msg);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public String toString() {
        return "ServerConClientThread{" +
                "socket=" + socket +
                '}';
    }

    public void dealCommMsg(Message msg) {

        //完成转发
        //获得接收放的通讯线程
        ServerConClientThread serverConClientThread = ManageServerThread.getClientThread(msg.getGetter());
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(serverConClientThread.socket.getOutputStream());
            oos.writeObject(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dealGetOnlineMsg() {

        String online = ManageServerThread.getAllOnline();

        String[] onlineList = online.split(" ");

        Message msg = new Message();
        for (int i = 0; i < onlineList.length; i++) {

            try {
                ObjectOutputStream oos = new ObjectOutputStream(
                        ManageServerThread.getClientThread(onlineList[i]).socket.getOutputStream());

                msg.setMesType(MessageType.messageOnlineFriend);
                msg.setSender("server");
                msg.setGetter(onlineList[i]);
                msg.setCon(online);
                System.out.println(msg.toString());
                oos.writeObject(msg);
            } catch (Exception e) {
                    e.printStackTrace();
            }
        }
    }

    public void dealBroadcastMsg(Message msg) {

        String[] onlineList = ManageServerThread.getAllOnline().split(" ");
        String[] distList = new String[onlineList.length-1];

        int q = 0;
        int j = 0;
        while (q < (onlineList.length -1)) {
            if (onlineList[j] != msg.getSender()) {
                distList[q++] = onlineList[j++];
            } else {
                j ++;
            }
        }

        //按照CommMsg类型进行广播
        Message backMsg = new Message();
        for (int i = 0; i < distList.length; i++) {

            try {
                ObjectOutputStream oos = new ObjectOutputStream(
                        ManageServerThread.getClientThread(distList[i]).socket.getOutputStream());

                backMsg.setMesType(MessageType.messageBroadcast);
                backMsg.setSender(msg.getSender());
                backMsg.setGetter(distList[i]);
                backMsg.setCon(msg.getCon());
                backMsg.setSendTime(msg.getSendTime());
                System.out.println(backMsg.toString());

                oos.writeObject(backMsg);


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void dealSignOut(Message msg) {

        ManageServerThread.deleteOutUser(msg.getSender());
        System.out.println(ManageServerThread.getAllOnline());

            //广播下线情况
        dealGetOnlineMsg();
    }

    public void dealGetIpRequest(Message msg) {

        ServerConClientThread receiver = ManageServerThread.getClientThread(msg.getGetter());
        ObjectOutputStream reOos = null;

        Message receive = new Message();
        receive.setGetter(msg.getGetter());
        receive.setSender(msg.getSender());
        receive.setMesType(MessageType.messageReceiveFile);
        receive.setCon(msg.getCon());
        receive.setSendTime(new Date().toString());
        System.out.println(receive.toString());

        try {
            reOos = new ObjectOutputStream(receiver.socket.getOutputStream());
            reOos.writeObject(receive);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ServerConClientThread sender = ManageServerThread.getClientThread(msg.getSender());
        ObjectOutputStream seOos = null;

        Message ipMsg = new Message();
        ipMsg.setMesType(MessageType.messageIp);
        ipMsg.setGetter(msg.getSender());
        ipMsg.setSender(msg.getGetter()); //模拟发送方为文件接收
        ipMsg.setCon(receiver.socket.getInetAddress().toString());
        ipMsg.setSendTime(new Date().toString());

        System.out.println(ipMsg.toString());

        try {
            seOos = new ObjectOutputStream(sender.socket.getOutputStream());
            seOos.writeObject(ipMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
