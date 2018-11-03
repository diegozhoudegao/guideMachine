package com.cssiot.cssbase.modules.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.cssiot.cssbase.modules.sys.data.UserCityManageModel;
import com.cssiot.cssutil.common.entity.CommonFields;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 员工管理城市表
 * @author
 *	2018-10-05 athena 创建
 */
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Table(name = "t_sys_userCityManage")
public class UserCityManage extends CommonFields{

	@ApiModelProperty(value="员工id")
	@Column(name="userId")
	private String userId;
	
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
	
	public UserCityManage(UserCityManageModel userCityManage) {
		super();
		this.userId = userCityManage.getUserId();
		this.province = userCityManage.getProvince();
		this.city = userCityManage.getCity();
		this.county = userCityManage.getCounty();
		this.scenicSpotId = userCityManage.getScenicSpotId();
	}
	
}