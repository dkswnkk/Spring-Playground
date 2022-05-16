package com.example.demo.src.domain.dto.user.modify;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PatchNameReq {
    @NotNull
    @Size(min = 1, max = 20)
    private String name;
}
