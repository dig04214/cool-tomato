import { mainAxios } from "./http";
import { AxiosHeaders, AxiosError } from "axios";
import { ReissueTokenAPI } from "./auth";
import { RegisterUser, RegisterSeller, userInfo } from "../types/DataTypes";

const http = mainAxios();
const headers = new AxiosHeaders();
headers.set("Content-Type", "application/json;charset=utf-8");

const url = "/users";

// 회원 API

async function loginUser(data: { loginId: string; password: string }) {
    try {
        const response = await http.post(`${url}/login`, data);
        const responseData = response.data;
        if (responseData.status === 201) {
            return responseData;
        }
    } catch (error) {
        if (error instanceof AxiosError) {
            console.error(error);
            if (error.response) {
                //   console.log(error.response.data.reason); // 'reason' 출력
                if (error.response.status === 401) {
                    if (error.response.data.divisionCode === "B003") {
                        throw new Error("비밀번호가 일치하지 않습니다.");
                    } else {
                        throw new Error("가입된 아이디가 아닙니다.");
                    }
                }
            }
            // console.log("에러에러에러에러에러");
        }
    }
}

async function logoutAPI(accessToken: string, refreshToken: string) {
    try {
        const response = await http.delete(`${url}/logout`, {
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + accessToken,
            },
        });
        return response;
    } catch (error) {
        if (error instanceof AxiosError) {
            if (error.response) {
                if (error.response.status === 401) {
                    if (error.response.data.divisionCode === "G013") {
                        localStorage.clear();
                        window.location.reload();
                        const response = await ReissueTokenAPI({
                            accessToken,
                            refreshToken,
                        });
                        console.log(response);
                    }
                }
            }
        }
        throw error;
    }
}

async function signupUserAPI(data: RegisterUser) {
    try {
        const response = await http.post(`${url}/join`, data);
        const responseData = response.data;
        if (responseData.status === 201) {
            return 1;
        } else if (responseData.status === 409) {
            return 33;
        }
    } catch (error) {
        if (error instanceof Error) {
            const axiosError = error as AxiosError;
            console.error(error);
            if (axiosError.response && axiosError.response.status === 409) {
                //   console.log("이미 가입된 이메일");
                return 33;
            }
            // console.log("에러에러에러에러에러");
        }
    }
}

async function checkIdAPI(data: { id: string }) {
    try {
        const response = await http.get(
            `${url}/join/check-login-id/${data.id}`
        );
        const responseData = response.data;
        if (
            responseData.status === 200 &&
            responseData.data.duplicate === false
        ) {
            return 1;
        } else {
            // console.log("아이디가 중복되었거나 요청에 문제가 있습니다.");
        }
    } catch (error) {
        console.error(error);
    }
}

async function sendEmailAPI(data: { email: string }) {
    try {
        const response = await http.post(`${url}/join/check-email`, data);
        const responseData = response.data;
        if (responseData.status === 201) {
            return 1;
        }
        console.log(response);
    } catch (error) {
        console.error(error);
    }
}

async function checkEmailAPI(data: { email: string; code: string }) {
    try {
        const response = await http.get(
            `${url}/join/check-email-verifications/${data.email}/${data.code}`
        );
        const responseData = response.data;
        if (responseData.status === 200 && responseData.data.verify === true) {
            return 1;
        } else {
            console.log(responseData);
        }
    } catch (error) {
        console.error(error);
    }
}

async function findIdAPI(data: { email: string }) {
    try {
        const response = await http.get(`${url}/find-login-id/${data.email}`);
        const responseData = response.data;
        if (responseData.status === 200) {
            // console.log("이메일 전송이 완료되었습니다");
        } else if (
            responseData.status === 401 &&
            responseData.divisionCode === "B005"
        ) {
            // console.log("가입된 이메일이 아닙니다 회원가입 ㄱ");
        } else {
            // console.log(responseData)
            // console.log("걍 문제있음")
        }
    } catch (error) {
        console.error(error);
        // console.log("오류임")
    }
}

