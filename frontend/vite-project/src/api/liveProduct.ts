import { liveProduct } from "../types/DataTypes";
import { itemAxios } from "./http";
import { AxiosHeaders } from "axios";

const http = itemAxios();
const headers = new AxiosHeaders();
headers.set("Content-Type", "application/json;charset=utf-8");

const url = "/live-products";

async function postLiveProduct(data: Array<liveProduct>, accessToken: string) {
    headers.set("Authorization", `Bearer ${accessToken}`);
    return await http.post(url, data, { headers });
}

async function getLiveProduct(
    params: {
        page?: number;
        size?: number;
        "live-id": number;
    },
    accessToken: string
) {
    const response = await http.get(`${url}/list`, {
        params: params,
        headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + accessToken,
        },
    });
    return response.data.data;
}

async function delLiveProduct(liveId: string, accessToken: string) {
    headers.set("Authorization", `Bearer ${accessToken}`);
    return await http.delete(`${url}/${liveId}`, { headers });
}

async function fetchCalendarItem(page: number, size: number, liveid: number) {
    return await http.get(`${url}/list`, {
        params: { page: page, size: size, "live-id": liveid },
    });
}

export { postLiveProduct, fetchCalendarItem, getLiveProduct, delLiveProduct };
