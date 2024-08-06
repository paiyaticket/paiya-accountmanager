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

@CrossOrigin
@RestController
@RequestMapping("/v1/cash-accounts")
public class CashAccountController {
    private final CashAccountServiceImpl cashAccountService;
    private final CashAccountMapper financialAccountMapper;

    public CashAccountController(CashAccountServiceImpl financialAccountService,
                                      CashAccountMapper financialAccountMapper) {
        this.cashAccountService = financialAccountService;
        this.financialAccountMapper = financialAccountMapper;
    }

    @PostMapping()
    public ResponseEntity<CashAccountResource> create(@RequestBody @Valid CashAccountResource financialAccountResource,
                                                           HttpServletRequest request) throws URISyntaxException {
        CashAccount financialAccount = financialAccountMapper.toEntity(financialAccountResource);
        financialAccount = cashAccountService.create(financialAccount);
        URI uri = new URI(request.getRequestURI()+"/"+financialAccount.getId());
        return ResponseEntity.created(uri).body(financialAccountMapper.toResource(financialAccount));
    }

    @GetMapping ("/{id}")
    public ResponseEntity<CashAccountResource> findById(@PathVariable(name = "id") String financialAccountId){
        CashAccount financialAccount = cashAccountService.findById(financialAccountId);
        return ResponseEntity.ok(this.financialAccountMapper.toResource(financialAccount));
    }

    @GetMapping ("/default")
    public ResponseEntity<CashAccountResource> findUserDefaultFinancialAccount(@RequestParam(name = "userEmail") String userEmail){
        CashAccount financialAccounts = cashAccountService.findByOwnerAndIsDefault(userEmail, true);
        return ResponseEntity.ok(this.financialAccountMapper.toResource(financialAccounts));
    }

    @GetMapping ()
    public ResponseEntity<List<CashAccountResource>> findByUserId(@RequestParam(name = "userId") String userId){
        List<CashAccount> financialAccounts = cashAccountService.findByOwner(userId);
        List<CashAccountResource> financialAccountResources = financialAccountMapper.toResourceList(financialAccounts);
        return ResponseEntity.ok(financialAccountResources);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CashAccountResource> update(@PathVariable(name = "id") String financialAccountId,
                                                           @RequestBody @Valid CashAccountResource financialAccountResource){
        CashAccount financialAccount = this.cashAccountService.findById(financialAccountId);
        financialAccountMapper.updateCashAccountFromResource(financialAccountResource, financialAccount);
        financialAccount = cashAccountService.update(financialAccount);
        return ResponseEntity.ok(financialAccountMapper.toResource(financialAccount));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "id") String financialAccountId){
        cashAccountService.deleteById(financialAccountId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping ()
    public ResponseEntity<Void> deleteByOwnerId(@RequestParam(name = "ownerId") String ownerId){
        cashAccountService.deleteByOwner(ownerId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping ("/{id}/all")
    public ResponseEntity<Void> deleteAllByUserId(@PathVariable(name = "id") String financialAccountId){
        cashAccountService.deleteAllByOwner(financialAccountId);
        return ResponseEntity.noContent().build();
    }


}
