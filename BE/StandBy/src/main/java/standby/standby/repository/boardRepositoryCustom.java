package standby.standby.repository;
import standby.standby.entity.Board;

import java.util.List;

public interface boardRepositoryCustom {
    List<Board> searchBoardsByKeywordsAndCategories(List<String> keywords, List<String> categories);
}
