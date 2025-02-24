package standby.standby.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import standby.standby.entity.Board;

import java.util.ArrayList;
import java.util.List;

public class BoardRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Board> searchBoardsByKeywordsAndCategories(List<String> keywords, List<String> categories) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Board> query = cb.createQuery(Board.class);
        Root<Board> board = query.from(Board.class);
        Join<Board, ?> categoryBoardJoin = board.join("categoryBoards", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();
        List<Predicate> keywordPredicates = new ArrayList<>();
        List<Predicate> categoryPredicates = new ArrayList<>();

        // 🔹 키워드 검색 (LIKE 연산자 사용)
        if (keywords != null && !keywords.isEmpty()) {
            for (String keyword : keywords) {
                keywordPredicates.add(cb.or(
                        cb.like(cb.lower(board.get("title")), "%" + keyword.toLowerCase() + "%"),
                        cb.like(cb.lower(board.get("content")), "%" + keyword.toLowerCase() + "%")
                ));
            }
        }

        // 🔹 카테고리 검색 (IN 연산자 사용)
        if (categories != null && !categories.isEmpty()) {
            categoryPredicates.add(categoryBoardJoin.get("category").get("categoryName").in(categories));
        }

        // 🔹 삭제되지 않은 데이터만 조회 (항상 적용)
        Predicate notDeleted = cb.isNull(board.get("deletedAt"));
        Predicate isActive = cb.isTrue(board.get("status"));

        // 🔹 키워드 OR 카테고리 조건 설정
        if (!keywordPredicates.isEmpty() && !categoryPredicates.isEmpty()) {
            // 🔥 키워드 OR 카테고리 조건 적용
            predicates.add(cb.or(
                    cb.or(keywordPredicates.toArray(new Predicate[0])),
                    cb.or(categoryPredicates.toArray(new Predicate[0]))
            ));
        } else if (!keywordPredicates.isEmpty()) {
            // 🔹 키워드만 존재하면 키워드 조건만 추가
            predicates.add(cb.or(keywordPredicates.toArray(new Predicate[0])));
        } else if (!categoryPredicates.isEmpty()) {
            // 🔹 카테고리만 존재하면 카테고리 조건만 추가
            predicates.add(cb.or(categoryPredicates.toArray(new Predicate[0])));
        }

        // 🔹 삭제되지 않고 마감되지 않은 게시글만 조회
        predicates.add(notDeleted);
        predicates.add(isActive);

        // 🔹 WHERE 절 설정 (최종 적용)
        query.select(board).distinct(true)
                .where(cb.and(predicates.toArray(new Predicate[0])))  // 🔥 삭제 & 마감 상태 필터링은 AND로 처리
                .orderBy(cb.desc(cb.coalesce(board.get("updatedAt"), board.get("createdAt"))));

        return entityManager.createQuery(query).getResultList();
    }
}
