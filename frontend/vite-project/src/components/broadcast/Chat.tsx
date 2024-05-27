import { Box, Text, Flex } from "@chakra-ui/layout";
import ChatList from "./ChatList";
import { chatMessageRecv, chatbotMessage } from "../../types/DataTypes";
import { useEffect, useRef, useState } from "react";
import { getStompClient } from "../../api/chatting";
import { useSelector } from "react-redux";
import { RootState } from "../../redux/stores/store";
import { useParams } from "react-router-dom";
import { getChatbotStomp } from "../../api/chatbot";

function Chat() {
    const [recv, setRecv] = useState<Array<chatMessageRecv>>([]);
    const accessToken = useSelector(
        (state: RootState) => state.user.accessToken
    );
    const stomp = useRef(getStompClient(accessToken));
    const chatbot = useRef(getChatbotStomp());
    const roomId = useParams().roomId!;
    const id = parseInt(roomId);

    useEffect(() => {
        console.log("Chat useEffect 1");
        stomp.current.onConnect = () => {
            console.log("stomp connected");
            stomp.current.subscribe(
                `/sub/room/` + id,
                (msg) => {
                    console.log("stomp subscribe");
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

    return (
        <Box w={"33%"} borderLeft="1px" overflow="auto" p={6}>
            <Text fontSize="2xl" fontWeight="bold" mb={4}>
                채팅
            </Text>
            <Flex
                bg="gray.200"
                rounded="md"
                h="82vh"
                flexDirection="column"
                justifyContent="end"
                overflow={"hidden"}
            >
                <ChatList recv={recv} setRecv={setRecv} />
            </Flex>
        </Box>
    );
}

export default Chat;
