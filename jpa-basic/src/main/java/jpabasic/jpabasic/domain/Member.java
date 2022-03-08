package jpabasic.jpabasic.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_ID")
    private Long id;
    @Column(name = "USERNAME")
    private String name;

    //기간 Period
    @Embedded
    private Period wordPeriod;

    //주소
    @Embedded
    private Address homeAddress;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory = new ArrayList<>();
//    @ElementCollection
//    @CollectionTable(name = "FAVORITE_FOOD", joinColumns =
//    @JoinColumn(name = "MEMBER_ID")
//    )
//    @Column(name = "FOOD_NAME")
//    private Set<String> favoriteFoods = new HashSet<>();
//
//    @ElementCollection
//    @CollectionTable(name = "ADDRESS", joinColumns =
//    @JoinColumn(name = "MEMBER_ID"))
//    private List<Address> addressHistory = new ArrayList<>();
}
