package study.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberDto;
import study.querydsl.dto.QMemberDto;
import study.querydsl.dto.UserDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.QTeam;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static study.querydsl.entity.QMember.*;
import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.team;

@SpringBootTest
@Transactional(readOnly = true)
public class QuerydslBasicTest {

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    public void before() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("안주", 25, teamA);
        Member member2 = new Member("고양이", 1, teamB);
        Member member3 = new Member("멍멍이", 1, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);

        em.flush();
        em.clear();
    }

    @Test
    public void startJPQL() {
        // 가정: 멤버 이름으로 찾기
        Member findMember = em.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", "안주")
                .getSingleResult();

        assertThat(findMember.getUsername()).isEqualTo("안주");
    }

    @Test
    public void startQuerydsl() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QMember m = new QMember("m");
        QMember m2 = member;    // 이렇게도 사용 가능

        Member findMember = queryFactory
                .select(m)
                .from(m)
                .where(m.username.eq("안주"))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("안주");

    }


    @Test
    public void search() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        Member result = queryFactory
                .selectFrom(member) //QMember.member 를 스태틱 임포트
                .where(member.username.eq("안주").and(member.age.eq(25)))
                .fetchOne();

        assertThat(result.getUsername()).isEqualTo("안주");
    }

    @Test
    public void searchAndParam() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        /*
            위랑 동일, where 절에 and로 체인을 거는 대신에 ','로도 사용 가능
         */
        Member result = queryFactory
                .selectFrom(member) //QMember.member 를 스태틱 임포트
                .where(
                        member.username.eq("안주"),
                        member.age.eq(25))
                .fetchOne();

        assertThat(result.getUsername()).isEqualTo("안주");
    }

    @Test
    public void resultFetch() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        List<Member> fetch = queryFactory
                .selectFrom(member)
                .fetch();


        /*
            단건 조회이기 때문에 한 건이 나와야 하지만 현재 두 건이 나와서 에러가 발생하는게 옳음.
         */
        assertThrows(com.querydsl.core.NonUniqueResultException.class, () -> {
            Member fetchOne = queryFactory
                    .selectFrom(member)
                    .fetchOne();
        });


        Member fetchFirst = queryFactory
                .selectFrom(member)
                .fetchFirst();  //limit(1)과 동일

        QueryResults<Member> memberQueryResults = queryFactory
                .selectFrom(member)
                .fetchResults();

        memberQueryResults.getTotal();
        List<Member> content = memberQueryResults.getResults();

    }

    /**
     * 회원 정렬 순서
     * 1. 회원 나이 내림차순(desc)
     * 2. 회원 이름 올림차순(asc)
     * 단 2에서 회원 이름이 같으면 마지막에 출력(null last)
     */
    @Test
    public void sort() {
        JPAQueryFactory jqf = new JPAQueryFactory(em);

        em.persist(new Member(null, 100));
        em.persist(new Member("멍멍이", 100));
        em.persist(new Member("야옹이", 100));

        List<Member> result = jqf
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(
                        member.age.desc(),
                        member.username.asc().nullsLast()
                )
                .fetch();

        Member 멍멍이 = result.get(0);
        Member 야옹이 = result.get(1);
        Member memberNull = result.get(2);

        assertThat(야옹이.getUsername()).isEqualTo("야옹이");
        assertThat(멍멍이.getUsername()).isEqualTo("멍멍이");
        assertThat(memberNull.getUsername()).isNull();

    }

    @Test
    public void paging1() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        List<Member> result = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetch();

        assertThat(result.size()).isEqualTo(2);

    }

    @Test
    public void paging2() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QueryResults<Member> result = queryFactory
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetchResults();

        assertThat(result.getTotal()).isEqualTo(3);
        assertThat(result.getLimit()).isEqualTo(2);
        assertThat(result.getOffset()).isEqualTo(1);
        assertThat(result.getResults().size()).isEqualTo(2);

    }

    @Test
    public void aggregation() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        List<Tuple> result = queryFactory
                .select(
                        member.count(),
                        member.age.sum(),
                        member.age.avg(),
                        member.age.max(),
                        member.age.min()
                )
                .from(member)
                .fetch();

        Tuple tuple = result.get(0);
        assertThat(tuple.get(member.count())).isEqualTo(3);
        assertThat(tuple.get(member.age.sum())).isEqualTo(27);
        System.out.println(result.toString());

    }


    /**
     * 팀의 이름과 각 팀의 평균 연령을 구해라.
     */
    @Test
    public void group() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        List<Tuple> result = queryFactory
                .select(team.name, member.age.avg())
                .from(member)
                .join(member.team, team)
                .groupBy(team.name)
                .fetch();

        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);

        System.out.println(teamA.toString());

        assertThat(teamA.get(team.name)).isEqualTo("teamA");
