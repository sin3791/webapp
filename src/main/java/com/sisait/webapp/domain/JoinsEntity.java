package com.sisait.webapp.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
// 서버가 실행되면 데이터베이스에 테이블이 없으면 자동으로 테이블을 생성함
// dto, vo의 역할도 한다
// 테이블명은 클래스명으로 만들어지고
// @Table 어노테이션 사용하면 테이블이름을 원하는 이름으로 적용할 수 있음

@Table(name="joins_Entity")
// @setter, @getter, @toString, @equals == @data
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinsEntity {

    // 자동일련번호, 중복허용X, 모든 entity에는 id가 있어야한다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincrement
    //              컬럼명         null허용여부
    @Column(name="JOIN_ID", nullable = false)
    private Integer id;

    //      중복안함(unique), 자리수: 20, Null허용안함
    @Column(name="USERID",nullable = false, length=20, unique = true)
    private String userid;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = false, length = 20)
    private String username;
    //                          연락처 입력 안하면 010-0000-0000
    @Column(nullable = false, columnDefinition = "varchar(15) default '010-0000-0000'")
    private String tel;

    @Column(nullable = false, length = 45)
    private String email;

    // 등록일 : 자동으로 오늘날짜
    // UpdateTimestamp: insert, update일때 현재날짜와 시간으로 변경
    // CreationTimestamp : insert 날짜 시간정보 등록이 되고 수정일때 변경하지 않느다.
    @CreationTimestamp                                      //insert후 최초정보 변경안됨
    @Column(columnDefinition = "DATETIME default now()", updatable = false)
    private String writedate;
}
