package com.sisait.webapp.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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

}
