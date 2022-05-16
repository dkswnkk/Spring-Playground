package com.example.demo.src.controller;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.domain.dto.sign.PostLoginReq;
import com.example.demo.src.domain.dto.user.*;
import com.example.demo.src.domain.dto.user.address.GetAddressRes;
import com.example.demo.src.domain.dto.user.address.PatchAddressReq;
import com.example.demo.src.domain.dto.user.address.PatchAddressRes;
import com.example.demo.src.domain.dto.user.address.PostAddressReq;
import com.example.demo.src.domain.dto.user.wish.GetWishListRes;
import com.example.demo.src.domain.entitiy.user.Address;
import com.example.demo.src.domain.entitiy.user.PushNotificationAgreement;
import com.example.demo.src.domain.entitiy.user.User;
import com.example.demo.src.service.user.UserProvider;
import com.example.demo.src.service.user.UserService;
import com.example.demo.src.service.user.recentlyviewproduct.RecentlyViewProductProvider;
import com.example.demo.src.service.user.wish.WishListProvider;
import com.example.demo.src.service.user.wish.WishListService;
import com.example.demo.utils.JwtService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private final WishListProvider wishListProvider;
    private final WishListService wishListService;
    private final RecentlyViewProductProvider recentlyViewProductProvider;
    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!


    @GetMapping("/kakao-login")
    public String kakaoCallBack(@RequestParam String code) throws BaseException {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=8ead72cbd44f9943b161c5418d935086"); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri=http://localhost:8080/app/users/kakao-login"); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&client_secret=ld8Pl5EGGd24gdOzo6M0TRRw08yFWK7g");
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
//            int responseCode = conn.getResponseCode();
//            System.out.println("responseCode : " + responseCode);

            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result.toString());

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();


            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }

    @GetMapping("kakao-user")
    public KakaoUserRes getUserInfo(@RequestParam String access_Token) {

        //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
//        HashMap<String, Object> userInfo = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        JsonObject kakao_account = null;
        KakaoUserRes kakaoUserRes = new KakaoUserRes();
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            //    요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

//            int responseCode = conn.getResponseCode();
//            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            kakaoUserRes.setName(properties.getAsJsonObject().get("nickname").getAsString());
            kakaoUserRes.setEmail(kakao_account.getAsJsonObject().get("email").getAsString());
            kakaoUserRes.setProfileImage(properties.getAsJsonObject().get("profile_image").getAsString());
//            kakaoUserRes.setAccessToken(access_Token);

//            userInfo.put("nickname", nickname);
//            userInfo.put("email", email);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return kakaoUserRes;
    }

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


    @PostMapping("/log-in")
    public BaseResponse<List<GetUserRes>> logIn(@RequestBody PostLoginReq postLoginReq) {
        try {
            Long userIdx = userProvider.logIn(postLoginReq);
            User user = userProvider.getUser(userIdx);
            List<GetUserRes> getUserRes = new ArrayList<>();
            List<Address> getAddress = userProvider.getAddress(userIdx);
            List<PushNotificationAgreement> getAgreements = userProvider.getAgreements(userIdx);
            getUserRes.add(new GetUserRes(jwtService.createJwt(userIdx), user, getAddress, getAgreements));

            return new BaseResponse<>(getUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 모든 회원 조회
     */
//    @ResponseBody
//    @GetMapping("")
//    public BaseResponse<List<GetUserRes>> getUsers() {
//        try {
//            List<GetUserRes> getUserRes = new ArrayList<>(); // 반환할거 클라이언트에게
//            List<User> getUsers = userProvider.getUsers();
//            for (User getUser : getUsers) {
//                Long userIdx = getUser.getUserIdx();
//                List<Address> getAddress = userProvider.getAddress(userIdx);
//                List<PushNotificationAgreement> getAgreements = userProvider.getAgreements(userIdx);
//                getUserRes.add(new GetUserRes(getUser, getAddress, getAgreements));
//            }
//            return new BaseResponse<>(getUserRes);
//
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }

    /**
     * 인덱스로 조회
     */
    @SneakyThrows
    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetUserRes>> getUser(@PathVariable("userIdx") Long userIdx) {
        try {
            Long userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (!userIdx.equals(userIdxByJwt)) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
        try {
            List<GetUserRes> getUserRes = new ArrayList<>(); // 반환할거 클라이언트에게
            User getUser = userProvider.getUser(userIdx);
            List<Address> getAddress = userProvider.getAddress(userIdx);
            List<PushNotificationAgreement> getAgreements = userProvider.getAgreements(userIdx);
            getUserRes.add(new GetUserRes(jwtService.getJwt(), (User) getUser, getAddress, getAgreements));
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
        try {
            Long userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (!userIdx.equals(userIdxByJwt)) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
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
        try {
            Long userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (!userIdx.equals(userIdxByJwt)) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
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


    @PatchMapping("/{userIdx}/profileImage")
    public BaseResponse<Boolean> updateProfileImage(@PathVariable Long userIdx, @RequestBody PatchProfileImageReq patchProfileImageReq) {
        try {
            Long userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (!userIdx.equals(userIdxByJwt)) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
        try {
            userService.updateUserProfileImage(userIdx, patchProfileImageReq.getUrl());
            return new BaseResponse<>(true);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @SneakyThrows
    @PatchMapping("/{userIdx}/address")
    public BaseResponse<PatchAddressRes> updateAddress(@PathVariable Long userIdx, @RequestBody PatchAddressReq getAddressReq) {
        try {
            Long userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (!userIdx.equals(userIdxByJwt)) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

        try {
            if (getAddressReq.getIsDefault()) {    // 지금 입력하는 주소지가 기본 주소라면 이미있는 기본 주소지를 0으로 만듬
                userService.initDefaultAddress(userIdx);
            }
            PatchAddressRes patchAddressRes = new PatchAddressRes(userService.updateAddress(userIdx, getAddressReq));
            return new BaseResponse<>(patchAddressRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @SneakyThrows
    @DeleteMapping("/{userIdx}/address")
    public BaseResponse<List<GetAddressRes>> deleteAddress(@PathVariable Long userIdx, @RequestParam("addressIdx") int addressIdx) {
        try {
            Long userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (!userIdx.equals(userIdxByJwt)) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
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
        try {
            Long userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (!userIdx.equals(userIdxByJwt)) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
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

    @GetMapping("/{userIdx}/wishList")
    public BaseResponse<List<GetWishListRes>> getWishList(@PathVariable Long userIdx) {
        try {
            Long userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (!userIdx.equals(userIdxByJwt)) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

        try {
            return new BaseResponse<>(wishListProvider.getWishList(userIdx));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @DeleteMapping("/{userIdx}/wishList")
    public BaseResponse<String> getWishList(@PathVariable Long userIdx, @RequestParam Long productIdx) {
        try {
            Long userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (!userIdx.equals(userIdxByJwt)) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

        try {
            wishListService.deleteWishList(userIdx, productIdx);
            return new BaseResponse<>("찜목록에서 제거했습니다.");
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @PostMapping("/{userIdx}/wishList")
    public BaseResponse<String> postWishList(@PathVariable Long userIdx, @RequestParam Long productIdx) {
        try {
            Long userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (!userIdx.equals(userIdxByJwt)) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

        try {
            wishListService.insertWishList(userIdx, productIdx);
            return new BaseResponse<>("찜목록에 추가했습니다.");
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @GetMapping("/{userIdx}/recently-view-product")
    public BaseResponse<List<GetRecentlyViewProductRes>> getRecentlyViewProducts(@PathVariable Long userIdx) {
        try {
            Long userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (!userIdx.equals(userIdxByJwt)) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
        try {
            return new BaseResponse<>(recentlyViewProductProvider.getRecentlyViewProduct(userIdx));
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }


    @SneakyThrows
    @PatchMapping("{userIdx}/notification")
    public BaseResponse<GetPushNotificationAgreementRes> updatePushNotificationAgreement(@PathVariable Long userIdx, @RequestParam("notificationName") String notificationName) {
        try {
            Long userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (!userIdx.equals(userIdxByJwt)) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
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
        try {
            Long userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (!userIdx.equals(userIdxByJwt)) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
        List<GetAddressRes> getAddressRes = new ArrayList<>();

        for (Address address : address2) {
            getAddressRes.add(new GetAddressRes(address));
        }
        return new BaseResponse<>(getAddressRes);
    }
}