package com.mini.project.miniProject.mapper;


import com.mini.project.miniProject.domain.Account;
import com.mini.project.miniProject.domain.UserAccount;
import com.mini.project.miniProject.features.accounts.dto.AccountRequest;
import com.mini.project.miniProject.features.accounts.dto.AccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface AccountMapper {
    @Mapping(target = "accountType", ignore = true)
    Account mapRequestToEntity(AccountRequest accountRequest);
    @Mapping(target="id", source = "userAccount.account.id")
    @Mapping(target = "accountNumber", source = "userAccount.account.accountNumber")
    @Mapping(target = "accountName", source = "userAccount.account.accountName")
    @Mapping(target = "accountBalance", source = "userAccount.account.accountBalance")
    @Mapping(target = "user", source = "userAccount.user", qualifiedByName = "toUserResponse")
    @Mapping(target = "accountType", source = "userAccount.account.accountType.name")
    AccountResponse mapUserAccountToAccountResponse(UserAccount userAccount);

    //    @Mapping(target = "user", source = "user")
//    @Mapping(target = "user", source = "user", qualifiedByName = "toUserResponse")
//    void setUserForAccountResponse(@MappingTarget AccountResponse accountResponse, User user);


}
