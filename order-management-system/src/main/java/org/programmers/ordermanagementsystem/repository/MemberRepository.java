package org.programmers.ordermanagementsystem.repository;

import org.programmers.ordermanagementsystem.domain.Member;
import org.programmers.ordermanagementsystem.dto.MemberCreateForm;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(MemberCreateForm args);

    Member update(Member item);

    Optional<Member> findById(Long id);

    List<Member> findAll();

    void delete(Long id);
}
