package com.bwzk.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.classic.Logger;

import com.bwzk.dao.JdbcDao;
import com.bwzk.dao.MlJdbcDaoImpl;
import com.bwzk.pojo.GepsMappingBean;
import com.bwzk.service.BaseService;
import com.bwzk.service.i.GepsService;
import com.bwzk.util.GlobalFinalAttr;
@Service("gepsService")
public class GepsServiceImpl  extends BaseService  implements GepsService {
	public List<Map<String, Object>> projectList() {
		GepsMappingBean gepsMappingBean = getMappingList();

		List<Map<String, Object>> volMapList = null;
		List<Map<String, Object>> dFileMapList = null;
		List<Map<String, Object>> eFileMapList = null;
		//通过代码表的项目查询语句 查询geps中间库的 未归档的项目列表
		List<Map<String, Object>> prjMapList = mlJdbcDao.quertListMap(gepsMappingBean.getPrjSql());

		for (Map<String, Object> prjItem : prjMapList) {
			Integer xmid = getIntValue(prjItem, "MLDID");
			Integer mlPrjDid = getIntValue(prjItem, "MLDID");
			// 插入prjitem 然后查询案卷
			volMapList = mlJdbcDao.quertListMap(gepsMappingBean.getVolSql()+" WHERE MLPID=" + mlPrjDid);
			
			prjItem.put("PID", -1);
			Integer unisPrjDid = insertMap(gepsMappingBean.getPrjTableName(), prjItem);// -- 插入项目
			for (Map<String, Object> volItem : volMapList) {
				Integer mlVolDid = getIntValue(volItem, "MLDID");
				dFileMapList = mlJdbcDao.quertListMap(gepsMappingBean.getdFileSql()+" WHERE MLPID=" + mlVolDid);
				
				volItem.put("PID", unisPrjDid);
				Integer unisVolDid = insertMap(gepsMappingBean.getVolTableName(), volItem);// -- 插入案卷
				for (Map<String, Object> dFileItem : dFileMapList) {
					Integer mlDfileDid = getIntValue(dFileItem, "MLDID");
					eFileMapList = mlJdbcDao.quertListMap(gepsMappingBean.geteFileSql()+" WHERE MLPID=" + mlDfileDid);
					
					dFileItem.put("PID", unisVolDid);
					dFileItem.put("ATTACHED", eFileMapList.size() > 0 ? 1  : 0 );//判断是否有电子文件 0 没有 1有
					Integer unisDfileDid = insertMap(gepsMappingBean.getDfileTableName(), dFileItem);// -- 插入文件
					for (Map<String, Object> efileItem : eFileMapList) {
						efileItem.put("PID", unisDfileDid);
						insertMap(gepsMappingBean.getEfileTableName(), efileItem);// -- 插入电子文件
					}
				}
			}
			//添加一条项目 修改一条
			updateXmStatus(xmid);
			
		}
		dataReceive();
		return mlJdbcDao.quertListMap("SELECT * FROM TE_DAJC_AJMX");
	}
	/**
	 * 依次扫描视图中案卷级、文件级、电子文件级F_OTHER_STATE为0的条目，根据条目提供信息找到上级DID,然后实现集成
	 * 注：前提需在档案系统项目、案卷、文件、电子文件表中增加MLDID、MLPID两个字段；需将F_OTHER_STATE改为0
	 * 
	 */
	private void dataReceive(){
		
		GepsMappingBean gepsMappingBean = getMappingList();
		String dprjTableName = gepsMappingBean.getPrjTableName();
		String dvolSql = gepsMappingBean.getVolSql() + " WHERE  MLDID IN (SELECT F_ID FROM TE_DAJC_AJMX WHERE F_OTHER_STATE = 0)";
		List<Map<String, Object>> dvolMapList = mlJdbcDao.quertListMap(dvolSql);
		for(Map<String , Object> dvolMap : dvolMapList){
			Integer mldid = dvolMap.get("MLDID") == null ? -100 : Integer.parseInt(dvolMap.get("MLDID").toString());
			Integer mlpid = dvolMap.get("MLPID") == null ? -100 : Integer.parseInt(dvolMap.get("MLPID").toString());
			String getPrjDidSql = "select did from "+dprjTableName+" where mldid = "+mlpid+"";
			String stringDid = jdbcDao.query4String(getPrjDidSql);
			if(StringUtils.isNotBlank(stringDid)){
				Integer dprjDid = stringDid == null ? -100 : Integer.parseInt(stringDid);
				dvolMap.put("PID", dprjDid);
				insertMap(gepsMappingBean.getVolTableName(), dvolMap);// -- 插入案卷
				mlJdbcDao.update("UPDATE TE_DAJC_AJMX SET F_OTHER_STATE="+getsState+" WHERE F_ID=" + mldid);// TE_DAJC_AJMX
			}
		}
		String dvolTableName = gepsMappingBean.getVolTableName();
		String dfileSql = gepsMappingBean.getdFileSql() + " WHERE  MLDID IN (SELECT F_ID FROM TE_DAJC_JNML WHERE F_OTHER_STATE = 0)";
		List<Map<String, Object>> dfileMapList = mlJdbcDao.quertListMap(dfileSql);
		for(Map<String , Object> dfileMap : dfileMapList){
			Integer mldid = dfileMap.get("MLDID") == null ? -100 : Integer.parseInt(dfileMap.get("MLDID").toString());
			Integer mlpid = dfileMap.get("MLPID") == null ? -100 : Integer.parseInt(dfileMap.get("MLPID").toString());
			String getVolDidSql = "select did from "+dvolTableName+" where mldid = "+mlpid+"";
			String stringDid = jdbcDao.query4String(getVolDidSql);
			if(StringUtils.isNotBlank(stringDid)){
				Integer dvolDid = stringDid == null ? -100 : Integer.parseInt(stringDid);
				dfileMap.put("PID" , dvolDid);
				insertMap(gepsMappingBean.getDfileTableName(), dfileMap);// -- 插入文件
				mlJdbcDao.update("UPDATE TE_DAJC_JNML SET F_OTHER_STATE="+getsState+" WHERE F_ID=" + mldid);// TE_DAJC_JNML
			}
		}
		String dfileTableName = gepsMappingBean.getDfileTableName();
		String efileSql = gepsMappingBean.geteFileSql() + " WHERE  MLDID IN (SELECT F_ID FROM TE_DAJC_ZLMX WHERE F_OTHER_STATE = 0)";
		List<Map<String, Object>> efileMapList = mlJdbcDao.quertListMap(efileSql);
		for(Map<String , Object> efileMap : efileMapList){
			Integer mldid = efileMap.get("MLDID") == null ? -100 : Integer.parseInt(efileMap.get("MLDID").toString());
			Integer mlpid = efileMap.get("MLPID") == null ? -100 : Integer.parseInt(efileMap.get("MLPID").toString());
			String getFileDidSql = "select did from "+dfileTableName+" where mldid = "+mlpid+"";
			String stringDid = jdbcDao.query4String(getFileDidSql);
			if(StringUtils.isNotBlank(stringDid)){
				Integer dfileDid = stringDid == null ? -100 : Integer.parseInt(stringDid);
				efileMap.put("PID", dfileDid);
				insertMap(gepsMappingBean.getEfileTableName(), efileMap);// -- 插入电子文件
				String UpdateSql = "update "+dfileTableName+" set attached = 1 where did = "+dfileDid+"";
				execSql(UpdateSql);
				mlJdbcDao.update("UPDATE TE_DAJC_ZLMX SET F_OTHER_STATE="+getsState+" WHERE F_ID = " + mldid);// TE_DAJC_ZLMX
			}
		}
	}
	
