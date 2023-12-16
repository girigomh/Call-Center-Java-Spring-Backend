package org.comcom.config.repository;

import org.comcom.config.model.Company_Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyCategoryRepository extends JpaRepository<Company_Category, Long> {
}
