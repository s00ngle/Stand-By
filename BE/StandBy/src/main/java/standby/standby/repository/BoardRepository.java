package standby.standby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import standby.standby.entity.Board;

import java.util.List;
import java.util.Optional;


@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, boardRepositoryCustom {

    @Query("SELECT b FROM Board b WHERE b.deletedAt IS NULL AND b.status = TRUE ORDER BY COALESCE(b.updatedAt, b.createdAt) DESC")
    List<Board> findAllBoards();



    // 특정 유저가 작성한 구인 중 게시글 조회
    List<Board> findByAuthor_UserIdAndStatus(Long userId, Boolean status);

    //  특정 게시글 ID로 게시글 조회 (평가 및 첨부파일 포함)
    Optional<Board> findById(Long boardId);


    // 구인자가 올린 게시글 갯수
    long countByAuthor_UserId(Long userId);
}



