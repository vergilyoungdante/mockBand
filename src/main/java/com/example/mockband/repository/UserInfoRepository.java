package com.example.mockband.repository;

import com.example.mockband.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @BelongsProject: mockBand
 * @BelongsPackage: com.example.mockband.repository
 * @Author: vergil young
 * @CreateTime: 2023-04-15  11:06
 * @Description: TODO
 */

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
    public UserInfo findByLoginName(String loginName);
}
