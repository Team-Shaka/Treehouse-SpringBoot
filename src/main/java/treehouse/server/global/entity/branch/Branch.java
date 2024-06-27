package treehouse.server.global.entity.branch;

import jakarta.persistence.*;
import lombok.*;
import treehouse.server.global.entity.common.BaseDateTimeEntity;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.treeHouse.TreeHouse;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Branch extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer branchDegree;

    @JoinColumn(name = "rootId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member root;
    @JoinColumn(name = "leafId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member leaf;

    @JoinColumn(name = "treehouseId")
    @ManyToOne(fetch = FetchType.LAZY)
    private TreeHouse treeHouse;
}
