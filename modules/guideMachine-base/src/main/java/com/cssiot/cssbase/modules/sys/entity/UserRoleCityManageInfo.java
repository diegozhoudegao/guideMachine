package com.cssiot.cssbase.modules.sys.entity;

import java.util.Date;

import javax.persistence.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 员工角色以及管理城市景点视图
 * @author
 *	2018-10-28 Diego.zhou 创建
 */
@Entity
@Data
@Table(name = "v_sys_userRoleCityManageInfo")
public class UserRoleCityManageInfo {
	
	@Id
	@ApiModelProperty(value="员工id")
	@Column(name="id")
	private String id;
	
	@ApiModelProperty(value="姓名")
	@Column(name="userName")
	private String userName;
	
	@ApiModelProperty(value="账号")
	@Column(name="loginName")
	private String loginName;
	
	@ApiModelProperty(value="手机")
	@Column(name="phone")
	private String phone;

	@ApiModelProperty(value="密码")
	@Column(name="password")
	private String password;
	
	@ApiModelProperty(value="员工微信对应openId")
	@Column(name="openId")
	private String openId;
	
	@Column(name="createUser")
	private String createUser;
	
	@Column(name="createTime")
	private Date createTime;
	
	@Column(name="lastUpdateUser")
	private String lastUpdateUser;
	
	@Column(name="lastUpdateTime")
	private Date lastUpdateTime;
	
	@Column(name="status")
	private String status;
	
	@ApiModelProperty(value="角色Id")
	@Column(name="roleId")
	private String roleId;
	
	@ApiModelProperty(value="角色名称")
	@Column(name="roleName")
	private String roleName;
	
	@ApiModelProperty(value="省份")
	@Column(name="province")
	private String province;

	@ApiModelProperty(value="城市")
	@Column(name="city")
	private String city;
	
	@ApiModelProperty(value="区县")
	@Column(name="county")
	private String county;
	
	@ApiModelProperty(value="景点id")
	@Column(name="scenicSpotId")
	private String scenicSpotId;
	
	@ApiModelProperty(value="景点名称")
	@Column(name="scenicSpotName")
	private String scenicSpotName;
}