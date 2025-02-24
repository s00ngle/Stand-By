package standby.standby.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import standby.standby.dto.portfolio.component.PortfolioCardComponentDto;
import standby.standby.dto.portfolio.request.PortfolioCardRequestDto;
import standby.standby.entity.*;
import standby.standby.repository.PortfolioCardImageRepository;
import standby.standby.repository.PortfolioCardRepository;
import standby.standby.repository.PortfolioRepository;
import standby.standby.repository.user.UserRepository;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PortfolioService {
    private final EntityManager em;

    private final PortfolioRepository portfolioRepository;
    private final PortfolioCardRepository portfolioCardRepository;
    private final PortfolioCardImageRepository portfolioCardImageRepository;
    private final UserRepository userRepository;
    private final FileService fileService;

    // 포트폴리오 카드 목록 조회
    public List<PortfolioCard> getPortfolioCards(Long userId) {
        Portfolio portfolio = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("userId로 user를 찾을 수 없습니다."))
                .getPortfolio();
        em.flush();
        if (portfolio == null) {
            return null;
        }
        // 포트폴리오 까지 찾을 수 있게 탐색
        int cardsCount = portfolio.getPortfolioCards().size();
        return portfolio.getPortfolioCards();
    }

    // 포트폴리오 카드 생성
    public PortfolioCard createPortfolioCard(
            Long userId,
            PortfolioCardRequestDto cardInfo,
            List<MultipartFile> files) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("포폴카드를 만들때 userId로 user를 찾을 수 없습니다"));
        Portfolio portfolio = user.getPortfolio();
        PortfolioCard card = new PortfolioCard();
        em.persist(card);

        card.setPortfolio(portfolio);
        card.setTitle(cardInfo.getTitle());
        card.setContent(cardInfo.getContent());
        card.setYoutubeUrl(cardInfo.getYoutubeUrl());

        portfolioCardRepository.save(card);
        em.flush();

        if (files != null && !files.isEmpty()) {
            // 이미지를 생성해서
            PortfolioCardImage image = new PortfolioCardImage();
            em.persist(image);
            // 부모를 지정해주고
            image.setPortfolioCard(card);
            // 이미지를 저장함
            for (MultipartFile file : files) {
                savePortfolioCardImages(image, file);
            }
        }
        em.flush();
        em.clear();
        PortfolioCard nowCard = portfolioCardRepository.findById(card.getPortfolioCardId())
                .orElseThrow(() -> new RuntimeException("유저아이디로 유저를 찾을 수 없습니다"));
        List<PortfolioCardImage> images = nowCard.getPortfolioCardImage();
        return nowCard;
    }


    // 포폴 카드 업데이트
    public PortfolioCard updatePortfolioCard(
            Long cardId,
            PortfolioCardRequestDto cardInfo,
            List<MultipartFile> files) {
        PortfolioCard card = portfolioCardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("cardId로 card를 찾을 수 없습니다."));
        card.setTitle(cardInfo.getTitle());
        card.setContent(cardInfo.getContent());
        card.setYoutubeUrl(cardInfo.getYoutubeUrl());
        List<PortfolioCardImage> images = card.getPortfolioCardImage();
        em.flush();
        MultipartFile file;
        if (files != null && !files.isEmpty()) {
            file = files.get(0);
        } else {
            file = null;
        }
        PortfolioCardImage updateImage;

        // 기존 이미지가 있었으면
        if (images != null && !images.isEmpty()) {
            Long imageId = images.get(0).getPortforlioCardImageId();
            // 이미지를 찾아서
            updateImage = portfolioCardImageRepository.findById(imageId)
                    .orElseThrow(() -> new RuntimeException("이미지 id로 이미지를 찾을 수 없습니다"));
            // 넣을 이미지가 존재할때는 이미지 객체만들어서
        } else {
            updateImage = new PortfolioCardImage();
            em.persist(updateImage);
            updateImage.setPortfolioCard(card);
        }
        // 실제 이미지 저장과 업데이트
        savePortfolioCardImages(updateImage, file);
        em.flush();
        em.clear();
        PortfolioCard updatedCard = portfolioCardRepository.findById(cardId).orElseThrow();
        int imageCount = updatedCard.getPortfolioCardImage().size();
        return updatedCard;
    }

    public void deletePortfolioCard(Long cardId) {
        List<PortfolioCardImage> images = portfolioCardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("cardId로 card를 찾을 수 없습니다."))
                .getPortfolioCardImage();
        for (PortfolioCardImage image : images) {
            image.setPortfolioCard(null);
            savePortfolioCardImages(image, null);
        }
        portfolioCardRepository.deleteById(cardId);
    }


    // image는 setPortfolioCard를 해준 상태에서 넣어줘야한다.
    // file 부분에 null 넣으면 기존 진짜 이미지 지워주기만 함.
    private void savePortfolioCardImages(PortfolioCardImage image, MultipartFile file) {
        // 배포할때는 위로 바꿔야 함
        String uploadDir = "/images/portfolio";
//        String uploadDir = "c:/images/portfolio";
        // 기존 이미지에 파일이 있었다면 실제 이미지 지워준다.
        if (image.getImagePath() != null) {
            File deleteFile = new File(image.getImagePath());
            boolean isDelete = deleteFile.delete();
        }
        if (file != null) {
            // 변수들
            String oriname = file.getOriginalFilename();
            String systemName = UUID.randomUUID().toString() + "_" + oriname;

            String imagePath = uploadDir + "/" + systemName;
            // 저장폴더 생성
            File dir = new File(uploadDir);
            boolean a = dir.mkdirs();
            // 실제 파일객체 생성
            File destFile = new File(imagePath);
            try {
                // 파일 저장
                file.transferTo(destFile);
                //파일 저장되면 객체 변경해서
                image.setImagePath(imagePath);
                image.setOriName(oriname);
                image.setSystemName(systemName);
                portfolioCardImageRepository.save(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
