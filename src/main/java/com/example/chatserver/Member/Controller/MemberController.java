package com.example.chatserver.Member.Controller;

import com.example.chatserver.Member.domain.Member;
import com.example.chatserver.Member.dto.MemberSaveRequest;
import com.example.chatserver.Member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/create")
    public ResponseEntity<?> memberCreate(@RequestBody MemberSaveRequest memberSaveRequest) {
        Member member = memberService.create(memberSaveRequest);
        return new ResponseEntity<>(member.getId(), HttpStatus.CREATED);
    }
}
