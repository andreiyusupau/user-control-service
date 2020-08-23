package com.nevermind.usercontrolservice.controller;

import com.nevermind.usercontrolservice.domain.Role;
import com.nevermind.usercontrolservice.domain.UserAccount;
import com.nevermind.usercontrolservice.service.UserAccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class UserController {

    private final UserAccountService userAccountService;

    public UserController(UserAccountService userAccountService){
        this.userAccountService = userAccountService;
    }

    @GetMapping(path="/list")
    public String getAllUserAccounts(@RequestParam Optional<String> username, @RequestParam Optional<Role> role, Model model) {
        model.addAttribute("userAccounts", userAccountService.findAll(username,role));
        return "list";
    }

    @PostMapping(path="/list")
    public String filterUserAccounts(@RequestParam Optional<String> username, @RequestParam Optional<Role> role, Model model) {
        model.addAttribute("userAccounts", userAccountService.findAll(username,role));
        return "list";
    }

    @GetMapping(path="/user/{id}")
    public String getUserAccount(@PathVariable Long id, Model model) {
        Optional<UserAccount> userAccount= userAccountService.findById(id);
        userAccount.ifPresent(account -> model.addAttribute("userAccount", account));

        return "view";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path="/user/new")
    public String createUserAccount(Model model) {
        model.addAttribute("userAccount",new UserAccount());
        return "new";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path="/user/new")
    public @ResponseBody String addUserAccount(@Valid @ModelAttribute UserAccount userAccount, BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
        System.out.println(bindingResult.getAllErrors());
        for (Object object : bindingResult.getAllErrors()) {
            if(object instanceof FieldError) {
                FieldError fieldError = (FieldError) object;

                System.out.println(fieldError.getCode());
            }

            if(object instanceof ObjectError) {
                ObjectError objectError = (ObjectError) object;

                System.out.println(objectError.getCode());
            }
        }
    }else {
        userAccountService.add(userAccount);
    }
        return "Saved";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path="/user/{id}/edit")
    public String editUserAccount(@PathVariable Long id, Model model) {
        Optional<UserAccount> userAccount= userAccountService.findById(id);
        userAccount.ifPresent(account -> model.addAttribute("userAccount", account));
        return "edit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(path="/user/{id}/edit")
    public @ResponseBody String updateUserAccount(@PathVariable Long id,@ModelAttribute UserAccount userAccount) {
        userAccountService.save(userAccount);
        return "Edited";
    }
}