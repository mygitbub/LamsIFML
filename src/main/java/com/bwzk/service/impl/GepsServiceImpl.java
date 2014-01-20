package com.bwzk.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.classic.Logger;

import com.bwzk.dao.JdbcDao;
import com.bwzk.dao.MlJdbcDaoImpl;
import com.bwzk.pojo.GepsMappingBean;
import com.bwzk.service.i.GepsService;
@Service("gepsService")
public class GepsServiceImpl implements GepsService {

	public List<Map<String, Object>> projectList() {
		GepsMappingBean gepsMappingBean = getMappingList();
		System.out.println(gepsMappingBean.getPrjSql());
		System.out.println(gepsMappingBean.getVolSql());
		System.out.println(gepsMappingBean.getdFileSql());

		return mlJdbcDao.quertListMap("SELECT * FROM JGZLZJ_ML");
	}
	
	
	
	private GepsMappingBean getMappingList(){
		List<Map<String, Object>> codeMappingList = null;
		if(null == gepsMapping){
			gepsMapping = new GepsMappingBean();
			codeMappingList = jdbcDao.quertListMap("select F1 XH, F2 TABLENAME, F3 SQL from " + gepsCodeTableName);
			for (Map<String, Object> map : codeMappingList) {
				String theSql = map.get("SQL") == null ? "" : map.get("SQL").toString();
				Integer xhKey = map.get("XH") == null ? 0 : Integer.parseInt(map.get("XH").toString());
				String theTableName = map.get("TABLENAME") == null ? "" : map.get("TABLENAME").toString();
				switch (xhKey) {
					case 1:
						gepsMapping.setPrjTableName(theTableName);
						gepsMapping.setPrjSql(theSql);
						break;
					case 2:
						gepsMapping.setVolTableName(theTableName);
						gepsMapping.setVolSql(theSql);
						break;
					case 3:
						gepsMapping.setdFileTableName(theTableName);
						gepsMapping.setdFileSql(theSql);
						break;
					default:
						log.error("初始化geps和紫光档案系统 geps对应表 错误 ");
						break;
				}
			}
		}
		return gepsMapping;
	}

	@Transactional("txManager_ML")
	public Boolean updateXmStatus(Integer xmid){// test  1749501150
		Boolean flag = false;
		try {
			mlJdbcDao.update("UPDATE JGZLZJ_XM SET GEPSSTATE="+getsState+" WHERE XMID=" + xmid);// JGZLZJ_XM
			mlJdbcDao.update("UPDATE JGZLZJ_DWGC SET GEPSSTATE="+getsState+" WHERE XMID=" + xmid);// JGZLZJ_DWGC
			List<Map<String, Object>> dwgcList = mlJdbcDao.quertListMap("SELECT XMID,DWGCID FROM JGZLZJ_DWGC WHERE XMID=" + xmid);
			for (Map<String, Object> dwgc : dwgcList) {
				Integer dwgcId = dwgc.get("DWGCID") == null ? -1 : Integer.parseInt(dwgc.get("DWGCID").toString());
				mlJdbcDao.update("UPDATE JGZLZJ_ML SET GEPSSTATE="+getsState+" WHERE DWGCID=" + dwgcId);// JGZLZJ_ML
				List<Map<String, Object>> mlList = mlJdbcDao.quertListMap("SELECT MLID,DWGCID FROM JGZLZJ_ML WHERE DWGCID=" + dwgcId);
				for (Map<String, Object> ml : mlList) {
					Integer mlid = ml.get("MLID") == null ? -1 : Integer.parseInt(ml.get("MLID").toString());
					mlJdbcDao.update("UPDATE JGZLZJ_ZL SET GEPSSTATE="+getsState+" WHERE MLID=" + mlid);// JGZLZJ_ZL
					List<Map<String, Object>> zlList = mlJdbcDao.quertListMap("SELECT ZLID,MLID FROM JGZLZJ_ZL WHERE MLID=" + mlid);
					for (Map<String, Object> zl : zlList) {
						Integer zlid = zl.get("ZLID") == null ? -1 : Integer.parseInt(zl.get("ZLID").toString());
						mlJdbcDao.update("UPDATE JGZLZJ_FILE SET GEPSSTATE="+getsState+" WHERE ZLID=" + zlid);// JGZLZJ_FILE
					}
					
				}
			}
			flag = true;
		} catch (Exception e) {
			flag = false;
			log.error(e.getMessage() , e);
		}
		return flag;
	}
	
	@Autowired
	private JdbcDao jdbcDao;
	@Autowired
	private MlJdbcDaoImpl mlJdbcDao;
	
	@Autowired
	@Value("${unit.geps.codetable}")
	private String gepsCodeTableName;
	
	/** -1：本记录被删除；0：本记录是新增记录；>1：本记录更新；默认为0*/
	@Autowired
	@Value("${geps.gepsstate}")
	private Integer getsState;
	
	/** 对应描述字段*/
	private GepsMappingBean gepsMapping = null;
	private Logger log =  (Logger) LoggerFactory.getLogger(this.getClass());
}
