package hello.servlet.domain;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 동시성 문제가 고려되어 있지 않아서, 실무에서는 ConcurrentHashMap, AtomicLong 사용을 고려해야한다.
* */
public class MemberRepository {
    private static Map<Long, Member> store = new HashMap<>();
    private static Long sequence = 0L;

    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance() {
        return instance;
    }
    private MemberRepository() {
    }

    public Member save(Member member) {
        if (member.getId() == null) {
            member.setId(++sequence);
        }
        store.put(member.getId(), member);
        return member;
    }
    public Member findById(Long id) {
        return store.get(id);
    }
    public List<Member> findAll(){
        return List.copyOf(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
