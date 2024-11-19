package com.example.haruplay.Diary.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_diary_info")
public class Diary {

    @Id
    @Column(name = "diary_id")
    private Integer diaryId;

    @Column(name = "user_id")
    private String userId;


}
