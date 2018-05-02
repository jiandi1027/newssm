package com.cjdjyf.newssm.base;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * @author : cjd
 * @description : 基准实体类
 * @date : 2018/4/24 11:18
 */
public abstract class DataEntity<T> implements Serializable
{
	private static final long serialVersionUID = 1L;
	/**主键 */
	private String id;
	/**创建人 */
	private String createPeople;
	/**创建时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	/**更新人 */
	private String updatePeople;
	/**更新时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss ", timezone = "GMT+8")
	private Date updateTime;
	/**删除标识 0/1 否/是 */
	private String delFlag = "0";

	/**排序字段*/
	private String sort;
	/**排序方式 asc/desc  */
	private String order;
	/**sort+order */
	private String orderStr ;

	/**页码 */
	private Integer page ;
	/**页长度 */
	private Integer rows ;

	/**登录账号的部门ID 用来查数据创建人部门在登录账号下的部门底下的数据 */
	private String LoginGroupId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreatePeople() {
		return createPeople;
	}

	public void setCreatePeople(String createPeople) {
		this.createPeople = createPeople;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdatePeople() {
		return updatePeople;
	}

	public void setUpdatePeople(String updatePeople) {
		this.updatePeople = updatePeople;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getOrderStr() {
		return orderStr;
	}

	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getLoginGroupId() {
		return LoginGroupId;
	}

	public void setLoginGroupId(String loginGroupId) {
		LoginGroupId = loginGroupId;
	}
}
