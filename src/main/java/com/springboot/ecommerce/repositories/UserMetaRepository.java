package com.springboot.ecommerce.repositories;

import com.springboot.ecommerce.entities.user.UserMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMetaRepository extends JpaRepository<UserMeta, Long> {

    @Query("select um " +
            "from UserMeta  as um " +
            "where um.user.id=?1")
    UserMeta getUserMetaByUser_Id(Long currentUserId);
}
