package treehouse.server.api.member.implementation;

import lombok.RequiredArgsConstructor;
import treehouse.server.api.member.persistence.MemberRepository;
import treehouse.server.api.treehouse.persistence.TreehouseRepository;
import treehouse.server.api.user.persistence.UserRepository;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.entity.member.Member;
import treehouse.server.global.entity.treeHouse.TreeHouse;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.ThrowClass.MemberException;
import treehouse.server.global.exception.ThrowClass.TreehouseException;
import treehouse.server.global.exception.ThrowClass.UserException;

@Adapter
@RequiredArgsConstructor
public class MemberQueryAdapter {

    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final TreehouseRepository treehouseRepository;

    public Member getMember(User user){

        return memberRepository.findByUser(user).orElseThrow(()-> new MemberException(GlobalErrorCode.USER_NOT_FOUND));
    }

    public Member findByUserAndTreehouse(User user, TreeHouse treehouse) {
        return memberRepository.findByUserAndTreeHouse(user, treehouse).orElseThrow(() -> new MemberException(GlobalErrorCode.USER_NOT_FOUND));
    }

    public boolean existsByTreeHouseAndPhoneNum(Long treeHouseId, String phoneNum){
        User user = userRepository.findByPhone(phoneNum)
                .orElseThrow(()-> new UserException(GlobalErrorCode.USER_NOT_FOUND));
        TreeHouse treeHouse = treehouseRepository.findById(treeHouseId).orElseThrow(()-> new TreehouseException(GlobalErrorCode.TREEHOUSE_NOT_FOUND));
        return memberRepository.existsByTreeHouseAndUser(treeHouse, user);
    }
}
