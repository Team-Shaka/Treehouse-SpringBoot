package treehouse.server.api.member.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import treehouse.server.api.member.presentation.dto.MemberResponseDTO;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.treeHouse.TreeHouse;

@Component
@RequiredArgsConstructor
public class MemberMapper {

    public static Member toMember(User user, String memberName, String bio, String profileImageUrl, TreeHouse treeHouse) {
        return Member.builder()
                .user(user)
                .name(memberName)
                .bio(bio)
                .profileImageUrl(profileImageUrl)
                .treeHouse(treeHouse)
                .build();
    }

    public static MemberResponseDTO.registerMember toRegister(Long treehouseId, Member member) {
        return MemberResponseDTO.registerMember.builder()
                .userId(member.getUser().getId())
                .treehouseId(treehouseId) // treehouseId는 관련 기능 구현 후 변경 예정
                .build();
    }

    public static MemberResponseDTO.getMemberProfile toGetMemberProfile(Member member) {
        return MemberResponseDTO.getMemberProfile.builder()
                .memberId(member.getId())
                .memberName(member.getName())
                .memberProfileImageUrl(member.getProfileImageUrl())
                .memberBranch(3) // Branch 기능 개발 이후 변경 예정
                .build();
    }
}
