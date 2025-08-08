package com.example.chatserver.Member.service;

import com.example.chatserver.Member.domain.Member;
import com.example.chatserver.Member.dto.MemberSaveRequest;
import com.example.chatserver.Member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member create(MemberSaveRequest memberSaveRequest) {
        if (memberRepository.findByEmail(memberSaveRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일");
        }
        Member newMember = Member.builder()
                .name(memberSaveRequest.getName())
                .email(memberSaveRequest.getEmail())
                .password(memberSaveRequest.getPassword())
                .build();

        return memberRepository.save(newMember);
    }
}