async function findPwAPI(data: { loginId: string; email: string }) {
    try {
        const response = await http.post(`${url}/find-password`, data);
        if (response.status === 201) {
            return 1; // 성공을 나타내는 값 (원하는 값으로 변경 가능)
        } else if (response.status === 400) {
            // console.log("올바른 아이디 혹은 이메일이 아님요");
            return 0;
        } else if (response.status === 401) {
            // console.log("아이디와 이메일의 가입정보가 일치하지 않습니다")
        }
    } catch (error) {
        // console.error("비밀번호 찾기 실패", error);
        return 0;
    }
}

async function getMyInfoAPI(accessToken: string) {
    try {
        const response = await http.get(`${url}`, {
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + accessToken,
            },
        });
        const responseData = response.data;
        return responseData;
    } catch (error) {
        console.log(error);
        throw error;
    }
}

async function postMyInfoAPI(
    data: userInfo,
    accessToken: string,
    refreshToken: string
) {
    try {
        const response = await http.post(`${url}`, data, {
            headers: {
                "Content-Type": "mulitpart/form-data",
                Authorization: "Bearer " + accessToken,
                "Refresh-Token": refreshToken,
            },
        });
        const responseData = response.data;
        if (responseData.status === 200) {
            // console.log("정보수정성공")
        }
        return responseData;
    } catch (error) {
        console.log(error);
        throw error;
    }
}

async function deleteMyInfoAPI(accessToken: string) {
    try {
        const response = await http.delete(`${url}`, {
            headers: {
                "Content-Type": "mulitpart/form-data",
                Authorization: "Bearer " + accessToken,
            },
        });
        const responseData = response.data;
        return responseData;
    } catch (error) {
        console.log(error);
        throw error;
    }
}

// 판매자 API

async function registerSellerAPI(data: RegisterSeller, accessToken: string) {
    try {
        const response = await http.post(`${url}/sellers`, data, {
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + accessToken,
            },
        });
        const responseData = response.data;
        if (responseData.status === 201) {
            return responseData;
        }
    } catch (error) {
        if (error instanceof AxiosError) {
            console.error(error);
            if (error.response) {
                console.log(error.response.data.reason); // 'reason' 출력
                if (error.response.status === 401) {
                    if (error.response.data.divisionCode === "B008") {
                        throw new Error("로그인 여부를 다시 확인해주세요");
                    } else {
                        // console.log("미가입");
                        throw new Error("JWT 문제");
                    }
                }
            }
            // console.log("에러에러에러에러에러");
        }
    }
}

// 판매자 상세정보 조회 함수
async function getSellerDetailAPI(sellerId: number) {
    try {
        const response = await http.get(`${url}/sellers/${sellerId}`);
        const responseData = response.data;
        if (responseData.status === 200) {
            return responseData;
        }
    } catch (error) {
        console.log("");
        throw error;
    }
}

// 팔로우 등록 함수
async function followSellerAPI(
    sellerId: number,
    alarmSetting: boolean,
    accessToken: string
) {
    try {
        const response = await http.post(
            `${url}/follow`,
            { sellerId, alarmSetting },
            {
                headers: {
                    "Content-Type": "application/json",
                    Authorization: "Bearer " + accessToken,
                },
            }
        );
        const responseData = response.data;
        if (responseData.status === 201) {
            return 1;
        }
    } catch (error) {
        console.log("");
        throw error;
    }
}

// 팔로우 여부 조회 함수
async function checkFollowAPI(sellerId: number, accessToken: string) {
    try {
        const response = await http.get(`${url}/follow/${sellerId}`, {
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + accessToken,
            },
        });
        const responseData = response.data;
        if (responseData.data.follow === true) {
            return 1;
        } else {
            return 2;
        }
    } catch (error) {
        console.log("팔로우 여부 조회 실패");
        throw error;
    }
}

// 팔로우 취소 함수
async function unfollowSellerAPI(sellerId: number, accessToken: string) {
    try {
        const response = await http.delete(`${url}/follow/${sellerId}`, {
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + accessToken,
            },
        });
        const responseData = response.data;
        if (responseData.status === 200) {
            return 1;
        }
    } catch (error) {
        console.log("");
        throw error;
    }
}

// 팔로잉 목록 조회 함수
async function getFollowingListAPI(accessToken: string) {
    try {
        const response = await http.get(`${url}/follow/following`, {
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + accessToken,
            },
        });
        const responseData = response.data;
        if (responseData.status === 200) {
            return responseData;
        }
    } catch (error) {
        console.log("");
        throw error;
    }
}

