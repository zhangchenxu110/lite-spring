package org.litespring.testBean.v4;

import com.litespring.bean.factory.annotation.Autowired;
import com.litespring.stereotype.Component;
import org.litespring.testBean.v4.dao.AccountDao;
import org.litespring.testBean.v4.dao.ItemDao;

/**
 * @author 张晨旭
 * @DATE 2018/9/3
 */
@Component(value="petStore")

public class PetStoreService {
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private ItemDao itemDao;

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }


}