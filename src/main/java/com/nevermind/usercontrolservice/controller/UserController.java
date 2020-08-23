package com.nevermind.usercontrolservice.controller;

import com.nevermind.usercontrolservice.domain.UserAccount;
import com.nevermind.usercontrolservice.service.UserAccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String getAllUserAccounts(Model model) {
       model.addAttribute("userAccounts",userAccountService.findAll());
        return "list";
    }

    @GetMapping(path="/user/{id}")
    public String getUserAccount(@PathVariable Long id, Model model) {
        Optional<UserAccount> userAccount= userAccountService.findById(id);
        userAccount.ifPresent(account -> model.addAttribute("userAccount", account));

        return "view";
    }

    @GetMapping(path="/user/new")
    public String createUserAccount(Model model) {
        model.addAttribute("userAccount",new UserAccount());
        return "new";
    }

    @PostMapping(path="/user/new")
    public @ResponseBody String addUserAccount(@Valid @ModelAttribute UserAccount userAccount, BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
        System.out.println("ERROR");
    }else {
        userAccountService.add(userAccount);
    }
        return "Saved";
    }

    @GetMapping(path="/users/{id}/edit")
    public String editUserAccount(@PathVariable Long id, Model model) {
        Optional<UserAccount> userAccount= userAccountService.findById(id);
        userAccount.ifPresent(account -> model.addAttribute("userAccount", account));
        return "edit";
    }

    @PutMapping(path="/users/{id}/edit")
    public @ResponseBody String updateUserAccount(@PathVariable Long id,@ModelAttribute UserAccount userAccount) {
        userAccountService.save(userAccount);
        return "Edited";
    }
}