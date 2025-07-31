package com.std.sbb.answer;

import com.std.sbb.question.Question;
import com.std.sbb.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam(value = "content") String content) {
        Question question = this.questionService.getQuestion(id);

        this.answerService.create(question, content);

         // 답변 달고서 다시 돌아감
        // 그래서 달린 답변을 바로 확인할 수 있음
        return String.format("redirect:/question/detail/%s", id);
    }
}