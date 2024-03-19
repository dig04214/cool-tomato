import { mainAxios } from "./http";

const http = mainAxios();
const url = "/analyze";

// 사후분석데이터 반환
async function postmortemAPI(broadcastId: number, accessToken: string) {
    const response = await http.get(`${url}/postmortem/${broadcastId}`, {
        headers: {
            Authorization: "Bearer " + accessToken,
        },
    });
    // console.log(response);
    return response.data.data;
}

export { postmortemAPI };
