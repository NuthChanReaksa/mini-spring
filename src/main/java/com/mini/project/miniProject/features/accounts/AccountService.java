package com.mini.project.miniProject.features.accounts;


import com.mini.project.miniProject.features.accounts.dto.AccountRequest;
import com.mini.project.miniProject.features.accounts.dto.AccountResponse;

import java.util.List;

public interface AccountService {
    List<AccountResponse> getAllAccounts();
    AccountResponse createAccount(AccountRequest request);
    AccountResponse findAccountById(String id);
    AccountResponse findAccountByAccountNumber(String accountNumber);
    List<AccountResponse> findAccountsByUserId(String userId);

    // enable and disable
    // auto create card when accounttype is card type !
}
