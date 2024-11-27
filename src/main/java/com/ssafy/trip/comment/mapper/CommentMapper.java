package com.ssafy.trip.comment.mapper;

import com.ssafy.trip.comment.dto.entity.Comment;
import com.ssafy.trip.comment.dto.response.CommentListResponse;
import com.ssafy.trip.comment.dto.response.CommentResponseDto;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentMapper {
    @Insert("INSERT INTO comment (user_id, post_id, content) VALUES (#{userId}, #{postId}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void registerComment(Comment comment);

    @Select("select * from comment where post_id = #{postId}")
    List<CommentResponseDto> selectByPostId(Long postId);

    @Select("select c.id, c.user_id, c.post_id, c.content, u.name as username , c.content, c.created_at as createdAt from comment c join user u on c.user_id = u.id where post_id = #{postId}")
    List<CommentListResponse> selectByPostIdIncludeUserName(Long postId);
}
