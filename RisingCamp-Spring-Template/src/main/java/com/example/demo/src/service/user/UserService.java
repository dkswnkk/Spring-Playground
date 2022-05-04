package com.example.demo.src.service.user;


import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.domain.user.dto.PatchAddressReq;
import com.example.demo.src.domain.user.dto.PostAddressReq;
import com.example.demo.src.domain.user.dto.PostUserReq;
import com.example.demo.src.domain.user.dto.PostUserRes;
import com.example.demo.src.domain.user.entitiy.Address;
import com.example.demo.src.repository.UserDao;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
// [Business Layer]는 컨트롤러와 데이터 베이스를 연결
public class UserService {
//    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log 처리부분: Log를 기록하기 위해 필요한 함수입니다.

    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************
    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!


    @Autowired //readme 참고
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!

    }

    // ******************************************************************************
    // 회원가입(POST)
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
            int userIdx = userDao.createUser(postUserReq);
            return new PostUserRes(userIdx);

//  *********** 해당 부분은 7주차 수업 후 주석해제하서 대체해서 사용해주세요! ***********
//            //jwt 발급.
//            String jwt = jwtService.createJwt(userIdx);
//            return new PostUserRes(jwt,userIdx);
//  *********************************************************************
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
    public void updateMembership(int userIdx, String memberType) throws BaseException {
        try {
            int result = userDao.updateMembership(userIdx, memberType);
            if (result == 0) {
                throw new BaseException(USERS_EMPTY_USER_ID);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<Address> getAddress(int userIdx) throws BaseException {
        try {
            List<Address> address = userDao.getAddress(userIdx);
            return address;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 주소 변경
    public PatchAddressReq updateAddress(PatchAddressReq patchAddressReq) throws BaseException {
        try {
            int result = userDao.updateAddress(patchAddressReq);
            if (result == 0) {
                throw new BaseException(UPDATE_FAIL_ADDRESS);
            }
            return patchAddressReq;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 해당 유저의 모든 주소를 기본배송지가 아님으로 변경
    public void initDefaultAddress(int userIdx) throws BaseException {
        try {
            int result = userDao.initDefaultAddress(userIdx);
            if (result == 0) {
                throw new BaseException(USERS_EMPTY_USER_ID);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 회원정보 삭제(Patch)
    public int deleteUser(int userIdx) throws BaseException {
        try {
            int result = userDao.deleteUser(userIdx);
            if (result == 0) {
                throw new BaseException(USERS_EMPTY_USER_ID);
            }
            return userIdx;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteAddress(int addressIdx) throws BaseException {
        try {
            int result = userDao.deleteAddress(addressIdx);
            if (result == 0) {
                throw new BaseException(ADDRESS_EMPTY_ADDRESS_ID);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public void insertTimeInfo(int addressIdx, PostAddressReq postAddressReq) throws BaseException {
        try {
            int result = userDao.insertTimeInfo(addressIdx, postAddressReq);
            if (result == 0) {
                throw new BaseException(ADDRESS_EMPTY_ADDRESS_ID);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int insertAddress(int userIdx, PostAddressReq postAddressReq) throws BaseException {
        try {
            int result = userDao.insertAddress(userIdx, postAddressReq);
            if (result == 0) {
                throw new BaseException(ADDRESS_EMPTY_ADDRESS_ID);
            }
            return userDao.getLastInsertId();
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
