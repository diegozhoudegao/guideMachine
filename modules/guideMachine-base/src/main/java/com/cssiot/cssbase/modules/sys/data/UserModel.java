package com.cssiot.cssbase.modules.sys.data;

import com.cssiot.cssbase.modules.sys.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 员工表信息
 * @author
 *	2018-10-05 athena 创建
 */
@Data
public class UserModel {
	
	@ApiModelProperty(value="员工id")
	private String userId;
	
	@ApiModelProperty(value="姓名")
	private String userName;
	
	@ApiModelProperty(value="账号")
	private String loginName;
	
	@ApiModelProperty(value="手机")
	private String phone;

	@ApiModelProperty(value="密码")
	private String password;

	public UserModel(User user) {
		super();
		this.userId = user.getId();
		this.userName = user.getUserName();
		this.loginName = user.getLoginName();
		this.phone = user.getPhone();
		this.password = user.getPassword();
	}

}