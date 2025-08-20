package com.mizegret.mps.mps_api.repositories;

import com.mizegret.mps.mps_api.entities.Site;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteRepository extends JpaRepository<Site, UUID> {}
