package com.std.sbb.uesr;

// 왜 SiteUser냐? 이미 시큐리티에 user라는 클래스가 있기때문

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// SiteUser 테이블 생성
@Getter
@Setter
@Entity
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

}
