package com.example.coffeeorderingwebapp.member.controller;

import com.example.coffeeorderingwebapp.member.dto.MemberDto;
import com.example.coffeeorderingwebapp.member.entity.Member;
import com.example.coffeeorderingwebapp.member.mapper.MemberMapper;
import com.example.coffeeorderingwebapp.member.service.MemberService;
import com.example.coffeeorderingwebapp.response.MultiResponseDto;
import com.example.coffeeorderingwebapp.response.SingleResponseDto;
import com.example.coffeeorderingwebapp.stamp.Stamp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/members")
@Validated
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper mapper;

    public MemberController(MemberService memberService, MemberMapper mapper) {
        this.memberService = memberService;
        this.mapper = mapper;
    }

    // 회원 정보 등록
    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post requestBody) {
        Member member = mapper.memberPostDtoToMember(requestBody);
        member.setStamp(new Stamp());
        Member createdMember = memberService.createMember(member);
        return new ResponseEntity<>(new SingleResponseDto<>(mapper.memberToMemberResponseDto(createdMember)), HttpStatus.CREATED);
    }

    // 특정 회원 정보 갱신
    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(@PathVariable("member-id") @Positive long memberId,
                                      @Valid @RequestBody MemberDto.Patch requestBody) {
        requestBody.setMemberId(memberId);
        Member member = memberService.updateMember(mapper.memberPatchDtoToMember(requestBody));
        return new ResponseEntity<>(new SingleResponseDto<>(mapper.memberToMemberResponseDto(member)), HttpStatus.OK);
    }

    // 특정 회원 정보 조회
    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId) {
        Member member = memberService.findMember(memberId);
        return new ResponseEntity<>(new SingleResponseDto<>(mapper.memberToMemberResponseDto(member)), HttpStatus.OK);
    }

    // 모든 회원 정보 조회
    @GetMapping
    public ResponseEntity getMembers(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size) {
        Page<Member> pageMembers = memberService.findMembers(page - 1, size);
        List<Member> members = pageMembers.getContent();
        return new ResponseEntity<>(new MultiResponseDto<>(mapper.membersToMemberResponseDtos(members), pageMembers), HttpStatus.OK);
    }

    // 특정 회원 정보 삭제
    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive long memberId) {
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
