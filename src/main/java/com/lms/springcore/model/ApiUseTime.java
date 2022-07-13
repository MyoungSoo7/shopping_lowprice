package com.lms.springcore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter // get 함수를 일괄적으로 만들어줍니다.
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity // DB 테이블 역할을 합니다.
public class ApiUseTime {
    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private Users user;

    @Column(nullable = false)
    private Long totalTime;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long totalCount;

    public ApiUseTime(Users user, long totalTime) {
        this.user = user;
        this.totalTime = totalTime;
        this.totalCount = 1L;
    }

    public void addUseTime(long useTime) {
        this.totalTime += useTime;
        this.totalCount += 1L;
    }
}