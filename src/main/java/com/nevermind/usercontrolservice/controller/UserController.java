package com.nevermind.usercontrolservice.controller;

import com.nevermind.usercontrolservice.domain.Role;
import com.nevermind.usercontrolservice.domain.UserAccount;
import com.nevermind.usercontrolservice.service.UserAccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserAccountService userAccountService;

    public UserController(UserAccountService userAccountService){
        this.userAccountService = userAccountService;
    }

    @GetMapping
    public String getAllUserAccounts(@RequestParam Optional<String> username,
                                     @RequestParam Optional<Role> role,
                                     Model model,
                                     @PageableDefault(size=1) Pageable pageable) {
        Page<UserAccount> page=userAccountService.findAll(username,role,pageable);
        model.addAttribute("page", page);
        model.addAttribute("userAccounts", page.getContent());
        return "list";
    }

    @PostMapping
    public String filterUserAccounts(@RequestParam Optional<String> username,
                                     @RequestParam Optional<Role> role,
                                     Model model,
                                     @PageableDefault(size=1) Pageable pageable) {
        Page<UserAccount> page=userAccountService.findAll(username,role,pageable);
        model.addAttribute("page", page);
        model.addAttribute("userAccounts", page.getContent());
        return "list";
    }

    @GetMapping("/{id}")
    public String getUserAccount(@PathVariable Long id, Model model) {
        Optional<UserAccount> userAccount= userAccountService.findById(id);
        userAccount.ifPresent(account -> model.addAttribute("userAccount", account));

        return "view";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/new")
    public String createUserAccount(Model model) {
        model.addAttribute("userAccount",new UserAccount());
        return "new";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/new")
    public String addUserAccount(@Valid @ModelAttribute  UserAccount userAccount, BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
       return "new";
    }else {
        userAccountService.add(userAccount);
    }
        return "list";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}/edit")
    public String editUserAccount(@PathVariable Long id, Model model) {
        Optional<UserAccount> userAccount= userAccountService.findById(id);
        userAccount.ifPresent(account -> model.addAttribute("userAccount", account));
        return "edit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}/edit")
    public @ResponseBody String updateUserAccount(@ModelAttribute UserAccount userAccount) {
        userAccountService.save(userAccount);
        return "Edited";
    }
}