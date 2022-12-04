package com.example.coffeeorderingwebapp.member.service;

import com.example.coffeeorderingwebapp.exception.BusinessLogicException;
import com.example.coffeeorderingwebapp.exception.ExceptionCode;
import com.example.coffeeorderingwebapp.member.entity.Member;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    public Member createMember(Member member) {
        Member createdMember = member;
        return createdMember;
    }

    public Member updateMember(Member member) {
        Member updatedMember = member;
        return updatedMember;
    }

    public Member findMember(long memberId) {
        throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
    }

    public List<Member> findMembers() {
        List<Member> members = List.of(
                new Member(1, "hgd@gmail.com", "홍길동", "010-1234-5678"),
                new Member(2, "lml@gmail.com", "이몽룡", "010-1111-2222")
        );
        return members;
    }

    public void deleteMember(long memberId) {
        String logResult = null;
        System.out.println(logResult.toUpperCase());
    }
}
