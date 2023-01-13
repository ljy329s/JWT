package com.ljy.jwt.model.repository;

import com.ljy.jwt.model.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository {

   Member selectMember(String username);
    
}
