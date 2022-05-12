package com.example.demo.src.controller;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.domain.dto.*;
import com.example.demo.src.domain.dto.address.GetAddressRes;
import com.example.demo.src.domain.dto.address.PatchAddressReq;
import com.example.demo.src.domain.dto.address.PatchAddressRes;
import com.example.demo.src.domain.dto.address.PostAddressReq;
import com.example.demo.src.domain.dto.sign.PostLoginReq;
import com.example.demo.src.domain.dto.user.GetUserRes;
import com.example.demo.src.domain.dto.user.PostUserReq;
import com.example.demo.src.domain.dto.user.PostUserRes;
import com.example.demo.src.domain.entitiy.user.Address;
import com.example.demo.src.domain.entitiy.user.PushNotificationAgreement;
import com.example.demo.src.domain.entitiy.user.User;
import com.example.demo.src.service.UserProvider;
import com.example.demo.src.service.UserService;
import com.example.demo.utils.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/app/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    // @Autowired  // 객체 생성을 스프링에서 자동으로 생성해주는 역할. 주입하려 하는 객체의 타입이 일치하는 객체를 자동으로 주입한다.
    // IoC(Inversion of Control, 제어의 역전) / DI(Dependency Injection, 의존관계 주입)에 대한 공부하시면, 더 깊이 있게 Spring에 대한 공부를 하실 수 있을 겁니다!(일단은 모르고 넘어가셔도 무방합니다.)
    // IoC 간단설명,  메소드나 객체의 호출작업을 개발자가 결정하는 것이 아니라, 외부에서 결정되는 것을 의미
    // DI 간단설명, 객체를 직접 생성하는 게 아니라 외부에서 생성한 후 주입 시켜주는 방식

    private final UserProvider userProvider;
    private final UserService userService;
    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!


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


    @ResponseBody
    @PostMapping("/log-in")
    public BaseResponse<List<GetUserRes>> logIn(@RequestBody PostLoginReq postLoginReq) {

        try {
            Long userIdx = userProvider.logIn(postLoginReq);
            User user = userProvider.getUser(userIdx);
            List<GetUserRes> getUserRes = new ArrayList<>();
            List<Address> getAddress = userProvider.getAddress(userIdx);
            List<PushNotificationAgreement> getAgreements = userProvider.getAgreements(userIdx);
            getUserRes.add(new GetUserRes(user, getAddress, getAgreements));

            List<String> list = new ArrayList<>();
            return new BaseResponse<>(getUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


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
                Long userIdx = getUser.getUserIdx();
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
    @SneakyThrows
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetUserRes>> getUser(@PathVariable("userIdx") Long userIdx) {
        Long userIdxByJwt = jwtService.getUserIdx();
        //userIdx와 접근한 유저가 같은지 확인
        if (!userIdx.equals(userIdxByJwt)) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }
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
    @SneakyThrows
    @DeleteMapping("/{userIdx}")
    public BaseResponse<String> deleteUser(@PathVariable("userIdx") Long userIdx) {
        Long userIdxByJwt = jwtService.getUserIdx();
        //userIdx와 접근한 유저가 같은지 확인
        if (!userIdx.equals(userIdxByJwt)) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }
        try {
            Long id = userService.deleteUser(userIdx);
            return new BaseResponse<>("true");
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("/user-token/{userIdx}")
    public BaseResponse<String> getUserToken(@PathVariable("userIdx") Long userIdx) {
        String userJwtByIdx = jwtService.createJwt(userIdx);
        return new BaseResponse<>(userJwtByIdx);
    }
    /**
     * 유저정보변경 API
     * [PATCH] /users/:userIdx
     */
//    @ResponseBody
//    @PatchMapping("/{userIdx}")
//    public BaseResponse<String> modifyUserName(@PathVariable("userIdx") Long userIdx, @RequestBody User user) {
//        try {
///**
// *********** 해당 부분은 7주차 - JWT 수업 후 주석해체 해주세요!  ****************
// //jwt에서 idx 추출.
// Long userIdxByJwt = jwtService.getUserIdx();
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
    @SneakyThrows
    @PatchMapping("/{userIdx}/membership")
    public BaseResponse<String> updateMembership(@PathVariable("userIdx") Long userIdx, @RequestParam("memberType") String memberType) {

        Long userIdxByJwt = jwtService.getUserIdx();
        //userIdx와 접근한 유저가 같은지 확인
        if (!userIdx.equals(userIdxByJwt)) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }

        if (!memberType.equals("BASIC") && !memberType.equals("ROCKET")) {
            return new BaseResponse<>(PATCH_USERS_INVALID_MEMBERSHIP_LEVEL);
        }
        try {
            userService.updateMembership(userIdx, memberType);
            return new BaseResponse<>(memberType);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @SneakyThrows
    @PatchMapping("/{userIdx}/address")
    public BaseResponse<PatchAddressRes> updateAddress(@PathVariable Long userIdx, @RequestBody PatchAddressReq getAddressReq) {
        Long userIdxByJwt = jwtService.getUserIdx();
        //userIdx와 접근한 유저가 같은지 확인
        if (!userIdx.equals(userIdxByJwt)) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }
        try {
            if (getAddressReq.getIsDefault()) {    // 지금 입력하는 주소지가 기본 주소라면 이미있는 기본 주소지를 0으로 만듬
                userService.initDefaultAddress(userIdx);
            }
            PatchAddressRes patchAddressRes = new PatchAddressRes(userService.updateAddress(getAddressReq));
            return new BaseResponse<>(patchAddressRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @SneakyThrows
    @DeleteMapping("/{userIdx}/address")
    public BaseResponse<List<GetAddressRes>> deleteAddress(@PathVariable Long userIdx, @RequestParam("addressIdx") int addressIdx) {
        Long userIdxByJwt = jwtService.getUserIdx();
        //userIdx와 접근한 유저가 같은지 확인
        if (!userIdx.equals(userIdxByJwt)) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }
        try {
            userService.deleteAddress(addressIdx);
            return getListBaseResponse(userService.getAddress(userIdx), userIdx);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @SneakyThrows
    @PostMapping("/{userIdx}/address")
    public BaseResponse<List<GetAddressRes>> insertAddress(@PathVariable Long userIdx, @RequestBody PostAddressReq postAddressReq) {
        Long userIdxByJwt = jwtService.getUserIdx();
        //userIdx와 접근한 유저가 같은지 확인
        if (!userIdx.equals(userIdxByJwt)) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }
        try {
            if (postAddressReq.getIsDefault()) {    // 지금 등록하는 주소지가 기본 주소라면 이미있는 기본 주소지를 0으로 만듬
                userService.initDefaultAddress(userIdx);
            }

            int lastAddressIdx = userService.insertAddress(userIdx, postAddressReq);
            userService.insertTimeInfo(lastAddressIdx, postAddressReq);

            return getListBaseResponse(userProvider.getAddress(userIdx), userIdx);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    @SneakyThrows
    @PatchMapping("{userIdx}/notification")
    public BaseResponse<GetPushNotificationAgreementRes> updatePushNotificationAgreement(@PathVariable Long userIdx, @RequestParam("notificationName") String notificationName) {
        Long userIdxByJwt = jwtService.getUserIdx();
        //userIdx와 접근한 유저가 같은지 확인
        if (!userIdx.equals(userIdxByJwt)) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }
        try {
            userIdx = userService.updatePushNotification(userIdx, notificationName);
            List<PushNotificationAgreement> pushNotificationAgreement = userProvider.getAgreements(userIdx);
            return new BaseResponse<>(new GetPushNotificationAgreementRes(pushNotificationAgreement.get(0)));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @NotNull
    private BaseResponse<List<GetAddressRes>> getListBaseResponse(List<Address> address2, @PathVariable Long userIdx) throws BaseException {
        Long userIdxByJwt = jwtService.getUserIdx();
        //userIdx와 접근한 유저가 같은지 확인
        if (!userIdx.equals(userIdxByJwt)) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }
        List<GetAddressRes> getAddressRes = new ArrayList<>();

        for (Address address : address2) {
            getAddressRes.add(new GetAddressRes(address));
        }
        return new BaseResponse<>(getAddressRes);
    }
}