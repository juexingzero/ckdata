package com.manhui.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.manhui.cacheManage.Cache;
import com.manhui.cacheManage.CacheManager;
import com.manhui.model.BaseUser;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * @ClassName: PlanResourcesUtil
 * @description: 查询计划资源相关信息
 * @author:	HeJiayan
 * @date Create in 下午5:50:43 2017年11月8日
 * @version: v1.0.0
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
public class PlanResourcesUtil {
	
	private static String flag;                //接口类别标识
	
	private static BaseUser user;
	
	
	public PlanResourcesUtil(BaseUser user) {
		this.user = user;
		putIntoCache();
	}
	
	/**
	 * @description: 查询所有机构部门信息
	 * @return 
	 * @throws
	 *//*
	private static List<Office> findAllOffice(){
		flag = "power";
		JSONObject json = new JSONObject();
		json.put("company_id", user.getCompanyId());  //重庆满惠
		json.put("user_id", user.getUserId());
		json.put("app_id", user.getAppId());
		json.put("app_key", user.getAppKey());
		json.put("token", user.getToken());
		
		String result = SendRequest.post(json, "/sys/out/getOffice", flag);
		JSONObject resultJson = (JSONObject) JSONValue.parse(result); 
		Integer respCode = (Integer) resultJson.get("respCode");
		
		List<Office> offices = new ArrayList<Office>();
		if(respCode == 1){
			//查询成功
			offices = getOfficeList(resultJson);
		}
		return offices;
	}*/
	
	/*public static List<BaseUser> findAllUser(){
		flag = "power";
		JSONObject json = new JSONObject();
		json.put("company_id", user.getCompanyId());  //重庆满惠
		json.put("user_id", user.getUserId());
		json.put("app_id", user.getAppId());
		json.put("app_key", user.getAppKey());
		json.put("token", user.getToken());
		
		String result = SendRequest.post(json, "/sys/out/getUsers", flag);
		JSONObject resultJson = (JSONObject) JSONValue.parse(result); 
		Integer respCode = (Integer) resultJson.get("respCode");
		
		List<User> users =  new ArrayList<User>();
		if(respCode == 1){
			//查询成功
			users = getUserList(resultJson);
		}
		return users;
	}*/
	
	/**
	 * @description: 查询所有流程
	 * @param 
	 * @return 
	 * @throws 
	 */
	/*private static List<Map> findAllCircuit() {
		flag = "workflow";
		JSONObject json = new JSONObject();
		json.put("user_id", user.getUserId());
		json.put("token", user.getToken());
		
		String result = SendRequest.post(json, "/act/taskApi/Particulars", flag);
		JSONObject resultJson = (JSONObject) JSONValue.parse(result); 
		Integer respCode = (Integer) resultJson.get("respCode");
		
		List<Map> circuits =  new ArrayList<Map>();
		if(respCode == 1){
			//查询成功
			circuits = getCircuitList(resultJson);
		}
		return circuits;
	}*/
	
	
	/**
	 * @description: 解析json，得到office集合
	 * @param json
	 * @return 
	 * @throws 
	 */
	/*private static List<Office> getOfficeList(JSONObject json) {
		JSONArray officeList = (JSONArray) ((JSONObject) json.get("data")).get("officeList");
		
		List<Office> list = new ArrayList<Office>();
		for (Object object : officeList) {
			JSONObject obj = (JSONObject) object;
			Office office = new Office();
			office.setId((String) obj.get("id"));
			office.setName((String) obj.get("name"));
			office.setParentId((String) obj.get("parentId"));
			office.setParentIds((String) obj.get("parentIds"));
			office.setGrade((String) obj.get("grade"));
			list.add(office);
		}
		String key = "officeList";
		Cache cache = CacheManager.getCacheInfo(key);
		if(cache == null){
			cache = new Cache();
			cache.setKey(key);
			cache.setValue(list);
			CacheManager.putCache(key, cache);
		}else{
			CacheManager.clearOnly(key);
			cache = new Cache();
			cache.setKey(key);
			cache.setValue(list);
			CacheManager.putCache(key, cache);
		}
		
		for (Office office : list) {
			System.out.println("部门ID:"+office.getId()+"             部门名称："+office.getName());
		}
		
		List<Office> newList = new ArrayList<Office>();
		for (Office office : list) {
			if("0".equals(office.getParentId())){
				List<Office> subList = new ArrayList<Office>();
				for (Office sub : list) {
					if(sub.getParentId().equals(office.getId())){
						List<Office> subSubList = new ArrayList<Office>();
						for (Office subSub : list) {
							if(subSub.getParentId().equals(sub.getId())){
								List<Office> subSubSubList = new ArrayList<Office>();
								for (Office subSubSub : list) {
									if(subSubSub.getParentId().equals(subSub.getId())){
										subSubSubList.add(subSubSub);
									}
								}
								Collections.reverse(subSubSubList);
								if(subSubSubList.size() > 0)
									subSub.setSubOffices(subSubSubList);
								subSubList.add(subSub);
							}
						}
						Collections.reverse(subSubList);
						if(subSubList.size() > 0)
							sub.setSubOffices(subSubList);
						subList.add(sub);
					}
				}
				Collections.reverse(subList);
				if(subList.size() > 0)
					office.setSubOffices(subList);
				newList.add(office);
			}
		}
		Collections.reverse(newList);
		return newList;
	}*/
	
