package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {
	
	@Autowired
	private MemberRepository memberRepository;
	
	 // 회원 등록 (Post 요청)
    @PostMapping
    public String addMember(@RequestBody Member member) {
        memberRepository.save(member);
        return "회원 등록 완료!";
    }
	
	//모든 회원 조회 (Get 요청)
	@GetMapping
	public List<Member> getAllMembers() {
		return memberRepository.findAll();
	}
	
	// 회원 1명 조회(Get 요청)
	@GetMapping("/{id}")
	public Member getMemberById(@PathVariable Long id) {
		return memberRepository.findById(id).orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));
	}
	
	// 이메일로 회원 검색 (Get 요청)
	@GetMapping("/search")
	public Member getMemberByEmail(@RequestParam String email) {
		return memberRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("해당 이메일의 회원이 없습니다."));
	}
	
	// 회원 정보 수정 (Put 요청)
	@PutMapping("/{id}")
	public String updateMember(@PathVariable Long id, @RequestBody Member updateMember) {
		Member member = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));
		member.setUsername(updateMember.getUsername());
		member.setEmail(updateMember.getEmail());
		memberRepository.save(member);
		return "회원 정보가 수정되었습니다.!";
	}
	
	// 회원 삭제 (Delete 요청)
	@DeleteMapping("/{id}")
	public String deleteMember(@PathVariable Long id) {
		memberRepository.deleteById(id);
		return "회원이 삭제되었습니다.!";
	}
	
	// JPQL로 이메일 검색
	@GetMapping("/search/jpql")
	public Optional<Member> searchByEmailJPQL(@RequestParam String email){
		return memberRepository.searchByEmailJPQL(email);
	}
}
