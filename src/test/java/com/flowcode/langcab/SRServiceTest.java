package com.flowcode.langcab;
import com.flowcode.langcab.db.Word;
import com.flowcode.langcab.services.SRService;
import com.flowcode.langcab.services.WordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest
public class SRServiceTest {

	@Autowired
	SRService srService;

	@Autowired
	WordService wordService;

	public Word sampleWordClean(String e, String p, String c) {
		Word word = new Word();
		word.setWordEnglish(e);
		word.setWordPinyin(p);
		word.setWordChinese(c);
		word.setLanguage("Chinese");
		return word;
	}

	public Word sampleWord() {
		Date timeStamp = new java.util.Date();
		Word word = new Word();
		word.setUserId("1");
		word.setWordEnglish("I");
		word.setWordPinyin("Wǒ");
		word.setWordChinese("我");
		word.setDifficulty(2.5);
		word.setCalls(0);
		word.setTimeCreated(timeStamp);
		word.setTimePractice(timeStamp);
		return word;
	}

	@Test
	public void SRServiceSetCallsTest() {
		Word word = sampleWord();
		int before = word.getCalls();
		srService.setCalls(word);
		int after = word.getCalls();
		assertThat(before).isLessThan(after);
	}

	@Test
	public void SRServiceSetEFTest() {
		Word me = sampleWord();
		srService.setEF(me, 5);
		assertThat(me.getDifficulty()).isEqualTo(2.6);
		srService.setEF(me, 5);
		assertThat(me.getDifficulty()).isEqualTo(2.7);
		srService.setEF(me, 5);
		assertThat(me.getDifficulty()).isGreaterThan(2.8);

		Word you = sampleWord();
		srService.setEF(you, 4);
		assertThat(you.getDifficulty()).isEqualTo(2.5);
		srService.setEF(you, 4);
		assertThat(you.getDifficulty()).isEqualTo(2.5);

		Word he = sampleWord();
		srService.setEF(he, 3);
		assertThat(he.getDifficulty()).isEqualTo(2.36);
		srService.setEF(he, 3);
		assertThat(he.getDifficulty()).isEqualTo(2.2199999999999998);
		srService.setEF(he, 3);
		assertThat(he.getDifficulty()).isLessThan(2.1);
	}

	@Test
	public void SRServiceUpdateWordTest() {
		Word word = sampleWord();
		Date timeStamp = new java.util.Date();

		srService.updateWord(5, word);

		assertThat(word.getCalls()).isEqualTo(1);
		assertThat(word.getTimePractice()).isAfter(timeStamp);
		assertThat(word.getDifficulty()).isEqualTo(2.6);

		srService.updateWord(1, word);

		assertThat(word.getCalls()).isEqualTo(2);
		assertThat(word.getTimePractice()).isAfter(timeStamp);
		assertThat(word.getDifficulty()).isEqualTo(2.06);

		srService.updateWord(1, word);

		assertThat(word.getCalls()).isEqualTo(3);
		assertThat(word.getTimePractice()).isAfter(timeStamp);
		assertThat(word.getDifficulty()).isEqualTo(1.52);
	}

	@Test
	@DirtiesContext
	public void SRServiceAfterStudyingTestEasy() throws Exception {
		wordService.save(sampleWordClean("I", "wǒ", "我"), "1");
		wordService.save(sampleWordClean("You", "Nǐ", "你"), "1");

		Date dateBeforeStudying = wordService.getWordById(1, "1").getTimePractice();
		srService.afterStudying(1, 5, "1");
		srService.afterStudying(1, 5, "1");
		srService.afterStudying(1, 5, "1");
		Date dateAfterStudying = wordService.getWordById(1, "1").getTimePractice();

		Calendar c = Calendar.getInstance();
		c.setTime(dateBeforeStudying);
		c.add(Calendar.DAY_OF_MONTH, 17);

		assertThat(dateAfterStudying).isEqualToIgnoringHours(c.getTime());
	}

	@Test
	@DirtiesContext
	public void SRServiceAfterStudyingTestDifficult() throws Exception {
		wordService.save(sampleWordClean("Good", "hǎo", "好"), "1");
		wordService.save(sampleWordClean("Germany", "Déguó", "德国"), "1");

		Date dateBeforeStudying = wordService.getWordById(1, "1").getTimePractice();
		srService.afterStudying(1, 1, "1");
		srService.afterStudying(1, 1, "1");
		srService.afterStudying(1, 1, "1");
		Date dateAfterStudying = wordService.getWordById(1, "1").getTimePractice();

		Calendar c = Calendar.getInstance();
		c.setTime(dateBeforeStudying);
		c.add(Calendar.DAY_OF_MONTH, 8);

		assertThat(dateAfterStudying).isEqualToIgnoringHours(c.getTime());
	}

	@Test
	@DirtiesContext
	public void SRServiceGetOverdueWords() throws Exception {
		Pageable p = new PageRequest(0, 20);

		wordService.save(sampleWordClean("I", "wǒ", "我"), "1");
		wordService.save(sampleWordClean("You", "Nǐ", "你"), "1");
		wordService.save(sampleWordClean("Good", "hǎo", "好"), "1");
		wordService.save(sampleWordClean("Germany", "Déguó", "德国"), "1");
		wordService.save(sampleWordClean("Potato", "tǔdòu", "土豆"), "1");
		wordService.save(sampleWordClean("Carrot", "húluóbo", "胡萝卜"), "2");
		wordService.save(sampleWordClean("Hello", "Zdravstvuyte", "Здравствуйте"), "2");

		assertThat(srService.getOverdueWords("1", "Chinese").size()).isEqualTo(5);
		assertThat(srService.getOverdueWords("2", "Chinese").size()).isEqualTo(2);
		srService.afterStudying(1, 1, "1");
		assertThat(srService.getOverdueWords("1", "Chinese").size()).isEqualTo(4);
		srService.afterStudying(2, 1, "1");
		srService.afterStudying(3, 1, "1");
		assertThat(srService.getOverdueWords("1", "Chinese").size()).isEqualTo(2);
		srService.afterStudying(6, 1, "2");
		srService.afterStudying(7, 1, "2");
		assertThat(srService.getOverdueWords("2", "Chinese").size()).isEqualTo(0);

	}

	@Test
	@DirtiesContext
	public void SRServiceGetChoices() throws Exception {
		wordService.save(sampleWordClean("I", "wǒ", "我"), "1");
		wordService.save(sampleWordClean("You", "Nǐ", "你"), "1");
		wordService.save(sampleWordClean("Good", "hǎo", "好"), "1");
		wordService.save(sampleWordClean("Germany", "Déguó", "德国"), "1");
		wordService.save(sampleWordClean("Potato", "tǔdòu", "土豆"), "1");
		wordService.save(sampleWordClean("Ich", "wǒ", "我"), "1");
		wordService.save(sampleWordClean("Du", "Nǐ", "你"), "1");
		wordService.save(sampleWordClean("Gut", "hǎo", "好"), "1");
		wordService.save(sampleWordClean("Deutschland", "Déguó", "德国"), "1");
		wordService.save(sampleWordClean("Kartoffel", "tǔdòu", "土豆"), "1");
		wordService.save(sampleWordClean("sdf", "tǔdòu", "sfdf"), "1");

		srService.afterStudying(1, 1, "1");
		srService.afterStudying(1, 5, "1");
		srService.afterStudying(1, 3, "1");
	}
}
