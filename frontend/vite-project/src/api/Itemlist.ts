import { itemAxios } from "./http";
import { AxiosHeaders } from "axios";

const http = itemAxios();
const headers = new AxiosHeaders();
headers.set("Content-Type", "application/json;charset=utf-8");

const URL = "/products";

async function ItemListDetailFetch(data: { id: number }) {
    const response = await http.get(`${URL}/${data.id}`);
    return response;
}

async function ItemAddFunction(data: FormData, at: string) {
    headers.set("Authorization", `Bearer ${at}`);
    headers.set("Content-Type", "multipart/form-data");
    const response = await http.post(`${URL}`, data, { headers: headers });
    return response;

}

async function ItemPutFunction(data: FormData, at: string) {
    headers.set("Authorization", `Bearer ${at}`);
    headers.set("Content-Type", "multipart/form-data");
    const response = await http.put(`${URL}`, data, { headers: headers });
    return response;
}

// ITEM조회 params가 바뀌어서 수정했습니다 충돌나면 요걸로 해주세욤
async function ItemListFetch(data: {
    page?: number;
    size?: number;
    "category-id"?: string;
    sellerId?: number;
}) {
    const response = await http.get(`${URL}/list`, {
        params: data,
    });
    return response.data.data;
}

async function ItemDetailDelete(id: number, at: string) {
    headers.set("Authorization", `Bearer ${at}`);
    await http.delete(`${URL}/${id}`, { headers: headers });
}

async function ItemDetailFetch(id: number) {
    const response = await http.get(`${URL}/${id}`);
    return response.data.data;
}

async function sellersMyproductsAPI(page: number, size: number, at: string) {
    headers.set("Authorization", `Bearer ${at}`);
    const response = await http.get(`${URL}/my/list`, {
        params: {
            page: page,
            size: size,
        }, headers: headers
    },);
    return response.data;
}


async function SellerBroadcastFetch(data: { page: number; size: number }) {
    const response = await http.get("products/my/list", {
        params: { page: data.page, size: data.size, },
    });
    return response.data.data.list;
}

async function ItemListSellerGet(
    params: { page: number; size: number },
    accessToken: string
) {
    headers.set("Authorization", `Bearer ${accessToken}`);
    return await http.get("/products/my/list", { headers, params });
}

async function ItemOneFetch(id: number) {
    const response = await http.get(`products/${id}`)
    return response.data.data
}

// 구매자 최근본상품 조회
async function GetRecentAPI(params: {idList: [number]}, accessToken: string) {
    const response = await http.get(`${URL}/stop`, {
        params: params,
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + accessToken
        }
    });
    return response.data
}

async function fetchCategory() {
    return await http.get('categories')
}



export {
    ItemListDetailFetch,
    ItemAddFunction,
    ItemListFetch,
    ItemDetailDelete,
    ItemDetailFetch,
    sellersMyproductsAPI,
    SellerBroadcastFetch,
    ItemListSellerGet,
    ItemOneFetch,
    ItemPutFunction,
    GetRecentAPI,
    fetchCategory
};
