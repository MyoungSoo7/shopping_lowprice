package com.lms.springcore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class ApiUseTime {

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