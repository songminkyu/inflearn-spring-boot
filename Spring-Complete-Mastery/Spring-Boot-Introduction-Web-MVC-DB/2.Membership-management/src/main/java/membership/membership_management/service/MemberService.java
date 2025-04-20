package membership.membership_management.service;

import membership.membership_management.domain.Member;
import membership.membership_management.repository.MemberRepository;
import membership.membership_management.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    /*
    * 회원가입
    */
    public Long join(Member member){
        vaildateDuplicateMember(member); //중복회원 검증
        return memberRepository.save(member).getId();
    }

    /*
     * 전체회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /*
     * 단일 회원 조회
     */
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }

    /*
     * 중복회원 체크
     */
    private void vaildateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()).ifPresent(m->{
            throw new IllegalStateException("이미 존재하는 회원 입니다.");
        });
    }
}
