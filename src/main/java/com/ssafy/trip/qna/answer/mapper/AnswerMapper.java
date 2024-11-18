package com.ssafy.trip.qna.answer.mapper;

import com.ssafy.trip.qna.answer.dto.request.AnswerRegisterRequest;
import com.ssafy.trip.qna.answer.dto.response.AnswerResponse;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AnswerMapper {

    @Select(value = "select a.*, u.name as username from answers a join user as u on a.user_id = u.id where a.question_id = #{quesetionId}")
    List<AnswerResponse> selectByQuestionId(long questionId);

    @Insert(value = "insert into answers(question_id, user_id, body, image_url) values (#{questionId}, #{userId}, #{body}, #{imageUrl} )")
    void insert(AnswerRegisterRequest request);
}
