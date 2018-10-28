package com.cssiot.cssbase.modules.sys.data;

import com.cssiot.cssbase.modules.sys.entity.UserCityManage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 员工管理城市表信息
 * @author
 *	2018-10-05 athena 创建
 *	2018-10-28 Diego.zhou 重写equals以及toString
 */
@Data
@NoArgsConstructor
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
	
	@ApiModelProperty(value="景点名称")
	private String scenicSpotName;
	
	public UserCityManageModel(UserCityManage userCityManage) {
		super();
		this.userCityManageId = userCityManage.getId();
		this.userId = userCityManage.getUserId();
		this.province = userCityManage.getProvince();
		this.city = userCityManage.getCity();
		this.county = userCityManage.getCounty();
		this.scenicSpotId = userCityManage.getScenicSpotId();
	}

	@Override
	public String toString() {
		return "UserCityManageModel [province=" + province + ", city=" + city + ", county=" + county + ", scenicSpotId="
				+ scenicSpotId + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserCityManageModel other = (UserCityManageModel) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (county == null) {
			if (other.county != null)
				return false;
		} else if (!county.equals(other.county))
			return false;
		if (province == null) {
			if (other.province != null)
				return false;
		} else if (!province.equals(other.province))
			return false;
		if (scenicSpotId == null) {
			if (other.scenicSpotId != null)
				return false;
		} else if (!scenicSpotId.equals(other.scenicSpotId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((county == null) ? 0 : county.hashCode());
		result = prime * result + ((province == null) ? 0 : province.hashCode());
		result = prime * result + ((scenicSpotId == null) ? 0 : scenicSpotId.hashCode());
		return result;
	}
	
	
}