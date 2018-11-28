package com.manhui.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.manhui.model.Company;
import com.manhui.util.PageBean;

/**
 * 
 * <p>Title: CompanyService</p>
 * @author LiuCheng
 */
@Mapper
public interface CompanyService {

	//显示公司列表
	List<Company> getCompanyList(Company company); 
	//分页显示公司列表
	List<Company> getPageCompany(Company company);
	//新增公司信息
	int insertCompany(Company company);
	//修改公司信息
	int updateCompany(Company company);
	//查看公司详细信息
	Company selectById(Company company);
	//删除公司信息
	int deleteCompany(Company comapny);
	//查询公司数量
	int getCompanyCount();
	//移动端查询公司信息
	List<Company> getAllCompany();
	//根据公司id查询详细信息
	Company getCompanyById(Integer id);
	//根据公司名称模糊查询
	List<Company> getCompanyByName(String name,Integer page);
	//查询id最大的公司信息
	Integer getCompanyMaxID();
}
