package com.sorz.multidatasourcespring.repository.reading;

import com.sorz.multidatasourcespring.model.reading.ReadingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingRepository extends JpaRepository<ReadingEntity, Long> {
}
