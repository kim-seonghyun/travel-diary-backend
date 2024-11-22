package com.ssafy.trip.qna.answer.mapper;

import com.ssafy.trip.qna.answer.dto.entity.Answer;
import com.ssafy.trip.qna.answer.dto.response.AnswerResponse;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AnswerMapper {

    @Select(value = "select a.*, u.name as username from answers a join user as u on a.user_id = u.id where a.question_id = #{quesetionId}")
    List<AnswerResponse> selectByQuestionId(long questionId);

    @Insert(value = "insert into answers(question_id, user_id, body) values (#{questionId}, #{userId}, #{body} )")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Answer request);

    @Select(value = "select a.*, u.name as username from answers a join user as u on a.user_id = u.id where a.id = #{answerId}")
    AnswerResponse selectByAnswerId(long answerId);
}
