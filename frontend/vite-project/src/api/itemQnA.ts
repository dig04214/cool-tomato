import { AxiosHeaders } from "axios";
import { itemAxios } from "./http";

const http = itemAxios();
const headers = new AxiosHeaders();
headers.set("Content-Type", "application/json;charset=utf-8");

const url = "/product-questions";

// 상품 번호로 상품 문의 리스트를 조회함
async function getQnAList(params: {
    page: number;
    size: number;
    "product-id": number;
}) {
    // return await http.get(url + "/list", { params });

    return await http.get(url + "/list", { params });
}

// 구매자가 상품 문의를 등록함
async function postItemQnA(
    data: {
        productId: number;
        questionContent: string;
    },
    accessToken: string
) {
    headers.set("Authorization", `Bearer ${accessToken}`);
    return await http.post(url, data, { headers });
}

// 구매자(문의 등록자)가 상품 문의를 삭제함
async function deleteItemQnA(productQuestionId: number, accessToken: string) {
    headers.set("Authorization", `Bearer ${accessToken}`);
    return await http.delete(`${url}/${productQuestionId}`, { headers });
}

// 구매자가 자신의 상품 문의 리스트를 조회함
async function buyerGetQnaAPI(page: number, size: number, accessToken: string) {
    try {
        const response = await http.get(`${url}/buyer/my/list`, {
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
            // console.log(responseData);
            return responseData;
        } else {
            // console.log("안돼~");
        }
    } catch (error) {
        // console.error(error);
        // console.log("응 에러야~");
    }
}

// 판매자가 받은 상품 문의 리스트를 조회함
async function sellerGetQnaAPI(
    page: number,
    size: number,
    accessToken: string
) {
    try {
        const response = await http.get(`${url}/seller/my/list`, {
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
            // console.log(responseData)
            return responseData;
        } else {
            // console.log("문의조회안돼~");
        }
    } catch (error) {
        console.error(error);
        // console.log("응 에러야~");
    }
}

// 판매자가 상품 문의 아이디로 상품 문의를 조회함(상세조회)
async function qnaDetailAPI(data: { productQuestionId: number }) {
    try {
        const response = await http.get(`${url}/${data.productQuestionId}`);
        const responseData = response.data;
        return responseData;
    } catch (error) {
        console.error(error);
    }
}

// 판매자가 상품 문의에 대한 답변을 등록함
async function sellerPutQnaAPI(
    data: {
        productQuestionBoardId: number;
        answerContent: string;
    },
    accessToken: string
) {
    try {
        await http.put(`${url}`, data, {
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + accessToken,
            },
        });
    } catch (error) {
        console.error(error);
    }
}

export {
    getQnAList,
    sellerPutQnaAPI,
    qnaDetailAPI,
    buyerGetQnaAPI,
    sellerGetQnaAPI,
    postItemQnA,
    deleteItemQnA,
};
