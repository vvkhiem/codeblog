package com.vuvankhiem.blogzine.Repository;

import com.vuvankhiem.blogzine.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    boolean existsByUsername(String username);

    @Query("select a from Account a where (a.username =:us or a.email =:us) and a.auth_provider=:auth")
    Account findAccountByUsernameOrEmailAndAuth_provider(@Param("us") String us, @Param("auth") String auth);

    Account findByAccountID(int accountID);

    List<Account> findAll();

    List<Account> findByFullNameContainingOrEmailContainingOrUsernameContaining(String fullName, String email, String username);

    List<Account> findByActive(boolean active);

    @Override
    void deleteById(Integer id);

}