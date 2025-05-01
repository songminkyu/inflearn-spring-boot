package membership_management.core.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MemoryMemberRepository implements MemberRepository {

    //HashMap 동시성 이슈가 있으므로 ConcurrentHashMap 사용 하는게 좋다
    //강의 예제 중심이다보니 현재 HashMap으로 사용.
    private static final Map<Long, Member> store = new HashMap<>();
    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
