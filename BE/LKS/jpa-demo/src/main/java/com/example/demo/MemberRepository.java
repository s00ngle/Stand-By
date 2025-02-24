package com.example.demo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
	// 이메일로 회원 조회하는 메서드
    Optional<Member> findByEmail(String email);
    
    // JPQL로 이메일 회원 검색
    @Query("SELECT m FROM Member m WHERE m.email = :email")
    Optional<Member> searchByEmailJPQL(String email);
}
