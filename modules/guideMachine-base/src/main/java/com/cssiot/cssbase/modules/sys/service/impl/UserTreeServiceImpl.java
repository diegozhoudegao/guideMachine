package com.cssiot.cssbase.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cssiot.cssbase.modules.sys.dao.UserTreeDao;
import com.cssiot.cssbase.modules.sys.data.TreeModel;
import com.cssiot.cssbase.modules.sys.entity.UserTree;
import com.cssiot.cssbase.modules.sys.service.UserTreeService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.enums.ResultEnum;
import com.cssiot.cssutil.common.exception.ResultException;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;
import com.cssiot.cssutil.common.utils.ChkUtil;

/**
 * 员工树视图ServiceImpl
 * @author
 *	2018-10-07 athena 创建
 */
@Service
@Transactional
public class UserTreeServiceImpl extends BaseServiceImpl<UserTree> implements UserTreeService{
	
	@Override
	public BaseDao<UserTree> getBaseDao() {
		return userTreeDao;
	}
	
	@Autowired
	private UserTreeDao userTreeDao;
	
	/**
	 * 获取用户对应树菜单接口
	 * @param userId 用户名
	 * @param token 安全令牌
	 * @param clientType 客户端类型(android/ios/web等)
	 * @return treeText 树结构
	 * @author
	 *	2018-10-09 athena 迁移
	 */
	@SuppressWarnings("all")
	public Object getTreeText(String userId,String token,String clientType){
		Map map = new HashMap<>();
		List treeList = new ArrayList<>();
		String sqlTree = "from UserTree model where model.userId='"+userId+"' and model.permission.name!='root' and (model.permission.schemeId='' or model.permission.schemeId is null or model.permission.schemeId='2' or model.permission.schemeId='1') and model.permission.state in('0','4','6','7') order by model.permission.number,model.permission.weight";
		List<UserTree> userPermissions=new ArrayList<>();
		try {
			userPermissions = userTreeDao.findByHql(sqlTree);
		} catch (Exception e) {
			throw new ResultException(ResultEnum.DATA_ERROR,token,userId,clientType);
		}
		if(ChkUtil.isEmptyAllObject(userPermissions)){
			throw new ResultException(ResultEnum.USER_PERMERROR,token,userId,clientType);
		}
		for(int k=0;k<userPermissions.size();k++){
			UserTree row = userPermissions.get(k);
			TreeModel treeModel=new TreeModel();
			treeModel.setF_ModuleId(row.getPermission().getNumber());				//number
			treeModel.setF_ParentId(row.getPermission().getParentNumber());				//parentNumber
			treeModel.setF_EnCode(row.getPermission().getContent());				//content
			treeModel.setF_FullName(row.getPermission().getName());				//name
			treeModel.setF_Icon(row.getPermission().getIcon());					//icon
			treeModel.setF_UrlAddress(row.getPermission().getUrl());			//url
			if(row.getPermission().getIsLeaf().equals("0")){//不是叶子节点为expand
				treeModel.setF_Target("expand");				//不是叶子节点为expand，叶子节点iframe
				treeModel.setF_IsMenu(0);				//isLeaf
			}else{//叶子节点iframe
				treeModel.setF_Target("iframe");				//不是叶子节点为expand，叶子节点iframe
				treeModel.setF_IsMenu(1);				//isLeaf
			}
			treeModel.setF_AllowExpand(1);			//默认1
			treeModel.setF_IsPublic(0);				//默认0
			treeModel.setF_AllowEdit(null);				//默认null
			treeModel.setF_AllowDelete(null);			//默认null
			treeModel.setF_SortCode(row.getPermission().getWeight());				//weight
			treeModel.setF_DeleteMark(0);			//默认0
			treeModel.setF_EnabledMark(1);			//默认1
			treeModel.setF_Description(row.getPermission().getName());			//name
			treeModel.setF_CreateDate(null);			//createTime
			treeModel.setF_CreateUserId(null);			//createUser
			treeModel.setF_CreateUserName(null);		//createUserName
			treeModel.setF_ModifyDate(null);			//lastUpdateTime
			treeModel.setF_ModifyUserId(null);			//lastUpdateUser
			treeModel.setF_ModifyUserName(null);		//lastUpdateUserName
			treeModel.setF_FormId(row.getPermission().getContent());//formId
			treeList.add(treeModel);
		}
		map.put("treeText", treeList);
		return map;
	}
}