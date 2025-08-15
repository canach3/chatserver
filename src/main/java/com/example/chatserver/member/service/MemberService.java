package com.example.chatserver.member.service;

import com.example.chatserver.member.domain.Member;
import com.example.chatserver.member.dto.MemberListRequest;
import com.example.chatserver.member.dto.MemberLoginRequest;
import com.example.chatserver.member.dto.MemberSaveRequest;
import com.example.chatserver.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(MemberSaveRequest memberSaveRequest) {
        if (memberRepository.findByEmail(memberSaveRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일");
        }
        Member newMember = Member.builder()
                .name(memberSaveRequest.getName())
                .email(memberSaveRequest.getEmail())
                .password(passwordEncoder.encode(memberSaveRequest.getPassword()))
                .build();

        return memberRepository.save(newMember);
    }

    public Member login(MemberLoginRequest memberLoginRequest) {
        Member member = memberRepository.findByEmail(memberLoginRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 이메일"));

        if (!passwordEncoder.matches(memberLoginRequest.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않음");
        }

        return member;
    }

    public List<MemberListRequest> findAll() {
        List<Member> members = memberRepository.findAll();
        List<MemberListRequest> memberListRequests = new ArrayList<>();
        for (Member member : members) {
            MemberListRequest memberListRequest = new MemberListRequest();
            memberListRequest.setId(member.getId());
            memberListRequest.setName(member.getName());
            memberListRequest.setEmail(member.getEmail());

            memberListRequests.add(memberListRequest);
        }

        return memberListRequests;
    }
}
