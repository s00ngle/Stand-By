package standby.standby.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import standby.standby.dto.board.BoardRequestDto;
import standby.standby.entity.Board;
import standby.standby.entity.Workplace;
import standby.standby.repository.WorkplaceRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkplaceService {

    private final WorkplaceRepository workplaceRepository;

    /**
     * 게시물에 대한 근무지 정보 생성 및 저장
     *
     * @param board 게시물 엔티티
     * @param requestDto 게시물 요청 DTO
     */
    public void createWorkplace(Board board, BoardRequestDto requestDto) {
        //  안전한 `Optional` 사용 → requestDto.getWorkplace()가 null일 경우 빈 리스트 반환
        List<Workplace> workplaceList = Optional.ofNullable(requestDto.getLocation())
                .orElse(List.of()) // 만약 null이면 빈 리스트 반환
                .stream()
                .map(dto -> new Workplace(
                        null, // ID는 자동 생성
                        board,
                        dto.getName(),
                        dto.getDetail(),
                        dto.getLatitude(),
                        dto.getLongitude()))
                .collect(Collectors.toList());

        //  리스트가 비어있지 않을 경우만 저장 (불필요한 DB 호출 방지)
        if (!workplaceList.isEmpty()) {
            workplaceRepository.saveAll(workplaceList);
        }
    }
}
