import { liveAxios } from "./http";

const http = liveAxios();
const url = "/live/search";

// 방송 제목 기반으로 방송 목록 반환
async function getTitleSearchAPI(params: {
    keyword: string;
    page: number;
    size: number;
}) {
    const response = await http.get(url + "/title", { params });
    return response.data
}

// 판매자 이름 기반으로 방송 목록 반환
async function getSellerSearchAPI(params: {
    name: string;
    page: number;
    size: number;
}) {
    const response = await http.get(url + "/seller", { params });
    return response.data
}

export { getTitleSearchAPI, getSellerSearchAPI }