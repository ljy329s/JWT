package com.ljy.jwt.model.repository;

import com.ljy.jwt.model.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository {

    static Optional<Member> selectUser(String email) {
        return null;
    }

    Member selectMember(String username);
}
