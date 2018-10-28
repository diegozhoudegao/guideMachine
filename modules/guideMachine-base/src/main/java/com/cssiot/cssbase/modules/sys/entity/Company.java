package com.cssiot.cssbase.modules.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cssiot.cssbase.modules.sys.data.CompanyModel;
import com.cssiot.cssutil.common.entity.CommonFields;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 公司表
 * @author
 *	2018-10-05 athena 创建
 */
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Table(name = "t_sys_company")
public class Company extends CommonFields{

	@ApiModelProperty(value="公司编码")
	@Column(name="companyNo")
	private String companyNo;
	
	@ApiModelProperty(value="上级公司编码")
	@Column(name="parentCompanyNo")
	private String parentCompanyNo;

	@ApiModelProperty(value="公司名称")
	@Column(name="companyName")
	private String companyName;
	
	@ApiModelProperty(value="省份")
	@Column(name="province")
	private String province;

	@ApiModelProperty(value="城市")
	@Column(name="city")
	private String city;
	
	@ApiModelProperty(value="区县")
	@Column(name="county")
	private String county;
	
	@ApiModelProperty(value="地点")
	@Column(name="address")
	private String address;
	
	@ApiModelProperty(value="联系电话")
	@Column(name="phone")
	private String phone;
	
	@ApiModelProperty(value="微信商户号")
	@Column(name="wechatNo")
	private String wechatNo;

	@ApiModelProperty(value="支付宝商户号")
	@Column(name="alipayNo")
	private String alipayNo;
	
	public Company(CompanyModel company) {
		super();
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