package com.sisait.webapp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.mapping.Join;

@Entity
@Data // getter+setter+toString+equals 가 있는 어노테이션
@NoArgsConstructor // 매개변수 없는 생성자
@AllArgsConstructor //매개변수 전체가 있는 생성자
@Table(name="BOARD_ENTITY")
public class BoardEntity {

    @Id // primary key 중복 x
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincrement
    @Column(name="BOARD_ID")
    private Integer id; // 일련번호

    @Column(nullable = false, length = 200)
    private String subject; // 제목필드

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content; //글 내용

    @Column(columnDefinition = "int default 0")
    private Integer hit; // 조회수

    @Column(nullable = false)
    private String ip; //글쓴이의 ip;

    @CreationTimestamp // 업데이트로 하면 @UpdateTimestamp : 수정시 날짜 변경
    @Column(columnDefinition = "DATETIME default now()")
    private String createDateTime; // 등록일 날짜 + 시간

    @UpdateTimestamp
    @Column(columnDefinition = "DATETIME default now()")
    private String updateDateTime;
    //글쓴이 : joins_entity의 join이 처리
    //회원정보 1명을 보관할 수 있는 entity변수로 저장
    //외래키 설정하기
    @ManyToOne //1:N
    @JoinColumn(name="JOINS_ID")
    private JoinsEntity joinsEntity;
}
