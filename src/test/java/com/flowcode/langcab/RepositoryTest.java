package com.flowcode.langcab;
import com.flowcode.langcab.db.Word;
import com.flowcode.langcab.services.WordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;

import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest
public class RepositoryTest {

    @Autowired
    WordService wordService;

    public Word sampleWord(String e, String p, String c, String l) {
        Word word = new Word();
        word.setWordEnglish(e);
        word.setWordPinyin(p);
        word.setWordChinese(c);
        word.setLanguage(l);
        return word;
    }

    @Test
    public void storeWord() throws Exception {
        wordService.save(sampleWord("I", "wǒ", "我", "Chinese"), "1");
        wordService.save(sampleWord("You", "Nǐ", "你", "Chinese"), "1");
        wordService.save(sampleWord("Good", "hǎo", "好", "Chinese"), "1");
        wordService.save(sampleWord("Germany", "Déguó", "德国", "Chinese"), "1");
    }

    @Test
    public void searchTest() throws Exception {
        Pageable p = new PageRequest(0, 20);
        wordService.save(sampleWord("I", "wǒ", "我", "Chinese"), "1");
        wordService.save(sampleWord("You", "Nǐ", "你", "Chinese"), "1");
        wordService.save(sampleWord("Good", "hǎo", "好", "Chinese"), "1");
        wordService.save(sampleWord("Germany", "Déguó", "德国", "Chinese"), "1");
        wordService.save(sampleWord("Potato", "tǔdòu", "土豆", "Chinese"), "1");
        wordService.save(sampleWord("Carrot", "húluóbo", "胡萝卜", "Chinese"), "2");
        wordService.save(sampleWord("Hello", "Zdravstvuyte", "Здравствуйте", "Russian"), "2");

        List<Word> list = wordService.getAll("u", "1", "Chinese", p).getContent();
        assertThat(list.size()).isEqualTo(3);
        list = wordService.getAll("O", "1", "Chinese", p).getContent();
        assertThat(list.size()).isEqualTo(3);
        list = wordService.getAll("德", "1", "Chinese", p).getContent();
        assertThat(list.size()).isEqualTo(1);
        list = wordService.getAll("k", "2", "Chinese", p).getContent();
        assertThat(list.size()).isEqualTo(0);
        list = wordService.getAll("", "2", "Chinese", p).getContent();
        assertThat(list.size()).isEqualTo(1);
        list = wordService.getAll("H", "2", "", p).getContent();
        assertThat(list.size()).isEqualTo(2);

    }

    @Test(expected = Exception.class)
    @DirtiesContext
    public void saveTooManyWords() throws Exception {
        for (int i = 0; i < 301; i++) {
            wordService.save(sampleWord("I", "wǒ", "我", "Chinese"), "1");
        }
    }
}
