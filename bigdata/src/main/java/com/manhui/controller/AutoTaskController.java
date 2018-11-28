package com.manhui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gargoylesoftware.htmlunit.History;
import com.manhui.model.AutoTask;
import com.manhui.model.Channel;
import com.manhui.model.DataHistory;
import com.manhui.service.AutoTaskService;
import com.manhui.service.ChannelService;
import com.manhui.service.DataHistoryService;

/**
 * @ClassName: AutoTaskController.java
 * @description:   自动任务控制器
 * @author: Jiangxiaosong
 * @date Create in 14:33 2018/3/10
 * @version: v1.2
 * @modify By:
 * @Copyright: 版权由满惠科技拥有
 */
@Controller
public class AutoTaskController {
	
	@Autowired
	private AutoTaskService autoTaskService;
	
	@Autowired
	private ChannelService channelService;
	@Autowired
	private DataHistoryService historyService;
	/**
	 * 查询自动任务信息
	 * @param model
	 * @return
	 */
	@RequestMapping("/autoTaskManager")
	public String autoTaskManager(Model model){
		List<AutoTask> atlist = autoTaskService.findAllAutoTask();
		if(atlist != null && atlist.size() > 0){
			for(AutoTask at : atlist){
				Integer channelId = at.getChannelId();
				Channel channel = channelService.getChannelById(channelId);
				at.setChannelName(channel.getName());
			}
		}
		model.addAttribute("atlist", atlist);
		return "pages/autotask/auto_task_list";
	}
	
	
	/**
	 * 跳转到新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/taskAdd")
	public String autoTaskAdd(Model model){
		//获取全部渠道数据
		List<Channel> channel = channelService.getAllChannel();
		model.addAttribute("channels", channel);
		return "pages/autotask/auto_task_add";
	}
	
	
	/**
	 * 任务保存
	 * @param model
	 * @param at
	 * @return
	 */
	@RequestMapping("/taskSave")
	public String autoTaskSave(Model model,AutoTask at){
		
		if(at != null){
			String taskurl = at.getTaskName();
			Integer state = 1;
			if(at.getOptions() != null && !"".equals(at.getOptions())){
			}else{
				at.setOptions("每天");
				if(at.getTaskTime() != null && !"".equals(at.getTaskTime())){
				}else{
					at.setTaskTime("0:00");
				}
			}
			at.setTaskUrl(taskurl);
			at.setState(state);
			autoTaskService.saveAutoTask(at);
		}
		
		return "redirect:autoTaskManager";
	}
	
	
	/**
	 * 任务修改跳转
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/taskedit")
	public String autoTaskEdit(Model model,String id){
		
		AutoTask at = new AutoTask();
		if(id != null && !"".equals(id)){
			Integer taskId = Integer.parseInt(id);
			at = autoTaskService.findAutoTaskByTaskId(taskId);
		}
		List<Channel> channel = channelService.getAllChannel();
		model.addAttribute("channels", channel);
		model.addAttribute("at", at);
		return "pages/autotask/auto_task_update";
	}
	
	
	/**
	 * 任务修改保存
	 * @param model
	 * @param at
	 * @return
	 */
	@RequestMapping("/taskupdate")
	public String autoTaskUpdate(Model model,AutoTask at,String id){
		
		if(at != null && id != null){
			at.setId(Integer.parseInt(id));
			autoTaskService.updateAutoTask(at);
		}
		return "redirect:autoTaskManager";
	}
	
	
	/**
	 * 任务删除
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/taskdelete")
	public String autoTaskDelete(Model model,String id){
		
		if(id != null && !"".equals(id)){
			autoTaskService.deleteAutoTask(Integer.parseInt(id));
		}
		return "redirect:autoTaskManager";
	}
	@RequestMapping("/getHistory")
	public String getHistory(Model model){
		List<DataHistory> list=historyService.getHistory();
		model.addAttribute("list",list);
		System.out.println(list);
		return "pages/autotask/auto_task_history";
	}
}
