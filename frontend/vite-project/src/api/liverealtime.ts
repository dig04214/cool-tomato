import { AxiosHeaders } from "axios";
import { liveAxios } from "./http";

const http = liveAxios();
const headers = new AxiosHeaders()
headers.set("Content-Type", "application/json;charset=utf-8")

const url = `analyze/realtime`;

async function fetchKeyword(roomId:number) {
    return http.get(`${url}/keyword/${roomId}`)
}

export {fetchKeyword}