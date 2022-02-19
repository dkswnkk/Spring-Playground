package basic.basic.member;

public interface MemberRepository {

    void save(Member ber);
    Member findById(Long memberId);

}
