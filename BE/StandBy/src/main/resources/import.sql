--
-- INSERT INTO user (username, email, password, role, created_at) VALUES('김철수', 'cheolsu.kim@example.com', 'password123', 'EMPLOYER', '2025-02-11 14:00:00');
-- INSERT INTO user (username, email, password, role, created_at) VALUES('박영희', 'younghee.park@example.com', 'password123', 'EMPLOYER', '2025-02-11 14:00:00');
--
-- -- Category 데이터 추가
-- INSERT INTO category (category_name, is_genre) VALUES ('장편영화', TRUE);
-- INSERT INTO category (category_name, is_genre) VALUES ('촬영', FALSE);
--
--
-- INSERT INTO board (author_id, title, content, start_date, end_date, pay_type, pay, status, available_positions, created_at) VALUES (1, '광고 모델 모집', '광고 촬영에 참여할 모델을 모집합니다.', '2025-02-15', '2025-02-28', '건별', 300000, TRUE, 3, '2025-02-11 14:00:00');
-- INSERT INTO board (author_id, title, content, start_date, end_date, pay_type, pay, status, available_positions, created_at) VALUES (2, '양싱 모델 모집', '영상 촬영에 참여할 모델을 모집합니다.', '2025-02-15', '2025-02-28', '건별', 300000, TRUE, 3, '2025-02-11 14:00:00');

INSERT INTO category (category_name, is_genre) VALUES ('장편영화', TRUE), ('단편영화', TRUE), ('드라마', TRUE), ('웹드라마', TRUE), ('뮤비', TRUE), ('CF', TRUE), ('다큐', TRUE), ('행사', TRUE), ('유튜브', TRUE), ('기타', TRUE);
INSERT INTO category (category_name, is_genre) VALUES ('기획', FALSE), ('시나리오', FALSE), ('연출', FALSE), ('촬영', FALSE), ('조명', FALSE), ('미술', FALSE), ('녹음', FALSE), ('편집', FALSE), ('음악', FALSE), ('믹싱', FALSE), ('CG', FALSE), ('각본', FALSE), ('분장', FALSE), ('출연', FALSE), ('장비관리', FALSE), ('성우', FALSE), ('기타', FALSE);

