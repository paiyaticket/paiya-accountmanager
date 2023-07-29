package events.paiya.accountmanager.controllers;

import events.paiya.accountmanager.domains.FinancialAccount;
import events.paiya.accountmanager.mappers.FinancialAccountMapper;
import events.paiya.accountmanager.resources.FinancialAccountResource;
import events.paiya.accountmanager.services.FinancialAccountService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@Log4j2
public class FinancialAccountController {
    private final FinancialAccountService financialAccountService;
    private final FinancialAccountMapper financialAccountMapper;

    public FinancialAccountController(FinancialAccountService financialAccountService,
                                      FinancialAccountMapper financialAccountMapper) {
        this.financialAccountService = financialAccountService;
        this.financialAccountMapper = financialAccountMapper;
    }

    @PutMapping("/{id}/financial-accounts")
    public ResponseEntity<List<FinancialAccountResource>> addFinancialAccountByUserId(@PathVariable String id,
                                                                                      @RequestBody @Valid FinancialAccountResource financialAccountResource){
        FinancialAccount financialAccount = financialAccountMapper.toEntity(financialAccountResource);
        List<FinancialAccount> financialAccounts = financialAccountService.addFinancialAccountByUserId(id, financialAccount);
        List<FinancialAccountResource> financialAccountResources = financialAccountMapper.toResourceList(financialAccounts);
        return ResponseEntity.ok(financialAccountResources);
    }

    @DeleteMapping ("/{id}/financial-accounts")
    public ResponseEntity<List<FinancialAccountResource>> removeFinancialAccountByUserId(@PathVariable String id,
                                                                                         @RequestParam String financialAccountId){
        List<FinancialAccount> financialAccounts = financialAccountService.removeFinancialAccountByUserId(id, financialAccountId);
        List<FinancialAccountResource> financialAccountResources = financialAccountMapper.toResourceList(financialAccounts);
        return ResponseEntity.ok(financialAccountResources);
    }

    @PutMapping("/{id}/financial-accounts/default")
    public ResponseEntity<FinancialAccountResource> changeDefaultFinancialAccountByUserId(@PathVariable String id,
                                                                                          @RequestParam String financialAccountId){
        FinancialAccount financialAccount = this.financialAccountService.changeDefaultFinancialAccountByUserId(id, financialAccountId);
        FinancialAccountResource financialAccountResource = this.financialAccountMapper.toResource(financialAccount);
        return ResponseEntity.ok(financialAccountResource);
    }

    @GetMapping("/{id}/financial-accounts/default")
    public ResponseEntity<FinancialAccountResource> findDefaultFinancialAccountByUserId(@PathVariable String id){
        FinancialAccount financialAccount = this.financialAccountService.findDefaultFinancialAccountByUserId(id);
        FinancialAccountResource financialAccountResource = this.financialAccountMapper.toResource(financialAccount);
        return ResponseEntity.ok(financialAccountResource);
    }

    @GetMapping ("/{id}/financial-accounts")
    public ResponseEntity<List<FinancialAccountResource>> findAllFinancialAccountByUserId(@PathVariable String id){
        List<FinancialAccount> financialAccounts = financialAccountService.findAllFinancialAccountByUserId(id);
        List<FinancialAccountResource> financialAccountResources = financialAccountMapper.toResourceList(financialAccounts);
        return ResponseEntity.ok(financialAccountResources);
    }

}
