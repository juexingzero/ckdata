package com.manhui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: DayCategoryLogController.java
 * @description:   类目数据管理控制器
 * @author: WangSheng
 * @date Create in 11:33 2018/1/31
 * @version: v1.2
 * @modify By:	
 * @Copyright: 版权由满惠科技拥有
 */

@Controller
public class DayCategoryLogController {

	@RequestMapping("categorystatistical")
	public String categoryStatistical(){
		
		return "pages/DayCategoryLog/categorystatistical";
	}
	
}
