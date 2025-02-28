package com.mini.project.miniProject.init;

import com.mini.project.miniProject.domain.AccountType;
import com.mini.project.miniProject.domain.Authority;
import com.mini.project.miniProject.domain.Role;
import com.mini.project.miniProject.features.accounttype.AccountTypeRepository;
import com.mini.project.miniProject.features.authority.AuthorityRepository;
import com.mini.project.miniProject.features.roles.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final RoleRepository roleRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final AuthorityRepository authorityRepository;

    @PostConstruct
    @Transactional
    void initializeData() {
        initAuthorities();
        initRoles();
        initAccountTypes();
    }

    private void initAuthorities() {
        List<String> authorities = List.of("READ", "WRITE", "DELETE");
        if (authorityRepository.count() == 0) {
            authorities.forEach(auth -> {
                Authority authority = new Authority();
                authority.setName(auth);
                authorityRepository.save(authority);
            });
        }
    }

    private void initRoles() {
        List<String> roles = List.of("ADMIN", "USER");
        if (roleRepository.count() == 0) {
            Set<Authority> allAuthorities = new HashSet<>(authorityRepository.findAll());

            roles.forEach(roleName -> {
                Role role = new Role();
                role.setName(roleName);

                if ("ADMIN".equals(roleName)) {
                    role.setAuthorities(allAuthorities);
                } else if ("USER".equals(roleName)) {
                    role.setAuthorities(allAuthorities.stream()
                            .filter(auth -> "READ".equals(auth.getName()))
                            .collect(Collectors.toSet()));
                }

                roleRepository.save(role);
            });
        }
    }

    private void initAccountTypes() {
        if (accountTypeRepository.count() == 0) {
            List<AccountType> accountTypes = List.of(
                    new AccountType().setName("SAVINGS")
                            .setDescription("This is the type of account that you gain interest when you save your money in the bank"),
                    new AccountType().setName("PAYROLLS")
                            .setDescription("This is an account to get paid by a company monthly."),
                    new AccountType().setName("CARD")
                            .setDescription("Allows you to create different cards for personal use.")
            );

            accountTypeRepository.saveAll(accountTypes);
        }
    }
}
