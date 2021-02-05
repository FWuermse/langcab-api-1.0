package com.flowcode.langcab.services;

import com.flowcode.langcab.db.Word;
import com.flowcode.langcab.repositories.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created by flori on 07.10.2017.
 */
@Service
public class WordService {

    private static final Logger log = Logger.getLogger( WordService.class.getName() );

    @Autowired
    WordRepository repository;

    public Page<Word> getAll(String search, String userId, String language, Pageable pageable) {
        if (language.equals("")) {
            return repository.findBySearch(userId, search, search, search, pageable);
        }
        return repository.findBySearchAndLanguage(userId, language, search, search, search, pageable);
    }

    public Word getWordById(long id, String userId) {
        Word word =  repository.findById(id).get();
        if (word.getUserId().equals(userId))
            return word;
        else
            throw new SecurityException("The word with Id: "+id+" doesn't match your google Id");
    }

    public void save(Word word, String userId) throws Exception {
        Date timeStamp = new java.util.Date();
        Pageable p = new PageRequest(0, 1);
        if (repository.findByTimeCreatedAfterAndUserId(checkRecentWords(timeStamp, -1), userId, p).getTotalElements() < 300) {
            if (repository.findByTimeCreatedAfterAndUserId(checkRecentWords(timeStamp, -28), userId, p).getTotalElements() < 1500) {
                word.setUserId(userId);
                word.setDifficulty(2.5);
                word.setCalls(0);
                word.setTimeCreated(timeStamp);
                word.setTimePractice(timeStamp);
                repository.save(word);
            } else {
                throw new Exception("You exceeded your daily limit of adding words. Please train / review your words before adding more");
            }
        } else {
            throw new Exception("You exceeded your daily limit of adding words. Take a break and add more tomorrow!");
        }
    }

    public Date checkRecentWords(Date timestamp, int delay) {
        Calendar c = Calendar.getInstance();
        c.setTime(timestamp);
        c.add(Calendar.DATE, -1);
        return c.getTime();
    }

    public String getGoogleUserByWord(long id) {
        return repository.findById(id).get().getUserId();
    }

    public void updateWholeWord(Word word, String userId) {
        if (repository.findById(word.getWordId()).isPresent())
            repository.save(word);
        else
            throw new SecurityException("The word with Id: "+word.getWordId()+" doesn't match your google Id");
    }

    public void update(Word word, String userId) {
        Word origWord = repository.findById(word.getWordId()).get();
        if (origWord.getUserId().equals(userId)) {
            origWord.setWordEnglish(word.getWordEnglish());
            origWord.setWordPinyin(word.getWordPinyin());
            origWord.setWordChinese(word.getWordChinese());
            origWord.setLanguage(word.getLanguage());
            repository.save(origWord);
        }
        else
            throw new SecurityException("The word with Id: "+word.getWordId()+" doesn't match your google Id");
    }

    public void deleteWordById(long wordId, String userId) {
        if (repository.findById(wordId).isPresent()) {
            if (repository.findById(wordId).get().getUserId().equals(userId))
                repository.deleteById(wordId);
        }
    }
}



