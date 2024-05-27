import { authAxios } from "./http";

const http = authAxios();
const url = "/auth";


// 토큰 재발급
async function ReissueTokenAPI(data: {accessToken: string, refreshToken: string}) {
    await http.post(`${url}/reissue`, data);
}

export {ReissueTokenAPI}
