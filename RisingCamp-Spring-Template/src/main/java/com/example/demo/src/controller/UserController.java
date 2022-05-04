package com.example.demo.src.controller;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.domain.user.dto.GetUserRes;
import com.example.demo.src.domain.user.dto.PostUserReq;
import com.example.demo.src.domain.user.dto.PostUserRes;
import com.example.demo.src.domain.user.entitiy.Address;
import com.example.demo.src.domain.user.entitiy.PushNotificationAgreement;
import com.example.demo.src.domain.user.entitiy.User;
import com.example.demo.src.service.user.UserProvider;
import com.example.demo.src.service.user.UserService;
import com.example.demo.utils.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/app/users")
@Slf4j
/**
 * Controller란?
 * 사용자의 Request를 전달받아 요청의 처리를 담당하는 Service, Prodiver 를 호출
 */
public class UserController {

//    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log를 남기기: 일단은 모르고 넘어가셔도 무방합니다.

    @Autowired  // 객체 생성을 스프링에서 자동으로 생성해주는 역할. 주입하려 하는 객체의 타입이 일치하는 객체를 자동으로 주입한다.
    // IoC(Inversion of Control, 제어의 역전) / DI(Dependency Injection, 의존관계 주입)에 대한 공부하시면, 더 깊이 있게 Spring에 대한 공부를 하실 수 있을 겁니다!(일단은 모르고 넘어가셔도 무방합니다.)
    // IoC 간단설명,  메소드나 객체의 호출작업을 개발자가 결정하는 것이 아니라, 외부에서 결정되는 것을 의미
    // DI 간단설명, 객체를 직접 생성하는 게 아니라 외부에서 생성한 후 주입 시켜주는 방식
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!


    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService) {
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!
    }

    // ******************************************************************************

    /**
     * 회원가입
     */
    @ResponseBody
    @PostMapping("/sign-up")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {

        if (postUserReq.getEmail() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }

        if (postUserReq.getProfileImage() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_PROFILE_IMAGE);
        }

        if (postUserReq.getPhoneNumber().length() != 11) {
            return new BaseResponse<>(POST_USERS_INVALID_PHONE_NUMBER);
        }

        //이메일 정규표현: 입력받은 이메일이 email@domain.xxx와 같은 형식인지 검사합니다. 형식이 올바르지 않다면 에러 메시지를 보냅니다.
        if (!isRegexEmail(postUserReq.getEmail())) {
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        try {
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 로그인 API
     * [POST] /users/logIn
     */
//    @ResponseBody
//    @PostMapping("/log-in")
//    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq) {
//        try {
//            // TODO: 로그인 값들에 대한 형식적인 validatin 처리해주셔야합니다!
//            // TODO: 유저의 status ex) 비활성화된 유저, 탈퇴한 유저 등을 관리해주고 있다면 해당 부분에 대한 validation 처리도 해주셔야합니다.
//            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
//            return new BaseResponse<>(postLoginRes);
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//    }


    /**
     * 모든 회원 조회
     */
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetUserRes>> getUsers() {
        try {
            List<GetUserRes> getUserRes = new ArrayList<>(); // 반환할거 클라이언트에게
            List<User> getUsers = userProvider.getUsers();
            for (User getUser : getUsers) {
                int userIdx = getUser.getUserIdx();
                List<Address> getAddress = userProvider.getAddress(userIdx);
                List<PushNotificationAgreement> getAgreements = userProvider.getAgreements(userIdx);
                getUserRes.add(new GetUserRes(getUser, getAddress, getAgreements));
            }
            return new BaseResponse<>(getUserRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 인덱스로 조회
     */
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetUserRes>> getUser(@PathVariable("userIdx") int userIdx) {
        try {
            List<GetUserRes> getUserRes = new ArrayList<>(); // 반환할거 클라이언트에게
            User getUser = userProvider.getUser(userIdx);
            List<Address> getAddress = userProvider.getAddress(userIdx);
            List<PushNotificationAgreement> getAgreements = userProvider.getAgreements(userIdx);
            getUserRes.add(new GetUserRes((User) getUser, getAddress, getAgreements));
            return new BaseResponse<>(getUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 회원탈퇴
     */
    @DeleteMapping("/{userIdx}")
    public BaseResponse<String> deleteUser(@PathVariable("userIdx") int userIdx) {
        try {
            int id = userService.deleteUser(userIdx);
            return new BaseResponse(String.format("유저 아이디 %d이 삭제되었습니다.", id));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 유저정보변경 API
     * [PATCH] /users/:userIdx
     */
//    @ResponseBody
//    @PatchMapping("/{userIdx}")
//    public BaseResponse<String> modifyUserName(@PathVariable("userIdx") int userIdx, @RequestBody User user) {
//        try {
///**
// *********** 해당 부분은 7주차 - JWT 수업 후 주석해체 해주세요!  ****************
// //jwt에서 idx 추출.
// int userIdxByJwt = jwtService.getUserIdx();
// //userIdx와 접근한 유저가 같은지 확인
// if(userIdx != userIdxByJwt){
// return new BaseResponse<>(INVALID_USER_JWT);
// }
// //같다면 유저네임 변경
// **************************************************************************
// */
//            PatchUserReq patchUserReq = new PatchUserReq(userIdx, user.getName());
//            userService.modifyUserName(patchUserReq);
//
//            String result = "회원정보가 수정되었습니다.";
//            return new BaseResponse<>(result);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
    @PatchMapping("/{userIdx}")
    public BaseResponse<String> updateMembership(@PathVariable("userIdx") int userIdx, @RequestParam("memberType") String memberType) {
        if (!memberType.equals("BASIC") && !memberType.equals("ROCKET")) {
            return new BaseResponse<>(PATCH_USERS_INVALID_MEMBERSHIP_LEVEL);
        }
        try {
            userService.updateMembership(userIdx, memberType);
            return new BaseResponse<>(String.format("유저 %d의 멤버 등급이 %s으로 변경되었습니다.", userIdx, memberType));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
