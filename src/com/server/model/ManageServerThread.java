package com.server.model;

import java.util.HashMap;
import java.util.Iterator;

public class ManageServerThread {

    private static HashMap hashMap = new HashMap<String, ServerConClientThread>();

    public static void addClientThread(String userId, ServerConClientThread serverConClientThread) {

        hashMap.put(userId, serverConClientThread);
    }

    public static ServerConClientThread getClientThread(String usrId) {

        return (ServerConClientThread) hashMap.get(usrId);
    }

    public static HashMap getHashMap() {
        return hashMap;
    }

    //返回当前在线的用户
    public static String getAllOnline() {

        //迭代器遍历hashmap的key值
        Iterator iterator = hashMap.keySet().iterator();

        String result = "";

        while (iterator.hasNext()) {

            result += iterator.next().toString() + " ";
        }

        return result;
    }
}