	/**
	 * @description: 解析json，得到user集合
	 * @param json
	 * @return 
	 * @throws 
	 */
	/*private static List<BaseUser> getUserList(JSONObject json) {
		JSONArray userList = (JSONArray) ((JSONObject) json.get("data")).get("userList");
		
		List<BaseUser> list =  new ArrayList<BaseUser>();
		for (Object object : userList) {
			JSONObject obj = (JSONObject) object;
			BaseUser user = new BaseUser();
			user.setName((String) obj.get("name"));
			String postName = "";
			JSONArray postList = (JSONArray) obj.get("roleList");
			for (Object postObject : postList) {
				JSONObject postObj = (JSONObject) postObject;
				officeObj = (JSONObject) postObj.get("office");
				if(((String)officeObj.get("id")).equals(user.getOffice().getId())){
					postName += postObj.get("name") + "/";
				}
			}
			if(!"".equals(postName))
				user.setPostName(postName.substring(0, postName.lastIndexOf("/")));
			list.add(user);
		}
		Collections.reverse(list);
		return list;
	}*/
	
	/**
	 * @description: 解析json，得到 Circuit 集合
	 * @param @param resultJson
	 * @param @return
	 * @return 
	 * @throws 
	 */
	private static List<Map> getCircuitList(JSONObject json) {
		JSONArray circuitList = (JSONArray) ((JSONObject) json.get("data")).get("taskList");
		
		List<Map> list =  new ArrayList<Map>();
		for (Object object : circuitList) {
			JSONObject obj = (JSONObject) object;
			Map<String, Object> circuit =  new HashMap<String, Object>();
			circuit.put("id", obj.get("id"));
			circuit.put("name", obj.get("name"));
			circuit.put("procDefKey", obj.get("procDefKey"));
			circuit.put("version", obj.get("version"));
			circuit.put("dgrmResourceName", obj.get("dgrmResourceName"));
			
			list.add(circuit);
		}
		
		//list = getCircuitNode(list);
		return list;
	}
	
	/**
	 * @description: 查询每一个流程的流转节点
	 * @param list
	 * @param
	 * @return 
	 * @throws 
	 */
	/*private static List<Map> getCircuitNode(List<Map> list) {
		flag = "workflow";
		JSONObject json = new JSONObject();
		json.put("user_id", user.getUserId());
		json.put("token", user.getToken());
		json.put("procDefId", "");
		
		for (Map<String, Object> map : list) {
			json.replace("procDefId", map.get("id"));
			
			String result = SendRequest.post(json, "/act/taskApi/getAllNodeByProcDefId", flag);
			System.out.println(result);
			JSONObject resultJson = (JSONObject) JSONValue.parse(result); 
			Integer respCode = (Integer) resultJson.get("respCode");
			if(respCode == 1){
				JSONArray allNode = (JSONArray) ((JSONObject) json.get("data")).get("allNode");
				List<String> nodeNames = new ArrayList<String>();
				for (Object object : allNode) {
					JSONObject obj = (JSONObject) object;
					String nodeName = (String) ((JSONObject) obj.get("nodeName")).get("expressionText");
					if(nodeName != null && !"".equals(nodeName))
						nodeNames.add(nodeName);
				}
				
				map.put("nodeNames", nodeNames);
			}
		}
		return list;
	}*/

