package org.litespring.testBean.v5.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 张晨旭
 * @DATE 2018/11/28
 */
public class MessageTracker {

    private static List<String> MESSAGES = new ArrayList<String>();

    public static void addMsg(String msg){
        MESSAGES.add(msg);
    }
    public static void clearMsgs(){
        MESSAGES.clear();
    }
    public static List<String> getMsgs(){
        return MESSAGES;
    }
}
