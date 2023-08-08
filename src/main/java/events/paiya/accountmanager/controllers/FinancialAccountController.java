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
@RequestMapping("/v1/financial-accounts")
@Log4j2
public class FinancialAccountController {
    private final FinancialAccountService financialAccountService;
    private final FinancialAccountMapper financialAccountMapper;

    public FinancialAccountController(FinancialAccountService financialAccountService,
                                      FinancialAccountMapper financialAccountMapper) {
        this.financialAccountService = financialAccountService;
        this.financialAccountMapper = financialAccountMapper;
    }

    @GetMapping ()
    public ResponseEntity<List<FinancialAccountResource>> findAllFinancialAccountByUserId(@RequestParam(name = "userId") String userId){
        List<FinancialAccount> financialAccounts = financialAccountService.findAllFinancialAccountByUserId(userId);
        List<FinancialAccountResource> financialAccountResources = financialAccountMapper.toResourceList(financialAccounts);
        return ResponseEntity.ok(financialAccountResources);
    }

    @PutMapping()
    public ResponseEntity<List<FinancialAccountResource>> addFinancialAccountByUserId(@RequestParam String userId,
                                                                                      @RequestBody @Valid FinancialAccountResource financialAccountResource){
        FinancialAccount financialAccount = financialAccountMapper.toEntity(financialAccountResource);
        List<FinancialAccount> financialAccounts = financialAccountService.addFinancialAccountByUserId(userId, financialAccount);
        List<FinancialAccountResource> financialAccountResources = financialAccountMapper.toResourceList(financialAccounts);
        return ResponseEntity.ok(financialAccountResources);
    }

    @DeleteMapping ()
    public ResponseEntity<List<FinancialAccountResource>> removeFinancialAccountByUserId(@RequestParam String userId,
                                                                                         @RequestParam String financialAccountId){
        List<FinancialAccount> financialAccounts = financialAccountService.removeFinancialAccountByUserId(userId, financialAccountId);
        List<FinancialAccountResource> financialAccountResources = financialAccountMapper.toResourceList(financialAccounts);
        return ResponseEntity.ok(financialAccountResources);
    }

    @PutMapping("/default")
    public ResponseEntity<FinancialAccountResource> changeDefaultFinancialAccountByUserId(@RequestParam String userId,
                                                                                          @RequestParam String financialAccountId){
        FinancialAccount financialAccount = this.financialAccountService.changeDefaultFinancialAccountByUserId(userId, financialAccountId);
        FinancialAccountResource financialAccountResource = this.financialAccountMapper.toResource(financialAccount);
        return ResponseEntity.ok(financialAccountResource);
    }

    @GetMapping("/default")
    public ResponseEntity<FinancialAccountResource> findDefaultFinancialAccountByUserId(@RequestParam String userId){
        FinancialAccount financialAccount = this.financialAccountService.findDefaultFinancialAccountByUserId(userId);
        FinancialAccountResource financialAccountResource = this.financialAccountMapper.toResource(financialAccount);
        return ResponseEntity.ok(financialAccountResource);
    }

}
