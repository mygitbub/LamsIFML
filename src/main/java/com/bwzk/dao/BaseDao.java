package com.bwzk.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseDao{
	/**
	 * 得到最大的did
	 */
	@Select("select max(did) from ${tableName}")
	Integer getMaxDid(@Param("tableName") String tableName);
	
	/**
	 * sqlserver得到数据库时间
	 */
	@Select("select getdate()")
	Date selectDateTimeForMSSQL();
	
	/**
	 * oracle得到数据库时间
	 */
	@Select("select SYSDATE from dual")
	Date selectDateTimeForOra();
	
	/**
	 * db2得到数据库时间
	 */
	@Select("select current timestamp from sysibm.sysdummy1")
	Date selectDateTimeForDB2();
	
	/**
	 * MySql得到数据库时间
	 */
	@Select("select now()")
	Date selectDateTimeForMySQL();
	
	/**
	 * h2得到数据库时间
	 */
	@Select("select CURRENT_TIMESTAMP")
	Date selectDateTimeForH2();
	
	/**
	 * copy表
	 * @param sName 源表
	 * @param tName 目标表
	 */
	@Insert("create table ${tName} as  (select * from ${sName})")
	void copyTable(@Param("sName") String sName , @Param("tName") String tName);
	@Insert("drop table ${tableName}")
	void dropTable(@Param("tableName") String tableName);
}
