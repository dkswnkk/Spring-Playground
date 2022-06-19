package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndAgeIsGreaterThan(String username, int age);

    @Query("select m from Member m where m.username =:username and m.age =:age")    // =: 애서 =: 뒤에 공백이 있으면 에러 발생
    List<Member> findUser(@Param("username") String username, @Param("age") int age);
}
