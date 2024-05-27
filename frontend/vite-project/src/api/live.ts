import { AxiosHeaders } from "axios";
import { liveAxios } from "./http";

const http = liveAxios();
const headers = new AxiosHeaders()
headers.set("Content-Type", "application/json;charset=utf-8")

const url = "/live/search";

async function fetchLiveCalendar(date: string, page: number, size: number) {
    return http.get(`${url}/date`, {
        params : {
            date: date,
            page: page,
            size: size
        }
    })
}

async function fetchLiveCarousel() {
    return http.get(`${url}/carousel`)
}

export { fetchLiveCalendar, fetchLiveCarousel }
