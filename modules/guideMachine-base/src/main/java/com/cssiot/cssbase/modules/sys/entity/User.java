package com.cssiot.cssbase.modules.sys.entity;

import javax.persistence.*;

import com.cssiot.cssbase.modules.sys.data.UserModel;
import com.cssiot.cssutil.common.entity.CommonFields;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 员工表
 * @author
 *	2018-10-05 athena 创建
 *	2018-10-19 athena 增加员工微信对应openId
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "t_sys_user")
public class User extends CommonFields{
	
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
	
	public User(UserModel user) {
		super();
		this.userName = user.getUserName();
		this.loginName = user.getLoginName();
		this.phone = user.getPhone();
		this.password = user.getPassword();
	}
}