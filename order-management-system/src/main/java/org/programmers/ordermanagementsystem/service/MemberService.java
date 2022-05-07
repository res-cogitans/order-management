package org.programmers.ordermanagementsystem.service;

import org.programmers.ordermanagementsystem.domain.Member;
import org.programmers.ordermanagementsystem.dto.MemberCreateForm;

import java.util.List;

public interface MemberService {
    List<Member> getAllMembers();

    Member registerMember(MemberCreateForm form);

    Member getMemberById(Long id);

    void deleteMember(Long id);
}
