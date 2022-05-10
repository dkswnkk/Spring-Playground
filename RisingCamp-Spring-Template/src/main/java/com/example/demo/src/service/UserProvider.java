package com.example.demo.src.service;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.domain.dto.PostLoginReq;
import com.example.demo.src.domain.entitiy.Address;
import com.example.demo.src.domain.entitiy.PushNotificationAgreement;
import com.example.demo.src.domain.entitiy.User;
import com.example.demo.src.repository.AddressDao;
import com.example.demo.src.repository.PushNotificationAgreementDao;
import com.example.demo.src.repository.UserDao;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

/**
 * Provider란?
 * Controller에 의해 호출되어 실제 비즈니스 로직과 트랜잭션을 처리: Read의 비즈니스 로직 처리
 * 요청한 작업을 처리하는 관정을 하나의 작업으로 묶음
 * dao를 호출하여 DB CRUD를 처리 후 Controller로 반환
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class UserProvider {
    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************
    private final UserDao userDao;
    //    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!
    private final AddressDao addressDao;
    private final PushNotificationAgreementDao pushNotificationAgreementDao;

    // 로그인(password 검사)
    public int logIn(PostLoginReq postLoginReq) throws BaseException {
        User user = userDao.getPwd(postLoginReq);
        if (!user.getStatus()) {
            throw new BaseException(FAILED_TO_LOGIN);
        }
        String password;
        try {
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getPassword()); // 암호화
            // 회원가입할 때 비밀번호가 암호화되어 저장되었기 때문에 로그인을 할때도 암호화된 값끼리 비교를 해야합니다.
        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }

        if (postLoginReq.getPassword().equals(password)) { //비말번호가 일치한다면 userIdx를 가져온다.
            return userDao.getPwd(postLoginReq).getUserIdx();
//  *********** 해당 부분은 7주차 - JWT 수업 후 주석해제 및 대체해주세요!  **************** //
//            String jwt = jwtService.createJwt(userIdx);
//            return new PostLoginRes(userIdx,jwt);
//  **************************************************************************

        } else { // 비밀번호가 다르다면 에러메세지를 출력한다.
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }

    // 해당 이메일이 이미 User Table에 존재하는지 확인
    public int checkEmail(String email) throws BaseException {
        try {
            return userDao.checkEmail(email);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 모든 User 조회
    public List<User> getUsers() throws BaseException {
        try {
            return userDao.getUsers();
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 주소 조회
    public List<Address> getAddress(int userIdx) throws BaseException {
        try {
            return addressDao.getAddress(userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<PushNotificationAgreement> getAgreements(int userIdx) throws BaseException {
        try {
            return pushNotificationAgreementDao.getAgreement(userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 해당 Email을 갖는 User들의 정보 조회
//    public List<GetUserRes> getUsersByEmail(String email) throws BaseException {
//        try {
//            List<GetUserRes> getUsersRes = userDao.getUsersByEmail(email);
//            return getUsersRes;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }


    //해당 userIdx를 갖는 User의 정보 조회
    public User getUser(int userIdx) throws BaseException {
        try {
            return userDao.getUser(userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
