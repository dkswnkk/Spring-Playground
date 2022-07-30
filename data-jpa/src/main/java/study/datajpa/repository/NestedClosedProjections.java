package study.datajpa.repository;

public interface NestedClosedProjections {

    /*
        중첩 구조에서 첫 번째는 최적화 해서 조회하지만, 두 번째 부터는 전부 조회한다(최적화 x){left join 으로 조회}
     */
    String getUsername();

    TeamInfo getTeam();

    interface TeamInfo {
        String getName();
    }
}
