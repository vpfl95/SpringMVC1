package hello.SpringMVC1.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */
public class MemberRepository {
    private static Map<Long, Member> store = new HashMap<>();
    private static  long sequence = 0L;

    private  static final MemberRepository instance = new MemberRepository();

    public static  MemberRepository getInstance(){
        return instance;
    }
    private MemberRepository(){}

    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }
    public Member findById(Long id){
        return store.get(id);
    }
    public List<Member> findAll(){
        return new ArrayList<>(store.values()); //store 자체를 보호하려고 Arraylist 값을 넣거나 조작해도 store에 있는 value 리스트를 건들고 싶지 않아서
    }
    public void clearStore(){
        store.clear();
    }


}
