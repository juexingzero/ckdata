package com.manhui.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.manhui.model.BaseRole;
import com.manhui.model.BaseRule;
import com.manhui.model.BaseUser;
import com.manhui.service.BaseRoleService;
import com.manhui.service.BaseRuleService;
import com.manhui.service.BaseUserService;


/**
 * @ClassName: SystemConfigController.java
 * @description:   系统设置控制器
 * @author: Jiangxiaosong
 * @date Create in 14:33 2018/1/27
 * @version: v1.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Controller
public class SystemConfigController {
	
	@Autowired
	private BaseUserService baseUserService;
	
	@Autowired
	private BaseRoleService baseRoleService;
	
	@Autowired
	private BaseRuleService baseRuleService;
	
	
	/**
	 * 管理员信息获取
	 * @param model
	 * @return
	 */
	@GetMapping("get_adminInfo")
	public String getAdminInfo(Model model,String flag){
		List<BaseRule> ruleList = new ArrayList<>();
		//获取管理员信息
		List<BaseRole> admin = baseRoleService.findRoleByName("admin");
		BaseRole role = admin.get(0);
		//获取到这些管理员的roleId
		Integer roleId = role.getId();
		//根据角色ID查询用户信息
		List<BaseUser> admins = baseUserService.findByRoleId(roleId);
		for(BaseUser user : admins){
			BaseRule rule = new BaseRule();
			rule.setUserId(user.getId());
			rule.setUsername(user.getUserName());
			rule.setPassword(user.getPassword());
			rule.setRoleId(role.getId());
			rule.setName(role.getName());
			rule.setDescr(role.getDescr());
			ruleList.add(rule);
		}
		if(flag != null && "update_sucess".equals(flag)){
			flag = "修改成功！";
		}else if(flag != null && "update_faild".equals(flag)){
			flag = "修改失败！";
		}else if(flag != null && "user_getfaild".equals(flag)){
			flag = "用户获取失败！";
		}
		
		model.addAttribute("rule", ruleList);
		model.addAttribute("flag", flag);
		return "pages/ManagerConfig/manager_list";
	}
	
	
	/**
	 * 修改管理员密码
	 * @param model
	 * @param rule
	 * @return
	 */
	@PostMapping("update_password")
	public String updatePassword(Model model,String userId,String password){
		String flag = "";
		if(!"".equals(userId) && !"".equals(password)){
			BaseUser user = baseUserService.findByUserId(Integer.parseInt(userId));
			user.setPassword(password);
			baseUserService.updateBaseUser(user);
			flag = "update_sucess";
		}else{
			flag = "update_faild";
		}
		return "redirect:get_adminInfo?flag="+flag;
	}
	
	
	/**
	 * 跳转到修改管理员密码
	 * @param model
	 * @param userId
	 * @return
	 */
	@GetMapping("to_edit_admin")
	public String toEditAdmin(Model model,String userId){
		String flag = "";
		if(userId != null && !"".equals(userId)){
			//查询需要回显的数据
			BaseUser user = baseUserService.findByUserId(Integer.parseInt(userId));
			List<BaseRole> rolelist = baseRoleService.findRoleByUserId(Integer.parseInt(userId));
			BaseRole role = rolelist.get(0);
			model.addAttribute("user", user);
			model.addAttribute("role", role);
			return "pages/ManagerConfig/manager_update";
		}else{
			flag = "user_getfaild";
			return "redirect:get_adminInfo?flag="+flag;
		}
	}
	
	
	/**
	 * 获取所有用户登录信息信息
	 * @param model
	 * @return
	 */
	@GetMapping("get_user_info")
	public String getUserInfo(Model model,String flag){
		List<BaseRule> rules = new ArrayList<>();
		//获取用户信息
		List<BaseUser> users = baseUserService.findBaseUser();
		if(users != null && users.size() > 0){
			for(BaseUser user : users){
				List<BaseRole> roles = baseRoleService.findRoleByUserId(user.getId());
				BaseRule rule = new BaseRule();
				rule.setRoleId(roles.get(0).getId());
				rule.setName(roles.get(0).getName());
				rule.setDescr(roles.get(0).getDescr());
				rule.setUserId(user.getId());
				rule.setUsername(user.getUserName());
				rule.setUserState(user.getState());
				rules.add(rule);
			}
		}
		if(flag != null && "stop_success".equals(flag)){
			flag = "用户停用成功！";
		}else if(flag != null && "stop_error".equals(flag)){
			flag = "用户停用失败！";
		}else if(flag != null && "enable_success".equals(flag)){
			flag = "用户启用成功！";
		}else if(flag != null && "enable_error".equals(flag)){
			flag = "用户启用失败！";
		}else if(flag != null && "delete_success".equals(flag)){
			flag = "用户删除成功！";
		}else if(flag != null && "delete_error".equals(flag)){
			flag = "用户删除失败！";
		}else if(flag != null && "add_success".equals(flag)){
			flag = "用户新增成功！";
		}else if(flag != null && "add_error".equals(flag)){
			flag = "用户新增失败！";
		}
		model.addAttribute("rule", rules);
		model.addAttribute("flag", flag);
		return "pages/ManagerConfig/user_list";
	}
	
	/**
	 * 停止用户
	 * @param model
	 * @param userId
	 * @return
	 */
	@GetMapping("stop_user")
	public String stopUser(Model model,String userId){
		String flag = "";
		if(userId != null && !"".equals(userId)){
			BaseUser user = new BaseUser();
			user.setId(Integer.parseInt(userId));
			user.setState(0);
			baseUserService.updateBaseUser(user);
			flag = "stop_success";
		}else{
			flag = "stop_error";
		}
		return "redirect:get_user_info?flag="+flag;
	}
	
	
	/**
	 * 启用用户
	 * @param model
	 * @param userId
	 * @return
	 */
	@GetMapping("enable_user")
	public String enableUser(Model model,String userId){
		String flag = "";
		if(userId != null && !"".equals(userId)){
			BaseUser user = new BaseUser();
			user.setId(Integer.parseInt(userId));
			user.setState(1);
			baseUserService.updateBaseUser(user);
			flag = "enable_success";
		}else{
			flag = "enable_error";
		}
		return "redirect:get_user_info?flag="+flag;
	}
	
	
	/**
	 * 删除用户
	 * @param model
	 * @param userId
	 * @return
	 */
	@GetMapping("delete_user")
	public String deleteUser(Model model,String userId){
		String flag = "";
		if(userId != null && !"".equals(userId)){
			baseUserService.deleteBaseUser(Integer.parseInt(userId));
			flag = "delete_success";
		}else{
			flag = "delete_error";
		}
		return "redirect:get_user_info?flag="+flag;
	}
	
	
	/**
	 * 跳转到新增用户
	 * @param model
	 * @return
	 */
	@GetMapping("to_add_user")
	public String toAddUser(Model model){
		//查询所有角色信息
		List<BaseRole> roles = baseRoleService.findBaseRole();
		model.addAttribute("roles", roles);
		return "pages/ManagerConfig/user_add";
	}
	
	
	/**
	 * 新增用户
	 * @param model
	 * @param user
	 * @param roleId
	 * @return
	 */
	@RequestMapping("add_user")
	public String addUser(Model model,BaseUser user,String roleId){
		String flag = "";
		if(user != null){
			//先录入用户信息
			Integer maxId = baseUserService.findBaseUserMaxId();
			user.setId(maxId+1);
			user.setState(1);
			baseUserService.insertBaseUser(user);
			//保存角色用户中间表
			BaseRule rule = new BaseRule();
			rule.setUserId(user.getId());
			rule.setRoleId(Integer.parseInt(roleId));
			baseRuleService.insertBaseRule(rule);
			flag = "add_success";
		}else{
			//用户未读取
			flag = "add_error";
		}
		return "redirect:get_user_info?flag="+flag;
	}
}
