package com.example.managementsystem.Service;

import com.example.managementsystem.DAO.AssessBatchDAO;
import com.example.managementsystem.DAO.AssessGroupDAO;
import com.example.managementsystem.DAO.UserDAO;
import com.example.managementsystem.DAO.UserGradeDAO;
import com.example.managementsystem.Model.AssessBatchPO;
import com.example.managementsystem.Model.AssessGroupPO;
import com.example.managementsystem.Model.UserGradePO;
import com.example.managementsystem.Model.UserPO;
import com.example.managementsystem.util.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Integer.min;

@Service
public class AssessBatchService {
    @Autowired
    private AssessBatchDAO assessBatchDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserGradeDAO userGradeDAO;

    @Autowired
    private AssessGroupDAO assessGroupDAO;

    public ResultInfo getAllAssessBatch(int userId, int page, int limit){
        UserPO user = userDAO.getUserById(userId).get(0);
        int groupId = user.getAssessGroupId();
        List<AssessBatchPO> assessBatches = assessBatchDAO.getAssessBatchByGroupId(groupId, page - 1, limit);
        List<Map<String, Object>> tempList = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();
        for(int i = 0;i < assessBatches.size();++i){
            Map<String, Object> temp = new HashMap<>();
            temp.put("index", i + 1);
            temp.put("title", assessBatches.get(i).getTitle());
            temp.put("assess_batch_id", assessBatches.get(i).getAssess_batch_id());
            tempList.add(temp);
        }
        result.put("count", assessBatchDAO.getAssessBatchAmountByGroupId(groupId));
        result.put("results", tempList);
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo createAssessBatch(Map<String, Object> assessBatchInfo){
        AssessBatchPO assessBatch = new AssessBatchPO();
        assessBatch.setGroupId((int)assessBatchInfo.get("group_id"));
        assessBatch.setIs_use((int)assessBatchInfo.get("is_use"));
        assessBatch.setQuarter((int)assessBatchInfo.get("quarter"));
        assessBatch.setTitle((String)assessBatchInfo.get("title"));
        assessBatch.setYear((int)assessBatchInfo.get("year"));
        assessBatchDAO.createAssessBatch(assessBatch);
        return new ResultInfo(0, "success", new HashMap<>());
    }

    public ResultInfo updateAssessBatch(Map<String, Object> assessBatchInfo){
        AssessBatchPO assessBatch = assessBatchDAO.getAssessBatchById((int)assessBatchInfo.get("assess_batch_id")).get(0);
        assessBatch.setTitle((String)assessBatchInfo.get("title"));
        assessBatch.setIs_use((int)assessBatchInfo.get("is_use"));
        assessBatchDAO.updateAssessBatch(assessBatch);
        return new ResultInfo(200, "success", new HashMap<>());
    }

    public ResultInfo deleteAssessBatchById(int assessBatchId){
        assessBatchDAO.deleteAssessBatchById(assessBatchId);

        // 删除关联到的所有UserGrade
        List<UserGradePO> userGrades = userGradeDAO.getUserGradeByAssessBatchId(assessBatchId);
        for(int i = 0;i < userGrades.size(); ++i){
            userGradeDAO.deleteUserGradeById(userGrades.get(i).getGrade_id());
        }
        return new ResultInfo(200, "success", new HashMap<>());
    }

    public ResultInfo getAssessBatchByUserIdAndTitle(int userId, String title, int page, int limit){
        UserPO user = userDAO.getUserById(userId).get(0);
        int groupId = user.getAssessGroupId();
        List<AssessBatchPO> assessBatches;
        if(title.equals("")){
            assessBatches = assessBatchDAO.getAssessBatchByGroupId(groupId, page, limit);
        } else {
            assessBatches = assessBatchDAO.getAssessBatchByGroupIdAndTitle(groupId, title, page - 1, limit);
        }
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        for(int i = 0;i < assessBatches.size(); ++i){
            AssessBatchPO assessBatch = assessBatches.get(i);
            Map<String, Object> temp = new HashMap<>();
            temp.put("title", assessBatch.getTitle());
            temp.put("assess_batch_id", assessBatch.getAssess_batch_id());
            temp.put("index", i + 1);
            tempList.add(temp);
        }
        result.put("count", assessBatchDAO.getAssessBatchAmountByGroupIdAndTitle(groupId, title));
        result.put("assess_batches", tempList);
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo getAdminAssessBatchByOrganizationId(int organizationId, int isOrganization, int page, int limit){
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

        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        for(int i = (page - 1) * limit;i < min(assessBatchIdList.size(), page * limit); ++i){
            Map<String, Object> temp = new HashMap<>();
            AssessBatchPO assessBatch = assessBatchDAO.getAssessBatchById(assessBatchIdList.get(i)).get(0);
            temp.put("assess_batch_id", assessBatch.getAssess_batch_id());
            temp.put("assess_batch_name", assessBatch.getTitle());
            AssessGroupPO group = assessGroupDAO.getGroupById(assessBatch.getGroupId()).get(0);
            temp.put("assess_group_name", group.getName());
            temp.put("is_use", assessBatch.getIs_use());
            String[] groupMemberList = group.getMemberList().split(",");
            if(!groupMemberList[0].equals("")){
                int cnt = 0;
                List<UserGradePO> userGrades = userGradeDAO.getUserGradeByAssessBatchId(assessBatchIdList.get(i));
                for(int j = 0;j < userGrades.size(); ++j){
                    UserGradePO userGrade = userGrades.get(j);
                    if(userGrade.getGradeRank().equals("优秀")){
                        cnt = cnt + 1;
                    }
                }
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                String batchResult = decimalFormat.format(cnt * 1.0 / groupMemberList.length * 100) + "%/" + groupMemberList.length;
                temp.put("batch_result", batchResult);
            } else {
                temp.put("batch_result", "0.00%/0");
            }
            temp.put("time", assessBatch.getYear() + "年第" + assessBatch.getQuarter() + "季度");
            tempList.add(temp);
        }
        result.put("count", assessBatchIdList.size());
        result.put("assess_batch", tempList);
        return new ResultInfo(200, "success", result);
    }

    public ResultInfo getAssessBatchByOrganizationIdAndTitle(int organizationId, int isOrganization, String title, int page, int limit){
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
                if(!assessBatches.get(j).getTitle().equals(title) && !title.equals(""))continue;
                assessBatchIdList.add(assessBatches.get(j).getAssess_batch_id());
            }
        }
        // assessBatchIdList 去重
        assessBatchIdList.stream().distinct().collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> tempList = new ArrayList<>();
        for(int i = (page - 1) * limit;i < min(assessBatchIdList.size(), page * limit); ++i){
            Map<String, Object> temp = new HashMap<>();
            AssessBatchPO assessBatch = assessBatchDAO.getAssessBatchById(assessBatchIdList.get(i)).get(0);
            temp.put("assess_batch_id", assessBatch.getAssess_batch_id());
            temp.put("assess_batch_name", assessBatch.getTitle());
            AssessGroupPO group = assessGroupDAO.getGroupById(assessBatch.getGroupId()).get(0);
            temp.put("assess_group_name", group.getName());
            temp.put("is_use", assessBatch.getIs_use());
            String[] groupMemberList = group.getMemberList().split(",");
            if(!groupMemberList[0].equals("")){
                int cnt = 0;
                List<UserGradePO> userGrades = userGradeDAO.getUserGradeByAssessBatchId(assessBatchIdList.get(i));
                for(int j = 0;j < userGrades.size(); ++j){
                    UserGradePO userGrade = userGrades.get(j);
                    if(userGrade.getGradeRank().equals("优秀")){
                        cnt = cnt + 1;
                    }
                }
                DecimalFormat decimalFormat = new DecimalFormat("#.##");
                String batchResult = decimalFormat.format(cnt * 1.0 / groupMemberList.length * 100) + "%/" + groupMemberList.length;
                temp.put("batch_result", batchResult);
            } else {
                temp.put("batch_result", "0.00%/0");
            }
            temp.put("time", assessBatch.getYear() + "年第" + assessBatch.getQuarter() + "季度");
            tempList.add(temp);
        }
        result.put("count", assessBatchIdList.size());
        result.put("assess_batch", tempList);
        return new ResultInfo(200, "success", result);
    }
}
