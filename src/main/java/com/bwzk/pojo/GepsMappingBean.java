package com.bwzk.pojo;

import java.io.Serializable;

/**
 * <p>Title: GepsMappingBean 描述代码表</p>
 * <p>Description: 梦龙的geps和档案系统映射的mapping</p>
 *
 * @author luyuwww
 * @date 2014年1月20日
 */
public class GepsMappingBean implements Serializable{
	private static final long serialVersionUID = 2219337505528263682L;
	private Integer libcode;
	private String prjSql;
	private String volSql;
	private String dFileSql;
	private String eFileSql;
	public Integer getLibcode() {
		return libcode;
	}
	public void setLibcode(Integer libcode) {
		this.libcode = libcode;
	}
	public String getPrjSql() {
		return prjSql;
	}
	public void setPrjSql(String prjSql) {
		this.prjSql = prjSql;
	}
	public String getVolSql() {
		return volSql;
	}
	public void setVolSql(String volSql) {
		this.volSql = volSql;
	}
	public String getdFileSql() {
		return dFileSql;
	}
	public void setdFileSql(String dFileSql) {
		this.dFileSql = dFileSql;
	}
	public String geteFileSql() {
		return eFileSql;
	}
	public void seteFileSql(String eFileSql) {
		this.eFileSql = eFileSql;
	}
	
	
	
	
}
