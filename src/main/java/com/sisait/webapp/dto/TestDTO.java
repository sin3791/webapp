package com.sisait.webapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestDTO {
    private String userid;
    private String username;
    private int age;
    private String proName;
    private int price;

    // @Getter와 @Setter 덕분에 아래에 메서드를 안 적어도 자동으로 생성됩니다!
}