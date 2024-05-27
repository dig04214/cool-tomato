import {
    Button,
    Center,
    Divider,
    Flex,
    Input,
    InputGroup,
    InputRightElement,
    ListItem,
    Text,
    UnorderedList,
} from "@chakra-ui/react";
import { useSelector } from "react-redux";
import { RootState } from "../../redux/stores/store";
import { useParams } from "react-router-dom";
import { useEffect, useRef, useState } from "react";
import {
    chatMessageRecv,
    chatMessageSend,
    chatbotMessage,
} from "../../types/DataTypes";
import React from "react";
import { getStompClient } from "../../api/chatting";
import { getChatbotStomp } from "../../api/chatbot";

export default function BuyerChat() {
    const [message, setMessage] = useState<string>("");
    const [recv, setRecv] = useState<Array<chatMessageRecv>>([]);

    const accessToken = useSelector(
        (state: RootState) => state.user.accessToken
    );
    const nickname = useSelector((state: RootState) => state.user.nickname);
    const userId = useSelector((state: RootState) => state.user.userId);
    const roomId = useParams().roomId!;
    const id = parseInt(roomId);
    const stomp = useRef(getStompClient(accessToken));
    const chatbot = useRef(getChatbotStomp());

    useEffect(() => {
        console.log("BuyerChat useEffect 1");

        stomp.current.onConnect = () => {
            console.log("stomp connected");
            stomp.current.subscribe(
                `/sub/room/` + id,
                (msg) => {
                    const recvMsg: chatMessageRecv = JSON.parse(msg.body);

                    setRecv((prev) => [...prev, recvMsg]);
                },
                { Authorization: "Bearer " + accessToken }
            );
        };

        chatbot.current.onConnect = () => {
            console.log("chatbot connected");
            chatbot.current.subscribe(`/sub/chat/room/` + id, (msg) => {
                const recvMsg: chatbotMessage = JSON.parse(msg.body);
                const chatbotMessage: chatMessageRecv = {
                    senderId: 0,
                    senderNickname: "챗봇",
                    message: recvMsg.message,
                };

                setRecv((prev) => [...prev, chatbotMessage]);
            });
        };

        stomp.current.activate();
        chatbot.current.activate();
        const stompClient = stomp.current;
        const chatbotClient = chatbot.current;
        return () => {
            stompClient.deactivate();
            chatbotClient.deactivate();
        };
    }, []);

    function handleChange(e: React.ChangeEvent<HTMLInputElement>) {
        setMessage(e.target.value);
    }
    function handleKeyEnter(e: React.KeyboardEvent<HTMLInputElement>) {
        if (e.key === "Enter") {
            if (!stomp.current.connected) stomp.current.activate();
            if (!chatbot.current.connected) chatbot.current.activate();
            if (message === "") return;
            if (message[0] === "!") sendChatbotMessage();
            else sendMessage();
            setMessage("");
        }
    }
    function handleSubmit(e: React.MouseEvent<HTMLButtonElement, MouseEvent>) {
        e.preventDefault();
        if (!stomp.current.connected) stomp.current.activate();
        if (!chatbot.current.connected) chatbot.current.activate();
        if (message === "") return;
        if (message[0] === "!") sendChatbotMessage();
        else sendMessage();
        setMessage("");
    }

    function sendMessage() {
        const messageSend: chatMessageSend = {
            senderId: userId,
            senderNickname: nickname,
            message: message,
            roomId: id,
        };
        stomp.current.publish({
            destination: "/pub/message",
            headers: { Authorization: "Bearer " + accessToken },
            body: JSON.stringify(messageSend),
        });
    }

    function sendChatbotMessage() {
        const chatbotMessage: chatbotMessage = {
            roomId: id,
            writer: userId,
            message: message.slice(1),
        };
        chatbot.current.publish({
            destination: "/pub/chat/message",
            body: JSON.stringify(chatbotMessage),
        });
    }

    return (
        <>
            <Flex direction={"column"} h={"100%"} p={"1rem"}>
                <Center>
                    <Text as={"b"} fontSize={"2xl"}>
                        실시간 채팅
                    </Text>
                </Center>
                <Divider></Divider>
                <Flex
                    flex="1"
                    direction={"column"}
                    overflowY={"auto"}
                    pt={"1rem"}
                    justifyContent={"flex-end"}
                >
                    <UnorderedList>
                        {recv.map((msg, index) => (
                            <ListItem key={index}>
                                {msg.senderNickname} : {msg.message}
                            </ListItem>
                        ))}
                    </UnorderedList>
                </Flex>
                <InputGroup>
                    <Input
                        variant="filled"
                        placeholder="채팅을 입력해주세요"
                        value={message}
                        onChange={handleChange}
                        onKeyDown={handleKeyEnter}
                    />
                    <InputRightElement>
                        <Button
                            variant="ghost"
                            color="themeGreen.500"
                            onClick={handleSubmit}
                        >
                            전송
                        </Button>
                    </InputRightElement>
                </InputGroup>
            </Flex>
        </>
    );
}
