package standby.standby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import standby.standby.entity.PortfolioCardImage;

@Repository
public interface PortfolioCardImageRepository extends JpaRepository<PortfolioCardImage, Long> {

}
