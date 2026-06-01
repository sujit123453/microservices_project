package com.bluethinkInc.user_service.repository;

import com.bluethinkInc.user_service.model.ActiveUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActiveUserRepo extends JpaRepository<ActiveUser,Long> {
}
