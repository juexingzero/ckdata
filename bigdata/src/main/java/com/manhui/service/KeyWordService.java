package com.manhui.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.model.KeyWord;

/**
 * @ClassName: KeyWordService
 * @description:热门关键词Service接口
 * @author: Jiangxiaosong
 * @date Create in 15:29 2018/3/13
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Mapper
public interface KeyWordService {

	//查询热门关键词
	List<KeyWord> findKeyWord();
	
}
