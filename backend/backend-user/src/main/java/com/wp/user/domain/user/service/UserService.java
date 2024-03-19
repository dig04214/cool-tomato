package com.wp.user.domain.user.service;


import com.wp.user.domain.user.dto.request.*;
import com.wp.user.domain.user.dto.response.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    void addUser(AddUserRequest addUserRequest);
    DuplicateLoginIdResponse getUserByLoginId(String loginId);
    CheckEmailResponse checkEmail(CheckEmailRequest checkEmailRequest);
    CheckEmailVerificationResponse checkEmailVerification(String email, String code);
    LoginResponse login(LoginRequest logInRequest);
    void getLoginIdByEmail(String email);
    void getPasswordByEmail(FindPasswordRequest findPasswordRequest);
    void logout(HttpServletRequest httpServletRequest);
    GetUserResponse getUser(HttpServletRequest httpServletRequest);
    ModifyUserResponse modifyUser(HttpServletRequest httpServletRequest, MultipartFile profileImgFile, ModifyUserRequest modifyUserRequest);
    void removeUser(HttpServletRequest httpServletRequest);
    GetUserListResponse getUsers(HttpServletRequest httpServletRequest, int page, int size);
    void forceRemoveUser(HttpServletRequest httpServletRequest, Long id);
    void sendEmail(String email, String title, StringBuffer sb);
}
