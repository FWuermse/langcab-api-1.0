package com.flowcode.langcab;

import com.flowcode.langcab.db.Word;
import com.flowcode.langcab.services.LanguageService;
import static org.assertj.core.api.Assertions.assertThat;
import com.flowcode.langcab.services.WordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest
public class LanguageServiceTest {

    @Autowired
    WordService wordService;

    @Autowired
    LanguageService languageService;

    @DirtiesContext
    @Test
    public void getMyLanguagesTest() throws Exception {
        Word word = new Word();
        word.setLanguage("German");
        word.setWordEnglish("Tree");
        word.setWordChinese("Baum");

        wordService.save(word, "1");

        Word word1 = new Word();
        word1.setLanguage("German");
        word1.setWordEnglish("Wallet");
        word1.setWordChinese("Geldbeutel");

        wordService.save(word1, "1");

        Word word2 = new Word();
        word2.setLanguage("Chinese");
        word2.setWordEnglish("Potato");
        word2.setWordChinese("Tu do");

        wordService.save(word2, "1");

        assertThat(languageService.getMyLanguages("1").size()).isEqualTo(2);
    }

    @DirtiesContext
    @Test
    public void getLastLanguageTest() throws Exception {
        Word word = new Word();
        word.setLanguage("German");
        word.setWordEnglish("Tree");
        word.setWordChinese("Baum");

        wordService.save(word, "1");

        Word word1 = new Word();
        word1.setLanguage("German");
        word1.setWordEnglish("Wallet");
        word1.setWordChinese("Geldbeutel");

        wordService.save(word1, "1");

        Word word2 = new Word();
        word2.setLanguage("Chinese");
        word2.setWordEnglish("Potato");
        word2.setWordChinese("Tu do");

        wordService.save(word2, "1");

        assertThat(languageService.getLastLanguage("1")).isEqualToIgnoringCase("Chinese");

        Word word3 = new Word();
        word3.setLanguage("Greek");
        word3.setWordEnglish("Potato");
        word3.setWordChinese("WhyWouldIKnow?? Do you think I'm god?");

        wordService.save(word3, "1");

        assertThat(languageService.getLastLanguage("1")).isEqualToIgnoringCase("Greek");
    }
}