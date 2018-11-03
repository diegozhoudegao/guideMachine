package com.cssiot.cssbase.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cssiot.cssbase.modules.sys.dao.RoleDao;
import com.cssiot.cssbase.modules.sys.data.PermissionCheckedModel;
import com.cssiot.cssbase.modules.sys.data.PermissionModel;
import com.cssiot.cssbase.modules.sys.data.RoleModel;
import com.cssiot.cssbase.modules.sys.data.RolePermissionInfo;
import com.cssiot.cssbase.modules.sys.data.RolePermissionModel;
import com.cssiot.cssbase.modules.sys.data.UserModel;
import com.cssiot.cssbase.modules.sys.entity.Permission;
import com.cssiot.cssbase.modules.sys.entity.PermissionDivide;
import com.cssiot.cssbase.modules.sys.entity.Role;
import com.cssiot.cssbase.modules.sys.entity.RolePermission;
import com.cssiot.cssbase.modules.sys.entity.RolePermissionDivide;
import com.cssiot.cssbase.modules.sys.entity.UserRole;
import com.cssiot.cssbase.modules.sys.entity.UserRoleCityManageInfo;
import com.cssiot.cssbase.modules.sys.service.PermissionDivideService;
import com.cssiot.cssbase.modules.sys.service.PermissionService;
import com.cssiot.cssbase.modules.sys.service.RolePermissionDivideService;
import com.cssiot.cssbase.modules.sys.service.RolePermissionService;
import com.cssiot.cssbase.modules.sys.service.RoleService;
import com.cssiot.cssbase.modules.sys.service.UserRoleService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.enums.ResultEnum;
import com.cssiot.cssutil.common.enums.StateEnum;
import com.cssiot.cssutil.common.exception.ResultException;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;
import com.cssiot.cssutil.common.utils.ChkUtil;
import com.cssiot.cssutil.common.utils.Encodes;
import com.cssiot.cssutil.common.utils.Page;
import com.cssiot.cssutil.common.utils.Parameter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.vavr.control.Try;

/**
 * 角色表ServiceImpl
 * @author
 *	2018-10-07 athena 创建
 *	2018-11-01 Diego.zhou 修改
 */
