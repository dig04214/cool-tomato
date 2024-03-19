import { Client, IStompSocket } from "@stomp/stompjs";
import { chatbotAxios, chatbotSocket, chatbotWebSocketUrl } from "./http";
import { AxiosHeaders } from "axios";
import SockJS from "sockjs-client";

const http = chatbotAxios();
const headers = new AxiosHeaders();
headers.set("Content-Type", "application/json;charset=utf-8");

const url = "chatbots";

// 챗봇 질의응답 등록
async function RegisterChatbotAPI(
    data: { roomId: number; question: string; answer: string },
    accessToken: string
) {
    headers.set("Authorization", `Bearer ${accessToken}`);
    await http.post(`${url}`, data, { headers });
}

// 챗봇 질의응답 수정
async function EditChatbotAPI(
    data: {
        chatbotId: number;
        roomId: number;
        question: string;
        answer: string;
    },
    accessToken: string
) {
    const response = await http.put(`${url}`, data, {
        headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + accessToken,
        },
    });
    const responseData = response.data;
    console.log(responseData);
}

// 챗봇 질의응답 삭제
async function DeleteChatbotAPI(chatbotId: number, accessToken: string) {
    await http.delete(`${url}/${chatbotId}`, {
        headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + accessToken,
        },
    });
}

// 내가 등록한 챗봇 질의응답 확인
async function GetChatbotListAPI(
    params: {
        page?: number;
        size?: number;
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

function getChatbotStomp() {
    console.log("chatbot getChatbotStomp");

    const client = new Client({
        brokerURL: chatbotWebSocketUrl,
        debug: function (msg) {
            console.log(msg);
        },
        reconnectDelay: 5000,
    });
    if (typeof WebSocket !== "function") {
        let sock = chatbotSocket();
        client.webSocketFactory = () => {
            console.log("chatting webSocketFactory sockjs");
            if (
                sock.readyState === SockJS.CLOSED ||
                sock.readyState === SockJS.CLOSING
            ) {
                sock = chatbotSocket();
            }
            return sock as IStompSocket;
        };
    }
    client.onDisconnect = () => {
        console.log("stomp disconnected");
    };
    client.onWebSocketClose = () => {
        console.log("stomp websocket closed");
    };
    client.onStompError = (frame) => {
        console.log("stomp error");
        console.log(frame);
    };

    return client;
}

export {
    RegisterChatbotAPI,
    EditChatbotAPI,
    DeleteChatbotAPI,
    GetChatbotListAPI,
    getChatbotStomp,
};
