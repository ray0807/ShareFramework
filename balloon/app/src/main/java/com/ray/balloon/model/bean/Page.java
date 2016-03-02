package com.ray.balloon.model.bean;

import java.io.Serializable;

public class Page implements Serializable {

	private static final long serialVersionUID = 8203617782382556694L;
	
	/** 当前页码 **/
	public int pageNo;
	/** 总页数 **/
	public int pageCount;
	/** 每页个数 **/
	public int pageSize;
	/** 数据总个数 **/
	public int totalCount;

	public Page(int pageNo, int pagesize, int totalpage) {
		this.pageNo = pageNo;
		this.pageCount = totalpage;
		this.pageSize = pagesize;
	}

	public Page() {
	}

}
