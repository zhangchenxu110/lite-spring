package org.litespring.testBean.v3;

import org.litespring.testBean.v3.dao.AccountDao;
import org.litespring.testBean.v3.dao.ItemDao;

/**
 * @author 张晨旭
 * @DATE 2018/8/28
 */
public class PetStoreService {

    private AccountDao accountDao;
    private ItemDao  itemDao;
    private int version;

    public PetStoreService(AccountDao accountDao, ItemDao itemDao){
        this.accountDao = accountDao;
        this.itemDao = itemDao;
        this.version = -1;
    }
    public PetStoreService(AccountDao accountDao, ItemDao itemDao, int version){
        this.accountDao = accountDao;
        this.itemDao = itemDao;
        this.version = version;
    }
    public int getVersion() {
        return version;
    }

    public AccountDao getAccountDao() {
        return accountDao;
    }

    public ItemDao getItemDao() {
        return itemDao;
    }


}
