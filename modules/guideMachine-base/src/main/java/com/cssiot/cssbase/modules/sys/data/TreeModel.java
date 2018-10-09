package com.cssiot.cssbase.modules.sys.data;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 用于形成树菜单的Model
 * @author
 *	2018-10-09 athena 迁移
 */
@Data
public class TreeModel {
	
	private String F_ModuleId;				//number
	private String F_ParentId;				//parentNumber
	private String F_EnCode;				//content
	private String F_FullName;				//name
	private String F_Icon;					//icon
	private String F_UrlAddress;			//url
	private String F_Target;				//不是叶子节点为expand，叶子节点iframe
	private int F_IsMenu;				//isLeaf
	private int F_AllowExpand;			//默认1
	private int F_IsPublic;				//默认0
	private String F_AllowEdit;				//默认null
	private String F_AllowDelete;			//默认null
	private int F_SortCode;				//weight
	private int F_DeleteMark;			//默认0
	private int F_EnabledMark;			//默认1
	private String F_Description;			//name
	private String F_CreateDate;			//createTime
	private String F_CreateUserId;			//createUser
	private String F_CreateUserName;		//createUserName
	private String F_ModifyDate;			//lastUpdateTime
	private String F_ModifyUserId;			//lastUpdateUser
	private String F_ModifyUserName;		//lastUpdateUserName
	private String F_GroupId; 				 //分组id
	private String F_GroupDetailId;  		//分组明细id
	private String F_FormId;  				//表单id
	private String F_BillId;				//单据Id
	private String F_BillType;				//单据类型
	
	public TreeModel() {
		super();
	}

	public TreeModel(String f_ModuleId, String f_ParentId, String f_EnCode, String f_FullName, String f_Icon,
			String f_UrlAddress, String f_Target, int f_IsMenu, int f_AllowExpand, int f_IsPublic, String f_AllowEdit,
			String f_AllowDelete, int f_SortCode, int f_DeleteMark, int f_EnabledMark, String f_Description,
			String f_CreateDate, String f_CreateUserId, String f_CreateUserName, String f_ModifyDate,
			String f_ModifyUserId, String f_ModifyUserName) {
		super();
		F_ModuleId = f_ModuleId;
		F_ParentId = f_ParentId;
		F_EnCode = f_EnCode;
		F_FullName = f_FullName;
		F_Icon = f_Icon;
		F_UrlAddress = f_UrlAddress;
		F_Target = f_Target;
		F_IsMenu = f_IsMenu;
		F_AllowExpand = f_AllowExpand;
		F_IsPublic = f_IsPublic;
		F_AllowEdit = f_AllowEdit;
		F_AllowDelete = f_AllowDelete;
		F_SortCode = f_SortCode;
		F_DeleteMark = f_DeleteMark;
		F_EnabledMark = f_EnabledMark;
		F_Description = f_Description;
		F_CreateDate = f_CreateDate;
		F_CreateUserId = f_CreateUserId;
		F_CreateUserName = f_CreateUserName;
		F_ModifyDate = f_ModifyDate;
		F_ModifyUserId = f_ModifyUserId;
		F_ModifyUserName = f_ModifyUserName;
	}


	@JSONField(name = "F_ModuleId")
	public String getF_ModuleId() {
		return F_ModuleId;
	}

	public void setF_ModuleId(String f_ModuleId) {
		F_ModuleId = f_ModuleId;
	}

	@JSONField(name = "F_ParentId")
	public String getF_ParentId() {
		return F_ParentId;
	}

	public void setF_ParentId(String f_ParentId) {
		F_ParentId = f_ParentId;
	}

	@JSONField(name = "F_EnCode")
	public String getF_EnCode() {
		return F_EnCode;
	}

	public void setF_EnCode(String f_EnCode) {
		F_EnCode = f_EnCode;
	}

	@JSONField(name = "F_FullName")
	public String getF_FullName() {
		return F_FullName;
	}

	public void setF_FullName(String f_FullName) {
		F_FullName = f_FullName;
	}

	@JSONField(name = "F_Icon")
	public String getF_Icon() {
		return F_Icon;
	}

	public void setF_Icon(String f_Icon) {
		F_Icon = f_Icon;
	}

	@JSONField(name = "F_UrlAddress")
	public String getF_UrlAddress() {
		return F_UrlAddress;
	}

	public void setF_UrlAddress(String f_UrlAddress) {
		F_UrlAddress = f_UrlAddress;
	}

	@JSONField(name = "F_Target")
	public String getF_Target() {
		return F_Target;
	}

	public void setF_Target(String f_Target) {
		F_Target = f_Target;
	}

