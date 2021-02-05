package com.flowcode.langcab.Controllers;

import com.flowcode.langcab.db.Word;
import com.flowcode.langcab.services.SRService;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;

import static com.google.auth.oauth2.GoogleCredentials.fromStream;

@CrossOrigin
@RestController
@RequestMapping("/learn")
public class LearnWordController {

    @Autowired
    SRService srService;

    @RequestMapping(value="/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public void afterStudying(@RequestBody HashMap<String, String> map,
                              @RequestHeader("Authorization") String idTokenString) throws GeneralSecurityException, IOException, FirebaseAuthException {
        srService.afterStudying(Integer.parseInt(map.get("id")), Integer.parseInt(map.get("di")), verifyToken(idTokenString));
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Word> getOverdueWords(@RequestHeader("Authorization") String idTokenString,
                                      @RequestParam(value = "language", defaultValue = "") String language) throws GeneralSecurityException, IOException, FirebaseAuthException {
        return srService.getOverdueWords(verifyToken(idTokenString), language);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Word> getAllOverdueWords(@RequestHeader("Authorization") String idTokenString) throws GeneralSecurityException, IOException, FirebaseAuthException {
        return srService.getAllOverdueWords(verifyToken(idTokenString));
    }

    @RequestMapping(value = "/all/amount", method = RequestMethod.GET)
    public Page<Word> getAllOverdueWordsPage(@RequestHeader("Authorization") String idTokenString, Pageable pageable) throws GeneralSecurityException, IOException, FirebaseAuthException {
        return srService.getAllOverduePage(verifyToken(idTokenString), pageable);
    }

    @RequestMapping(value = "/choice", method = RequestMethod.GET)
    public List<Word> getChoice(@RequestParam(value = "language", defaultValue = "") String language,
                                @RequestHeader("Authorization") String idTokenString) throws GeneralSecurityException, IOException, FirebaseAuthException {
        return srService.getChoice(verifyToken(idTokenString), language);
    }

    @RequestMapping(value = "/choice/all", method = RequestMethod.GET)
    public List<Word> getChoice(@RequestHeader("Authorization") String idTokenString) throws GeneralSecurityException, IOException, FirebaseAuthException {
        return srService.getGlobalChoice(verifyToken(idTokenString));
    }

    public String verifyToken(String idToken) throws FirebaseAuthException {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        return decodedToken.getUid();
    }
}
