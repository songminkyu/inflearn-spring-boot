package membership_management.core.member;

public interface MemberRepository {

    void save(Member member);
    Member findById(Long memberId);
}
