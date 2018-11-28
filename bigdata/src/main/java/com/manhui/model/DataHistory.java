package com.manhui.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据采集记录实体类
 * @author Administrator
 *
 */
public class DataHistory extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private Integer dataCount;//采集数据量
	private Integer channelId;//来源ID
	private Integer state;//状态 1进行中 2已结束
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getDataCount() {
		return dataCount;
	}
	public void setDataCount(Integer dataCount) {
		this.dataCount = dataCount;
	}
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "DataHistory [id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + ", dataCount="
				+ dataCount + ", channelId=" + channelId + ", state=" + state + "]";
	}

}
