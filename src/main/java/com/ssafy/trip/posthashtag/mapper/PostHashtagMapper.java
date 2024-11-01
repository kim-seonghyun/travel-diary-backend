package com.ssafy.trip.posthashtag.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostHashtagMapper {


    @Delete("delete from post_hashtag where post_id = #{postId}")
    void deleteByPostId(Long postId);
}
