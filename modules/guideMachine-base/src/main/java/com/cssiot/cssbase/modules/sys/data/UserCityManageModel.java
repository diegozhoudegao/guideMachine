package com.cssiot.cssbase.modules.sys.data;

import com.cssiot.cssbase.modules.sys.entity.UserCityManage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 员工管理城市表信息
 * @author
 *	2018-10-05 athena 创建
 */
@Data
public class UserCityManageModel {
	
	@ApiModelProperty(value="员工管理城市id")
	private String userCityManageId;
	
	@ApiModelProperty(value="员工id")
	private String userId;
	
	@ApiModelProperty(value="省份")
	private String province;

	@ApiModelProperty(value="城市")
	private String city;
	
	@ApiModelProperty(value="区县")
	private String county;
	
	@ApiModelProperty(value="景点id")
	private String scenicSpotId;
	
	public UserCityManageModel(UserCityManage userCityManage) {
		super();
		this.userCityManageId = userCityManage.getId();
		this.userId = userCityManage.getUserId();
		this.province = userCityManage.getProvince();
		this.city = userCityManage.getCity();
		this.county = userCityManage.getCounty();
		this.scenicSpotId = userCityManage.getScenicSpotId();
	}
	
}