package com.sisait.webapp.domain;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DATA_ENTITY")

@Entity
@Data
public class DataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DATA_ID")
    private int id;

    @Column(nullable = false, length = 200)
    private String subject;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    @Column(columnDefinition = "int default 0")
    private int hit;

    @CreationTimestamp
    @Column(columnDefinition = "DATETIME default now()")
    private String writedate;


    @ManyToOne
    @JoinColumn(name= "JOINS_id")
    private JoinsEntity joinsEntity;



    //첨부파일 : 원글 삭제시 첨부파일도 ㅏㅈ동삭제 되도록 Cascade설정
    @OneToMany(
            mappedBy = "dataEntity", // 실제 연관관계의 주인은 FileEntity.dataEntity라는 의미
            cascade = CascadeType.ALL, // 부모인 DataEntity에서 수행한 작업을 자식 FileEntity에도 전달
            orphanRemoval = true// fileList에서 제거된 파일 엔티티를 DB에서도 삭제
    )

    @JsonManagedReference
    private List<FileEntity> fileList = new ArrayList<FileEntity>();

    //MultipartFile 객체를 request변수를 선언한다.
    @Transient //db에 field를 선언하지 않는다.
    List<MultipartFile> files;

}
