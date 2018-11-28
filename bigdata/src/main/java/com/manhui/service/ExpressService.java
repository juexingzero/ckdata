package com.manhui.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.model.Express;
import com.manhui.util.PageBean;

/**
 * @ClassName: ExpressService
 * @description:快递物流方式信息Service接口
 * @author: Jiangxiaosong
 * @date Create in 15:29 2018/2/1
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Mapper
public interface ExpressService {
	
	//查询所有物流信息数量(分页)
	int findAllExpressCount();
	
	//查询所有物流信息(分页)
	List<Express> findAllExpressPage(Express express);
	
	//物流信息管理用的物流信息分页
	List<Express> getPageExpress(Express express);
	
	//查询所有物流信息
	List<Express> findAllExpress();
	
	//根据ID查询物流信息
	Express findExpressById(Integer id);
	
	//新增物流信息
	void insertExpress(Express express);
	
	//删除物流信息
	void deleteExpress(Integer id);
	
	//分组查询所有物流公司名称
	List<Map> selectExpressName();
	
	//修改物流信息
	void updateExpress(Express express);
	
	//删除物流信息，修改状态
	void deleteExpressState(Express express);
	
	//根据物流名称查询物流信息
	List<Express> findExpressByName(String name);
}
