package com.manhui.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.manhui.model.Channel;

/**
 * @ClassName: ChannelService
 * @description:采集渠道Service 接口
 * @author: Jiangxiaosong
 * @date Create in 15:29 2018/1/31
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Mapper
public interface ChannelService {
	
	//分页用获取渠道总数
	int getChannelCount();
	
	//分页用获取渠道数据
	List<Channel> getChannelPage(Channel channel);
	
	//查询全部渠道数据
	List<Channel> getAllChannel();
	
	//根据id查询渠道数据
	Channel getChannelById(Integer id);
	
	//新增渠道数据
	void insertChannel(Channel channel);
	
	//删除渠道数据
	void deleteChannelById(Integer id);
}
