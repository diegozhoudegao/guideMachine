package com.cssiot.cssbase.modules.sys.data;

import java.util.List;

import com.cssiot.cssbase.modules.sys.entity.User;
import com.cssiot.cssbase.modules.sys.entity.UserRoleCityManageInfo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 员工表信息
 * @author
 *	2018-10-05 athena 创建
 *	2018-10-28 Diego.zhou 增加员工管理城市以及角色
 */
@Data
@NoArgsConstructor
public class UserModel {
	
	@ApiModelProperty(value="员工id")
	private String userId;
	
	@Excel(name = "姓名", orderNum = "0")
	@ApiModelProperty(value="姓名")
	private String userName;
	
	@Excel(name = "账号", orderNum = "1")
	@ApiModelProperty(value="账号")
	private String loginName;
	
	@Excel(name = "手机号", orderNum = "2")
	@ApiModelProperty(value="手机")
	private String phone;

	@Excel(name = "密码", orderNum = "3")
	@ApiModelProperty(value="密码")
	private String password;
	
	@ApiModelProperty(value="角色Id")
	private String roleId;
	
	@ApiModelProperty(value="角色名称")
	private String roleName;

	@ApiModelProperty(value="管理城市集合")
	private List<UserCityManageModel> manageList;
	
	public UserModel(User user) {
		super();
		this.userId = user.getId();
		this.userName = user.getUserName();
		this.loginName = user.getLoginName();
		this.phone = user.getPhone();
		this.password = user.getPassword();
	}
	
	public UserModel(UserRoleCityManageInfo userInfo) {
		this.userId = userInfo.getId();
		this.userName = userInfo.getUserName();
		this.loginName = userInfo.getLoginName();
		this.phone = userInfo.getPhone();
		this.roleId = userInfo.getRoleId();
		this.roleName = userInfo.getRoleName();
//		this.password = userInfo.getPassword();
	}

}