//        assertThat(teamA.get(member.age.avg())).isEqualTo()
    }


    /**
     * 팀 A에 소속된 모든 회원 조회
     */
    @Test
    public void join() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        List<Member> result = queryFactory
                .selectFrom(member)
                .join(member.team, team)
                .where(team.name.eq("teamA"))
                .fetch();

        assertThat(result)
                .extracting("username")
                .containsExactly("안주");
    }

    /**
     * 연관관계 없어도 join 가능
     * 세타조인
     * 회원의 이름이 팀 이름과 같은 회원 조회
     */
    @Test
    public void theta_join() {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        List<Member> result = queryFactory
                .select(member)
                .from(member, team)
                .where(member.username.eq(team.name))
                .fetch();

        assertThat(result)
                .extracting("username")
                .containsExactly("teamA", "teamB");

    }


    /**
     * 예) 회원과 팀을 조인하면서, 팀 이름이 teamA인 팀만 조인, 회원은 모두 조회
     * JPQL: select m, t from Member m left join m.team on t.name = 'teamA'
     */
    @Test
    public void join_on_filtering() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        List<Tuple> result = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(member.team, team).on(team.name.eq("teamA"))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println(tuple);
        }
    }

    /**
     * 연관관계 없는 엔티티 외부 조인
     * 예) 회원의 이름과 팀의 이름이 같은 대상 외부 조인
     * JPQL: SELECT m, t FROM Member m LEFT JOIN Team t on m.username = t.name
     * SQL: SELECT m.*, t.* FROM Member m LEFT JOIN Team t ON m.username = t.name
     */
    @Test
    public void join_on_no_relation() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        List<Tuple> result = queryFactory
                .select(member, team)
                .from(member)
                .leftJoin(team).on(member.username.eq(team.name))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println(tuple);
        }
    }

    @PersistenceUnit
    EntityManagerFactory emf;

    @Test
    public void fetchJoinNo() {
        em.flush();
        em.clear();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        Member findMember = queryFactory
                .selectFrom(member)
                .where(member.username.eq("안주"))
                .fetchOne();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
        assertThat(loaded).as("페치 조인 미적용").isFalse();

    }

    @Test
    public void fetchJoinUse() {
        em.flush();
        em.clear();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        Member findMember = queryFactory
                .selectFrom(member)
                .join(member.team, team).fetchJoin()
                .where(member.username.eq("안주"))
                .fetchOne();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
        assertThat(loaded).as("페치 조인 적용").isTrue();

    }

    /**
     * 나이가 가장 많은 회원 조회
     */
    @Test
    public void subQuery() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QMember memberSub = new QMember("memberSub");
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.eq(
                        select(memberSub.age.max())
                                .from(memberSub)
                ))
                .fetch();

        assertThat(result).extracting("age")
                .containsExactly(25);
    }


    /**
     * 나이가 평균 이상인 회원 조회
     */
    @Test
    public void subQueryGoe() { // 크거나 equal
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QMember memberSub = new QMember("memberSub");
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.goe(
                        select(memberSub.age.avg())
                                .from(memberSub)
                ))
                .fetch();

        assertThat(result).extracting("age")
                .containsExactly(25);
    }

    /**
     * 나이가 평균 이상인 회원 조회
     */
    @Test
    public void subQueryIn() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QMember memberSub = new QMember("memberSub");
        List<Member> result = queryFactory
                .selectFrom(member)
                .where(member.age.in(
                        select(memberSub.age)
                                .from(memberSub)
                                .where(memberSub.age.gt(10))
                ))
                .fetch();
    }

    @Test
    public void selectSubQuery() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QMember memberSub = new QMember("memberSub");

        List<Tuple> result = queryFactory
                .select(member.username,
                        select(memberSub.age.avg())
                                .from(memberSub))
                .from(member)
                .fetch();

        for (Tuple tuple : result) {
            System.out.println(tuple);
        }

    }

    /**
     * case문 단순한 로직
     */
    @Test
    public void basicCase() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        List<String> result = queryFactory
                .select(member.age
                        .when(10).then("열살")
                        .when(25).then("스물다섯")
                        .otherwise("기타")
                )
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println(s);
        }
    }

    /**
     * case문 복잡한 로직
     */
    @Test
    public void complexCase() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        List<String> result = queryFactory
                .select(new CaseBuilder()
                        .when(member.age.between(0, 18)).then("미성년자")
                        .when(member.age.between(19, 99)).then("성인")
                        .otherwise("??"))
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println(s);
        }
    }

    @Test
    public void constant() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        List<Tuple> result = queryFactory
                .select(member.username, Expressions.constant("A"))
                .from(member)
                .fetch();

        for (Tuple tuple : result) {
            System.out.println(tuple);
        }

    }


    @Test
    public void concat() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        //{username_age}
        List<String> result = queryFactory
                .select(member.username.concat("_").concat(member.age.stringValue()))   // .stringValue()의 경우 쓸 일이 많다.
                .from(member)
                .where(member.username.eq("안주"))
                .fetch();
        for (String s : result) {
            System.out.println(s);
        }

    }

    /**
     * 프로젝션 대상이 하나면 타입을 명확하게 지정할 수 있음
     * 프로젝션 대상이 둘 이상이면 튜플이나 DTO로 조회
     */
    @Test
    public void simpleProjection() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        List<String> result = queryFactory
                .select(member.username)
                .from(member)
                .fetch();
    }


    /**
     * 김영한: 튜플이 레포지토리 계층을 넘어 서비스나 컨트롤러 계층까지 가는 경우는 좋은 설계가 아님.
     * 바깥으로 나갈때는 DTO로 변환해서 사용하는 것을 권장
     */
    @Test
    public void tupleProjection() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        List<Tuple> result = queryFactory
                .select(member.username, member.age)    // 여러개가 넘어오면 튜플
                .from(member)
                .fetch();

        for (Tuple tuple : result) {
            String username = tuple.get(member.username);
            Integer age = tuple.get(member.age);
            System.out.println(username);
            System.out.println(age);
        }
    }

    /**
     * 순수 JPA에서 DTO를 조회할 때는 new 명령어를 사용해야함
     * DTO의 package이름을 다 적어줘야해서 지저분함
     * 생성자 방식만 지원함
     */
    @Test
    public void findDtoByJQPL() {
        List<MemberDto> resultList = em.createQuery("select new study.querydsl.dto.MemberDto(m.username, m.age) from Member m ", MemberDto.class)
                .getResultList();

        for (MemberDto memberDto : resultList) {
            System.out.println(memberDto);
        }
    }

    /**
     * setter로 생성
     */
    @Test
    public void findDtoBySetter() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        List<MemberDto> result = queryFactory
                .select(Projections.bean(MemberDto.class, member.username, member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println(memberDto);
        }
    }

    /**
     * setter 없어도 field에 값을 바로 넣음
     */
    @Test
    public void findDtoByField() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        List<MemberDto> result = queryFactory
                .select(Projections.fields(MemberDto.class, member.username, member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println(memberDto);
        }
    }

    /**
     * 생성자
     */
    @Test
    public void findDtoByConstructor() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        List<MemberDto> result = queryFactory
                .select(Projections.constructor(MemberDto.class, member.username, member.age))
                .from(member)
                .fetch();

        for (MemberDto memberDto : result) {
            System.out.println(memberDto);
        }
    }

    @Test
    public void findUserDto() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QMember membersub = new QMember("memberSub");

        List<UserDto> result = queryFactory
                .select(Projections.fields(UserDto.class,
                        member.username.as("name"),
                        ExpressionUtils.as(select(membersub.age.max())
                                .from(membersub), "age")
                ))
                .from(member)
                .fetch();
    }


    @Test
    public void findDtoByQueryProjection() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        List<MemberDto> result = queryFactory
                .select(new QMemberDto(member.username, member.age))
                .from(member)
                .fetch();
        for (MemberDto memberDto : result) {
            System.out.println(memberDto);
        }

    }

    @Test
    public void dynamicQuery_BooleanBuilder() {
        String usernameParam = "member1";
        Integer ageParam = 10;

        List<Member> result = searchMember1(usernameParam, ageParam);
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMember1(String usernameCond, Integer ageCond) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        BooleanBuilder builder = new BooleanBuilder();
        if (usernameCond != null) {
            builder.and(member.username.eq(usernameCond));
        }
        if (ageCond != null) {
            builder.and(member.age.eq(ageCond));
        }
        return queryFactory
                .selectFrom(member)
                .where(builder)
                .fetch();
    }


}
