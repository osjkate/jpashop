package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // 이거 하면 final 인거 포함해서 생성자 만들어 줌
public class MemberService {

    private final MemberRepository memberRepository;

//    @Autowired  // 생성자 하나면 자동 주입 해줌 -> 생략 가
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원 가입
     */
    @Transactional  // 따로 설정하면 우선권 먹음
    public Long join(Member member) {
        validateDuplicateMember(member);    // 증복 회원 검증 비즈니스 로직
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // EXCEPTION
        // 동시성 문제가 발생할 수 있음 (멀티쓰레드 고려하기)
        // 데이터베이스에 name을 유니크 제약 조건으로 잡아 놓는 것이 더 안전함!
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) throw new IllegalStateException("이미 존재하는 회원입니다.");
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 한 건 조회 (id)
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
