package treehouse.server.api.member.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.branch.business.BranchService;
import treehouse.server.api.branch.implementation.BranchQueryAdapter;
import treehouse.server.api.invitation.implement.InvitationCommandAdapter;
import treehouse.server.api.invitation.implement.InvitationQueryAdapter;
import treehouse.server.api.member.implementation.MemberCommandAdapter;
import treehouse.server.api.member.implementation.MemberQueryAdapter;
import treehouse.server.api.member.presentation.dto.MemberRequestDTO;
import treehouse.server.api.member.presentation.dto.MemberResponseDTO;
import treehouse.server.api.treehouse.implementation.TreehouseQueryAdapter;
import treehouse.server.global.entity.Invitation.Invitation;
import treehouse.server.global.entity.Invitation.InvitationStatus;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.branch.Branch;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.treeHouse.TreeHouse;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MemberService {

    private final MemberQueryAdapter memberQueryAdapter;

    private final MemberCommandAdapter memberCommandAdapter;
    private final TreehouseQueryAdapter treehouseQueryAdapter;
    private final BranchQueryAdapter branchQueryAdapter;

    private final InvitationCommandAdapter invitationCommandAdapter;
    private final InvitationQueryAdapter invitationQueryAdapter;

    private final BranchService branchService;

    /**
     * 트리하우스에 가입
     * @param user
     * @param request
     * @return
     */
    @Transactional
    public MemberResponseDTO.registerMember register(User user, MemberRequestDTO.registerMember request){
        TreeHouse treeHouse = treehouseQueryAdapter.getTreehouseById(request.getTreehouseId());
        Member member = MemberMapper.toMember(user, request, treeHouse);
        Member savedMember = memberCommandAdapter.register(member);

        user.addMember(savedMember); // User에 Member 추가
        treeHouse.addMember(savedMember); // TreeHouse에 Member 추가

        // 트리하우스에 초대한 멤버와의 브랜치 생성
        Invitation acceptedInvitation = invitationQueryAdapter.findAcceptedInvitation(user, treeHouse);
        branchService.createBranch(treeHouse, acceptedInvitation.getSender(), savedMember);

        return MemberMapper.toRegister(savedMember);
    }

    /**
     * 트리하우스 창립자로 가입
     * @param user
     * @param request
     * @return
     */
    @Transactional
    public MemberResponseDTO.registerMember registerFounder(User user, MemberRequestDTO.registerMember request) {
        TreeHouse treeHouse = treehouseQueryAdapter.getTreehouseById(request.getTreehouseId());
        Member member = MemberMapper.toMember(user, request, treeHouse);
        Member savedMember = memberCommandAdapter.register(member);

        user.addMember(savedMember); // User에 Member 추가
        treeHouse.addMember(savedMember); // TreeHouse에 Member 추가

        return MemberMapper.toRegister(savedMember);
    }

    @Transactional(readOnly = true)
    public MemberResponseDTO.getProfile getMyProfile(User user, Long treehouseId){
        TreeHouse treeHouse = treehouseQueryAdapter.getTreehouseById(treehouseId);
        Member member = memberQueryAdapter.findByUserAndTreehouse(user, treeHouse);
        List<Branch> branches = branchQueryAdapter.findAllByTreeHouse(treeHouse); // 트리하우스 내 모든 브랜치 조회
        return MemberMapper.toGetProfile(branches, member, member);
    }

    public MemberResponseDTO.getProfile getMemberProfile(User user, Long memberId, Long treehouseId){
        TreeHouse treeHouse = treehouseQueryAdapter.getTreehouseById(treehouseId);
        Member member = memberQueryAdapter.findByUserAndTreehouse(user, treeHouse);
        Member targetMember = memberQueryAdapter.findById(memberId);
        List<Branch> branches = branchQueryAdapter.findAllByTreeHouse(treeHouse); // 트리하우스 내 모든 브랜치 조회
        return MemberMapper.toGetMemberProfile(branches, member, targetMember);
    }



    @Transactional
    public MemberResponseDTO.updateProfile updateProfile(User user, Long treehouseId, MemberRequestDTO.updateProfile request){
        TreeHouse treeHouse = treehouseQueryAdapter.getTreehouseById(treehouseId);
        Member member = memberQueryAdapter.findByUserAndTreehouse(user, treeHouse);
        member.updateMember(request.getMemberName(), request.getBio(), request.getProfileImageURL());
        return MemberMapper.toUpdateProfile(member);
    }


}