	/**
	 * <p>Title: 得到Lams和geps的对应代码表的map</p>
	 * <p>项目级别(0) D_PRJ_LEVEL(0)<br>案卷级别(1) D_VOL_LEVEL(1)<br> 文件级别(2) D_FILE_LEVEL(2)<br> 电子原文级别(3) E_EFILE_LEVEL(3)
	 * </p>
	 * @return
	 * @date 2014年1月20日
	*/
	private GepsMappingBean getMappingList(){
		List<Map<String, Object>> codeMappingList = null;
		if(null == gepsMapping){
			try {
				gepsMapping = new GepsMappingBean();
				codeMappingList = jdbcDao.quertListMap("SELECT F1 LEVEL, F2 LIBCODE, F3 TSQL FROM " + gepsCodeTableName);
				for (Map<String, Object> map : codeMappingList) {
					Integer theLevel = map.get("LEVEL") == null ? -1 : Integer.parseInt(map.get("LEVEL").toString());
					Integer theLibCode = map.get("LIBCODE") == null ? -1 : Integer.parseInt(map.get("LIBCODE").toString());
					String theSql = map.get("TSQL") == null ? "" : map.get("TSQL").toString();
					gepsMapping.setLibcode(theLibCode);
					switch (theLevel) {
					case 0://prj level
						gepsMapping.setPrjSql(theSql);
						break;
					case 1://vol level
						gepsMapping.setVolSql(theSql);
						break;
					case 2://dFile level
						gepsMapping.setdFileSql(theSql);
						break;
					case 3://eFile level
						gepsMapping.seteFileSql(theSql);
						break;
					default:
						log.error("初始化geps和紫光档案系统 geps对应表 错误 ");
						break;
					}
				}
			} catch (Exception e) {
				log.error("初始化geps和紫光档案系统 geps对应表 错误 " + e.getMessage());
				gepsMapping = null;
			}
		}
		return gepsMapping;
	}

