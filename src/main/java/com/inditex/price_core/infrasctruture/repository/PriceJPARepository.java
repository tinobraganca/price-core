package com.inditex.price_core.infrasctruture.repository;

import com.inditex.price_core.infrasctruture.entity.PriceEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceJPARepository extends JpaRepository<PriceEntity, Long> {
    @Query("SELECT p FROM PriceEntity p WHERE p.brandId = :brandId AND p.productId = :productId " +
            "AND :applicationDate BETWEEN p.startDate AND p.endDate ORDER BY p.priority DESC")
    List<PriceEntity> findApplicablePrice(Long brandId, Long productId, LocalDateTime applicationDate);

}
