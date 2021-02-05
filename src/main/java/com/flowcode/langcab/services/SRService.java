package com.flowcode.langcab.services;

import com.flowcode.langcab.db.Word;
import com.flowcode.langcab.repositories.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class SRService {

    @Autowired
    WordRepository repository;

    @Autowired
    WordService wordService;

    // How update a word in the repo after studying
    public void updateWord(int difficulty, Word word) {
        int num = setCalls(word);
        double ef = setEF(word, difficulty);
        Date d = updateDate(ef, num, word);
    }

    // How often a word was called
    public int setCalls(Word word) {
        int num = word.getCalls();
        num ++;
        word.setCalls(num);
        return num;
    }

    /**
     * Effort factor
     * @param word (Old effort factor)
     * @param difficulty
     * @return New effort factor
     */
    public double setEF(Word word, int difficulty) {
        double ef = word.getDifficulty();
        ef = updateEF(difficulty, ef);
        word.setDifficulty(ef);
        return ef;
    }

    public Date updateDate(double ef, int num, Word word) {
        int daysNext = calculateDays(ef, num);

        Date d = new java.util.Date();

        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DATE, daysNext);
        d = c.getTime();

        word.setTimePractice(d);

        return d;
    }

    public int calculateDays(double ef, int num) {
        if(num == 1) return 1;
        if(num == 2) return 6;
        if (num >2) {
            double numDouble = num*(num-1)*ef;
            return num = (int) Math.round(numDouble);
        }
        else
            return 0;
    }

    public double updateEF(int q, double ef) {
        double newEF = ef+(0.1-(5-q)*(0.08+(5-q)*0.02));
        if (newEF < 1.3)
            newEF = 1.3;
        return newEF;
    }

    public void afterStudying(int id, int difficulty, String userId) {
        Word word = wordService.getWordById(id, userId);
        updateWord(difficulty, word);
        wordService.updateWholeWord(word, userId);
    }

    public List<Word> getOverdueWords(String userId, String language) {
        Date timeStamp = new java.util.Date();
        return repository.findByTimePracticeBeforeAndUserIdEqualsAndLanguageEquals(timeStamp, userId, language);
    }

    public List<Word> getAllOverdueWords(String userId) {
        Date timeStamp = new java.util.Date();
        return repository.findByTimePracticeBeforeAndUserIdEquals(timeStamp, userId);
    }

    public Page<Word> getAllOverduePage(String userId, Pageable pageable) {
        Date timeStamp = new java.util.Date();
        return repository.findByTimePracticeBeforeAndUserIdEquals(timeStamp, userId, pageable);
    }

    public List<Word> getChoice(String userId, String language) {
        return repository.findTop10ByUserIdAndLanguageOrderByDifficulty(userId, language);
    }

    public List<Word> getGlobalChoice(String userId) {
        return repository.findTop10ByUserIdOrderByDifficulty(userId);
    }
}