	@Transactional("txManager_ML")
	public Boolean updateXmStatus(Integer xmid){// test  1749501150
		Boolean flag = false;
		try {
			mlJdbcDao.update("UPDATE TE_DAJC_DAML SET F_OTHER_STATE="+getsState+" WHERE F_ID=" + xmid);// TE_DAJC_DAML
				List<Map<String, Object>> mlList = mlJdbcDao.quertListMap("SELECT F_ID FROM TE_DAJC_AJMX WHERE F_DAML_ID=" + xmid);
				for (Map<String, Object> ml : mlList) {
					Integer mlid = ml.get("F_ID") == null ? -1 : Integer.parseInt(ml.get("F_ID").toString());
					mlJdbcDao.update("UPDATE TE_DAJC_AJMX SET F_OTHER_STATE="+getsState+" WHERE F_ID=" + mlid);//TE_DAJC_AJMX
					List<Map<String, Object>> zlList = mlJdbcDao.quertListMap("SELECT F_ID FROM TE_DAJC_JNML WHERE F_AJMX_ID=" + mlid);
					for (Map<String, Object> zl : zlList) {
						Integer zlid = zl.get("F_ID") == null ? -1 : Integer.parseInt(zl.get("F_ID").toString());
						mlJdbcDao.update("UPDATE TE_DAJC_JNML SET F_OTHER_STATE="+getsState+" WHERE F_ID=" + zlid);// TE_DAJC_JNML
						List<Map<String, Object>> eList = mlJdbcDao.quertListMap("SELECT F_ID FROM TE_DAJC_ZLMX WHERE F_JNML_ID=" + mlid);
						for (Map<String, Object> el : eList) {
							Integer eid = el.get("F_ID") == null ? -1 : Integer.parseInt(el.get("F_ID").toString());
							mlJdbcDao.update("UPDATE TE_DAJC_ZLMX SET F_OTHER_STATE="+getsState+" WHERE F_ID=" + eid);// TE_DAJC_ZLMX
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
	
	/**
	 * <p>通过key的到map里面的值并且返回int</p>
	 * <p>Description: </p>
	 * @param mapItem 一个map
	 * @param key 
	 * @return 一个 int值 返回 -1 表示错误
	 * @date 2014年2月13日
	*/
	private Integer getIntValue(Map<String , Object> mapItem , String key){
		Integer tempInt = -1;
//		if(mapItem != null && mapItem.get(key) != null && mapItem.get(key) instanceof Integer){
			if(mapItem != null && mapItem.get(key) != null){
			try {
				tempInt = Integer.parseInt(mapItem.get(key).toString());
			} catch (Exception e) {
				tempInt = -1;
				log.error(e.getMessage() , e);
			}
		}
		return tempInt;
	}

	/**
	 * <p>将map插入 指定的表内</p>
	 * <p>Description: </p>
	 * @param tableName 表名
	 * @param valueMap Map对象
	 * @return
	 * 
	 * @date 2014年2月16日
	*/
	private Integer insertMap(String tableName , Map<String , Object> valueMap){
		Integer maxDid = -1;
		if(StringUtils.isNotEmpty(tableName) && valueMap != null){
			StringBuffer fields = new StringBuffer();
			StringBuffer values = new StringBuffer();
			StringBuffer insertSql = new StringBuffer("INSERT INTO ").append(tableName);
			maxDid = getMaxDid(tableName);
			try {
				Set<String> keySet = valueMap.keySet();
				for (String key : keySet) {
					Object theValue = valueMap.get(key);
					if(theValue != null){
//						System.out.println(theValue.getClass());
						fields.append(key).append(",");
						if(theValue instanceof Timestamp){
							values.append(GlobalFinalAttr.DatabaseType.SQLSERVER.generateTimeToSQLDate(theValue))
								.append(",");
						}else if(theValue instanceof Integer){
							values.append(theValue).append(",");
						}else{
							values.append("'").append(theValue).append("'").append(",");
						}
					}
				}
				fields.append("DID");
				values.append(maxDid);
				insertSql.append("(").append(fields.toString()).append(") VALUES (").append(values.toString()).append(")");
				System.out.println(insertSql.toString());
				jdbcDao.insert(insertSql.toString());
			} catch (Exception e) {
				log.error(e.getMessage() , e);
			}
			
		}
		return maxDid;
	}
	
	@Autowired
	private JdbcDao jdbcDao;
	@Autowired
	private MlJdbcDaoImpl mlJdbcDao;
	
	@Autowired
	@Value("${unis.geps.codetable}")
	private String gepsCodeTableName;
	
	/** -1：本记录被删除；0：本记录是新增记录；>1：本记录更新；默认为0*/
	@Autowired
	@Value("${geps.gepsstate}")
	private Integer getsState;
	
	/** 对应描述字段*/
	private GepsMappingBean gepsMapping = null;
	private Logger log =  (Logger) LoggerFactory.getLogger(this.getClass());
}