@Service
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService{
	
	@Override
	public BaseDao<Role> getBaseDao() {
		return roleDao;
	}
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private RolePermissionService rolePermissionService;
	
	@Autowired
	private RolePermissionDivideService rolePermissionDivideService;
	
	@Autowired
	private PermissionDivideService permissionDivideService;
	
	@Autowired
	private UserRoleService userRoleService;
	
	/**
	 * 角色保存初始化接口
	 * @param roleId 角色Id
	 * @param userId 当前登录者Id
	 * @param token token令牌
	 * @return
	 * @author 
	 * 	2018-11-1 Diego.zhou 新建
	 */
	@Override
	@SuppressWarnings("all")
	public Object doSaveRoleData(String roleId,String userId,String token){
		Map map = new HashMap<>();
		try{
			List<String> perIds = new ArrayList<String>();
			List<String> dataIds = new ArrayList<String>();
			RolePermissionInfo model = new RolePermissionInfo();
			//初始化角色赋权限下拉菜单，已有权限勾选
			List<Permission> permissionlist = permissionService.findByHql("from Permission where status <>'1'");
			if(!ChkUtil.isEmptyAllObject(roleId)) {
				Role role = roleDao.get(roleId);
				if(ChkUtil.isEmptyAllObject(role)){
					throw new ResultException(ResultEnum.DATA_NULL,token,userId,null);
				}
				model.setRoleId(roleId);
				model.setRoleName(role.getRoleName());
				List<RolePermission> rolePermissionlist = rolePermissionService.findByHql("from RolePermission where roleId='"+roleId+"' and status='0'");
				if(null != rolePermissionlist && rolePermissionlist.size() >0){
					for(RolePermission rolePermission:rolePermissionlist){
						perIds.add(new String(rolePermission.getPermissionId()));
						List<RolePermissionDivide> rolePermissionDividelist = rolePermissionDivideService.findByHql("from RolePermissionDivide where rolePermissionId='"+rolePermission.getId()+"' and status='0'");
						if(null != rolePermissionDividelist && rolePermissionDividelist.size() >0){
							for(RolePermissionDivide rolePermissionDivide:rolePermissionDividelist){
								dataIds.add(new String(rolePermissionDivide.getKeyNameId()));
							}
						}
					}
				}
			}
			PermissionCheckedModel permissionModel = new PermissionCheckedModel();
			List modellist = new ArrayList<>();
			if(null == permissionlist || permissionlist.size() <=0){
				permissionModel.setId("1");
				permissionModel.setPId("0");
				permissionModel.setName(ResultEnum.DATA_NULL.getMessage());
				modellist.add(permissionModel);
			}else{
				for(Permission permission:permissionlist){
					permissionModel = new PermissionCheckedModel();
					permissionModel.setId(permission.getNumber());
					permissionModel.setPId(permission.getParentNumber());
					permissionModel.setName(permission.getName());
					permissionModel.setPermissionId(permission.getId());
					permissionModel.setChecked(perIds.contains(permission.getId())?true:false);
					modellist.add(permissionModel);
					String context=permission.getContent();
					List<PermissionDivide> permissionDividelist = permissionDivideService.findByHql("from PermissionDivide where status='0' and content='"+context+"' and module='permission'");
					if(null!=permissionDividelist && permissionDividelist.size()>0){
						for(PermissionDivide permissionDivide:permissionDividelist){
							permissionModel = new PermissionCheckedModel();
							permissionModel.setId(permissionDivide.getId());
							permissionModel.setPId(permission.getNumber());
							permissionModel.setName(permissionDivide.getName());
							permissionModel.setPermissionId(permission.getId()+";;"+permissionDivide.getId());
							permissionModel.setChecked(dataIds.contains(permissionDivide.getId())?true:false);
							modellist.add(permissionModel);
						}
					}
				}
			}
			model.setPermissionList(modellist);
			map.put("rolePermission", model);
		} catch(ResultException e){ 
			throw e;
		} catch (Exception e) {
			throw new ResultException(-3, e.getMessage(), token, userId, null);
		}
		return map;
	}


	/**
	 * 角色保存接口
	 * @param roleId 角色Id
	 * @param userId 当前登录者Id
	 * @param token token令牌
	 * @return
	 * @author 
	 * 	2018-11-1 Diego.zhou 新建
	 */
	@Override
	public Object doSaveRoleInfo(String jsonStr, String userId, String token) {
		//json校验
		if(ChkUtil.isEmptyAllObject(jsonStr)) {
			throw new ResultException(ResultEnum.JSONSTR_NULL, token, userId, null);
		}
		//解析json
		JSONObject json = new JSONObject();
		try{
			json=(JSONObject) JSON.parse(Encodes.unescapeHtml(jsonStr));
		}catch(Exception e){
			throw new ResultException(ResultEnum.FORMAT_ERROR, token, userId, null);
		}
		//json转模型
		Gson gson = new Gson();
		RolePermissionInfo dataModel = gson.fromJson(json.getString("dataModel"),new TypeToken<List<RolePermissionInfo>>(){}.getType());
		if(null == dataModel) {
			throw new ResultException(-2, "数据为空！", token, userId, null);
		}
		//名称必填校验
		Map nameMap = ChkUtil.fieldCheck(dataModel.getRoleName(),true,20, "角色名称","");
		if(ChkUtil.isEmptyAllObject(nameMap)){
			throw new ResultException(ResultEnum.DATA_ERROR,token,userId, null);
		}
		if(!nameMap.get("code").equals("1")){
			throw new ResultException(-2, nameMap.get("message").toString(), token, userId, null);
		}
		try {
			//判断新增还是修改
			if(ChkUtil.isEmptyAllObject(dataModel.getRoleId())) {//新增
				//判断是否已存在名为roleName的角色
				Role existRole = roleDao.getByHql("from Role where status<>'"+StateEnum.DELETESTATE.getCode()+"' and roleName='"+dataModel.getRoleName()+"'");
				if(null != existRole) {
					throw new ResultException(-2, "已存在名称为："+dataModel.getRoleName()+"的角色数据！", token, userId, null);
				}
				//保存数据
				Role role = new Role();
				role.setRoleName(dataModel.getRoleName());
				roleDao.save(role);
				//判断对应操作是否为空，不为空新增保存
				if(!ChkUtil.isEmptyAllObject(dataModel.getPermissionList())) {
					RolePermission row = null;
					String permissionId = null;
					String permissionDivideId = null;
					String rolePermissionId = null;
					for(PermissionCheckedModel model : dataModel.getPermissionList()) {
						if(model.getPermissionId().contains(";;")){
							permissionId = model.getPermissionId().split(";;")[0];
							permissionDivideId = model.getPermissionId().split(";;")[1];
							RolePermission rolePermissionmx = rolePermissionService.getByHql("from RolePermission where roleId='"+role.getId()+"' and permissionId='"+permissionId+"' and status<>'1'");
							if(null==rolePermissionmx){
								row = new RolePermission();
								row.setCreateUser(userId);
								row.setCreateTime(new Date());
								row.setLastUpdateUser(userId);
								row.setLastUpdateTime(new Date());
								row.setStatus("0");
								row.setRoleId(role.getId());
								row.setPermissionId(permissionId);
								rolePermissionService.save(row);
								rolePermissionId=row.getId();
							}else{
								rolePermissionId=rolePermissionmx.getId();
							}
							RolePermissionDivide rolePermissionDivide=new RolePermissionDivide();
							rolePermissionDivide.setRolePermissionId(rolePermissionId);
							rolePermissionDivide.setKeyNameId(permissionDivideId);
							rolePermissionDivide.setName(model.getName());
							rolePermissionDivide.setCreateUser(userId);
							rolePermissionDivide.setCreateTime(new Date());
							rolePermissionDivide.setLastUpdateUser(userId);
							rolePermissionDivide.setLastUpdateTime(new Date());
							rolePermissionDivide.setStatus("0");
							rolePermissionDivideService.save(rolePermissionDivide);
						}else{
							RolePermission rolePermissionmx = rolePermissionService.getByHql("from RolePermission where roleId='"+role.getId()+"' and permissionId='"+model.getPermissionId()+"' and status='0'");
							if(null==rolePermissionmx){
								row = new RolePermission();
								row.setCreateUser(userId);
								row.setCreateTime(new Date());
								row.setLastUpdateUser(userId);
								row.setLastUpdateTime(new Date());
								row.setStatus("0");
								row.setRoleId(role.getId());
								row.setPermissionId(model.getPermissionId());
								rolePermissionService.save(row);
							}
						}
					}
				}
			}else {//修改
				//判断是否已存在名为roleName的角色
				Role existRole = roleDao.getByHql("from Role where status<>'"+StateEnum.DELETESTATE.getCode()+"' and roleName='"+dataModel.getRoleName()+"' and id<>'"+dataModel.getRoleId()+"'");
				if(null != existRole) {
					throw new ResultException(-2, "已存在名称为："+dataModel.getRoleName()+"的角色数据！", token, userId, null);
				}
				//获取数据中被选中的数据集合（修改时）
				if(!ChkUtil.isEmptyAllObject(dataModel.getPermissionList())) {//不为空
					List<PermissionCheckedModel> addList = new ArrayList<>();//新增的集合
					List<String> delPermissionList = new ArrayList<>();//删除的权限集合
					List<String> delPermissionDivideList = new ArrayList<>();//删除的权限操作集合
					String roleId = dataModel.getRoleId();
					List<PermissionCheckedModel> checkedList = new ArrayList<>();
					for(PermissionCheckedModel pm : dataModel.getPermissionList()) {
						if(pm.isChecked()) {
							checkedList.add(pm);
						}
					}
					//原角色权限数据RolePermission
					List<String> permissionIdList = new ArrayList<String>();
					List<String> permissionDivideIdList = new ArrayList<String>();
					//原角色权限操作数据RolePermissionDivide数据
					List<RolePermission> rolePermissionlist = rolePermissionService.findByHql("from RolePermission where roleId='"+roleId+"' and status='0'");
					if(!ChkUtil.isEmptyAllObject(rolePermissionlist)){
						for(RolePermission rolePermission:rolePermissionlist){
							permissionIdList.add(rolePermission.getPermissionId());
							List<RolePermissionDivide> rolePermissionDividelist = rolePermissionDivideService.findByHql("from RolePermissionDivide where rolePermissionId='"+rolePermission.getId()+"' and status='0'");
							if(!ChkUtil.isEmptyAllObject(rolePermissionDividelist)){
								for(RolePermissionDivide rolePermissionDivide:rolePermissionDividelist){
									permissionDivideIdList.add(rolePermissionDivide.getKeyNameId());
								}
							}
						}
					}
					//若checkedList为空，清空权限rolePermission以及操作数据rolePermissionDivide数据
					if(ChkUtil.isEmptyAllObject(checkedList)) {
						//删除旧角色权限以及角色权限操作数据
						if(!ChkUtil.isEmptyAllObject(rolePermissionlist)) {
							for(RolePermission rolePermission:rolePermissionlist){
								List<RolePermissionDivide> rolePermissionDividelist = rolePermissionDivideService.findByHql("from RolePermissionDivide where rolePermissionId='"+rolePermission.getId()+"' and status='0'");
								if(!ChkUtil.isEmptyAllObject(rolePermissionDividelist)){
									for(RolePermissionDivide rolePermissionDivide:rolePermissionDividelist){
										rolePermissionDivideService.delete(rolePermissionDivide);
									}
								}
								rolePermissionService.delete(rolePermission);
							}
						}
					}else {
						//获取删除集合
						if(!ChkUtil.isEmptyAllObject(permissionIdList)) {
							for(String permissionId : permissionIdList) {
								boolean isContains = false;
								for(PermissionCheckedModel pm : checkedList) {
									if(!pm.getPermissionId().contains(";;") && pm.getPermissionId().equals(permissionId)) {
										isContains = true;
										break;
									}
								}
								if(!isContains) {
									delPermissionList.add(permissionId);
								}
							}
						}
						if(!ChkUtil.isEmptyAllObject(permissionDivideIdList)) {
							for(String permissionDivideId : permissionDivideIdList) {
								boolean isContains = false;
								for(PermissionCheckedModel pm : checkedList) {
									if(pm.getPermissionId().contains(";;") && pm.getPermissionId().split(";;")[1].equals(permissionDivideId)) {
										isContains = true;
										break;
									}
								}
								if(!isContains) {
									delPermissionDivideList.add(permissionDivideId);
								}
							}
						}
						//进行增量判断，获取删除集合以及新增集合
						for(PermissionCheckedModel pm : checkedList) {
							if(pm.getPermissionId().contains(";;")) {//含有;;表示角色权限操作数据
								String permissionDivideId = pm.getPermissionId().split(";;")[1];
								if(!permissionDivideIdList.contains(permissionDivideId)) {//旧的不包含新的，表示新增的rolePermissionDivide集合
									addList.add(pm);
								}
							}else {
								if(!permissionDivideIdList.contains(pm.getPermissionId())) {//旧的不包含新的，表示新增的rolePermissionDivide集合
									addList.add(pm);
								}
							}
						}
						//获取删除数据
						if(!ChkUtil.isEmptyAllObject(delPermissionList)) {
							for(String permissionId : delPermissionList) {
								RolePermission rolePermission = rolePermissionService.getByHql("from RolePermission where roleId='"+roleId+"' and permissionId = '"+permissionId+"' and status<>'1'");
								if(null!=rolePermission){
									rolePermissionService.delete(rolePermission);
								}
							}
						}
						if(!ChkUtil.isEmptyAllObject(delPermissionDivideList)) {
							for(String permissionId : delPermissionDivideList) {
								RolePermission rolePermission = rolePermissionService.getByHql("from RolePermission where roleId='"+roleId+"' and permissionId='"+permissionId.split(";;")[0]+"' and status<>'1'");
								if(null!=rolePermission){
									RolePermissionDivide rpd=rolePermissionDivideService.getByHql("from RolePermissionDivide where rolePermissionId='"+rolePermission.getId()+"' and keyNameId = '"+permissionId.split(";;")[1]+"' and status<>'1'");
									if(null!=rpd){
										rolePermissionDivideService.delete(rpd);
									}
								}
							}
						}
						//新增数据
						if(!ChkUtil.isEmptyAllObject(addList)) {
							RolePermission row = null;
							String permissionId = null;
							String permissionDivideId = null;
							String rolePermissionId = null;
							for(PermissionCheckedModel model : addList) {
								if(model.getPermissionId().contains(";;")){
									permissionId = model.getPermissionId().split(";;")[0];
									permissionDivideId = model.getPermissionId().split(";;")[1];
									RolePermission rolePermissionmx = rolePermissionService.getByHql("from RolePermission where roleId='"+roleId+"' and permissionId='"+permissionId+"' and status<>'1'");
									if(null==rolePermissionmx){
										row = new RolePermission();
										row.setCreateUser(userId);
										row.setCreateTime(new Date());
										row.setLastUpdateUser(userId);
										row.setLastUpdateTime(new Date());
										row.setStatus("0");
										row.setRoleId(roleId);
										row.setPermissionId(permissionId);
										rolePermissionService.save(row);
										rolePermissionId=row.getId();
									}else{
										rolePermissionId=rolePermissionmx.getId();
									}
									RolePermissionDivide rolePermissionDivide=new RolePermissionDivide();
									rolePermissionDivide.setRolePermissionId(rolePermissionId);
									rolePermissionDivide.setKeyNameId(permissionDivideId);
									rolePermissionDivide.setName(model.getName());
									rolePermissionDivide.setCreateUser(userId);
									rolePermissionDivide.setCreateTime(new Date());
									rolePermissionDivide.setLastUpdateUser(userId);
									rolePermissionDivide.setLastUpdateTime(new Date());
									rolePermissionDivide.setStatus("0");
									rolePermissionDivideService.save(rolePermissionDivide);
								}else{
									RolePermission rolePermissionmx = rolePermissionService.getByHql("from RolePermission where roleId='"+roleId+"' and permissionId='"+model.getPermissionId()+"' and status='0'");
									if(null==rolePermissionmx){
										row = new RolePermission();
										row.setCreateUser(userId);
										row.setCreateTime(new Date());
										row.setLastUpdateUser(userId);
										row.setLastUpdateTime(new Date());
										row.setStatus("0");
										row.setRoleId(roleId);
										row.setPermissionId(model.getPermissionId());
										rolePermissionService.save(row);
									}
								}
							}
						}
					}
				}else {//为空，清空权限rolePermission以及操作数据rolePermissionDivide数据
					String roleId = dataModel.getRoleId();
					//删除旧角色权限以及角色权限操作数据
					List<RolePermission> rolePermissionlist = rolePermissionService.findByHql("from RolePermission where roleId='"+roleId+"' and status='0'");
					if(!ChkUtil.isEmptyAllObject(rolePermissionlist)) {
						for(RolePermission rolePermission:rolePermissionlist){
							List<RolePermissionDivide> rolePermissionDividelist = rolePermissionDivideService.findByHql("from RolePermissionDivide where rolePermissionId='"+rolePermission.getId()+"' and status='0'");
							if(!ChkUtil.isEmptyAllObject(rolePermissionDividelist)){
								for(RolePermissionDivide rolePermissionDivide:rolePermissionDividelist){
									rolePermissionDivideService.delete(rolePermissionDivide);
								}
							}
							rolePermissionService.delete(rolePermission);
						}
					}
				}
			}
		} catch(ResultException e){ 
			throw e;
		} catch (Exception e) {
			throw new ResultException(-3, e.getMessage(), token, userId, null);
		}
		return null;
	}


	/**
	 * 角色查询接口
	 * @param request
	 * @param response
	 * @param jsonStr
	 * @param pageNo
	 * @param pageSize
	 * @param userId 当前登录者Id
	 * @param token token令牌
	 * @return
	 * @author 
	 * 	2018-11-1 Diego.zhou 新建
	 */
	@Override
	public Object doSelectRoleInfo(HttpServletRequest request, HttpServletResponse response, String jsonStr,
			String pageNo, String pageSize, String userId, String token) {
		Map map = new HashMap<>();
    	//JSON解析
    	String orderBy="";
    	Parameter parameter =new Parameter();
    	String property=" from Role where status<>'"+StateEnum.DELETESTATE.getCode()+"' ";
    	if(!ChkUtil.isEmptyAllObject(jsonStr)) {
    		// JSON解析
    		JSONObject json = Try.of(() -> (JSONObject) JSON.parse(Encodes.unescapeHtml(jsonStr)))
    				.getOrElseThrow(() -> new ResultException(ResultEnum.FORMAT_ERROR, null, null, null));
    		//角色名称
    		if(!ChkUtil.isEmpty(json.get("roleName"))){
				String roleName = json.getString("roleName");
				property=property+" and roleName like: roleName ";
				parameter.put("roleName", "%"+roleName+"%");
			}
    		//角色Id
    		if(!ChkUtil.isEmpty(json.get("roleId"))){
				String roleId = json.getString("roleId");
				property=property+" and roleId =: roleId ";
				parameter.put("id", roleId);
			}
			if(!ChkUtil.isEmpty(json.get("orderBy"))){
				orderBy=json.get("orderBy").toString();
			}
    	}
    	//下面两行设置翻页
		Page pages = new Page<UserRoleCityManageInfo>(request,response);
		if(ChkUtil.isInteger(pageNo) && !pageNo.equals("0")){
			pages.setPageNo(Integer.parseInt(pageNo));
		}
		if(ChkUtil.isInteger(pageSize) && !pageSize.equals("0")){
			pages.setPageSize(Integer.parseInt(pageSize));
		}
		if(!ChkUtil.isEmpty(orderBy)){
			pages.setOrderBy(orderBy);
		}
		Page<Role> page = roleDao.find(pages, property,parameter);
		List<Role> list = page.getList();
		List roleList=new ArrayList<>();
		if(!ChkUtil.isEmptyAllObject(list)){
			for(Role role : list) {
				RoleModel roleModel = new RoleModel(role);
				roleList.add(roleModel);
			}
			page.setList(roleList);
		}
		map.put("page", page);
    	return map;
	}


	/**
	 * 角色搜索下拉查询接口
	 * @param request
	 * @param response
	 * @param jsonStr
	 * @param pageNo 
	 * @param pageSize
	 * @param userId 当前登录者Id
	 * @param token token令牌
	 * @return
	 */
	@Override
	public Object doSearchRoleInfo(HttpServletRequest request, HttpServletResponse response, String jsonStr,
			String pageNo, String pageSize, String userId, String token) {
		Map map = new HashMap<>();
    	//JSON解析
    	String orderBy="";
    	Parameter parameter =new Parameter();
    	String property=" from Role where status<>'"+StateEnum.DELETESTATE.getCode()+"' ";
    	if(!ChkUtil.isEmptyAllObject(jsonStr)) {
    		// JSON解析
    		JSONObject json = Try.of(() -> (JSONObject) JSON.parse(Encodes.unescapeHtml(jsonStr)))
    				.getOrElseThrow(() -> new ResultException(ResultEnum.FORMAT_ERROR, null, null, null));
    		//角色名称
    		if(!ChkUtil.isEmpty(json.get("roleName"))){
				String roleName = json.getString("roleName");
				property=property+" and roleName like: roleName ";
				parameter.put("roleName", "%"+roleName+"%");
			}
			if(!ChkUtil.isEmpty(json.get("orderBy"))){
				orderBy=json.get("orderBy").toString();
			}
    	}
    	//下面两行设置翻页
		Page pages = new Page<UserRoleCityManageInfo>(request,response);
		if(ChkUtil.isInteger(pageNo) && !pageNo.equals("0")){
			pages.setPageNo(Integer.parseInt(pageNo));
		}
		if(ChkUtil.isInteger(pageSize) && !pageSize.equals("0")){
			pages.setPageSize(Integer.parseInt(pageSize));
		}
		if(!ChkUtil.isEmpty(orderBy)){
			pages.setOrderBy(orderBy);
		}
		Page<Role> page = roleDao.find(pages, property,parameter);
		List<Role> list = page.getList();
		List roleList=new ArrayList<>();
		if(!ChkUtil.isEmptyAllObject(list)){
			for(Role role : list) {
				RoleModel roleModel = new RoleModel(role);
				roleList.add(roleModel);
			}
			page.setList(roleList);
		}
		map.put("page", page);
    	return map;
	}


	/**
	 * 获取所有角色数据接口
	 * @param userId 当前登录者Id
	 * @param token token令牌
	 * @return
	 * @author 
	 * 	2018-11-1 Diego.zhou 新建
	 */
	@Override
	public Object doSelectAllRoleInfo(String userId, String token) {
		Map map = new HashMap<>();
    	String property=" from Role where status<>'"+StateEnum.DELETESTATE.getCode()+"' ";
    	try {
			List<Role> roleList = roleDao.findByHql(property);
			if(!ChkUtil.isEmptyAllObject(roleList)) {
				List<RoleModel> modelList = new ArrayList<>();
				RoleModel roleModel = null;
				for(Role role : roleList) {
					roleModel = new RoleModel(role);
					modelList.add(roleModel);
				}
				map.put("roleList", modelList);
			}
		} catch(ResultException e){ 
			throw e;
		} catch (Exception e) {
			throw new ResultException(-3, e.getMessage(), token, userId, null);
		}
    	return map;
	}


	/**
	 * 角色删除接口
	 * @param roleId 角色Id
	 * @param userId 当前登录者Id
	 * @param token token令牌
	 * @return
	 * @author 
	 * 	2018-11-1 Diego.zhou 新建
	 */
	@Override
	public Object doDeleteRoleInfo(String roleId, String userId, String token) {
		//roleId校验
		if(ChkUtil.isEmptyAllObject(roleId)) {
			throw new ResultException(-2, "角色Id为空！", token, userId, null);
		}
		//获取角色信息，判断该角色是否存在
		try {
			Role role = roleDao.get(roleId);
			if(null == role || ChkUtil.isEmptyAllObject(role.getStatus()) ||role.getStatus().equals(StateEnum.DELETESTATE.getCode())) {
				throw new ResultException(-2, "该角色已被删除！", token, userId, null);
			}
			//判断该角色是否被引用
			List<UserRole> userRoleList = userRoleService.findByHql("from UserRole where status<>'"+StateEnum.DELETESTATE.getCode()+"' and roleId='"+roleId+"'");
			if(ChkUtil.isEmptyAllObject(userRoleList)) {
				throw new ResultException(-2, "该角色数据已被引用，不能删除！", token, userId, null);
			}
			role.setStatus(StateEnum.DELETESTATE.getCode());
			role.setLastUpdateTime(new Date());
			role.setLastUpdateUser(userId);
			roleDao.save(role);
		} catch(ResultException e){ 
			throw e;
		} catch (Exception e) {
			throw new ResultException(-3, e.getMessage(), token, userId, null);
		}
		return null;
	}
	
	
}