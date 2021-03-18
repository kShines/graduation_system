package com.example.managementsystem.Controller;

import com.example.managementsystem.Service.UserService;
import com.example.managementsystem.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value="getUserById", method= RequestMethod.POST)
    public ResultInfo getUserById(@RequestBody Map<String, Object> jsonParam){
        return userService.getUserById((int)jsonParam.get("userId"));
    }

    @RequestMapping(value="getUserInfoById", method = RequestMethod.POST)
    public ResultInfo getUserInfoById(@RequestBody Map<String, Object> jsonParam){
        return userService.getUserById((int)jsonParam.get("user_id"));
    }

    @RequestMapping(value="login", method=RequestMethod.POST)
    public ResultInfo login(@RequestBody Map<String, Object> jsonParam){
        return userService.login((String)jsonParam.get("username")
                ,(String)jsonParam.get("password")
                ,(String)jsonParam.get("is_admin"));
    }

    @RequestMapping(value="modifyUserInfo", method = RequestMethod.POST)
    public ResultInfo modifyUserInfo(@RequestBody Map<String, Object> jsonParam){
        return userService.modifyUserInfo(jsonParam);
    }

    @RequestMapping(value="deleteUserById", method = RequestMethod.POST)
    public ResultInfo deleteUserById(@RequestBody Map<String, Object> jsonParam){
        return userService.deleteUserById((int)jsonParam.get("user_id"));
    }

    @RequestMapping(value="createUser", method = RequestMethod.POST)
    public ResultInfo createUser(@RequestBody Map<String, Object> jsonParam){
        return userService.createUser(jsonParam);
    }

    @RequestMapping(value="getAdminOrganizationByUserId", method = RequestMethod.POST)
    public ResultInfo getAdminOrganizationByUserId(@RequestBody Map<String, Object> jsonParam){
        return userService.getAdminOrganizationByUserId((int)jsonParam.get("user_id"));
    }

    @RequestMapping(value="getAdminUserByUserId", method = RequestMethod.POST)
    public ResultInfo getAdminUserByUserId(@RequestBody Map<String, Object> jsonParam){
        return userService.getTotalManageUserIdByAdminUserId(
                (int)jsonParam.get("user_id"),
                (int)jsonParam.get("page"),
                (int)jsonParam.get("limit")
        );
    }

    // 管理员用户查看 某个组织 包括其下属的所有用户
    @RequestMapping(value="getAdminUserByOrganizationId", method = RequestMethod.POST)
    public ResultInfo getAdminUserByOrganizationId(@RequestBody Map<String, Object> jsonParam){
        return userService.getAdminUserByOrganizationId(
                (int)jsonParam.get("user_id"),
                (int)jsonParam.get("organization_id"),
                (int)jsonParam.get("is_organization"),
                (int)jsonParam.get("page"),
                (int)jsonParam.get("limit")
        );
    }

    @RequestMapping(value="getTotalUser", method = RequestMethod.POST)
    public ResultInfo getTotalUser(@RequestBody Map<String, Object> jsonParam){
        return userService.getTotalUser(
                (int)jsonParam.get("page"),
                (int)jsonParam.get("limit")
        );
    }

    @RequestMapping(value="getUserByOrganizationIdAndName", method = RequestMethod.POST)
    public ResultInfo getUserByOrganizationIdAndName(@RequestBody Map<String, Object> jsonParam){
        return userService.getUserByOrganizationIdAndName(
                (int)jsonParam.get("user_id"),
                (int)jsonParam.get("organization_id"),
                (int)jsonParam.get("is_organization"),
                (String)jsonParam.get("name"),
                (int)jsonParam.get("page"),
                (int)jsonParam.get("limit")
        );
    }

    @RequestMapping(value="addAdminByOrganizationId", method = RequestMethod.POST)
    public ResultInfo addAdminByOrganizationId(@RequestBody Map<String, Object> jsonParam){
        return userService.addAdminByOrganizationId(
                (int)jsonParam.get("organization_id"),
                (int)jsonParam.get("is_organization"),
                (String)jsonParam.get("name"),
                (String)jsonParam.get("idNumber"),
                (String)jsonParam.get("username"),
                (int)jsonParam.get("sex"),
                (String)jsonParam.get("password"),
                (int)jsonParam.get("be_organization_id"),
                (String)jsonParam.get("position"),
                (int)jsonParam.get("be_assess_organization_id"),
                (String)jsonParam.get("assess_position")
        );
    }

    // 获某个组织的所有管理员
    @RequestMapping(value="getOrganizationAdminById", method = RequestMethod.POST)
    public ResultInfo getOrganizationAdminById(@RequestBody Map<String, Object> jsonParam){
        return userService.getOrganizationAdminById(
                (int)jsonParam.get("organization_id"),
                (int)jsonParam.get("is_organization"),
                (int)jsonParam.get("page"),
                (int)jsonParam.get("limit")
        );
    }

    @RequestMapping(value="updateUserInfoByIdList", method = RequestMethod.POST)
    public ResultInfo updateUserInfoByIdList(@RequestBody Map<String, Object> jsonParam){
        return userService.updateUserInfoByIdList((List<Map<String, Object>>) jsonParam.get("user_info"));
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResultInfo uploadFile(MultipartFile file, int organization_id, int is_organization) {
        return userService.uploadUser(
                file,
                organization_id,
                is_organization
        );

    }
}
