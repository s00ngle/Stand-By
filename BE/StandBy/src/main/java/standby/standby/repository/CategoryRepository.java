package standby.standby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import standby.standby.entity.Category;
import standby.standby.entity.Evaluation;
import standby.standby.entity.User;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


    //  parentId가 아니라 categoryBoards를 통해 찾기
    @Query("SELECT c FROM Category c JOIN c.categoryBoards cb WHERE cb.board.boardId = :boardId")
    List<Category> findByBoardId(@Param("boardId") Long boardId);


    Category findByCategoryName(String categoryName);
}
