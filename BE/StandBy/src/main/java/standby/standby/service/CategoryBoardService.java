package standby.standby.service;

import org.springframework.stereotype.Service;
import standby.standby.dto.board.BoardRequestDto;
import standby.standby.dto.boardCommon.CategoryDto;
import standby.standby.entity.Board;
import standby.standby.entity.Category;
import standby.standby.entity.CategoryBoard;
import standby.standby.repository.CategoryBoardRepository;
import standby.standby.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryBoardService {
    CategoryRepository categoryRepository;
    CategoryBoardRepository categoryBoardRepository;

    public CategoryBoardService(CategoryRepository categoryRepository, CategoryBoardRepository categoryBoardRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryBoardRepository = categoryBoardRepository;
    }

    public void CreateCategory(Board board, BoardRequestDto requestDto){

        if(requestDto.getGenre() != null){ // 장르가 null이 아니면
            Category category = categoryRepository.findByCategoryName(requestDto.getGenre());
            CategoryBoard categoryBoard = new CategoryBoard();
            categoryBoard.setBoard(board);
            categoryBoard.setCategory(category);

            categoryBoardRepository.save(categoryBoard);
        }

        if(requestDto.getRoles() != null && !requestDto.getRoles().isEmpty()) { // 직무가 null이 아니면
            for (String dto : requestDto.getRoles()) {
                Category category = categoryRepository.findByCategoryName(dto);
                CategoryBoard categoryBoard = new CategoryBoard();
                categoryBoard.setBoard(board);
                categoryBoard.setCategory(category);

                categoryBoardRepository.save(categoryBoard);
            }
        }

    }

//    public List<Category> getRootCategories() {
//        return categoryRepository.findRootCategories();
//    }
}
