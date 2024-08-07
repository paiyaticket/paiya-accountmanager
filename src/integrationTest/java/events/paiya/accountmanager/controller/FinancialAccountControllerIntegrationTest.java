package events.paiya.accountmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import events.paiya.accountmanager.controllers.CashAccountController;
import events.paiya.accountmanager.domains.CashAccount;
import events.paiya.accountmanager.enumerations.CardProvider;
import events.paiya.accountmanager.enumerations.FinancialAccountType;
import events.paiya.accountmanager.enumerations.MobileMoneyProvider;
import events.paiya.accountmanager.mappers.CashAccountMapper;
import events.paiya.accountmanager.resources.BankAccountResource;
import events.paiya.accountmanager.resources.CardAccountResource;
import events.paiya.accountmanager.resources.CashAccountResource;
import events.paiya.accountmanager.resources.MobileMoneyAccountResource;
import events.paiya.accountmanager.services.CashAccountServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("integration-test")
public class FinancialAccountControllerIntegrationTest {
    private final String MOBILE_ID = "64acee0e2162f374bd198208";
    private final String CARD_ID = "68acee0e2162f374bd198208";
    private final String OWNER_ID = "owner@paiya.event";
    private final String OTHER_OWNER_ID = "other_owner@paiya.event";
    private static final String BASE_URI_TEMPLATE = "/v1/cash-accounts";
    private static ObjectMapper objectMapper;

    @Autowired
    private CashAccountServiceImpl cashAccountService;
    @Autowired
    private CashAccountMapper cashAccountMapper;

    private MockMvc mockMvc;

    @BeforeAll
    public static void init(){
        objectMapper = new ObjectMapper();
    }

    @BeforeEach
    void setup(){
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new CashAccountController(cashAccountService, cashAccountMapper))
                .build();
    }

    @Test
    @Order(0)
    void create() throws Exception {
        cashAccountService.deleteAll();
        CashAccountResource mobileAccountResource = this.buildCardAccount();
        mockMvc.perform(post(BASE_URI_TEMPLATE)
                        .content(objectMapper.writeValueAsBytes(mobileAccountResource))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value(CARD_ID));
    }

    @Test
    @Order(1)
    void findById() throws Exception {
        mockMvc.perform(get(BASE_URI_TEMPLATE+"/"+CARD_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value(CARD_ID));
    }

    @Test
    @Order(2)
    void findUserDefaultFinancialAccount() throws Exception {
        CashAccountResource mobileAccountResource = this.buildMobileMoneyAccount();
        cashAccountService.create(cashAccountMapper.toEntity(mobileAccountResource));

        mockMvc.perform(get(BASE_URI_TEMPLATE+"/default")
                        .param("owner", OWNER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value(MOBILE_ID))
                .andExpect(jsonPath("$.isDefault").value(true));
    }

    @Test
    @Order(3)
    void findByOwner() throws Exception {

        mockMvc.perform(get(BASE_URI_TEMPLATE)
                        .param("owner", OWNER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].owner").value(OWNER_ID));
    }

    @Test
    @Order(4)
    void update() throws Exception {
        CashAccountResource financialAccountResource = this.buildMobileMoneyAccount();
        financialAccountResource.setIsDefault(false);
        mockMvc.perform(patch(BASE_URI_TEMPLATE+"/"+MOBILE_ID)
                        .content(objectMapper.writeValueAsBytes(financialAccountResource))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.isDefault").value(false))
                .andExpect(jsonPath("$.owner").value(OWNER_ID));
    }

    @Test
    @Order(5)
    void deleteById() throws Exception {
        mockMvc.perform(delete(BASE_URI_TEMPLATE+"/"+CARD_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Assertions.assertThrows(NoSuchElementException.class, () -> cashAccountService.findById(CARD_ID));
    }

    @Test
    @Order(6)
    void deleteByOwner() throws Exception {
        CashAccountResource buildBankAccount = this.buildBankAccount();
        cashAccountService.create(cashAccountMapper.toEntity(buildBankAccount));

        mockMvc.perform(delete(BASE_URI_TEMPLATE)
                        .param("owner", OWNER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        List<CashAccount> financialAccounts = cashAccountService.findByOwner(OWNER_ID);
        Assertions.assertTrue(financialAccounts.isEmpty());
    }


    private CashAccountResource buildMobileMoneyAccount(){
        return MobileMoneyAccountResource.builder().id(MOBILE_ID)
                .financialAccountType(FinancialAccountType.MOBILE_MONEY)
                .mobileMoneyProvider(MobileMoneyProvider.WAVE_CI)
                .phoneNumber("0709652655")
                .countryPrefixNumber("+225")
                .owner(OWNER_ID)
                .isDefault(true)
                .build();
    }

    private CashAccountResource buildCardAccount(){
        return CardAccountResource.builder().id(CARD_ID)
                .financialAccountType(FinancialAccountType.CARD)
                .cardNumber("4541122587796253")
                .expirationDate("2026/12")
                .provider(CardProvider.VISA).owner(OWNER_ID).build();
    }

    private CashAccountResource buildBankAccount(){
        String BANK_ID = "68acee0e2162f374bd198208";
        return BankAccountResource.builder().id(BANK_ID)
                .financialAccountType(FinancialAccountType.BANK_ACCOUNT)
                .banqueCode("123456789")
                .accountNumber("648444649").checkNumber("2843")
                .owner(OTHER_OWNER_ID).build();
    }
}
