package com.example.managementsystem.Model.repository;

import com.example.managementsystem.Model.UserGradePO;
import com.example.managementsystem.Model.tempQueryEntity.QueryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserGradeRepository extends CrudRepository<UserGradePO, Integer> {
    @Query("SELECT t FROM UserGradePO t WHERE t.assessBatchId = :assessBatchId")
    List<UserGradePO> getUserGradeByAssessBatchId(@Param("assessBatchId") int assessBatchId);

    @Query("SELECT new QueryEntity(t.grade_id, t.gradeRank, t.gradeRank) FROM UserGradePO t LEFT JOIN UserPO u ON t.userId = u.user_id WHERE u.name=:userName AND t.assessBatchId=:assessBatchId")
    List<QueryEntity> getUserGradeByAssessBatchIdAndUserName(@Param("userName") String userName, @Param("assessBatchId") int assessBatchId);

    @Query("SELECT t FROM UserGradePO t WHERE t.grade_id = :userGradeId")
    List<UserGradePO> getUserGradeById(@Param("userGradeId") int userGradeId);
}
