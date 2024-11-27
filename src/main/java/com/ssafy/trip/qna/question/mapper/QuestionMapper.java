package com.ssafy.trip.qna.question.mapper;

import com.ssafy.trip.qna.question.dto.request.QuestionDto;
import com.ssafy.trip.qna.question.dto.request.QuestionRegisterDto;
import com.ssafy.trip.qna.question.dto.response.QuestionResponse;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface QuestionMapper {
    @Select(value = "select q.*, u.name as username from questions q join user u on q.user_id = u.id")
    List<QuestionDto> selectAll();

    @Select(value = "select q.*, u.name as username from questions q join user u on q.user_id = u.id where q.id = #{id}")
    QuestionResponse selectById(Long id);

    @Insert(value = "insert into questions(user_id, title, category, body, image_url) values (#{userId}, #{title}, #{category}, #{body}, #{imageUrl})")
    void insert(QuestionRegisterDto questionRegisterDto);
}
