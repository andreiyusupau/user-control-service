package com.nevermind.usercontrolservice.repository;

import com.nevermind.usercontrolservice.domain.Role;
import com.nevermind.usercontrolservice.domain.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="users" )
public interface UserAccountRepository extends PagingAndSortingRepository<UserAccount,Long> {
    UserAccount findByUsername(String username);
    Page<UserAccount> findAllByRole(Role role, Pageable pageable);
}
