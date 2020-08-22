package com.nevermind.usercontrolservice.service;

import com.nevermind.usercontrolservice.domain.Status;
import com.nevermind.usercontrolservice.domain.UserAccount;
import com.nevermind.usercontrolservice.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

        userAccountRepository.save(userAccount);

        return true;
    }

    public Iterable<UserAccount> findAll() {
        return userAccountRepository.findAll();
    }

    public void save(UserAccount userAccount) {
        userAccountRepository.save(userAccount);
    }

    public Optional<UserAccount> findById(Long id) {
      return   userAccountRepository.findById(id);
    }
}
