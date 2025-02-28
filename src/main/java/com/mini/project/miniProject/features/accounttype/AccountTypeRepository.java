package com.mini.project.miniProject.features.accounttype;


import com.mini.project.miniProject.domain.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountTypeRepository extends JpaRepository<AccountType,String> {
    boolean existsByName(String name);
    Optional<AccountType> findByName(String name);
}