	@JSONField(name = "F_IsMenu")
	public int getF_IsMenu() {
		return F_IsMenu;
	}

	public void setF_IsMenu(int f_IsMenu) {
		F_IsMenu = f_IsMenu;
	}

	@JSONField(name = "F_AllowExpand")
	public int getF_AllowExpand() {
		return F_AllowExpand;
	}

	public void setF_AllowExpand(int f_AllowExpand) {
		F_AllowExpand = f_AllowExpand;
	}

	@JSONField(name = "F_IsPublic")
	public int getF_IsPublic() {
		return F_IsPublic;
	}

	public void setF_IsPublic(int f_IsPublic) {
		F_IsPublic = f_IsPublic;
	}

	@JSONField(name = "F_AllowEdit")
	public String getF_AllowEdit() {
		return F_AllowEdit;
	}

	public void setF_AllowEdit(String f_AllowEdit) {
		F_AllowEdit = f_AllowEdit;
	}

	@JSONField(name = "F_AllowDelete")
	public String getF_AllowDelete() {
		return F_AllowDelete;
	}

	public void setF_AllowDelete(String f_AllowDelete) {
		F_AllowDelete = f_AllowDelete;
	}

	@JSONField(name = "F_SortCode")
	public int getF_SortCode() {
		return F_SortCode;
	}

	public void setF_SortCode(int f_SortCode) {
		F_SortCode = f_SortCode;
	}

	@JSONField(name = "F_DeleteMark")
	public int getF_DeleteMark() {
		return F_DeleteMark;
	}

	public void setF_DeleteMark(int f_DeleteMark) {
		F_DeleteMark = f_DeleteMark;
	}

	@JSONField(name = "F_EnabledMark")
	public int getF_EnabledMark() {
		return F_EnabledMark;
	}

	public void setF_EnabledMark(int f_EnabledMark) {
		F_EnabledMark = f_EnabledMark;
	}

	@JSONField(name = "F_Description")
	public String getF_Description() {
		return F_Description;
	}

	public void setF_Description(String f_Description) {
		F_Description = f_Description;
	}

	@JSONField(name = "F_CreateDate")
	public String getF_CreateDate() {
		return F_CreateDate;
	}

	public void setF_CreateDate(String f_CreateDate) {
		F_CreateDate = f_CreateDate;
	}

	@JSONField(name = "F_CreateUserId")
	public String getF_CreateUserId() {
		return F_CreateUserId;
	}

	public void setF_CreateUserId(String f_CreateUserId) {
		F_CreateUserId = f_CreateUserId;
	}

	@JSONField(name = "F_CreateUserName")
	public String getF_CreateUserName() {
		return F_CreateUserName;
	}

	public void setF_CreateUserName(String f_CreateUserName) {
		F_CreateUserName = f_CreateUserName;
	}

	@JSONField(name = "F_ModifyDate")
	public String getF_ModifyDate() {
		return F_ModifyDate;
	}

	public void setF_ModifyDate(String f_ModifyDate) {
		F_ModifyDate = f_ModifyDate;
	}

	@JSONField(name = "F_ModifyUserId")
	public String getF_ModifyUserId() {
		return F_ModifyUserId;
	}

	public void setF_ModifyUserId(String f_ModifyUserId) {
		F_ModifyUserId = f_ModifyUserId;
	}

	@JSONField(name = "F_ModifyUserName")
	public String getF_ModifyUserName() {
		return F_ModifyUserName;
	}

	public void setF_ModifyUserName(String f_ModifyUserName) {
		F_ModifyUserName = f_ModifyUserName;
	}

	@JSONField(name = "F_GroupId")
	public String getF_GroupId() {
		return F_GroupId;
	}

	public void setF_GroupId(String f_GroupId) {
		F_GroupId = f_GroupId;
	}

	@JSONField(name = "F_GroupDetailId")
	public String getF_GroupDetailId() {
		return F_GroupDetailId;
	}

	public void setF_GroupDetailId(String f_GroupDetailId) {
		F_GroupDetailId = f_GroupDetailId;
	}

	@JSONField(name = "F_FormId")
	public String getF_FormId() {
		return F_FormId;
	}

	public void setF_FormId(String f_FormId) {
		F_FormId = f_FormId;
	}

	@JSONField(name = "F_BillId")
	public String getF_BillId() {
		return F_BillId;
	}

	public void setF_BillId(String f_BillId) {
		F_BillId = f_BillId;
	}

	@JSONField(name = "F_BillType")
	public String getF_BillType() {
		return F_BillType;
	}

	public void setF_BillType(String f_BillType) {
		F_BillType = f_BillType;
	}
}