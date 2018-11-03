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
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cssiot.cssbase.modules.base.entity.ScenicSpot;
import com.cssiot.cssbase.modules.base.service.ScenicSpotService;
import com.cssiot.cssbase.modules.sys.dao.UserDao;
import com.cssiot.cssbase.modules.sys.data.UserCityManageModel;
import com.cssiot.cssbase.modules.sys.data.UserModel;
import com.cssiot.cssbase.modules.sys.data.VisitorsModel;
import com.cssiot.cssbase.modules.sys.entity.Permission;
import com.cssiot.cssbase.modules.sys.entity.PermissionDivide;
import com.cssiot.cssbase.modules.sys.entity.Role;
import com.cssiot.cssbase.modules.sys.entity.User;
import com.cssiot.cssbase.modules.sys.entity.UserCityManage;
import com.cssiot.cssbase.modules.sys.entity.UserPermissionDivide;
import com.cssiot.cssbase.modules.sys.entity.UserRole;
import com.cssiot.cssbase.modules.sys.entity.UserRoleCityManageInfo;
import com.cssiot.cssbase.modules.sys.entity.Visitors;
import com.cssiot.cssbase.modules.sys.service.PermissionDivideService;
import com.cssiot.cssbase.modules.sys.service.PermissionService;
import com.cssiot.cssbase.modules.sys.service.RoleService;
import com.cssiot.cssbase.modules.sys.service.UserCityManageService;
import com.cssiot.cssbase.modules.sys.service.UserPermissionDivideService;
import com.cssiot.cssbase.modules.sys.service.UserRoleCityManageInfoService;
import com.cssiot.cssbase.modules.sys.service.UserRoleService;
import com.cssiot.cssbase.modules.sys.service.UserService;
import com.cssiot.cssbase.modules.sys.service.VisitorsService;
import com.cssiot.cssutil.common.dao.BaseDao;
import com.cssiot.cssutil.common.data.IsEnabledModel;
import com.cssiot.cssutil.common.enums.ResultEnum;
import com.cssiot.cssutil.common.enums.StateEnum;
import com.cssiot.cssutil.common.enums.VisitorsTypeEnum;
import com.cssiot.cssutil.common.exception.ResultException;
import com.cssiot.cssutil.common.service.impl.BaseServiceImpl;
import com.cssiot.cssutil.common.utils.ChkUtil;
import com.cssiot.cssutil.common.utils.EasyPoiFileUtil;
import com.cssiot.cssutil.common.utils.Encodes;
import com.cssiot.cssutil.common.utils.MD5Util;
import com.cssiot.cssutil.common.utils.OptionUtil;
import com.cssiot.cssutil.common.utils.Page;
import com.cssiot.cssutil.common.utils.Parameter;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import io.vavr.control.Try;

/**
 * 员工表ServiceImpl
 * @author
 *	2018-10-07 athena 创建
 */
