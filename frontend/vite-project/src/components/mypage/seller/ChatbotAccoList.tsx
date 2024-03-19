import {
    Box,
    Accordion,
    AccordionButton,
    AccordionItem,
    TableContainer,
    AccordionPanel,
    AccordionIcon,
    Table,
    Thead,
    Tbody,
    Tr,
    Th,
} from "@chakra-ui/react";
import { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../../redux/stores/store";
import { 
    GetChatbotListAPI, 
    // EditChatbotAPI, 
    DeleteChatbotAPI 
} from "../../../api/chatbot";
import ChatbotItem from "./ChatbotAccoItem";

interface broadcastInfo {
    liveBroadcastId: number;
    broadcastTitle: string;
    nickName: string;
    viewCount: number;
    sellerId: number;
    broadcastStatus: boolean;
}

interface chatbotInfo {
    chatbotId: number;
    roomId: number;
    question: string;
    answer: string;
    registerDate: string;
}

function ChatbotList({ livePlans, chatbotChanged, setChatbotChanged }: 
    { livePlans: broadcastInfo[], chatbotChanged: boolean, setChatbotChanged: (chatbotChanged: boolean) => void }) {
    const [chatbotList, setChatbotList] = useState<chatbotInfo[]>([]);
    const user = useSelector((state: RootState) => state.user);

    useEffect(() => {
        const fetchData = async () => {
            const response = await GetChatbotListAPI({page:0, size:100}, user.accessToken);
            setChatbotList(response.list)
            setChatbotChanged(false);
        };
        fetchData();
    }, [user.accessToken, chatbotChanged])

    // const editChatbot = async (chatbotId: number) => {
    //     try {
    //         await EditChatbotAPI({ chatbotId, roomId: 0, question: "", answer: "" }, user.accessToken);
    //         setChatbotList(prevChatbotList => prevChatbotList.filter(chatbot => chatbot.chatbotId !== chatbotId));
    //     } catch (error) {
    //         console.error("Error editing chatbot:", error);
    //     }
    // };

    const deleteChatbot = async (chatbotId: number) => {
        try {
            await DeleteChatbotAPI(chatbotId, user.accessToken);
            setChatbotList(prevChatbotList => prevChatbotList.filter(chatbot => chatbot.chatbotId !== chatbotId));
            setChatbotChanged(true);
        } catch (error) {
            console.error("Error deleting chatbot:", error);
        }
    };

    return (
        <>
<Accordion allowMultiple>
    {livePlans.map((item, index) => {
        // 각 방송마다 해당하는 roomId에 대한 chatbotList를 필터링합니다.
        const filteredChatbotList = chatbotList.filter(chatbot => chatbot.roomId === item.liveBroadcastId);

        return (
            <AccordionItem key={index}>
                {/* 아코디언 초기 상태 */}
                <AccordionButton justifyContent="space-between">
                    <Box
                        color='gray.500'
                        fontWeight='semibold'
                        letterSpacing='wide'
                        fontSize='xl'
                        textTransform='uppercase'
                        ml='2'
                    >
                        예약방송제목: {item.broadcastTitle}
                    </Box>
                    <AccordionIcon />
                </AccordionButton>

                {/* 아코디언 펼쳐졌을 때 내용 */}
                <AccordionPanel>
                    <TableContainer style={{ overflowX: "hidden" }}>
                        <Table variant='simple' style={{ tableLayout: "fixed", width: "100%" }}>
                            <Thead>
                                <Tr>
                                    <Th width="10%">키워드</Th>
                                    <Th width="70%">자동답변</Th>
                                    <Th width="20%">관리</Th>
                                </Tr>
                            </Thead>

                            <Tbody>
                            <ChatbotItem
                                    chatbotList={filteredChatbotList}
                                    // editChatbot={editChatbot}
                                    deleteChatbot={deleteChatbot}
                                />
                            </Tbody>
                        </Table>
                    </TableContainer>
                </AccordionPanel>

            </AccordionItem>
        )
    })}
</Accordion>
            
        </>
    );
}

export default ChatbotList;