// 팔로워 목록 조회 함수
async function getFollowerListAPI(accessToken: string) {
    try {
        const response = await http.get(`${url}/follow/follower`, {
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + accessToken,
            },
        });
        const responseData = response.data;
        if (responseData.status === 200) {
            return responseData;
        }
    } catch (error) {
        console.log("");
        throw error;
    }
}

// 유저 정보 get 함수
async function fetchProfile(accessToken: string, refreshToken: string) {
    headers.set("Authorization", `Bearer ${accessToken}`);
    headers.set("Refresh-Token", refreshToken);
    headers.set("Content-Type", "application/json");

    return await http.get(url, { headers: headers });
}

// 유저 정보 수정 함수
async function setProfile(
    data: FormData,
    accessToken: string,
    refreshToken: string
) {
    headers.set("Authorization", `Bearer ${accessToken}`);
    headers.set("Refresh-Token", refreshToken);
    headers.set("Content-Type", "multipart/form-data");
    return await http.post(url, data, { headers: headers });
}

// 관리자 API
// 모든 회원 정보 조회
async function getAllUsersAPI(page: number, size: number, accessToken: string) {
    try {
        const response = await http.get(`${url}/admin`, {
            params: {
                page,
                size,
            },
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + accessToken,
            },
        });
        const responseData = response.data;
        if (responseData.status === 200) {
            return responseData;
        }
    } catch (error) {
        console.log("");
        throw error;
    }
}

// 관리자가 회원을 강제로 탈퇴시키는 함수
async function deleteUserByAdminAPI(id: number) {
    try {
        const response = await http.delete(`${url}/admin/${id}`);
        const responseData = response.data;
        return responseData;
    } catch (error) {
        console.log("회원 탈퇴 실패");
        throw error;
    }
}

// 관리자의 판매자 전환 신청 목록 조회 함수
async function getSellerApplicationsAPI(
    page: number,
    size: number,
    accessToken: string
) {
    try {
        const response = await http.get(`${url}/sellers/admin`, {
            params: {
                page,
                size,
            },
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + accessToken,
            },
        });
        const responseData = response.data;
        return responseData;
    } catch (error) {
        console.log("판매자 전환 신청 목록 조회 실패");
        throw error;
    }
}

// 관리자의 판매자 전환 신청 상세 조회 함수
async function getSellerApplicationDetailAPI(sellerInfoId: number) {
    try {
        const response = await http.get(
            `${url}/sellers/admin-sellers/${sellerInfoId}`
        );
        const responseData = response.data;
        return responseData;
    } catch (error) {
        console.log("");
        throw error;
    }
}

// 관리자의 판매자 전환 승인 함수
async function approveSellerApplicationAPI(
    sellerInfoId: number,
    accessToken: string
) {
    headers.set("Authorization", `Bearer ${accessToken}`);

    return await http.put(
        `${url}/sellers/admin/approve/${sellerInfoId}`,
        {},
        { headers: headers }
    );
}

// 관리자의 판매자 전환 철회 함수
async function cancelSellerApplicationAPI(sellerInfoId: number) {
    try {
        const response = await http.put(
            `${url}/sellers/admin/cancle/${sellerInfoId}`
        );
        const responseData = response.data;
        return responseData;
    } catch (error) {
        console.log("판매자 전환 철회 실패");
        throw error;
    }
}

export {
    loginUser,
    signupUserAPI,
    checkIdAPI,
    sendEmailAPI,
    checkEmailAPI,
    registerSellerAPI,
    findIdAPI,
    findPwAPI,
    logoutAPI,
    getMyInfoAPI,
    postMyInfoAPI,
    deleteMyInfoAPI,
    getAllUsersAPI,
    deleteUserByAdminAPI,
    getSellerApplicationsAPI,
    getSellerApplicationDetailAPI,
    approveSellerApplicationAPI,
    cancelSellerApplicationAPI,
    getSellerDetailAPI,
    followSellerAPI,
    checkFollowAPI,
    unfollowSellerAPI,
    getFollowingListAPI,
    getFollowerListAPI,
    setProfile,
    fetchProfile,
};
