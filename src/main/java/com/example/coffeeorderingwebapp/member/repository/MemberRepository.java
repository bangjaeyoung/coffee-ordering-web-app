package com.example.coffeeorderingwebapp.member.repository;

import com.example.coffeeorderingwebapp.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> { // 수정된 부분
    Optional<Member> findByEmail(String email);
}
