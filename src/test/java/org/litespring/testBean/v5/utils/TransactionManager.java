package org.litespring.testBean.v5.utils;

/**
 * @author 张晨旭
 * @DATE 2018/11/28
 */
public class TransactionManager {

    public void start(){
//        System.out.println("start tx");
        MessageTracker.addMsg("start tx");
    }
    public void commit(){
//        System.out.println("commit tx");
        MessageTracker.addMsg("commit tx");
    }
    public void rollback(){
//        System.out.println("rollback tx");
        MessageTracker.addMsg("rollback tx");
    }
}

