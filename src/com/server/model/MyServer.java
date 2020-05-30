package com.server.model;

import com.common.Message;
import com.common.MessageType;
import com.common.User;
import com.server.dao.Database;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class MyServer {

    static ServerSocket serverSocket;
    static Socket socket;

//    public static void main(String[] args){
//        MyServer myServer = new MyServer();
//    }

    /**
     * 验证登陆情况
     * 1：成功
     * 2：失败
     */
    public MyServer() {

        try {

            System.out.println("监听9999......");
            //监听9999端口
            serverSocket = new ServerSocket(9999);

            while (true) {
                //阻塞，等待连接
                socket = serverSocket.accept();

                //接收客户端发来的信息
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                User user = (User) ois.readObject();

                Message msg = new Message();
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                Database database = new Database();

                if (database.login(user.getName(), user.getPassword())) {
                    msg.setMesType(MessageType.messageSucceed);
                    oos.writeObject(msg);

                    System.out.println(user.getName() + "登陆成功");

                    //单开线程，保持该线程和客户端的通信
                    ServerConClientThread serverToClientThread = new ServerConClientThread(socket);
                    ManageServerThread.addClientThread(user.getName(), serverToClientThread);
                    System.out.println(ManageServerThread.getHashMap());
                    //启动该线程
                    serverToClientThread.start();


                } else {
                    msg.setMesType(MessageType.messageLoginFail);
                    System.out.println(user.getName() + "登陆失败");
                    oos.writeObject(msg);

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                socket.close();
                serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}
