package com.example.board;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.board.controller.MemberShipController;
import com.example.board.model.Membership;
import com.example.board.repository.MembershipRepository;

@SpringBootTest
class BoardApplicationTests {

	@Autowired
   MembershipRepository membershipRepository;

	@Autowired
	MemberShipController membershipController;


	@Test
	public void testRemoveExpiredMemberships() {
		// 테스트 데이터 생성
		Membership expiredMembership = new Membership();
    expiredMembership.setEndDate(LocalDate.now().minusDays(1)); // 만료된 멤버십
    membershipRepository.save(expiredMembership);
	
		// 메소드 호출
		membershipController.removeExpiredMemberships();

		// 데이터가 제대로 삭제되었는지 확인
    List<Membership> memberships = membershipRepository.findAll();
    assertTrue(memberships.isEmpty());

}
}
