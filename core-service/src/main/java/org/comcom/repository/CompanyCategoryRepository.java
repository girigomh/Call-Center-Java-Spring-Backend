package org.comcom.repository;

import org.comcom.model.Company_Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyCategoryRepository extends JpaRepository<Company_Category, Long> {
}
