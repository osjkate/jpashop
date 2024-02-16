package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 단순 유닛 테스트가 아님(실제 동작 과정)
@Transactional
class MemberServiceTest {

    @Autowired MemberRepository memberRepository;
    @Autowired MemberService memberService;
    @Autowired EntityManager em;

    @Test
    //@Rollback(false) <- 기본값이 true로 설정되어 있기 때문에 테스트 끝나면 다 롤백함!
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");
        
        //when
        Long savedId = memberService.join(member);

        //then
        em.flush(); // db에 쿼리 날리기
        assertEquals(member, memberRepository.findOne(savedId));
    }
    
    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");
        
        //when
        memberService.join(member1);
        try {
            memberService.join(member2);    // 예외가 발생해야 한다.
        } catch (IllegalStateException e) {
            return;
        }

        //then
        fail("예외가 발생해야 합니다. ");
    }

}