package treehouse.server.api.member.business;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import treehouse.server.api.branch.business.BranchUtil;
import treehouse.server.api.member.presentation.dto.MemberRequestDTO;
import treehouse.server.api.member.presentation.dto.MemberResponseDTO;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.branch.Branch;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.treeHouse.TreeHouse;

import java.util.ArrayList;
import java.util.List;

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

    public static MemberResponseDTO.getWriterProfile toGetWriterProfile(Member member, Member writer, List<Branch> branches) {
        return MemberResponseDTO.getWriterProfile.builder()
                .memberId(writer.getId())
                .memberName(writer.getName())
                .memberProfileImageUrl(writer.getProfileImageUrl())
                .memberBranch(BranchUtil.calculateBranchDegree(branches, member.getId(), writer.getId()))
                .build();
    }

    public static MemberResponseDTO.getProfile toGetProfile(List<Branch> branches, Member member, Member targetMember) {
        return MemberResponseDTO.getProfile.builder()
                .memberId(member.getId())
                .memberName(member.getName())
                .userName(member.getUser().getName())
                .closestMemberCount(BranchUtil.countClosestMembers(branches, targetMember.getId())) // ClosestMember 기능 개발 이후 변경 예정
                .treehouseCount(member.getUser().getMemberList().size()) // TreehouseCount 기능 개발 이후 변경 예정
                .fromMe(BranchUtil.calculateBranchDegree(branches, member.getId(), targetMember.getId()))
                .profileImageUrl(member.getProfileImageUrl())
                .bio(member.getBio())
                .build();
    }

    public static MemberResponseDTO.getProfile toGetMemberProfile(List<Branch> branches, Member member, Member targetMember) {
        return MemberResponseDTO.getProfile.builder()
                .memberId(targetMember.getId())
                .memberName(targetMember.getName())
                .userName(targetMember.getUser().getName())
                .closestMemberCount(BranchUtil.countClosestMembers(branches, targetMember.getId())) // ClosestMember 기능 개발 이후 변경 예정
                .treehouseCount(targetMember.getUser().getMemberList().size()) // TreehouseCount 기능 개발 이후 변경 예정
                .fromMe(BranchUtil.calculateBranchDegree(branches, member.getId(), targetMember.getId()))
                .profileImageUrl(targetMember.getProfileImageUrl())
                .bio(targetMember.getBio())
                .build();
    }

    public static MemberResponseDTO.updateProfile toUpdateProfile(Member member) {
        return MemberResponseDTO.updateProfile.builder()
                .memberId(member.getId())
                .build();
    }
}
