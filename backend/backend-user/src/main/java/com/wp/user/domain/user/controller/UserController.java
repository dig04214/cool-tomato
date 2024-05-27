package com.wp.user.domain.user.controller;

import com.wp.user.domain.user.dto.request.*;
import com.wp.user.domain.user.dto.response.*;
import com.wp.user.domain.user.service.UserService;
import com.wp.user.global.common.code.SuccessCode;
import com.wp.user.global.common.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("v1/users")
@Tag(name = "회원 API", description = "회원 관리 용 API")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    @Operation(summary = "회원가입", description = "사용자는 회원 정보를 입력하여 회원가입을 합니다.")
    public ResponseEntity<SuccessResponse<?>> addUser(@Valid @RequestBody AddUserRequest addUserRequest) {

        userService.addUser(addUserRequest);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.INSERT_SUCCESS.getStatus())
                .message(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/join/check-login-id/{login-id}")
    @Operation(summary = "아이디 중복 확인", description = "사용자는 회원가입 시 로그인 ID를 입력하여 중복 여부를 확인합니다.")
    public ResponseEntity<SuccessResponse<?>> isDuplicateLoginId(@NotBlank(message = "로그인 ID를 입력해 주세요.") @PathVariable("login-id") String loginId) {
        DuplicateLoginIdResponse duplicateLoginIdResponse = userService.getUserByLoginId(loginId);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.SELECT_SUCCESS.getStatus())
                .message(SuccessCode.SELECT_SUCCESS.getMessage())
                .data(duplicateLoginIdResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/join/check-email")
    @Operation(summary = "이메일 인증", description = "사용자는 email을 입력하여 이메일 인증 번호를 이메일로 받습니다.")
    public ResponseEntity<SuccessResponse<?>> checkEmail(@Valid @RequestBody CheckEmailRequest checkEmailRequest) {
        userService.checkEmail(checkEmailRequest);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.INSERT_SUCCESS.getStatus())
                .message(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/join/check-email-verifications/{email}/{code}")
    @Operation(summary = "이메일 인증 확인", description = "사용자는 email과 인증번호를 입력하여 이메일 인증을 완료합니다.")
    public ResponseEntity<SuccessResponse<?>> checkEmailVerification(@NotBlank(message = "이메일을 입력해주세요.") @Email(message = "이메일 형식에 맞춰 입력해주세요.") @PathVariable String email, @NotBlank @PathVariable String code) {
        CheckEmailVerificationResponse checkEmailVerificationResponse = userService.checkEmailVerification(email, code);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.SELECT_SUCCESS.getStatus())
                .message(SuccessCode.SELECT_SUCCESS.getMessage())
                .data(checkEmailVerificationResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "사용자는 로그인 ID와 PASSWORD를 입력하여 로그인합니다.")
    public ResponseEntity<SuccessResponse<?>> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.login(loginRequest);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.INSERT_SUCCESS.getStatus())
                .message(SuccessCode.INSERT_SUCCESS.getMessage())
                .data(loginResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/find-login-id/{email}")
    @Operation(summary = "아이디 찾기", description = "사용자는 email을 입력하여 로그인 ID를 찾습니다.")
    public ResponseEntity<SuccessResponse<?>> findLoginId(@NotBlank(message = "이메일을 입력해주세요.") @Email(message = "이메일 형식에 맞춰 입력해주세요.") @PathVariable String email) {
        userService.getLoginIdByEmail(email);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.SELECT_SUCCESS.getStatus())
                .message(SuccessCode.SELECT_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/find-password")
    @Operation(summary = "비밀번호 찾기", description = "사용자는 로그인 ID와 email을 입력하여 password를 재설정합니다.")
    public ResponseEntity<SuccessResponse<?>> findPassword(@Valid @RequestBody FindPasswordRequest findPasswordRequest) {
        userService.getPasswordByEmail(findPasswordRequest);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.INSERT_SUCCESS.getStatus())
                .message(SuccessCode.INSERT_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    @Operation(summary = "로그아웃", description = "사용자는 로그아웃합니다.")
    public ResponseEntity<SuccessResponse<?>> logout(HttpServletRequest request) {
        userService.logout(request);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.DELETE_SUCCESS.getStatus())
                .message(SuccessCode.DELETE_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "개인 회원 정보 조회", description = "사용자는 자신의 회원 정보를 조회합니다.")
    public ResponseEntity<SuccessResponse<?>> getUser(HttpServletRequest request) {
        GetUserResponse getUserResponse = userService.getUser(request);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.SELECT_SUCCESS.getStatus())
                .message(SuccessCode.SELECT_SUCCESS.getMessage())
                .data(getUserResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "회원 정보 수정", description = "사용자는 회원 정보를 수정합니다.")
    public ResponseEntity<SuccessResponse<?>> updateUser(HttpServletRequest httpServletRequest,
                                                         @RequestPart(required = false) MultipartFile profileImgFile,
                                                         @Valid @RequestPart ModifyUserRequest modifyUserRequest) {
        ModifyUserResponse modifyUserResponse = userService.modifyUser(httpServletRequest, profileImgFile, modifyUserRequest);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.UPDATE_SUCCESS.getStatus())
                .message(SuccessCode.UPDATE_SUCCESS.getMessage())
                .data(modifyUserResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping
    @Operation(summary = "회원탈퇴", description = "사용자는 회원탈퇴를 합니다.")
    public ResponseEntity<SuccessResponse<?>> removeUser(HttpServletRequest request) {
        userService.removeUser(request);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.DELETE_SUCCESS.getStatus())
                .message(SuccessCode.DELETE_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/admin")
    @Operation(summary = "전체 회원 정보 목록 조회", description = "관리자는 전체 회원 정보를 조회합니다.")
    public ResponseEntity<SuccessResponse<?>> getUsers(HttpServletRequest request, @NotNull(message = "페이지 번호를 입력해주세요.") @RequestParam int page, @NotNull(message = "크기를 입력해주세요.") @RequestParam int size) {
        GetUserListResponse getUserListResponse = userService.getUsers(request, page, size);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.SELECT_SUCCESS.getStatus())
                .message(SuccessCode.SELECT_SUCCESS.getMessage())
                .data(getUserListResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/admin/{id}")
    @Operation(summary = "회원탈퇴", description = "관리자는 강제로 회원을 탈퇴시킵니다.")
    public ResponseEntity<SuccessResponse<?>> forceRemoveUser(HttpServletRequest request, @NotNull(message = "사용자의 id를 입력해주세요.") @PathVariable Long id) {
        userService.forceRemoveUser(request, id);
        SuccessResponse<?> response = SuccessResponse.builder()
                .status(SuccessCode.DELETE_SUCCESS.getStatus())
                .message(SuccessCode.DELETE_SUCCESS.getMessage())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
