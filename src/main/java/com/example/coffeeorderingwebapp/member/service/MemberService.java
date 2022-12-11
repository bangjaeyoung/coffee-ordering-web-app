package com.example.coffeeorderingwebapp.member.service;

import com.example.coffeeorderingwebapp.exception.BusinessLogicException;
import com.example.coffeeorderingwebapp.exception.ExceptionCode;
import com.example.coffeeorderingwebapp.member.entity.Member;
import com.example.coffeeorderingwebapp.member.repository.MemberRepository;
import com.example.coffeeorderingwebapp.utils.CustomBeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final CustomBeanUtils<Member> beanUtils;

    public MemberService(MemberRepository memberRepository, CustomBeanUtils<Member> beanUtils) {
        this.memberRepository = memberRepository;
        this.beanUtils = beanUtils;
    }

    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());
        return memberRepository.save(member);
    }

//    public Member updateMember(Member member) {
//        Member findMember = findVerifiedMember(member.getMemberId());
//        Optional.ofNullable(member.getName()).ifPresent(findMember::setName);
//        Optional.ofNullable(member.getPhone()).ifPresent(findMember::setPhone);
//        Optional.ofNullable(member.getMemberStatus()).ifPresent(findMember::setMemberStatus);
//        return memberRepository.save(findMember);
//    }

    public Member updateMember(Member member) {
        Member findMember = findVerifiedMember(member.getMemberId());
        beanUtils.copyNonNullProperties(member, findMember);
        return memberRepository.save(findMember);
    }

    public Member findMember(long memberId) {
        return findVerifiedMember(memberId);
    }

    public Page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page, size, Sort.by("memberId").descending()));
    }

    public void deleteMember(long memberId) {
        Member findMember = findVerifiedMember(memberId);
        memberRepository.delete(findMember);
    }

    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }

    private void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
    }
}
