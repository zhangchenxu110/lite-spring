package org.litespring.testBean.v5;


import com.litespring.bean.factory.annotation.Autowired;
import com.litespring.stereotype.Component;
import org.litespring.testBean.v5.dao.AccountDao;
import org.litespring.testBean.v5.dao.ItemDao;
import org.litespring.testBean.v5.utils.MessageTracker;

@Component(value = "petStore")
public class PetStoreService {
    @Autowired
    AccountDao accountDao;
    @Autowired
    ItemDao itemDao;

    public PetStoreService() {

    }

    public ItemDao getItemDao() {
        return itemDao;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public void placeOrder() {
        System.out.println("place order");
        MessageTracker.addMsg("place order");

    }

    public void placeOrderWithException() {
        throw new NullPointerException();
    }
}
