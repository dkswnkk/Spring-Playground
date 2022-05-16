package com.example.demo.src.service.user;


import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.domain.dto.user.address.PatchAddressReq;
import com.example.demo.src.domain.dto.user.address.PostAddressReq;
import com.example.demo.src.domain.dto.user.PostUserReq;
import com.example.demo.src.domain.dto.user.PostUserRes;
import com.example.demo.src.domain.dto.user.modify.PatchNameReq;
import com.example.demo.src.domain.entitiy.user.Address;
import com.example.demo.src.repository.user.AddressDao;
import com.example.demo.src.repository.user.PushNotificationAgreementDao;
import com.example.demo.src.repository.user.UserDao;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

/**
 * Service란?
 * Controller에 의해 호출되어 실제 비즈니스 로직과 트랜잭션을 처리: Create, Update, Delete 의 로직 처리
 * 요청한 작업을 처리하는 관정을 하나의 작업으로 묶음
 * dao를 호출하여 DB CRUD를 처리 후 Controller로 반환
 */
@Slf4j
@Service    // [Business Layer에서 Service를 명시하기 위해서 사용] 비즈니스 로직이나 respository layer 호출하는 함수에 사용된다.
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!
    private final PushNotificationAgreementDao pushNotificationAgreementDao;
    private final AddressDao addressDao;


    // ******************************************************************************
    // 회원가입(POST)
    @Transactional(readOnly = false)
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        // 중복 확인: 해당 이메일을 가진 이미 있을 때
        if (userProvider.checkEmail(postUserReq.getEmail()) == 1) {
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }

        String pwd;
        try {
            // 암호화: postUserReq에서 제공받은 비밀번호를 보안을 위해 암호화시켜 DB에 저장합니다.
            // ex) password123 -> dfhsjfkjdsnj4@!$!@chdsnjfwkenjfnsjfnjsd.fdsfaifsadjfjaf
            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postUserReq.getPassword()); // 암호화코드
            postUserReq.setPassword(pwd);
        } catch (Exception ignored) { // 암호화가 실패하였을 경우 에러 발생
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try {
            Long userIdx = userDao.createUser(postUserReq);
            pushNotificationAgreementDao.insertPushNotificationAgreement(userIdx, postUserReq);
            String jwt = jwtService.createJwt(userIdx);
            return new PostUserRes(userIdx, jwt);
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 회원정보 수정(Patch)
//    public void modifyUserName(PatchUserReq patchUserReq) throws BaseException {
//        try {
//            int result = userDao.modifyUserName(patchUserReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
//            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
//                throw new BaseException(MODIFY_FAIL_USERNAME);
//            }
//        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
    @Transactional(readOnly = false)
    public void modifyName(Long userIdx, PatchNameReq patchNameReq) throws BaseException{
        userDao.modifyName(userIdx, patchNameReq);
    }


    @Transactional(readOnly = false)
    public void updateMembership(Long userIdx, String memberType) throws BaseException {
        userDao.updateMembership(userIdx, memberType);
    }

    @Transactional(readOnly = false)
    public List<Address> getAddress(Long userIdx) throws BaseException {
        return addressDao.getAddress(userIdx);
    }

    // 주소 변경
    @Transactional(readOnly = false)
    public PatchAddressReq updateAddress(Long userIdx, PatchAddressReq patchAddressReq) throws BaseException {
        if (patchAddressReq.getIsDefault()) {    // 지금 입력하는 주소지가 기본 주소라면 이미있는 기본 주소지를 0으로 만듬
            addressDao.initDefaultAddress(userIdx);
        }
        addressDao.updateAddress(patchAddressReq);
        return patchAddressReq;
    }

    // 해당 유저의 모든 주소를 기본배송지가 아님으로 변경
    @Transactional(readOnly = false)
    public void initDefaultAddress(Long userIdx) throws BaseException {
        addressDao.initDefaultAddress(userIdx);
    }

    // 회원정보 삭제(Patch)
    @Transactional(readOnly = false)
    public Long deleteUser(Long userIdx) throws BaseException {
        userDao.deleteUser(userIdx);
        return userIdx;
    }

    @Transactional(readOnly = false)
    public void deleteAddress(int addressIdx) throws BaseException {
        addressDao.deleteAddress(addressIdx);
    }

    @Transactional(readOnly = false)
    public void insertTimeInfo(int addressIdx, PostAddressReq postAddressReq) throws BaseException {
        addressDao.insertTimeInfo(addressIdx, postAddressReq);
    }

    @Transactional(readOnly = false)
    public int insertAddress(Long userIdx, PostAddressReq postAddressReq) throws BaseException {
        addressDao.insertAddress(userIdx, postAddressReq);
        return userDao.getLastInsertId();

    }

    @Transactional(readOnly = false)
    public Long updatePushNotification(Long userIdx, String notificationName) throws BaseException {
        pushNotificationAgreementDao.updatePushNotification(userIdx, notificationName);
        return userIdx;
    }

    @Transactional(readOnly = false)
    public void updateUserProfileImage(Long userIdx, String url) throws BaseException {
        userDao.updateUserProfileImage(userIdx, url);
    }
}
