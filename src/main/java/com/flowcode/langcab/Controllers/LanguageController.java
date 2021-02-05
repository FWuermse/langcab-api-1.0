package com.flowcode.langcab.Controllers;

import com.flowcode.langcab.services.LanguageService;
import com.flowcode.langcab.services.LoginService;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import static com.google.auth.oauth2.GoogleCredentials.fromStream;

@CrossOrigin
@RestController
@RequestMapping("/language")
public class LanguageController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    LanguageService languageService;

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public List<String> getAllLanguages() {
        return languageService.getAllLanguages();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/mine")
    public List<String> getMyLanguages(@RequestHeader("Authorization") String idTokenString) throws Exception {
        return languageService.getMyLanguages(verifyToken(idTokenString));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/last")
    public String getLastLanguage(@RequestHeader("Authorization") String idTokenString) throws Exception {
        return languageService.getLastLanguage(verifyToken(idTokenString));
    }

    // Verify token
    public String verifyToken(String idToken) throws FirebaseAuthException, IOException {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        return decodedToken.getUid();
    }
}