package com.example.coffeeorderingwebapp.member.mapstruct.mapper;

import com.example.coffeeorderingwebapp.member.dto.MemberPatchDto;
import com.example.coffeeorderingwebapp.member.dto.MemberPostDto;
import com.example.coffeeorderingwebapp.member.dto.MemberResponseDto;
import com.example.coffeeorderingwebapp.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member memberPostDtoToMember(MemberPostDto memberPostDto);
    Member memberPatchDtoToMember(MemberPatchDto memberPatchDto);
    MemberResponseDto memberToMemberResponseDto(Member member);
}
