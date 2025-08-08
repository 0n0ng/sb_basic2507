package com.std.sbb.answer;

import com.std.sbb.question.Question;
import com.std.sbb.uesr.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

// 생성자 자동 생성
@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;


    public void create(Question question, String content, SiteUser siteUser) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(siteUser);

        //this 써도 되고 안 써도 되고
        this.answerRepository.save(answer);
    }
}
