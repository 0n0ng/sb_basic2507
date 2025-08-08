package com.std.sbb.question;

import com.std.sbb.answer.AnswerForm;
import com.std.sbb.uesr.SiteUser;
import com.std.sbb.uesr.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {
    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(value = "page", defaultValue = "0")
                       int page) {
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);

        return "question_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String getQuestion(
            Model model,
            @PathVariable("id") Integer id,
            AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);

        return "question_detail";
    }

    //이 애너테이션을 메서드에 붙이면 해당 메서드는 로그인한 사용자만 호출할 수 있다.
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    // BindingResult 매개변수는 항상 @Valid 매개변수 바로 뒤에 위치해야한다.
    // @Valid QuestionForm questionForm, BindingResult bindingResult :
    // QuestionForm 에서 설정한 조건들을 실제로 검사한다.
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(
            @Valid QuestionForm questionForm,
            BindingResult bindingResult,
            Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());

        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
        return "redirect:/question/list";
    }

    //수정
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        Question question = this.questionService.getQuestion(id);

        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        //수정버튼 눌렀을 때 기존 내용이 보인다.
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());

        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }
}