package com.example.APIPractice.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value = {"password", "ssn"})
//@JsonFilter("UserInfo")
public class User {
    private Integer id;

    @Size(min=2, message = "name은 두 글자 이상 입력해 주세요.")
    private String name;
    @Past // 미래 날짜는 사용할 수 없음
    private Date joinDate;

    private String password;
    private String ssn;
}
