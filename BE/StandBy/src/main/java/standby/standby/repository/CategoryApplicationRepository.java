package standby.standby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import standby.standby.entity.ApplicationStatus;
import standby.standby.entity.Category;
import standby.standby.entity.CategoryApplication;

import java.util.List;

@Repository
public interface CategoryApplicationRepository extends JpaRepository<CategoryApplication, Long> {

    CategoryApplication findByApplicationStatus(ApplicationStatus applicationStatus);

    void deleteByApplicationStatus(ApplicationStatus applicationStatus);
}
