package standby.standby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import standby.standby.entity.Attachment;
import standby.standby.entity.Board;
import standby.standby.entity.Workplace;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment,Long> {
   List<Attachment> findByBoard(Board board);

}
