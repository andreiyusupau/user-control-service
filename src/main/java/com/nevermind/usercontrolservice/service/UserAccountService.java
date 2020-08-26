package com.nevermind.usercontrolservice.service;

import com.nevermind.usercontrolservice.domain.Role;
import com.nevermind.usercontrolservice.domain.Status;
import com.nevermind.usercontrolservice.domain.UserAccount;
import com.nevermind.usercontrolservice.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;


@Service
public class UserAccountService implements UserDetailsService {

    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository.findByUsername(username);

        if (userAccount == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return userAccountRepository.findByUsername(username);
    }

    public boolean add(UserAccount userAccount) {
        UserAccount userAccountFromDatabase = userAccountRepository.findByUsername(userAccount.getUsername());

        if (userAccountFromDatabase != null) {
            return false;
        }

        userAccount.setStatus(Status.ACTIVE);
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        userAccount.setCreatedAt(LocalDate.now());
        userAccountRepository.save(userAccount);

        return true;
    }

    public Page<UserAccount> findAll(Optional<String> username, Optional<Role> role, Pageable pageable) {
        if (username.isPresent()
                && !username.get().isEmpty()) {
            return new PageImpl<>(Collections.singletonList(userAccountRepository.findByUsername(username.get())));
        } else if (role.isPresent()) {
            return userAccountRepository.findAllByRole(role.get(), pageable);
        } else {
            return userAccountRepository.findAll(pageable);
        }

    }

    public void save(UserAccount userAccount) {
        userAccountRepository.save(userAccount);
    }

    public Optional<UserAccount> findById(Long id) {
        return userAccountRepository.findById(id);
    }
}
