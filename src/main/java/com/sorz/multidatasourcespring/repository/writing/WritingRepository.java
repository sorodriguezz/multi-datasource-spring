package com.sorz.multidatasourcespring.repository.writing;

import com.sorz.multidatasourcespring.model.writing.WritingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WritingRepository extends JpaRepository<WritingEntity, Long> {
}
