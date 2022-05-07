package org.programmers.ordermanagementsystem.dto;

import org.programmers.ordermanagementsystem.domain.Address;
import org.programmers.ordermanagementsystem.domain.Member;

public record MemberDto(Long id, String name, String age, String email, Address address) {

    public static MemberDto from(Member member) {
        return new MemberDto(member.getId(), member.getName(), member.getAge().toString(), member.getEmail().toString(), member.getAddress());
    }
}
