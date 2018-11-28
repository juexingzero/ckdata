package com.manhui.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.model.Shop;

@Mapper
public interface OrginShopService {
	public int insertList(@Param("list") List<Shop> list);
}
