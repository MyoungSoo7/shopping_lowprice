package com.lms.springcore.service;

import com.lms.springcore.model.UserName;
import com.lms.springcore.repository.UserNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserNameService {
    private final UserNameRepository userRepository;

    @Autowired
    public UserNameService(UserNameRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserName createUser() {
        // 테스트 회원 "user1" 객체 추가
        UserName beforeSavedUser = new UserName("user1", "정국", "불족발");
        // 회원 "user1" 객체를 영속화
        UserName savedUser = userRepository.save(beforeSavedUser);

        // beforeSavedUser: 영속화되기 전 상태의 자바 일반객체
        // savedUser:영속성 컨텍스트 1차 캐시에 저장된 객체
        assert (beforeSavedUser != savedUser);

        // 회원 "user1" 을 조회
        UserName foundUser1 = userRepository.findById("user1").orElse(null);
        assert (foundUser1 == savedUser);

        // 회원 "user1" 을 또 조회
        UserName foundUser2 = userRepository.findById("user1").orElse(null);
        assert (foundUser2 == savedUser);

        // 회원 "user1" 을 또또 조회
        UserName foundUser3 = userRepository.findById("user1").orElse(null);
        assert (foundUser3 == savedUser);

        return foundUser3;
    }

    public UserName deleteUser() {
            // 테스트 회원 "user1" 객체 추가
        UserName firstUser = new UserName("user1", "지민", "엄마는 외계인");
        // 회원 "user1" 객체를 영속화
        UserName savedFirstUser = userRepository.save(firstUser);

// 회원 "user1" 삭제
        userRepository.delete(savedFirstUser);

// 회원 "user1" 조회
        UserName deletedUser1 = userRepository.findById("user1").orElse(null);
        assert (deletedUser1 == null);

// -------------------
// 테스트 회원 "user1" 객체를 다시 추가
// 회원 "user1" 객체 추가
        UserName secondUser = new UserName("user1", "지민", "엄마는 외계인");

// 회원 "user1" 객체를 영속화
        UserName savedSecondUser = userRepository.save(secondUser);
        assert (savedFirstUser != savedSecondUser);
        assert (savedFirstUser.getUsername().equals(savedSecondUser.getUsername()));
        assert (savedFirstUser.getNickname().equals(savedSecondUser.getNickname()));
        assert (savedFirstUser.getFavoriteFood().equals(savedSecondUser.getFavoriteFood()));

// 회원 "user1" 조회
        UserName foundUser = userRepository.findById("user1").orElse(null);
        assert (foundUser == savedSecondUser);

        return foundUser;
    }

    public UserName updateUserFail() {
// 회원 "user1" 객체 추가
        UserName user = new UserName("user1", "뷔", "콜라");
// 회원 "user1" 객체를 영속화
        UserName savedUser = userRepository.save(user);

// 회원의 nickname 변경
        savedUser.setNickname("얼굴천재");
// 회원의 favoriteFood 변경
        savedUser.setFavoriteFood("버거킹");

// 회원 "user1" 을 조회
        UserName foundUser = userRepository.findById("user1").orElse(null);
// 중요!) foundUser 는 DB 값이 아닌 1차 캐시에서 가져오는 값
        assert (foundUser == savedUser);
        assert (foundUser.getUsername().equals(savedUser.getUsername()));
        assert (foundUser.getNickname().equals(savedUser.getNickname()));
        assert (foundUser.getFavoriteFood().equals(savedUser.getFavoriteFood()));

        return foundUser;
    }

    public UserName updateUser1() {
// 테스트 회원 "user1" 생성
        UserName user = new UserName("user1", "RM", "고기");
// 회원 "user1" 객체를 영속화
        UserName savedUser1 = userRepository.save(user);

// 회원의 nickname 변경
        savedUser1.setNickname("남준이");
// 회원의 favoriteFood 변경
        savedUser1.setFavoriteFood("육회");

// user1 을 저장
        UserName savedUser2 = userRepository.save(savedUser1);
        assert (savedUser1 == savedUser2);

        return savedUser2;
    }

    @Transactional
    public UserName updateUser2() {
// 테스트 회원 "user1" 생성
// 회원 "user1" 객체 추가
        UserName user = new UserName("user1", "진", "꽃등심");
// 회원 "user1" 객체를 영속화
        UserName savedUser = userRepository.save(user);

// 회원의 nickname 변경
        savedUser.setNickname("월드와이드핸섬 진");
// 회원의 favoriteFood 변경
        savedUser.setFavoriteFood("까르보나라");

        return savedUser;
    }
}