@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService{
	
	@Override
	public BaseDao<User> getBaseDao() {
		return userDao;
	}
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserPermissionDivideService userPermissionDivideService;
	
	@Autowired
	private PermissionDivideService permissionDivideService;
	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private VisitorsService visitorsService;
	
	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private UserCityManageService userCityManageService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ScenicSpotService scenicSpotService;
	
	@Autowired
	private UserRoleCityManageInfoService userRoleCityManageInfoService;
	
	/**
	 * 判断当前用户是否具有操作此功能的权限
	 * @param userId 用户ID
	 * @param content 模块内容
	 * @param operate 功能操作
	 * @return
	 * @author
	 * 	2018-10-24 athena 复制
	 */
	@SuppressWarnings("all")
	public IsEnabledModel isAccess(String userId,String content,String operate) throws Exception{
		IsEnabledModel isEnabledModel = new IsEnabledModel();
		List<UserPermissionDivide> upd=userPermissionDivideService.findByHql("from UserPermissionDivide where userId='"+userId+"' and content='"+content+"' and keyName='"+operate+"'");
		if(ChkUtil.isEmptyAllObject(upd)){
			isEnabledModel.setCode("4008");
			PermissionDivide dc = permissionDivideService.getByHql("from PermissionDivide where content='"+content+"' and keyName='"+operate+"' and state<>'1'");
			String msg="";
			if(dc!=null){
				Permission per = permissionService.getByHql("from Permission where content='"+content+"' and state<>'1'");
				if(ChkUtil.isEmptyAllObject(per)){
					msg="失败，没有该功能的‘"+dc.getName()+"’操作权限！如果需要请联系管理员添加！";
				}else{
					msg="失败，没有‘"+per.getName()+"’功能的‘"+dc.getName()+"’操作权限！如果需要请联系管理员添加！";
				}
			}else if(dc==null){
				Permission per = permissionService.getByHql("from Permission where content='"+content+"' and state<>'1'");
				if(ChkUtil.isEmptyAllObject(per)){
					msg="失败，没有该功能的操作权限！如果需要请联系管理员添加！";
				}else{
					msg="失败，没有‘"+per.getName()+"’功能的操作权限！如果需要请联系管理员添加！";
				}
			}else{
				msg="失败，没有该功能的操作权限！如果需要请联系管理员添加！";
			}
			isEnabledModel.setMessage(msg);
			return isEnabledModel;
		}else{
			isEnabledModel.setCode("1");
			isEnabledModel.setMessage("查询成功！");
		}
		return isEnabledModel;
	}
	
	/**
	 * 查询当前用户具有哪些操作权限
	 * @param userId 用户ID
	 * @param content 模块内容
	 * @param operate 功能操作
	 * @return
	 * @author
	 * 	2018-10-24 athena 复制
	 */
	@SuppressWarnings("all")
	public IsEnabledModel hasAccess(String userId,String content) throws Exception{
		IsEnabledModel isEnabledModel = new IsEnabledModel();
		List<UserPermissionDivide> upd=userPermissionDivideService.findByHql("from UserPermissionDivide where userId='"+userId+"' and content='"+content+"' ");
		if(!ChkUtil.isEmptyAllObject(upd)){
			List dataList=new ArrayList<>();
			for(UserPermissionDivide ud:upd){
				if(!dataList.contains(ud.getKeyName())){
					dataList.add(ud.getKeyName());
				}
			}
			isEnabledModel.setDataList(dataList);
			isEnabledModel.setMessage("查询成功！");
			isEnabledModel.setCode("1");
		}else{
			isEnabledModel.setMessage("失败，没有该功能的操作权限！");
			isEnabledModel.setCode("4008");
		}
		return isEnabledModel;
	}

	/**
	 * 员工信息查询接口
	 */
	@Override
	public Object doSelectUsersInfo(HttpServletRequest request, HttpServletResponse response, String jsonStr,
			String pageNo, String pageSize, String userId, String token) {
		Map map = new HashMap<>();
    	//JSON解析
    	String orderBy="";
    	Parameter parameter =new Parameter();
    	String property=" from UserRoleCityManageInfo where status<>'"+StateEnum.DELETESTATE.getCode()+"' ";
    	if(!ChkUtil.isEmptyAllObject(jsonStr)) {
    		// JSON解析
    		JSONObject json = Try.of(() -> (JSONObject) JSON.parse(Encodes.unescapeHtml(jsonStr)))
    				.getOrElseThrow(() -> new ResultException(ResultEnum.FORMAT_ERROR, null, null, null));
    		//获取注册省份
    		if(!ChkUtil.isEmpty(json.get("province"))){
				String province = json.getString("province");
				property=property+" and province = :province ";
				parameter.put("province", province);
			}
    		//获取注册城市
    		if(!ChkUtil.isEmpty(json.get("city"))){
				String city = json.getString("city");
				property=property+" and city = :city ";
				parameter.put("city", city);
			}
    		//获取注册区县
    		if(!ChkUtil.isEmpty(json.get("county"))){
				String county = json.getString("county");
				property=property+" and county = :county ";
				parameter.put("county", county);
			}
    		//获取注册景点Id
    		if(!ChkUtil.isEmpty(json.get("scenicSpotId"))){
				String scenicSpotId = json.getString("scenicSpotId");
				property=property+" and scenicSpotId =:scenicSpotId ";
				parameter.put("scenicSpotId", scenicSpotId);
			}
    		//手机号
    		if(!ChkUtil.isEmpty(json.get("phone"))){
				String phone = json.getString("phone");
				property=property+" and phone =:phone ";
				parameter.put("phone", phone);
			}
    		//账号
    		if(!ChkUtil.isEmpty(json.get("loginName"))){
				String loginName = json.getString("loginName");
				property=property+" and loginName like:loginName ";
				parameter.put("loginName", "%"+loginName+"%");
			}
    		//姓名
    		if(!ChkUtil.isEmpty(json.get("userName"))){
				String userName = json.getString("userName");
				property=property+" and userName like:userName ";
				parameter.put("userName", "%"+userName+"%");
			}
    		//角色Id
    		if(!ChkUtil.isEmpty(json.get("roleId"))){
				String roleId = json.getString("roleId");
				property=property+" and roleId =:roleId ";
				parameter.put("roleId", roleId);
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
		Page<UserRoleCityManageInfo> page = userRoleCityManageInfoService.find(pages, property,parameter);
		List<UserRoleCityManageInfo> list = page.getList();
		List usersList=new ArrayList<>();
		if(!ChkUtil.isEmptyAllObject(list)){
			for(UserRoleCityManageInfo userInfo : list) {
				UserModel userModel = new UserModel(userInfo);
				usersList.add(userModel);
			}
			page.setList(usersList);
		}
		map.put("page", page);
    	return map;
	}

	/**
	 * 员工信息新建保存接口
	 */
	@Override
	public Object doSaveUsersInfo(String jsonStr, String userId, String token) {
		// JSON校验
		OptionUtil.of(jsonStr)
				.getOrElseThrow(() -> new ResultException(ResultEnum.JSONSTR_NULL, token, userId, null));
		// JSON解析
		JSONObject json = Try.of(() -> (JSONObject) JSON.parse(Encodes.unescapeHtml(jsonStr)))
				.getOrElseThrow(() -> new ResultException(ResultEnum.FORMAT_ERROR, token, userId, null));
		//获取VIP游客信息
		Gson gson = new Gson();
		//获取员工数据
		if(ChkUtil.isEmptyAllObject(json.get("userModel"))){
			throw new ResultException(-2, "员工数据为空！", token, userId, null);
		}
		UserModel userModel = gson.fromJson(json.getString("userModel"),new TypeToken<UserModel>(){}.getType());
		if(null == userModel) {
			throw new ResultException(-2, "员工数据为空！", token, userId, null);
		}
		//姓名校验
		Map userNameMap = ChkUtil.fieldCheck(userModel.getUserName(),true,10, "账号","");
		if(ChkUtil.isEmptyAllObject(userNameMap)){
			throw new ResultException(ResultEnum.DATA_ERROR,token,userId,null);
		}
		if(!userNameMap.get("code").equals("1")){
			throw new ResultException(-2, userNameMap.get("message").toString(), token, userId, null);
		}
		//手机号格式校验
		Map phoneMap = ChkUtil.fieldCheck(userModel.getPhone(),true,11, "手机号","4");
		if(ChkUtil.isEmptyAllObject(phoneMap)){
			throw new ResultException(ResultEnum.DATA_ERROR,token,userId,null);
		}
		if(!phoneMap.get("code").equals("1")){
			throw new ResultException(-2, phoneMap.get("message").toString(), token, userId, null);
		}
		//账号校验
		Map loginNameMap = ChkUtil.fieldCheck(userModel.getLoginName(),true,10, "账号","");
		if(ChkUtil.isEmptyAllObject(loginNameMap)){
			throw new ResultException(ResultEnum.DATA_ERROR,token,userId,null);
		}
		if(!loginNameMap.get("code").equals("1")){
			throw new ResultException(-2, loginNameMap.get("message").toString(), token, userId, null);
		}
		//密码
		Map passwordMap = ChkUtil.fieldCheck(userModel.getPassword(),false,20, "密码","");
		if(ChkUtil.isEmptyAllObject(passwordMap)){
			throw new ResultException(ResultEnum.DATA_ERROR,token,userId,null);
		}
		if(!passwordMap.get("code").equals("1")){
			throw new ResultException(-2, passwordMap.get("message").toString(), token, userId, null);
		}
		if(ChkUtil.isEmptyAllObject(userModel.getPassword())) {
			userModel.setPassword("123456");
		}
		//角色校验
		if(ChkUtil.isEmptyAllObject(userModel.getRoleId())) {
			throw new ResultException(-2, "角色不能为空！", token, userId, null);
		}
		//管理城市
		if(ChkUtil.isEmptyAllObject(userModel.getManageList())) {
			throw new ResultException(-2, "员工管理城市景点不能为空！", token, userId, null);
		}
		try {
			List<User> loginNameList = userDao.findByHql("from User where status<>'"+StateEnum.DELETESTATE.getCode()+"' and loginName='"+userModel.getLoginName()+"'");
			if(!ChkUtil.isEmptyAllObject(loginNameList)) {
				throw new ResultException(-2, "登录名:"+userModel.getLoginName()+"存在重复！！", token, userId, null);
			}
			List<User> phoneList = userDao.findByHql("from User where status<>'"+StateEnum.DELETESTATE.getCode()+"' and phone='"+userModel.getPhone()+"'");
			if(!ChkUtil.isEmptyAllObject(phoneList)) {
				throw new ResultException(-2, "手机号:"+userModel.getPhone()+"存在重复！！", token, userId, null);
			}
		} catch (Exception e) {
			throw new ResultException(ResultEnum.DATA_ERROR, token, userId, null);
		}
		//管理城市/景点重复校验
		boolean isMatch = isManageMatch(userModel.getManageList());
		if(!isMatch) {
			throw new ResultException(-2, "员工管理城市景点信息存在重复！", token, userId, null);
		}
		//保存员工信息，并加入VIP
		User user = new User(userModel);
		//对员工密码进行MD5二次加密
		user.setPassword(MD5Util.getMD5ofStr(userModel.getPassword(), 2));
		user.setCreateTime(new Date());
		user.setCreateUser(userId);
		user.setLastUpdateTime(new Date());
		user.setLastUpdateUser(userId);
		user.setStatus(StateEnum.NEWSTATE.getCode());
		userDao.save(user);
		//判断是否存在手机号为userModel.getPhone的游客，若存在，将其加入VIP,不存在，新建VIP游客
		Visitors existVisitors = visitorsService.getByHql("from Visitors where status<>'"+StateEnum.DELETESTATE.getCode()+"' and phone='"+userModel.getPhone()+"'");
		if(null == existVisitors) {
			//保存VIP游客信息
			Visitors visitors = new Visitors();
			visitors.setRegisterTime(new Date());
			visitors.setVisitorsType(VisitorsTypeEnum.VIP.getCode());
			visitors.setPhone(userModel.getPhone());
			visitors.setVipRemark("系统员工");
			visitors.setCreateTime(new Date());
			visitors.setCreateUser(userId);
			visitors.setLastUpdateTime(new Date());
			visitors.setLastUpdateUser(userId);
			visitors.setStatus(StateEnum.NEWSTATE.getCode());
			visitorsService.save(visitors);
		}else {
			if(ChkUtil.isEmptyAllObject(existVisitors.getVisitorsType()) || !existVisitors.getVisitorsType().equals(VisitorsTypeEnum.VIP.getCode())) {
				existVisitors.setVisitorsType(VisitorsTypeEnum.VIP.getCode());
				existVisitors.setVipRemark("系统员工");
				existVisitors.setLastUpdateTime(new Date());
				existVisitors.setLastUpdateUser(userId);
				visitorsService.save(existVisitors);
			}
		}
		//保存用户角色数据
		UserRole userRole = new UserRole();
		userRole.setUserId(user.getId());
		userRole.setRoleId(userModel.getRoleId());
		userRole.setCreateTime(new Date());
		userRole.setCreateUser(userId);
		userRole.setLastUpdateTime(new Date());
		userRole.setLastUpdateUser(userId);
		userRole.setStatus(StateEnum.NEWSTATE.getCode());
		userRoleService.save(userRole);
		//保存管理城市景点数据（管理城市设置不能重复）
		UserCityManage manage = null;
		for(UserCityManageModel manageModel : userModel.getManageList()) {
			manage = new UserCityManage(manageModel);
			manage.setUserId(user.getId());
			manage.setCreateTime(new Date());
			manage.setCreateUser(userId);
			manage.setLastUpdateTime(new Date());
			manage.setLastUpdateUser(userId);
			manage.setStatus(StateEnum.NEWSTATE.getCode());
			userCityManageService.save(manage);
		}
		return null;
	}
	
	/**
	 * 判断景点授权信息是否存在重复
	 * @param manageList 授权景点信息
	 * @return
	 * @author 
	 * 	2018-10-28 Diego.zhou 新建
	 */
	private boolean isManageMatch(List<UserCityManageModel> manageList) {
		boolean isMatch = true;
		//解析授权景点信息，将其转为
		for(int i= 0 ; i<manageList.size(); i++) {
			String iContent = manageList.get(i).toString();
			if(ChkUtil.isEmptyAllObject(manageList.get(i).getScenicSpotId())) {//景点为空
				boolean isBreak = false;
				for(int j=0;j<manageList.size(); j++) {
					if(j != i && manageList.get(j).toString().contains(iContent)) {
						isMatch = false;
						isBreak = true;
						break;
					}
				}
				if(isBreak) {
					break;
				}
			}else {
				boolean isBreak = false;
				for(int j=0;j<manageList.size(); j++) {
					if(j != i && manageList.get(j).toString().equals(iContent)) {
						isMatch = false;
						isBreak = true;
						break;
					}
				}
				if(isBreak) {
					break;
				}

			}
		}
		return isMatch;
	}
	

	/**
	 * 员工信息模板导出接口
	 */
	@Override
	public Object doExportUsersTemplateInfo(HttpServletResponse response) {
		// 导出操作
		EasyPoiFileUtil.exportExcel(new ArrayList<>(), "员工信息", "sheet1", UserModel.class, "员工信息导入模板.xls", response);
		return null;
	}

	/**
	 * 员工信息导入接口
	 */
	@Override
	public Object doImportUsersInfo(MultipartFile file, String token, String userId) {
		// 解析excel
		List<UserModel> usersList = EasyPoiFileUtil.importExcel(file, 1, 1, UserModel.class);
		if (!ChkUtil.isEmptyAllObject(usersList)) {
			for (UserModel userModel : usersList) {
				//姓名校验
				Map userNameMap = ChkUtil.fieldCheck(userModel.getUserName(),true,10, "账号","");
				if(ChkUtil.isEmptyAllObject(userNameMap)){
					throw new ResultException(ResultEnum.DATA_ERROR,token,userId,null);
				}
				if(!userNameMap.get("code").equals("1")){
					throw new ResultException(-2, userNameMap.get("message").toString(), token, userId, null);
				}
				//手机号格式校验
				Map phoneMap = ChkUtil.fieldCheck(userModel.getPhone(),true,11, "手机号","4");
				if(ChkUtil.isEmptyAllObject(phoneMap)){
					throw new ResultException(ResultEnum.DATA_ERROR,token,userId,null);
				}
				if(!phoneMap.get("code").equals("1")){
					throw new ResultException(-2, phoneMap.get("message").toString(), token, userId, null);
				}
				//账号校验
				Map loginNameMap = ChkUtil.fieldCheck(userModel.getLoginName(),true,10, "账号","");
				if(ChkUtil.isEmptyAllObject(loginNameMap)){
					throw new ResultException(ResultEnum.DATA_ERROR,token,userId,null);
				}
				if(!loginNameMap.get("code").equals("1")){
					throw new ResultException(-2, loginNameMap.get("message").toString(), token, userId, null);
				}
				//密码
				Map passwordMap = ChkUtil.fieldCheck(userModel.getPassword(),false,20, "密码","");
				if(ChkUtil.isEmptyAllObject(passwordMap)){
					throw new ResultException(ResultEnum.DATA_ERROR,token,userId,null);
				}
				if(!passwordMap.get("code").equals("1")){
					throw new ResultException(-2, passwordMap.get("message").toString(), token, userId, null);
				}
				if(ChkUtil.isEmptyAllObject(userModel.getPassword())) {
					userModel.setPassword("123456");
				}
				//保存员工信息并默认加入游客VIP
				//账号、手机重复校验
				try {
	//				List<User> userNameList = userDao.findByHql("from User where status<>'"+StateEnum.DELETESTATE.getCode()+"' and userName='"+userModel.getUserName()+"'");
	//				if(!ChkUtil.isEmptyAllObject(userNameList)) {
	//					throw new ResultException(-2, "已存在姓名为"+userModel.getUserName()+"的员工！", token, userId, null);
	//				}
					List<User> loginNameList = userDao.findByHql("from User where status<>'"+StateEnum.DELETESTATE.getCode()+"' and loginName='"+userModel.getLoginName()+"'");
					if(!ChkUtil.isEmptyAllObject(loginNameList)) {
						throw new ResultException(-2, "登录名:"+userModel.getLoginName()+"存在重复！！", token, userId, null);
					}
					List<User> phoneList = userDao.findByHql("from User where status<>'"+StateEnum.DELETESTATE.getCode()+"' and phone='"+userModel.getPhone()+"'");
					if(!ChkUtil.isEmptyAllObject(phoneList)) {
						throw new ResultException(-2, "手机号:"+userModel.getPhone()+"存在重复！！", token, userId, null);
					}
				} catch (Exception e) {
					throw new ResultException(ResultEnum.DATA_ERROR, token, userId, null);
				}
				//保存员工信息，并加入VIP
				User user = new User(userModel);
				//对员工密码进行MD5二次加密
				user.setPassword(MD5Util.getMD5ofStr(userModel.getPassword(), 2));
				user.setCreateTime(new Date());
				user.setCreateUser(userId);
				user.setLastUpdateTime(new Date());
				user.setLastUpdateUser(userId);
				user.setStatus(StateEnum.NEWSTATE.getCode());
				userDao.save(user);
				//判断是否存在手机号为userModel.getPhone的游客，若存在，将其加入VIP,不存在，新建VIP游客
				Visitors existVisitors = visitorsService.getByHql("from Visitors where status<>'"+StateEnum.DELETESTATE.getCode()+"' and phone='"+userModel.getPhone()+"'");
				if(null == existVisitors) {
					//保存VIP游客信息
					Visitors visitors = new Visitors();
					visitors.setRegisterTime(new Date());
					visitors.setVisitorsType(VisitorsTypeEnum.VIP.getCode());
					visitors.setPhone(userModel.getPhone());
//					visitors.setIdentityCard(visitorsModel.getIdentityCard());
					visitors.setVipRemark("系统员工");
					visitors.setCreateTime(new Date());
					visitors.setCreateUser(userId);
					visitors.setLastUpdateTime(new Date());
					visitors.setLastUpdateUser(userId);
					visitors.setStatus(StateEnum.NEWSTATE.getCode());
					visitorsService.save(visitors);
				}else {
					if(ChkUtil.isEmptyAllObject(existVisitors.getVisitorsType()) || !existVisitors.getVisitorsType().equals(VisitorsTypeEnum.VIP.getCode())) {
						existVisitors.setVisitorsType(VisitorsTypeEnum.VIP.getCode());
						existVisitors.setVipRemark("系统员工");
						existVisitors.setLastUpdateTime(new Date());
						existVisitors.setLastUpdateUser(userId);
						visitorsService.save(existVisitors);
					}
				}
			}
		}else {
			throw new ResultException(-2, "员工信息为空", token, userId, null);
		}
		return null;
	}

	/**
	 * 员工信息修改初始化接口
	 */
	@Override
	public Object doUpdateUsersData(String usersId, String userId, String token) {
		Map map = new HashMap<>();
		//员工Id校验
    	OptionUtil.of(usersId).getOrElseThrow(() -> 
    		new ResultException(-2,"员工Id为空!", token, userId, null));
    	//获取对应游客信息
		try {
			User user = userDao.get(usersId);
			if(null == usersId || ChkUtil.isEmptyAllObject(user.getStatus()) 
	    			|| user.getStatus().equals(StateEnum.DELETESTATE.getCode())) {
				throw new ResultException(-2, "该游客数据异常！", token, userId, null);
	    	}
			UserModel userModel = new UserModel(user);
			userModel.setPassword(null);
			//获取用户角色信息
			List<UserRole> urList = userRoleService.findByHql("from UserRole where status='"+StateEnum.NEWSTATE.getCode()+"' and userId='"+usersId+"'");
			if(!ChkUtil.isEmptyAllObject(urList)) {
				Role role = roleService.get(urList.get(0).getRoleId());
				if(null != role) {
					userModel.setRoleId(role.getId());
					userModel.setRoleName(role.getRoleName());
				}
			}
			List<UserCityManage> ucmList = userCityManageService.findByHql("from UserCityManage where status<>'"+StateEnum.DELETESTATE.getCode()+"' and userId='"+usersId+"'");
			if(!ChkUtil.isEmptyAllObject(ucmList)) {
				List<UserCityManageModel> manageList = new ArrayList<>();
				UserCityManageModel ucmModel = null;
				for(UserCityManage ucm : ucmList) {
					ucmModel = new UserCityManageModel(ucm);
					if(!ChkUtil.isEmptyAllObject(ucm.getScenicSpotId())) {
						ScenicSpot ss = scenicSpotService.get(ucm.getScenicSpotId());
						if(null != ss) {
							ucmModel.setScenicSpotName(ss.getScenicSpotName());
						}
					}
					manageList.add(ucmModel);
				}
				userModel.setManageList(manageList);
			}
			map.put("userModel", userModel);
		} catch (ResultException e) {
			throw e;
		} catch (Exception e) {
			throw new ResultException(-3, e.getMessage(), token, userId, null);
		}
		return map;
	}

	/**
	 * 员工信息修改保存接口
	 */
	@Override
	public Object doUpdateUsersInfo(String jsonStr, String userId, String token) {
		// JSON校验
		OptionUtil.of(jsonStr)
				.getOrElseThrow(() -> new ResultException(ResultEnum.JSONSTR_NULL, token, userId, null));
		// JSON解析
		JSONObject json = Try.of(() -> (JSONObject) JSON.parse(Encodes.unescapeHtml(jsonStr)))
				.getOrElseThrow(() -> new ResultException(ResultEnum.FORMAT_ERROR, token, userId, null));
		//获取VIP游客信息
		Gson gson = new Gson();
		//获取员工数据
		if(ChkUtil.isEmptyAllObject(json.get("userModel"))){
			throw new ResultException(-2, "员工数据为空！", token, userId, null);
		}
		UserModel userModel = gson.fromJson(json.getString("userModel"),new TypeToken<UserModel>(){}.getType());
		if(null == userModel) {
			throw new ResultException(-2, "员工数据为空！", token, userId, null);
		}
		//id校验
		if(ChkUtil.isEmptyAllObject(userModel.getUserId())) {
			throw new ResultException(-2, "员工Id为空！", token, userId, null);
		}
		//姓名校验
		Map userNameMap = ChkUtil.fieldCheck(userModel.getUserName(),true,10, "账号","");
		if(ChkUtil.isEmptyAllObject(userNameMap)){
			throw new ResultException(ResultEnum.DATA_ERROR,token,userId,null);
		}
		if(!userNameMap.get("code").equals("1")){
			throw new ResultException(-2, userNameMap.get("message").toString(), token, userId, null);
		}
		//手机号格式校验
		Map phoneMap = ChkUtil.fieldCheck(userModel.getPhone(),true,11, "手机号","4");
		if(ChkUtil.isEmptyAllObject(phoneMap)){
			throw new ResultException(ResultEnum.DATA_ERROR,token,userId,null);
		}
		if(!phoneMap.get("code").equals("1")){
			throw new ResultException(-2, phoneMap.get("message").toString(), token, userId, null);
		}
		//账号校验
		Map loginNameMap = ChkUtil.fieldCheck(userModel.getLoginName(),true,10, "账号","");
		if(ChkUtil.isEmptyAllObject(loginNameMap)){
			throw new ResultException(ResultEnum.DATA_ERROR,token,userId,null);
		}
		if(!loginNameMap.get("code").equals("1")){
			throw new ResultException(-2, loginNameMap.get("message").toString(), token, userId, null);
		}
		//密码
		Map passwordMap = ChkUtil.fieldCheck(userModel.getPassword(),false,20, "密码","");
		if(ChkUtil.isEmptyAllObject(passwordMap)){
			throw new ResultException(ResultEnum.DATA_ERROR,token,userId,null);
		}
		if(!passwordMap.get("code").equals("1")){
			throw new ResultException(-2, passwordMap.get("message").toString(), token, userId, null);
		}
		if(ChkUtil.isEmptyAllObject(userModel.getPassword())) {
			userModel.setPassword("123456");
		}
		//角色校验
		if(ChkUtil.isEmptyAllObject(userModel.getRoleId())) {
			throw new ResultException(-2, "角色不能为空！", token, userId, null);
		}
		//管理城市
		if(ChkUtil.isEmptyAllObject(userModel.getManageList())) {
			throw new ResultException(-2, "员工管理城市景点不能为空！", token, userId, null);
		}
		try {
			//判断该员工是否存在
			User user = userDao.get(userModel.getUserId());
			if(null == user || ChkUtil.isEmptyAllObject(user.getStatus()) || user.getStatus().equals(StateEnum.DELETESTATE.getCode())) {
				throw new ResultException(-2, "该员工数据异常！", token, userId, null);
			}
			//重复登录名校验
			List<User> loginNameList = userDao.findByHql("from User where status<>'"+StateEnum.DELETESTATE.getCode()+"' and loginName='"+userModel.getLoginName()+"' and id<>'"+userModel.getUserId()+"'");
			if(!ChkUtil.isEmptyAllObject(loginNameList)) {
				throw new ResultException(-2, "登录名:"+userModel.getLoginName()+"存在重复！！", token, userId, null);
			}
			//重复手机号校验
			List<User> phoneList = userDao.findByHql("from User where status<>'"+StateEnum.DELETESTATE.getCode()+"' and phone='"+userModel.getPhone()+"' and id<>'"+userModel.getUserId()+"'");
			if(!ChkUtil.isEmptyAllObject(phoneList)) {
				throw new ResultException(-2, "手机号:"+userModel.getPhone()+"存在重复！！", token, userId, null);
			}
			//管理城市/景点重复校验
			boolean isMatch = isManageMatch(userModel.getManageList());
			if(!isMatch) {
				throw new ResultException(-2, "员工管理城市景点信息存在重复！", token, userId, null);
			}
			user.setLoginName(userModel.getLoginName());
			user.setUserName(userModel.getUserName());
			//对员工密码进行MD5二次加密
			user.setPassword(MD5Util.getMD5ofStr(userModel.getPassword(), 2));
			user.setLastUpdateTime(new Date());
			user.setLastUpdateUser(userId);
			user.setPhone(userModel.getPhone());
			userDao.save(user);
			//判断是否存在手机号为userModel.getPhone的游客，若存在，将其加入VIP,不存在，新建VIP游客
			Visitors existVisitors = visitorsService.getByHql("from Visitors where status<>'"+StateEnum.DELETESTATE.getCode()+"' and phone='"+userModel.getPhone()+"'");
			if(null == existVisitors) {
				//保存VIP游客信息
				Visitors visitors = new Visitors();
				visitors.setRegisterTime(new Date());
				visitors.setVisitorsType(VisitorsTypeEnum.VIP.getCode());
				visitors.setPhone(userModel.getPhone());
				visitors.setVipRemark("系统员工");
				visitors.setCreateTime(new Date());
				visitors.setCreateUser(userId);
				visitors.setLastUpdateTime(new Date());
				visitors.setLastUpdateUser(userId);
				visitors.setStatus(StateEnum.NEWSTATE.getCode());
				visitorsService.save(visitors);
			}else {
				if(ChkUtil.isEmptyAllObject(existVisitors.getVisitorsType()) || !existVisitors.getVisitorsType().equals(VisitorsTypeEnum.VIP.getCode())) {
					existVisitors.setVisitorsType(VisitorsTypeEnum.VIP.getCode());
					existVisitors.setVipRemark("系统员工");
					existVisitors.setLastUpdateTime(new Date());
					existVisitors.setLastUpdateUser(userId);
					visitorsService.save(existVisitors);
				}
			}
			//删除旧员工角色数据
			UserRole userRole = userRoleService.getByHql("from UserRole where status<>'"+StateEnum.DELETESTATE.getCode()+"' and userId='"+userModel.getUserId()+"'");
			if(null != userRole) {
				//更新用户角色数据
				userRole.setRoleId(userModel.getRoleId());
				userRole.setLastUpdateTime(new Date());
				userRole.setLastUpdateUser(userId);
				userRoleService.save(userRole);
			}
			//删除旧管理城市数据，并保存新数据
			List<UserCityManage> ucmList = userCityManageService.findByHql("from UserCityManage where status<>'"+StateEnum.DELETESTATE.getCode()+"' and userId='"+userModel.getUserId()+"'");
			if(!ChkUtil.isEmptyAllObject(ucmList)) {
				for(UserCityManage ucm : ucmList) {
					userCityManageService.delete(ucm);
				}
			}
			//保存管理城市景点数据（管理城市设置不能重复）
			UserCityManage manage = null;
			for(UserCityManageModel manageModel : userModel.getManageList()) {
				manage = new UserCityManage(manageModel);
				manage.setUserId(user.getId());
				manage.setCreateTime(new Date());
				manage.setCreateUser(userId);
				manage.setLastUpdateTime(new Date());
				manage.setLastUpdateUser(userId);
				manage.setStatus(StateEnum.NEWSTATE.getCode());
				userCityManageService.save(manage);
			}
		} catch (Exception e) {
			throw new ResultException(ResultEnum.DATA_ERROR, token, userId, null);
		}
		return null;
	}

	/**
	 * 员工离职接口
	 */
	@Override
	public Object doDisableUsersInfo(String usersId, String userId, String token) {
		//员工Id校验
    	OptionUtil.of(usersId).getOrElseThrow(() -> 
    		new ResultException(-2,"员工Id为空!", token, userId, null));
    	//获取对应游客信息
		try {
			User user = userDao.get(usersId);
			if(null == usersId || ChkUtil.isEmptyAllObject(user.getStatus()) 
	    			|| user.getStatus().equals(StateEnum.DELETESTATE.getCode())) {
				throw new ResultException(-2, "该游客数据异常！", token, userId, null);
	    	}
			//判断是否已是离职状态
			if(user.getStatus().equals(StateEnum.DISABLESTATE.getCode())) {
				throw new ResultException(-2, "该员工已处于离职状态，请勿重复操作！", token, userId, null);
			}
			user.setStatus(StateEnum.DISABLESTATE.getCode());
			user.setLastUpdateTime(new Date());
			user.setLastUpdateUser(userId);
			userDao.save(user);
			//获取手机号为user.getPhone的员工，撤销其VIP
			Visitors existVisitors = visitorsService.getByHql("from Visitors where status<>'"+StateEnum.DELETESTATE.getCode()+"' and phone='"+user.getPhone()+"'");
			if(null != existVisitors) {
				existVisitors.setVisitorsType(VisitorsTypeEnum.NORMAL.getCode());
				existVisitors.setLastUpdateTime(new Date());
				existVisitors.setLastUpdateUser(userId);
				visitorsService.save(existVisitors);
			}
		} catch (ResultException e) {
			throw e;
		} catch (Exception e) {
			throw new ResultException(-3, e.getMessage(), token, userId, null);
		}
		return null;
	}

	/**
	 * 员工离职转在职接口
	 */
	@Override
	public Object doEnableUsersInfo(String usersId, String userId, String token) {
		//员工Id校验
    	OptionUtil.of(usersId).getOrElseThrow(() -> 
    		new ResultException(-2,"员工Id为空!", token, userId, null));
    	//获取对应游客信息
		try {
			User user = userDao.get(usersId);
			if(null == usersId || ChkUtil.isEmptyAllObject(user.getStatus()) 
	    			|| user.getStatus().equals(StateEnum.DELETESTATE.getCode())) {
				throw new ResultException(-2, "该游客数据异常！", token, userId, null);
	    	}
			//判断是否已是离职状态
			if(user.getStatus().equals(StateEnum.NEWSTATE.getCode())) {
				throw new ResultException(-2, "该员工已处于在职状态，请勿重复操作！", token, userId, null);
			}
			user.setStatus(StateEnum.NEWSTATE.getCode());
			user.setLastUpdateTime(new Date());
			user.setLastUpdateUser(userId);
			userDao.save(user);
			//获取手机号为user.getPhone的员工，撤销其VIP
			Visitors existVisitors = visitorsService.getByHql("from Visitors where status<>'"+StateEnum.DELETESTATE.getCode()+"' and phone='"+user.getPhone()+"'");
			if(null != existVisitors) {
				existVisitors.setVisitorsType(VisitorsTypeEnum.VIP.getCode());
				existVisitors.setLastUpdateTime(new Date());
				existVisitors.setLastUpdateUser(userId);
				visitorsService.save(existVisitors);
			}
		} catch (ResultException e) {
			throw e;
		} catch (Exception e) {
			throw new ResultException(-3, e.getMessage(), token, userId, null);
		}
		return null;
	}
	
	
}