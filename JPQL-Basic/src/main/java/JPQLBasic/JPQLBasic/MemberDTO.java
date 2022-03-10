package JPQLBasic.JPQLBasic;

import lombok.Getter;

@Getter
public class MemberDTO {

    public MemberDTO(String username, int id) {
        this.username = username;
        this.id = id;
    }

    private String username;
    private int id;
}
