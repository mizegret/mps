package com.mizegret.mps.mps_api.repositories;

import com.mizegret.mps.mps_api.entities.Product;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, UUID> {}
