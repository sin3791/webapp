package com.sisait.webapp.dto;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductDTO {
    private String proCode;
    private String proName;
    private String option;
    private int price;


}