	/**
	 * @description: 获取所有部门以及部门员工
	 * @param 
	 * @return 
	 * @throws 
	 */
	/*private List<Office> getAllOfficeAndEmp() {
		List<Office> allOfficeAndEmp = this.allOffice;
		for (Office office : allOfficeAndEmp) {
			List<BaseUser> users = new ArrayList<BaseUser>();
			for (BaseUser user : allUser) {
				if(user.getOffice().getName().equals(office.getName()))
					users.add(user);
			}
			if(users != null && users.size() > 0)
				office.setEmpList(users);
			List<Office> subs = office.getSubOffices();
			if(subs != null && subs.size() > 0){
				for (Office sub : subs) {
					List<User> Uusers = new ArrayList<User>();
					for (User user : allUser) {
						if(user.getOffice().getName().equals(sub.getName()))
							Uusers.add(user);
					}
					if(Uusers != null && Uusers.size() > 0)
						sub.setEmpList(Uusers);
					List<Office> subSubs = sub.getSubOffices();
					if(subSubs != null && subSubs.size() > 0){
						for (Office subSub : subSubs) {
							List<User> Uuusers = new ArrayList<User>();
							for (User user : allUser) {
								if(user.getOffice().getName().equals(subSub.getName()))
									Uuusers.add(user);
							}
							if(Uuusers != null && Uuusers.size() > 0)
								subSub.setEmpList(Uuusers);
							List<Office> subSubSubs = subSub.getSubOffices();
							if(subSubSubs != null && subSubSubs.size() > 0){
								for (Office subSubSub : subSubSubs) {
									List<User> Uuuusers = new ArrayList<User>();
									for (User user : allUser) {
										if(user.getOffice().getName().equals(subSubSub.getName()))
											Uuuusers.add(user);
									}
									if(Uuuusers != null && Uuuusers.size() > 0)
										subSubSub.setEmpList(Uuuusers);
								}
							}
						}
					}
				}
			}
		}
		return allOfficeAndEmp;
	}*/
	
	/**
	 * @description: 存入缓存中
	 * @return 
	 * @throws 
	 */
	private static void putIntoCache() {
		String key = "";
		if(user != null){
			key = "user";
			Cache cache = CacheManager.getCacheInfo(key);
			if(cache == null){
				cache = new Cache();
				cache.setKey(key);
				cache.setValue(user);
				CacheManager.putCache(key, cache);
			}else{
				CacheManager.clearOnly(key);
				cache = new Cache();
				cache.setKey(key);
				cache.setValue(user);
				CacheManager.putCache(key, cache);
			}
		}
		
		/*if(allOffice != null && allOffice.size() > 0){
			key = "allOffice";
			Cache cache = CacheManager.getCacheInfo(key);
			if(cache == null){
				cache = new Cache();
				cache.setKey(key);
				cache.setValue(allOffice);
				CacheManager.putCache(key, cache);
			}else{
				CacheManager.clearOnly(key);
				cache = new Cache();
				cache.setKey(key);
				cache.setValue(allOffice);
				CacheManager.putCache(key, cache);
			}
		}*/
		
		/*if(allUser != null && allUser.size() > 0){
			key = "allUser";
			Cache cache = CacheManager.getCacheInfo(key);
			if(cache == null){
				cache = new Cache();
				cache.setKey(key);
				cache.setValue(allUser);
				CacheManager.putCache(key, cache);
			}else{
				CacheManager.clearOnly(key);
				cache = new Cache();
				cache.setKey(key);
				cache.setValue(allUser);
				CacheManager.putCache(key, cache);
			}
		}*/
		
		/*if(allCircuit != null && allCircuit.size() > 0){
			key = "allCircuit";
			Cache cache = CacheManager.getCacheInfo(key);
			if(cache == null){
				cache = new Cache();
				cache.setKey(key);
				cache.setValue(allCircuit);
				CacheManager.putCache(key, cache);
			}else{
				CacheManager.clearOnly(key);
				cache = new Cache();
				cache.setKey(key);
				cache.setValue(allCircuit);
				CacheManager.putCache(key, cache);
			}
		}*/
		
		/*if(allOfficeAndEmp != null && allOfficeAndEmp.size() > 0){
			key = "allOfficeAndEmp";
			Cache cache = CacheManager.getCacheInfo(key);
			if(cache == null){
				cache = new Cache();
				cache.setKey(key);
				cache.setValue(allOfficeAndEmp);
				CacheManager.putCache(key, cache);
			}else{
				CacheManager.clearOnly(key);
				cache = new Cache();
				cache.setKey(key);
				cache.setValue(allOfficeAndEmp);
				CacheManager.putCache(key, cache);
			}
		}*/
	}
}
