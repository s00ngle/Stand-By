package standby.standby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import standby.standby.dto.boardCommon.WorkplaceDto;
import standby.standby.entity.Board;
import org.springframework.data.jpa.repository.Query;
import standby.standby.entity.Workplace;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkplaceRepository extends JpaRepository<Workplace,Long> {
    List<Workplace> findByBoard(Board board);

    void deleteByBoard(Board board);

}
