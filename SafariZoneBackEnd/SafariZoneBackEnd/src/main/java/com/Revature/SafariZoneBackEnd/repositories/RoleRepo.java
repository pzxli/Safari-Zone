package com.Revature.SafariZoneBackEnd.repositories;

import com.Revature.SafariZoneBackEnd.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<UserRole, Integer> {
}
