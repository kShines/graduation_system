package com.example.managementsystem.Controller;

import com.example.managementsystem.Service.UserGradeService;
import com.example.managementsystem.Service.UserService;
import com.example.managementsystem.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.resource.HttpResource;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/api")
public class UserGradeController {

    @Autowired
    private UserGradeService userGradeService;



    @RequestMapping(value="getLatestGrade", method = RequestMethod.POST)
    public ResultInfo getLatestGrade(@RequestBody Map<String, Object> jsonParam){
        return userGradeService.getLatestGrade(
                (int)jsonParam.get("user_id"),
                (int)jsonParam.get("page"),
                (int)jsonParam.get("limit")
        );
    }

    @RequestMapping(value="getUserGradeByAssessBatchId", method = RequestMethod.POST)
    public ResultInfo getUserGradeByAssessBatchId(@RequestBody Map<String, Object> jsonParam){
        return userGradeService.getUserGradeByAssessBatchId(
                (int)jsonParam.get("assess_batch_id"),
                (int)jsonParam.get("page"),
                (int)jsonParam.get("limit")
        );
    }

    @RequestMapping(value="getLatestGradeByName", method = RequestMethod.POST)
    public ResultInfo getLatestGradeByName(@RequestBody Map<String, Object> jsonParam){
        return userGradeService.getLatestUserGradeByName(
                (int)jsonParam.get("user_id"),
                (String)jsonParam.get("name"),
                (int)jsonParam.get("page"),
                (int)jsonParam.get("limit")
        );
    }

    @RequestMapping(value = "uploadUserGrade", method = RequestMethod.POST)
    public ResultInfo uploadUserGrade(MultipartFile file, int assess_batch_id) {
        return userGradeService.uploadUserGrade(file, assess_batch_id);
    }

    @RequestMapping(value = "downloadUserGrade", method = RequestMethod.GET, produces="application/octet-stream")
    public void downloadUserGrade(int organization_id, int is_organization, HttpServletResponse response){
        userGradeService.downloadUserGrade(
                organization_id,
                is_organization,
                response
        );
    }

    @RequestMapping(value = "updateUserGradeByIdList", method = RequestMethod.POST)
    public ResultInfo updateUserGradeByIdList(@RequestBody Map<String, Object> jsonParam){
        return userGradeService.updateUserGradeByIdList(
                (List<Map<String, Object>>) jsonParam.get("user_grade_info")
        );
    }

    @RequestMapping(value = "deleteUserGradeById", method = RequestMethod.POST)
    public ResultInfo deleteUserGradeById(@RequestBody Map<String, Object> jsonParam){
        return userGradeService.deleteUserGradeById(
                (int)jsonParam.get("grade_id")
        );
    }
}
