package com.ljy.jwt.model.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@Alias("member")
public class Member {
    
    private String userName;
    private String passWord;
    private String role;
    private String email;
}
