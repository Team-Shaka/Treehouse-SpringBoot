package treehouse.server.api.member.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import treehouse.server.api.member.presentation.dto.MemberRequestDTO;
import treehouse.server.api.member.presentation.dto.MemberResponseDTO;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.treeHouse.TreeHouse;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class MemberMapper {

    public static Member toMember(User user, MemberRequestDTO.registerMember request, TreeHouse treeHouse) {
        return Member.builder()
                .user(user)
                .name(request.getMemberName())
                .bio(request.getBio())
                .profileImageUrl(request.getProfileImageURL())
                .treeHouse(treeHouse)
                .notificationList(new ArrayList<>())
                .commentList(new ArrayList<>())
                .invitationList(new ArrayList<>())
                .reactionList(new ArrayList<>())
                .build();
    }

    public static MemberResponseDTO.registerMember toRegister(Member member) {
        return MemberResponseDTO.registerMember.builder()
                .userId(member.getUser().getId())
                .treehouseId(member.getTreeHouse().getId())
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
