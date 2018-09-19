package com.cssiot.cssutil.common.utils;

import java.io.File;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;
import org.apache.tools.ant.types.EnumeratedAttribute;

import com.cssiot.cssutil.common.constant.JdbcConstant;
import com.cssiot.cssutil.common.data.IsEnabledModel;

import lombok.extern.slf4j.Slf4j;

/**
 * 执行sql
 * @author
 *  2018-01-02 athena 迁移调整
 */
@Slf4j
public class AntSqlUtil {
	private static SQLExec sqlExec = null;
	private static String driverClassName = "";
	private static String jdbcUrl = "";
	private static String jdbcUsername = "";
	private static String jdbcPassword = "";

	/**
	 * 初始工具类的参数
	 * @param driverClassName 驱动类名称
	 * @param jdbcUrl 数据库地址
	 * @param jdbcUsername 数据库用户名
	 * @param jdbcPassword 数据库密码
	 * @author
	 * 	2018-01-02 athena 迁移调整
	 */
	public AntSqlUtil( String driverClassName, String jdbcUrl,
			String jdbcUsername, String jdbcPassword) {
		this.driverClassName = driverClassName;
		this.jdbcUrl = jdbcUrl;
		this.jdbcUsername = jdbcUsername;
		this.jdbcPassword = jdbcPassword;
//		if (null == sqlExec) {
		sqlExec = new SQLExec();
//		}
		sqlExec.setDriver(driverClassName);
		sqlExec.setUrl(jdbcUrl);
		sqlExec.setUserid(jdbcUsername);
		sqlExec.setPassword(jdbcPassword);
		sqlExec.setEncoding("utf-8");
	}

	public AntSqlUtil() {
		super();
	}
	
	/**
	 * 获取配置文件的内容
	 * @author
	 * 	2018-01-02 athena 迁移调整
	 */
	private static void GetPopertiesValue(){
		try {
			if ("".equals(driverClassName)) {
				driverClassName = JdbcConstant.driverClassName;
			}
			if ("".equals(jdbcUrl)) {
				jdbcUrl = JdbcConstant.jdbcUrl;
			}
			if ("".equals(jdbcUsername)) {
				jdbcUsername = JdbcConstant.jdbcUsername;

			}
			if ("".equals(jdbcPassword)) {
				jdbcPassword = JdbcConstant.jdbcPassword;
			}

//			if (null == sqlExec) {
			sqlExec = new SQLExec();
//			}
			sqlExec.setDriver(driverClassName);
			sqlExec.setUrl(jdbcUrl);
			sqlExec.setUserid(jdbcUsername);
			sqlExec.setPassword(jdbcPassword);
			sqlExec.setEncoding("utf-8");
		} catch (Exception e) {
			log.error("数据源配置文件获取失败={}",e);
		}
	}

	/**
	 * 执行sql脚本文件
	 * @param path sql脚本文件路径
	 * @author
	 * 	2018-01-02 athena 迁移调整
	 * 	2018-02-11 athena 增加返回信息
	 */
	public static IsEnabledModel runSql(String path) {
		IsEnabledModel isEnabledModel = new IsEnabledModel();
		File f = new File(path);
		if (!f.exists()) {
			log.error("sql文件不存在");
			isEnabledModel.setCode("4008");
			isEnabledModel.setMessage("sql文件不存在！");
			return isEnabledModel;
		}
		try {
			if(sqlExec!=null){
				sqlExec.setSrc(f);
				// continue继续, stop停止,abort中止，默认abort
				sqlExec.setOnerror((SQLExec.OnError) (EnumeratedAttribute
						.getInstance(SQLExec.OnError.class, "abort")));
				sqlExec.setPrint(true); //设置是否输出
				sqlExec.setProject(new Project());
				sqlExec.execute();
				isEnabledModel.setCode("1");
			}else{
				log.error("请设置数据源信息");
				isEnabledModel.setCode("4008");
				isEnabledModel.setMessage("请设置数据源信息！");
			}
		} catch (Exception e) {
			log.error("sql文件失败={}",e);
			isEnabledModel.setCode("4008");
			isEnabledModel.setMessage("sql文件失败="+e.getMessage());
		}
		return isEnabledModel;
	}
	
	/**
	 * 在默认数据库中执行sql脚本文件
	 * @param path sql脚本文件路径
	 * @author
	 * 	2018-01-02 athena 迁移调整
	 * 	2018-02-11 athena 增加返回信息
	 */
	public static IsEnabledModel runSqlByDefultProperties(String path) {
		IsEnabledModel isEnabledModel = new IsEnabledModel();
		File f = new File(path);
		if (!f.exists()) {
			log.error("sql文件不存在");
			isEnabledModel.setCode("4008");
			isEnabledModel.setMessage("sql文件不存在！");
			return isEnabledModel;
		}
		try {
			GetPopertiesValue();
			if(sqlExec!=null){
				sqlExec.setSrc(f);
				// continue继续, stop停止,abort中止，默认abort
				sqlExec.setOnerror((SQLExec.OnError) (EnumeratedAttribute
						.getInstance(SQLExec.OnError.class, "abort")));
				sqlExec.setPrint(true); //设置是否输出
				sqlExec.setProject(new Project());
				sqlExec.execute();
				isEnabledModel.setCode("1");
			}else{
				log.error("请设置数据源信息");
				isEnabledModel.setCode("4008");
				isEnabledModel.setMessage("请设置数据源信息！");
			}
		} catch (Exception e) {
			log.error("sql文件失败={}",e);
			isEnabledModel.setCode("4008");
			isEnabledModel.setMessage("sql文件失败="+e.getMessage());
		}
		return isEnabledModel;
	}

}
