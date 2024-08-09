package com.example.haruplay.user;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users WHERE id = #{id}")
    UserEntity findById(Long id);

    @Select("SELECT * FROM users")
    List<UserEntity> findAll();

    @Insert("INSERT INTO users(name, email) VALUES(#{name}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(UserEntity user);

    @Update("UPDATE users SET name = #{name}, email = #{email} WHERE id = #{id}")
    void update(UserEntity user);

    @Delete("DELETE FROM users WHERE id = #{id}")
    void delete(Long id);
}
