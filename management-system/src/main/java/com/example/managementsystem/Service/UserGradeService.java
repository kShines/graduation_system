package com.example.managementsystem.Service;

import com.example.managementsystem.DAO.*;
import com.example.managementsystem.Model.*;
import com.example.managementsystem.Model.tempQueryEntity.QueryEntity;
import com.example.managementsystem.util.ExcelUtil;
import com.example.managementsystem.util.FileUtil;
import com.example.managementsystem.util.ResultInfo;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Math.min;


@Service
public class UserGradeService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserGradeDAO userGradeDAO;

    @Autowired
    private AssessBatchDAO assessBatchDAO;

    @Autowired
    private AssessGroupDAO assessGroupDAO;

    @Autowired
    private OrganizationDAO organizationDAO;

    public ResultInfo getLatestGrade(int userId, int page, int limit){
        Map<String, Object> result = new HashMap<>();
        UserPO user = userDAO.getUserById(userId).get(0);
        int groupId = user.getAssessGroupId();
        List<AssessBatchPO> assessBatches = assessBatchDAO.getLatestAssessBatchByGroupId(groupId);
        System.out.println("debug: "+assessBatches.size());
        if(assessBatches.size()==0){
            return new ResultInfo(200,"success",result);
        }
        int assessBatchId = assessBatches.get(0).getAssess_batch_id();
        List<UserGradePO>userGrades = userGradeDAO.getUserGradeByAssessBatchId(assessBatchId);


        List<Map<String, Object>> tempList = new ArrayList<>();
        int index = 0;
        for(int i = (page - 1) * limit;i < min(page * limit, userGrades.size()); ++i){
            UserGradePO userGrade = userGrades.get(i);
            Map<String,Object> temp = new HashMap<>();
            temp.put("index", ++index);
            temp.put("grade_id", userGrade.getGrade_id());
            UserPO gradeUser = userDAO.getUserById(userGrade.getUserId()).get(0);
            temp.put("name", gradeUser.getName());
            temp.put("grade_rank", userGrade.getGradeRank());
            temp.put("title", assessBatches.get(0).getTitle());
            tempList.add(temp);
        }
        result.put("count", userGrades.size());
        result.put("results", tempList);
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo getUserGradeByAssessBatchId(int assessBatchId, int page, int limit){
        AssessBatchPO assessBatch = assessBatchDAO.getAssessBatchById(assessBatchId).get(0);
        List<UserGradePO>userGrades = userGradeDAO.getUserGradeByAssessBatchId(assessBatchId);
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        int index = 0;

        for(int i = (page - 1) * limit;i < min(userGrades.size(), page * limit); ++i){
            UserGradePO userGrade = userGrades.get(i);
            Map<String,Object> temp = new HashMap<>();
            temp.put("index", ++index);
            temp.put("grade_id", userGrade.getGrade_id());
            UserPO gradeUser = userDAO.getUserById(userGrade.getUserId()).get(0);
            temp.put("name", gradeUser.getName());
            temp.put("grade_rank", userGrade.getGradeRank());
            temp.put("democracy_grade",userGrade.getDemocracyGrade());
            temp.put("leader_grade",userGrade.getLeaderGrade());
            temp.put("idNumber", gradeUser.getIdNumber());
            temp.put("title", assessBatch.getTitle());
            tempList.add(temp);
        }
        result.put("count", userGrades.size());
        result.put("grades", tempList);
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo getLatestUserGradeByName(int userId, String name, int page, int limit){
        UserPO user = userDAO.getUserById(userId).get(0);
        int groupId = user.getAssessGroupId();
        List<AssessBatchPO> assessBatches = assessBatchDAO.getLatestAssessBatchByGroupId(groupId);
        int assessBatchId = assessBatches.get(0).getAssess_batch_id();
        List<QueryEntity> tempEntity = userGradeDAO.getUserGradeByAssessBatchIdAndUserName(assessBatchId, name);
        List<Map<String, Object>> tempList = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();
        int index = 0;
        for(int i = (page - 1) * limit; i < min(tempEntity.size(), page * limit); ++i){
            QueryEntity queryEntity = tempEntity.get(i);
            Map<String, Object> temp = new HashMap<>();
            temp.put("index", ++index);
            temp.put("grade_id", queryEntity.getId());
            temp.put("name", name);
            temp.put("title", assessBatches.get(0).getTitle());
            temp.put("grade_rank", queryEntity.getTitle());
            tempList.add(temp);
        }
        result.put("count", tempEntity.size());
        result.put("grades", tempList);
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo uploadUserGrade(MultipartFile file, int assessBatchId){
        ArrayList<ArrayList<String>> grade = FileUtil.analysis(file);
        List<Map<String, Object>> isNotExist = new ArrayList<>();
        for(int i = 0;i < grade.size(); ++i){
            UserGradePO userGrade = new UserGradePO();
            String username = grade.get(i).get(7);
            List<UserPO> users = userDAO.getUserByUsername(username);
            UserPO user;
            if(users.size() == 0){
                Map<String, Object> temp = new HashMap<>();
                temp.put("name", grade.get(i).get(1));
                temp.put("username", username);
                isNotExist.add(temp);
            } else {
                user = users.get(0);
                userGrade.setUserId(user.getUser_id());
                userGrade.setAssessBatchId(assessBatchId);
                userGrade.setGradeRank(grade.get(i).get(4));
                userGradeDAO.createUserGrade(userGrade);
            }
        }
        return new ResultInfo(200, "success", isNotExist);
    }

    public ResultInfo downloadUserGrade(int organizationId, int isOrganization, HttpServletResponse response){
        // 获取某一机构下所有 考察组 的Id
        List<Integer> assessGroupIdList = new ArrayList<>();
        if(isOrganization == 1){
            assessGroupIdList = assessGroupDAO.getTotalManageGroupIdByOrganizationId(organizationId);
        } else {
            assessGroupIdList.add(organizationId);
        }

        // 根据 assessGroupIdList 获取 assessBatchIdList
        List<Integer> assessBatchIdList = new ArrayList<>();
        for(int i = 0;i < assessGroupIdList.size(); ++i){
            List<AssessBatchPO> assessBatches = assessBatchDAO.getTotalAssessBatchByGroupId(assessGroupIdList.get(i));
            for(int j = 0;j < assessBatches.size(); ++j){
                assessBatchIdList.add(assessBatches.get(j).getAssess_batch_id());
            }
        }
        // assessBatchIdList 去重
        assessBatchIdList.stream().distinct().collect(Collectors.toList());

        List<List<String>> excelInfo = new ArrayList<>();
        List<String> title = new ArrayList<>();
        title.add("姓名");title.add("电话号码");title.add("身份证号");title.add("所属机构");
        title.add("考核机构");title.add("考核组");title.add("考核周期");title.add("成绩");
        for(int i = 0;i < assessBatchIdList.size(); ++i){
            AssessBatchPO assessBatch = assessBatchDAO.getAssessBatchById(assessBatchIdList.get(i)).get(0);
            List<UserGradePO> userGrades = userGradeDAO.getUserGradeByAssessBatchId(assessBatchIdList.get(i));
            for(int j = 0;j < userGrades.size(); ++j){
                List<String> temp = new ArrayList<>();
                UserGradePO userGrade = userGrades.get(j);
                UserPO user = userDAO.getUserById(userGrade.getUserId()).get(0);
                temp.add(user.getName());
                temp.add(user.getUsername());
                temp.add(user.getIdNumber());
                if(user.getOrganization() != null){
                    OrganizationPO organization = organizationDAO.getOrganizationById(user.getOrganization()).get(0);
                    temp.add(organization.getName());
                } else {
                    temp.add("");
                }
                if(user.getAssessOrganizationId() != null){
                    OrganizationPO assessOrganization = organizationDAO.getOrganizationById(user.getAssessOrganizationId()).get(0);
                    temp.add(assessOrganization.getName());
                } else {
                    temp.add("");
                }
                if(user.getAssessGroupId() != null){
                    AssessGroupPO assessGroup = assessGroupDAO.getGroupById(user.getAssessGroupId()).get(0);
                    temp.add(assessGroup.getName());
                } else {
                    temp.add("");
                }
                temp.add(assessBatch.getYear() + "年第" + assessBatch.getQuarter() + "周期");
                temp.add(userGrade.getGradeRank());
                excelInfo.add(temp);
            }
        }
        String fileName;
        if(isOrganization == 1){
            OrganizationPO organization = organizationDAO.getOrganizationById(organizationId).get(0);
            fileName = organization.getName() + "考核成绩";
        } else {
            AssessGroupPO assessGroup = assessGroupDAO.getGroupById(organizationId).get(0);
            fileName = assessGroup.getName() + "考核成绩";
        }

        XSSFWorkbook wb = ExcelUtil.getXSSFWorkbook("考核成绩", title, excelInfo);

        try {
            fileName = new String(fileName.getBytes(), "UTF-8");
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("utf-8"),"ISO8859-1") + ".xls");
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResultInfo(200, "success", new HashMap<>());
    }

    public ResultInfo updateUserGradeByIdList(List<Map<String, Object>> userGradeInfoList){
        for (int i = 0;i < userGradeInfoList.size(); ++i){
            Map<String, Object> userGradeInfo = userGradeInfoList.get(i);
            UserGradePO userGrade = userGradeDAO.getUserGrade((int)userGradeInfo.get("grade_id")).get(0);
            if(userGrade.getGradeRank().equals(userGradeInfo.get("grade_rank"))){
                continue;
            } else {
                userGrade.setGradeRank((String) userGradeInfo.get("grade_rank"));
                userGradeDAO.updateUserGrade(userGrade);
            }
        }
        return new ResultInfo(200,"success", new HashMap<>());
    }

    public ResultInfo deleteUserGradeById(int userGradeId){
        userGradeDAO.deleteUserGradeById(userGradeId);
        return new ResultInfo(200,"success", new HashMap<>());
    }
}
