package com.std.sbb.answer;

import com.std.sbb.question.Question;
import com.std.sbb.uesr.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class Answer {
    private LocalDateTime modifyDate;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Question question;

    //한 명이 질문 여러개 작성할 수 있다.
    //글쓴이의 id정보가 함께 저장된다.
    @ManyToOne
    private SiteUser author;

    @ManyToMany
    Set<SiteUser> voter;
}