import { Box, Flex } from "@chakra-ui/layout";
import {
    Button
  } from '@chakra-ui/react'
import { FaEdit } from "react-icons/fa";
import { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { getLivePlanAPI } from "../../../api/openVidu";
import { RootState } from "../../../redux/stores/store";
import ChatbotRegistrationModal from "./ChatbotRegister";
import ChatbotList from "./ChatbotAccoList"

interface broadcastInfo {
    liveBroadcastId: number;
    broadcastTitle: string;
    nickName: string;
    viewCount: number;
    sellerId: number;
    broadcastStatus: boolean;
}

function Chatbot() {
    const user = useSelector((state: RootState) => state.user);
    const [modalOpen, setModalOpen] = useState(false);
    const [chatbotChanged, setChatbotChanged] = useState(false);
    const [ livePlans, setLivePlans ] = useState<Array<broadcastInfo>>([]);

    useEffect(() => {
        const fetchData = async () => {
            const response = await getLivePlanAPI({page:0, size:10}, user.accessToken);
            setLivePlans(response.broadcastInfoList);
        };
        fetchData();
    }, [user.accessToken])


    const handleModalOpen = () => {
        setModalOpen(prevState => !prevState);
        setChatbotChanged(true); // Set chatbotChanged to true when a chatbot is registered
    }
    

    return (
        <Box flexDirection="column" w="90%" h="full">

            {/* 챗봇 등록 모달 오픈 버튼 */}
            <Flex justifyContent={"flex-end"} mb="5">
                <ChatbotRegistrationModal
                    isOpen={modalOpen}
                    handleModalOpen={handleModalOpen}
                    livePlans={livePlans}
                />
                <Button
                    leftIcon={<FaEdit />}
                    colorScheme="themeGreen"
                    size={"sm"}
                    onClick={handleModalOpen}
                >
                    자동응답 추가
                </Button>
            </Flex>

            {/* 방송 별 챗봇 리스트 */}
            <ChatbotList livePlans={livePlans} chatbotChanged={chatbotChanged} setChatbotChanged={setChatbotChanged} />
        </Box>
    )
}

export default Chatbot