package com.bwzk.service.i;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: GepsService</p>
 * <p>Description: 广梦项目同步接口（读取中间数据库）</p>
 *
 * @author luyuwww
 * @date 2014年1月14日
 */
public interface GepsService {

	/**
	 * <p>Title： 列出所有待归档的数据</p>
	 * <p>Description: 从中间库中查询所有待归档的项目 [JGZLZJ_ML]</p>
	 * @return
	 * 
	 * @date 2014年1月14日
	*/
	public List<Map<String , Object>> projectList();
	public Boolean updateXmStatus(Integer xmid);

}
