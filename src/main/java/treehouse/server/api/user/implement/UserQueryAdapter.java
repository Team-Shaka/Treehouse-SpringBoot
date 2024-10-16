package treehouse.server.api.user.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.user.persistence.UserRepository;
import treehouse.server.api.user.presentation.dto.UserRequestDTO;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.User.User;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.ThrowClass.UserException;

import java.util.Optional;

@Adapter
@RequiredArgsConstructor
public class UserQueryAdapter {

    private final UserRepository userRepository;

    @Transactional
    public boolean checkName(UserRequestDTO.checkName request){
        return userRepository.findByName(request.getUserName()).isPresent();
    }

    public Boolean existById(Long id){
        return userRepository.existsById(id);
    }

    public User findByPhoneNumber(String phone){
        return userRepository.findByPhone(phone).orElseThrow(()->new UserException(GlobalErrorCode.USER_NOT_FOUND));
    }

    public Optional<User> findByPhoneNumberOptional(String phone){
        return userRepository.findByPhone(phone);
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(()->new UserException(GlobalErrorCode.USER_NOT_FOUND));
    }

    public Optional<User> findByIdOptional(Long id){
        return userRepository.findById(id);
    }

    public Optional<User> optionalUserFindById(Long id){
        return userRepository.findById(id);
    }

    public Boolean existByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhone(phoneNumber);
    }
}
