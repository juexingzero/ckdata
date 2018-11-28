package com.manhui.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.model.Article;

@Mapper
public interface OrginArticleService {

	public int insertList(@Param("list")List<Article>list);
}
