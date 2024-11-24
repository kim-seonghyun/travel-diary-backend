package com.ssafy.trip.comment.mapper;

import com.ssafy.trip.comment.dto.entity.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface CommentMapper {
    @Insert("INSERT INTO comment (user_id, post_id, content) VALUES (#{userId}, #{postId}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void registerComment(Comment comment);
}
