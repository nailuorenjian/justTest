package com.example.batch5.mapper;

import com.example.batch5.dto.ResultDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QueryMapper {
    // 查询所有用户
    @Select("SELECT * FROM users")
    List<ResultDto> findAllUsers();
}
