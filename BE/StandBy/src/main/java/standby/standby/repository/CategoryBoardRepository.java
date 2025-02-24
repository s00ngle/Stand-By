package standby.standby.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import standby.standby.entity.Board;
import standby.standby.entity.CategoryBoard;

import java.util.List;
@Repository
public interface CategoryBoardRepository extends JpaRepository<CategoryBoard, Long> {
    List<CategoryBoard> findByBoard(Board board);

    void deleteByBoard(Board board);

    // 구인자 프로필에서 가장 많이 선택한 장르 1개와 역할 최대 3개까지 가져오는 쿼리
    @Query(value = """
    (
        SELECT c.category_name
        FROM category_board cb
        JOIN category c ON cb.category_id = c.category_id
        JOIN board b ON cb.board_id = b.board_id
        WHERE b.author_id = :userId AND c.is_genre = TRUE
        GROUP BY c.category_name
        ORDER BY COUNT(c.category_id) DESC
        LIMIT 1
    )
    UNION ALL
    (
        SELECT c.category_name
        FROM category_board cb
        JOIN category c ON cb.category_id = c.category_id
        JOIN board b ON cb.board_id = b.board_id
        WHERE b.author_id = :userId AND c.is_genre = FALSE
        GROUP BY c.category_name
        ORDER BY COUNT(c.category_id) DESC
        LIMIT 3
    )
    """, nativeQuery = true)
    List<String> findTopCategoriesByEmployer(@Param("userId") Long userId);



    // 구직자가 가장 많이 지원한 장르 1개 & 역할 최대 3개 조회
    @Query(value = """
(
    -- 지원한 게시글에서 가장 많이 선택된 장르 1개 가져오기
    SELECT c.category_name
    FROM category_board cb
    JOIN category c ON cb.category_id = c.category_id
    JOIN board b ON cb.board_id = b.board_id
    JOIN application_status a ON a.board_id = b.board_id
    WHERE a.applicant_id = :userId
      AND c.is_genre = TRUE  -- 장르 필터 (게시글에 등록된 장르)
    GROUP BY c.category_name
    ORDER BY COUNT(*) DESC  -- 가장 많이 선택된 장르 우선
    LIMIT 1
)
UNION ALL
(
    -- 지원자가 직접 선택한 역할 중에서 가장 많이 선택한 역할 최대 3개 가져오기
    SELECT c.category_name
    FROM category_application ca
    JOIN category c ON ca.category_id = c.category_id
    JOIN application_status a ON ca.application_status_id = a.application_status_id
    WHERE a.applicant_id = :userId
      AND c.is_genre = FALSE  -- 역할 필터 (지원자가 선택한 역할)
    GROUP BY c.category_name
    ORDER BY COUNT(*) DESC  -- 가장 많이 선택된 역할 우선
    LIMIT 3
);
""", nativeQuery = true)
    List<String> findTopCategoriesByApplicant(@Param("userId") Long userId);



}
