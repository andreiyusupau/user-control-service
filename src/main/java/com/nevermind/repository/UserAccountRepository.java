package com.nevermind.repository;

import com.nevermind.domain.UserAccount;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(itemResourceRel = "user",collectionResourceRel ="users",  path="users" )
public interface UserAccountRepository extends PagingAndSortingRepository<UserAccount,Long> {
}
