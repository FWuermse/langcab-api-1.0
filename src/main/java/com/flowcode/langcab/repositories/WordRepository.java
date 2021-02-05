package com.flowcode.langcab.repositories;

import com.flowcode.langcab.db.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface WordRepository extends CrudRepository<Word, Long> {

    List<Word> findByTimePracticeBeforeAndUserIdEqualsAndLanguageEquals(Date timestamp, String userId, String language);

    List<Word> findByTimePracticeBeforeAndUserIdEquals(Date timestamp, String userId);

    Page<Word> findByTimePracticeBeforeAndUserIdEquals(Date timestamp, String userId, Pageable pageable);

    List<Word> findTop10ByUserIdAndLanguageOrderByDifficulty(String userId, String language);

    List<Word> findTop10ByUserIdOrderByDifficulty(String userId);

    Optional<Word> findTopByTimeCreatedBeforeAndUserIdEqualsOrderByTimeCreatedDesc(Date timestamp, String userId);

    Page<Word> findByTimeCreatedAfterAndUserId(Date timestamp, String userId, Pageable pageable);

    //@Query("select w from Word w where w.userId = :userId and w.language = :language and (lower(w.wordEnglish) like concat('%', lower(:wordEnglish), '%') or lower(w.wordChinese) like concat('%', lower(:wordChinese), '%') or lower(w.wordPinyin) like concat('%', lower(:wordPinyin), '%'))")
    @Query("select w from Word w where w.userId = :userId and w.language = :language and (lower(w.wordEnglish) like concat('%', lower(:wordEnglish), '%') or lower(w.wordChinese) like concat('%', lower(:wordChinese), '%') or lower(w.wordPinyin) like concat('%', lower(:wordPinyin), '%'))")
    Page<Word> findBySearchAndLanguage(@Param("userId") String userId,
                                       @Param("language") String language,
                                       @Param("wordEnglish") String wordEnglish,
                                       @Param("wordChinese") String wordChinese,
                                       @Param("wordPinyin") String wordPinyin, Pageable pageable);

    //@Query("select w from Word w where w.userId = :userId and (lower(w.wordEnglish) like concat('%', lower(:wordEnglish), '%') or lower(w.wordChinese) like concat('%', lower(:wordChinese), '%') or lower(w.wordPinyin) like concat('%', lower(:wordPinyin), '%'))")
    @Query("select w from Word w where w.userId = :userId and (lower(w.wordEnglish) like concat('%', lower(:wordEnglish), '%') or lower(w.wordChinese) like concat('%', lower(:wordChinese), '%') or lower(w.wordPinyin) like concat('%', lower(:wordPinyin), '%'))")
    Page<Word> findBySearch(@Param("userId") String userId,
                            @Param("wordEnglish") String wordEnglish,
                            @Param("wordChinese") String wordChinese,
                            @Param("wordPinyin") String wordPinyin, Pageable pageable);

    @Query("select distinct w.language from Word w where w.userId = :userId")
    Optional<List<String>> findLanguagesByUserId(@Param("userId") String userId);
}