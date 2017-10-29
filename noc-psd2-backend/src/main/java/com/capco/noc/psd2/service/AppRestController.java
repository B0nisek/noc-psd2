package com.capco.noc.psd2.service;

import com.capco.noc.psd2.domain.Account;
import com.capco.noc.psd2.repository.AccountRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/psd")
public class AppRestController {

    private static final String AUTH_TOKEN_COOKIE_PARAM = "auth-token";

    private final AccountRepository accountRepository;

    //Key - authToken, value - userName
    private static Map<String, String> loggedInUserMap = new HashMap<>();

    @Autowired
    AppRestController(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    ResponseEntity<String> login(@RequestBody String loginInformation){
        JsonElement jsonElement = new JsonParser().parse(loginInformation);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String username = jsonObject.get("username").getAsString();
        String password = jsonObject.get("password").getAsString();

        Account account = accountRepository.findByUsername(username);
        if(account == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!password.equals(account.getPassword())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String authToken = UUID.randomUUID().toString();
        loggedInUserMap.put(authToken, account.getUsername());

        return new ResponseEntity<>("{\"token\": \""+authToken+"\"}" , HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/logout")
    ResponseEntity<?> logout(@RequestHeader(AUTH_TOKEN_COOKIE_PARAM) String authToken){
        loggedInUserMap.remove(authToken);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/acc")
    ResponseEntity<Account> getAccount(@RequestHeader(AUTH_TOKEN_COOKIE_PARAM) String authToken){
        if(!loggedInUserMap.containsKey(authToken)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Account account = accountRepository.findByUsername(loggedInUserMap.get(authToken));
        if(account == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
