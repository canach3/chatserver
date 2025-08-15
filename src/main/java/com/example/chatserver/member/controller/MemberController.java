package com.example.chatserver.member.controller;

import com.example.chatserver.member.domain.Member;
import com.example.chatserver.member.dto.MemberListRequest;
import com.example.chatserver.member.dto.MemberLoginRequest;
import com.example.chatserver.member.dto.MemberSaveRequest;
import com.example.chatserver.member.service.MemberService;
import com.example.chatserver.common.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/create")
    public ResponseEntity<?> memberCreate(@RequestBody MemberSaveRequest memberSaveRequest) {
        Member member = memberService.create(memberSaveRequest);
        return new ResponseEntity<>(member.getId(), HttpStatus.CREATED);
    }

    @PostMapping("/doLogin")
    public ResponseEntity<?> doLogin(@RequestBody MemberLoginRequest memberLoginRequest) {
        Member member = memberService.login(memberLoginRequest);

        String jwtToken = jwtTokenProvider.createToken(member.getEmail(), member.getRole().toString());
        Map<String, Object> loginInfo = new HashMap<>();
        loginInfo.put("id", member.getId());
        loginInfo.put("token", jwtToken);

        return new ResponseEntity<>(loginInfo, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<?> memberList() {
        List<MemberListRequest> memberListRequests = memberService.findAll();
        return new ResponseEntity<>(memberListRequests, HttpStatus.OK);
    }
}
