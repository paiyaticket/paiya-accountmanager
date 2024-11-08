package events.paiya.accountmanager.controllers;

import events.paiya.accountmanager.domains.CashAccount;
import events.paiya.accountmanager.mappers.CashAccountMapper;
import events.paiya.accountmanager.resources.CashAccountResource;
import events.paiya.accountmanager.services.CashAccountServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

// @CrossOrigin
@RestController
@RequestMapping("/v1/cash-accounts")
public class CashAccountController {
    private final CashAccountServiceImpl cashAccountService;
    private final CashAccountMapper cashAccountMapper;

    public CashAccountController(CashAccountServiceImpl cashAccountService,
                                      CashAccountMapper cashAccountMapper) {
        this.cashAccountService = cashAccountService;
        this.cashAccountMapper = cashAccountMapper;
    }

    @PostMapping()
    public ResponseEntity<CashAccountResource> create(@RequestBody @Valid CashAccountResource cashAccountResource,
                                                        HttpServletRequest request) throws URISyntaxException {
        CashAccount cashAccount = cashAccountMapper.toEntity(cashAccountResource);
        cashAccount = cashAccountService.create(cashAccount);
        URI uri = new URI(request.getRequestURI()+"/"+cashAccount.getId());
        return ResponseEntity.created(uri).body(cashAccountMapper.toResource(cashAccount));
    }

    @GetMapping ("/{id}")
    public ResponseEntity<CashAccountResource> findById(@PathVariable(name = "id") String cashAccountId){
        CashAccount financialAccount = cashAccountService.findById(cashAccountId);
        return ResponseEntity.ok(this.cashAccountMapper.toResource(financialAccount));
    }

    @GetMapping ("/default")
    public ResponseEntity<CashAccountResource> findUserDefaultFinancialAccount(@RequestParam(name = "owner") String userEmail){
        CashAccount financialAccounts = cashAccountService.findByOwnerAndIsDefault(userEmail, true);
        return ResponseEntity.ok(this.cashAccountMapper.toResource(financialAccounts));
    }

    @GetMapping ()
    public ResponseEntity<List<CashAccountResource>> findByOwner(@RequestParam(name = "owner") String userId){
        List<CashAccount> financialAccounts = cashAccountService.findByOwner(userId);
        List<CashAccountResource> financialAccountResources = cashAccountMapper.toResourceList(financialAccounts);
        return ResponseEntity.ok(financialAccountResources);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CashAccountResource> update(@PathVariable(name = "id") String cashAccountId,
                                                      @RequestBody @Valid CashAccountResource cashAccountResource){

        CashAccount cashAccount = this.cashAccountService.findById(cashAccountId);
        cashAccount = cashAccountService.updateCashAccountFromResource(cashAccountResource, cashAccount);

        CashAccount updatedCashAccount = cashAccountService.update(cashAccount);
        return ResponseEntity.ok(cashAccountMapper.toResource(updatedCashAccount));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "id") String cashAccountId){
        cashAccountService.deleteById(cashAccountId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping ()
    public ResponseEntity<Void> deleteByOwner(@RequestParam(name = "owner") String ownerId){
        cashAccountService.deleteByOwner(ownerId);
        return ResponseEntity.noContent().build();
    }


}
