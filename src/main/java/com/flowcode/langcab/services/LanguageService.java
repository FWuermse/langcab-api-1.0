package com.flowcode.langcab.services;

import com.flowcode.langcab.db.Word;
import com.flowcode.langcab.repositories.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LanguageService {

    @Autowired
    WordRepository repository;

    public List<String> getAllLanguages() {
        List<String> allLanguages = new LinkedList<>();
        List<String> languages = Arrays.asList(Locale.getISOLanguages());
        for (String language : languages) {
            Locale loc = new Locale(language);
            allLanguages.add(loc.getDisplayLanguage());
        }
        return allLanguages;
    }

    public List<String> getMyLanguages(String userId) throws Exception {
        Optional<List<String>> myLanguagesOptional = repository.findLanguagesByUserId(userId);
        if (myLanguagesOptional.isPresent())
            return myLanguagesOptional.get();
        else
            throw new Exception("Please add a new language first");
    }


    public String getLastLanguage(String userId) throws Exception {
        Date timeStamp = new java.util.Date();
        Optional<Word> lastAddedWordOptional = repository.findTopByTimeCreatedBeforeAndUserIdEqualsOrderByTimeCreatedDesc(timeStamp, userId);
        if (lastAddedWordOptional.isPresent())
            return lastAddedWordOptional.get().getLanguage();
        else
            throw new Exception("Welcome! Start by adding your first word.");
    }
}
