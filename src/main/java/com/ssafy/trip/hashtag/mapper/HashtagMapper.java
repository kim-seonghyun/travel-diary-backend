package com.ssafy.trip.hashtag.mapper;

import com.ssafy.trip.hashtag.dto.entity.Hashtag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface HashtagMapper {
    @Insert("insert into hashtag(tag) values (#{tag})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int regist(Hashtag request);

    @Delete("delete from hashtag where id = #{id}")
    void delete(Long id);
}
