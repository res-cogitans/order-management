package org.programmers.ordermanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.programmers.ordermanagementsystem.domain.Member;
import org.programmers.ordermanagementsystem.dto.MemberCreateForm;
import org.programmers.ordermanagementsystem.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Member registerMember(MemberCreateForm form) {
        return memberRepository.save(form);
    }

    @Override
    public Member getMemberById(Long id) {
        return memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

        @Override
    public void deleteMember(Long id) {
        memberRepository.delete(id);
    }
}
