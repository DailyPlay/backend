package com.example.haruplay.repository;

import com.example.haruplay.domain.User;
import com.example.haruplay.projection.UserInfoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    @Query(value = "SELECT u.user_email AS userEmail, u.nickname AS nickname, u.reg_dtm AS regTm FROM tb_user_info u",
            nativeQuery = true)
    List<UserInfoProjection> getUserInfo();
}
