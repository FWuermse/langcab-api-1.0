package com.flowcode.langcab.db;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Created by flori on 07.10.2017.
 */
@Entity
@Table(name = "words")
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long wordId;

    @NotEmpty
    private String userId;

    @NotEmpty
    private String wordEnglish;

    @NotEmpty
    private String wordChinese;

    @NotEmpty
    private String language;

    @NotNull
    private Date timeCreated;

    @ElementCollection
    private Set<String> tags = new HashSet<>();

    private String wordPinyin;

    private Date timePractice;

    private double difficulty;

    private int calls;


    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getTimePractice() {
        return timePractice;
    }

    public void setTimePractice(Date timePractice) {
        this.timePractice = timePractice;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public int getCalls() {
        return calls;
    }

    public void setCalls(int calls) {
        this.calls = calls;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public long getWordId() {
        return wordId;
    }

    public void setWordId(long wordId) {
        this.wordId = wordId;
    }

    public String getWordEnglish() {
        return wordEnglish;
    }

    public void setWordEnglish(String wordEnglish) {
        this.wordEnglish = wordEnglish;
    }

    public String getWordPinyin() {
        return wordPinyin;
    }

    public void setWordPinyin(String wordPinyin) {
        this.wordPinyin = wordPinyin;
    }

    public String getWordChinese() {
        return wordChinese;
    }

    public void setWordChinese(String wordChinese) {
        this.wordChinese = wordChinese;
    }

    @Override
    public String toString() {
        return "Word{" +
                "wordId=" + wordId +
                ", userId='" + userId + '\'' +
                ", wordEnglish='" + wordEnglish + '\'' +
                ", wordChinese='" + wordChinese + '\'' +
                ", language='" + language + '\'' +
                ", timeCreated=" + timeCreated +
                ", wordPinyin='" + wordPinyin + '\'' +
                ", timePractice=" + timePractice +
                ", difficulty=" + difficulty +
                ", calls=" + calls +
                '}';
    }
}
