package events.paiya.accountmanager.services;

import events.paiya.accountmanager.domains.CashAccount;

import java.util.List;

public interface CashAccountService {
    CashAccount findById(String id);
    CashAccount create(CashAccount cashAccount);
    CashAccount update(CashAccount cashAccount);
    List<CashAccount> findByOwner(String owner);
    CashAccount findByOwnerAndIsDefault(String owner, Boolean isDefault);
    void deleteAllByOwner(String owner);
    void deleteByOwner(String owner);
    void deleteById(String id);
    void deleteAll();
}
