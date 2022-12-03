package com.example.coffeeorderingwebapp.member.mapper;

import com.example.coffeeorderingwebapp.member.dto.MemberPatchDto;
import com.example.coffeeorderingwebapp.member.dto.MemberPostDto;
import com.example.coffeeorderingwebapp.member.dto.MemberResponseDto;
import com.example.coffeeorderingwebapp.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {
    public Member memberPostDtoToMember(MemberPostDto memberPostDto) {
        return new Member(0L,
                memberPostDto.getEmail(),
                memberPostDto.getName(),
                memberPostDto.getPhone());
    }

    public Member memberPatchDtoToMember(MemberPatchDto memberPatchDto) {
        return new Member(memberPatchDto.getMemberId(),
                null,
                memberPatchDto.getName(),
                memberPatchDto.getPhone());
    }

    public MemberResponseDto memberToMemberResponseDto(Member member) {
        return new MemberResponseDto(member.getMemberId(),
                member.getEmail(),
                member.getName(),
                member.getPhone());
    }
}
