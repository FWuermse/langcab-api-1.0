package com.flowcode.langcab.Controllers;

import com.flowcode.langcab.db.Word;
import com.flowcode.langcab.services.WordService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * Created by flori on 07.10.2017.
 */
@CrossOrigin
@RestController
@RequestMapping("/word")
public class WordController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    WordService wordService;

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public Page<Word> getAllWords(@RequestParam(value = "search", defaultValue = "") String search,
                                   @RequestParam(value = "language", defaultValue = "") String language,
                                   @RequestHeader("Authorization") String idTokenString, Pageable pageable) throws FirebaseAuthException {
        return wordService.getAll(search, verifyToken(idTokenString), language, pageable);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{wordId}")
    public Word getWord(@PathVariable ("wordId") int wordId,
                        @RequestHeader("Authorization") String idTokenString) throws FirebaseAuthException {
        return wordService.getWordById(wordId, verifyToken(idTokenString));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{wordId}")
    public void deleteWord(@PathVariable ("wordId") int wordId,
                        @RequestHeader("Authorization") String idTokenString) throws FirebaseAuthException {
        wordService.deleteWordById(wordId, verifyToken(idTokenString));
    }

    @RequestMapping(value="/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public void addNewWord(@RequestBody Word word,
                           @RequestHeader("Authorization") String idTokenString) throws Exception {
        wordService.save(word, verifyToken(idTokenString));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/", produces = "application/json", consumes = "application/json")
    @ResponseBody
    public void updateWord(@RequestBody Word word,
                           @RequestHeader("Authorization") String idTokenString) throws FirebaseAuthException {
        wordService.update(word, verifyToken(idTokenString));
    }

    // Verify token
    public String verifyToken(String idToken) throws FirebaseAuthException {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        return decodedToken.getUid();
    }

}
