package com.cssiot.cssbase.modules.sys.data;

import com.cssiot.cssbase.modules.sys.entity.Company;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 公司表信息
 * @author
 *	2018-10-05 athena 创建
 */
@Data
public class CompanyModel {
	
	@ApiModelProperty(value="公司id")
	private String companyId;
	
	@ApiModelProperty(value="公司编码")
	private String companyNo;
	
	@ApiModelProperty(value="上级公司编码")
	private String parentCompanyNo;

	@ApiModelProperty(value="公司名称")
	private String companyName;
	
	@ApiModelProperty(value="省份")
	private String province;

	@ApiModelProperty(value="城市")
	private String city;
	
	@ApiModelProperty(value="区县")
	private String county;
	
	@ApiModelProperty(value="地点")
	private String address;
	
	@ApiModelProperty(value="联系电话")
	private String phone;
	
	@ApiModelProperty(value="微信商户号")
	private String wechatNo;

	@ApiModelProperty(value="支付宝商户号")
	private String alipayNo;

	public CompanyModel(Company company) {
		super();
		this.companyId = company.getId();
		this.companyNo = company.getCompanyNo();
		this.parentCompanyNo = company.getParentCompanyNo();
		this.companyName = company.getCompanyName();
		this.province = company.getProvince();
		this.city = company.getCity();
		this.county = company.getCounty();
		this.address = company.getAddress();
		this.phone = company.getPhone();
		this.wechatNo = company.getWechatNo();
		this.alipayNo = company.getAlipayNo();
	}

}