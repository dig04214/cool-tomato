import { Client, IStompSocket } from "@stomp/stompjs";
import { chatSocket, chatWebSocketUrl, chatAxios } from "./http";
import SockJS from "sockjs-client";
import { AxiosHeaders } from "axios";

const headers = new AxiosHeaders();
headers.set("Content-Type", "application/json;charset=utf-8");

const url = "/chat/block";
const http = chatAxios();

function getStompClient(accessToken: string) {
    console.log("chatting getStompClient");

    const client = new Client({
        brokerURL: chatWebSocketUrl,
        connectHeaders: {
            Authorization: "Bearer " + accessToken,
        },
        debug: function (str) {
            console.log(str);
        },
        reconnectDelay: 5000,
    });
    if (typeof WebSocket !== "function") {
        let sock = chatSocket();
        client.webSocketFactory = () => {
            console.log("chatting webSocketFactory sockjs");
            if (
                sock.readyState === SockJS.CLOSED ||
                sock.readyState === SockJS.CLOSING
            ) {
                sock = chatSocket();
            }
            return sock as IStompSocket;
            // return sock as IStompSocket;
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

function postBlockUser(blockUserId: number, accessToken: string) {
    headers.set("Authorization", "Bearer " + accessToken);
    return http.post(url + "/" + blockUserId, {}, { headers });
}

export { getStompClient, postBlockUser };
