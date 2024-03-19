import { chatAxios } from "./http";
import { AxiosHeaders } from "axios";

const http = chatAxios();
const headers = new AxiosHeaders();
headers.set("Content-Type", "application/json;charset=utf-8");

const url = "chat/block";

// 차단유저 등록
async function postBlockUserAPI(blockedId: number, accessToken: string) {
    await http.post(`${url}/${blockedId}`, {
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + accessToken
        }
    });
}

// 차단유저 삭제
async function deleteBlockUserAPI(blockedId: number, accessToken: string) {
    await http.delete(`${url}/${blockedId}`, {
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + accessToken
        }
    });
}

// 차단목록 조회
async function getBlockUserAPI(accessToken: string) {
    const response =  await http.get(`${url}`, { 
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + accessToken
        }
    });
    return response.data.data.block
}

export {getBlockUserAPI, deleteBlockUserAPI, postBlockUserAPI}