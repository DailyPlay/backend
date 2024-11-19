package com.example.haruplay.User.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "tb_user_info")
public class User {

    @Id
    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "sns_type")
    private String snsType;

    private String nickname;

    @Column(name = "reg_dtm")
    private LocalDateTime regDtm;

    @Column(name = "withdraw_dtm")
    private LocalDateTime withdrawDtm;

    @Column(name = "upd_dtm")
    private LocalDateTime updDtm;

    @Column(name = "withdraw_reason")
    private String withdrawReason;

    @Column(name = "recommend_dtm")
    private LocalDateTime recommendDtm;
}
