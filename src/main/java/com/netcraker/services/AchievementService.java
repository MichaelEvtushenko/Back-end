package com.netcraker.services;

import com.netcraker.model.Achievement;
import com.netcraker.model.constants.TableName;
import com.netcraker.model.vo.AchievementReq;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface AchievementService {
    Optional<Achievement> getAchievementById(int achievementId);
    @NonNull List<Achievement> getAchievementsByTableName(TableName tableName);
    Optional<Achievement> getAchievementByName(String name);
    Optional<Achievement> createAchievement(AchievementReq achievement);
    boolean deleteAchievement(int achievementId);
}
