package events.paiya.accountmanager.controllers;

import events.paiya.accountmanager.domains.FinancialAccount;
import events.paiya.accountmanager.mappers.FinancialAccountMapper;
import events.paiya.accountmanager.resources.FinancialAccountResource;
import events.paiya.accountmanager.services.FinancialAccountServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/financial-accounts")
public class FinancialAccountController {
    private final FinancialAccountServiceImpl financialAccountService;
    private final FinancialAccountMapper financialAccountMapper;

    public FinancialAccountController(FinancialAccountServiceImpl financialAccountService,
                                      FinancialAccountMapper financialAccountMapper) {
        this.financialAccountService = financialAccountService;
        this.financialAccountMapper = financialAccountMapper;
    }

    @PostMapping()
    public ResponseEntity<FinancialAccountResource> create(@RequestBody @Valid FinancialAccountResource financialAccountResource,
                                                           HttpServletRequest request) throws URISyntaxException {
        FinancialAccount financialAccount = financialAccountMapper.toEntity(financialAccountResource);
        financialAccount = financialAccountService.create(financialAccount);
        URI uri = new URI(request.getRequestURI()+"/"+financialAccount.getId());
        return ResponseEntity.created(uri).body(financialAccountMapper.toResource(financialAccount));
    }

    @GetMapping ("/{id}")
    public ResponseEntity<FinancialAccountResource> findById(@PathVariable(name = "id") String financialAccountId){
        FinancialAccount financialAccount = financialAccountService.findById(financialAccountId);
        return ResponseEntity.ok(this.financialAccountMapper.toResource(financialAccount));
    }

    @GetMapping ("/default")
    public ResponseEntity<FinancialAccountResource> findUserDefaultFinancialAccount(@RequestParam(name = "userId") String userId){
        FinancialAccount financialAccounts = financialAccountService.findByUserIdAndIsDefault(userId, true);
        return ResponseEntity.ok(this.financialAccountMapper.toResource(financialAccounts));
    }

    @GetMapping ()
    public ResponseEntity<List<FinancialAccountResource>> findByUserId(@RequestParam(name = "userId") String userId){
        List<FinancialAccount> financialAccounts = financialAccountService.findByUserId(userId);
        List<FinancialAccountResource> financialAccountResources = financialAccountMapper.toResourceList(financialAccounts);
        return ResponseEntity.ok(financialAccountResources);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FinancialAccountResource> update(@PathVariable(name = "id") String financialAccountId,
                                                           @RequestBody @Valid FinancialAccountResource financialAccountResource){
        FinancialAccount financialAccount = this.financialAccountService.findById(financialAccountId);
        financialAccountMapper.updateFinancialAccoutFromResource(financialAccountResource, financialAccount);
        financialAccount = financialAccountService.update(financialAccount);
        return ResponseEntity.ok(financialAccountMapper.toResource(financialAccount));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "id") String financialAccountId){
        financialAccountService.deleteById(financialAccountId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping ()
    public ResponseEntity<Void> deleteByOwnerId(@RequestParam(name = "ownerId") String ownerId){
        financialAccountService.deleteByOwnerId(ownerId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping ("/{id}/all")
    public ResponseEntity<Void> deleteAllByUserId(@PathVariable(name = "id") String financialAccountId){
        financialAccountService.deleteAllByOwnerId(financialAccountId);
        return ResponseEntity.noContent().build();
    }


}
