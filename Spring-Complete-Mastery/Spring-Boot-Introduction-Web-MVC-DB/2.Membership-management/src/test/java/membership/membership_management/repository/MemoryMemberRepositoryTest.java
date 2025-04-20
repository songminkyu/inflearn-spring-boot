package membership.membership_management.repository;

import membership.membership_management.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring!!");
        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result1 = repository.findByName("spring1").get();
        assertThat(result1).isEqualTo(member1);

        Member result2 = repository.findByName("spring2").get();
        assertThat(result2).isEqualTo(member2);
    }

    @Test
    public void findAll() {

        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> members = repository.findAll();
        assertThat(members).hasSize(2);
        assertThat(repository.findAll()).containsExactly(member1, member2);

    }

    @Test
    public void loopTest(){

        ArrayList<Integer> arrayList = new ArrayList<Integer>();

        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        arrayList.add(5);

        int i = 0;
        for(Integer value : arrayList){
            System.out.println(value);
            i++;
        }
        assertThat(arrayList).hasSize(5);
    }
}
