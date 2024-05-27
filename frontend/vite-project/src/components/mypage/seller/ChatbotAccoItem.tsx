import {
    Button,
    Tr,
    Td,
} from "@chakra-ui/react";

interface chatbotInfo {
    chatbotId: number;
    roomId: number;
    question: string;
    answer: string;
    registerDate: string;
}

function ChatbotItem({chatbotList, deleteChatbot} : {chatbotList: chatbotInfo[], deleteChatbot: (chatbotId: number) => void;}) {

    if (!chatbotList || chatbotList.length === 0) {
        return (
                <Tr>
                    <Td width="10%" />
                    <Td width="70%">
                        등록된 자동응답이 없습니다!
                    </Td>
                    <Td width="20%" />
                </Tr>
        );
    }

    return (
        <>
            {chatbotList.map((item, index) => (
                <Tr key={index}>
                    <Td style={{ whiteSpace: "pre-wrap" }}>{item.question}</Td>
                    <Td style={{ whiteSpace: "pre-wrap" }}>{item.answer}</Td>
                    <Td width="20%">
                        {/* <Button
                            onClick={onClickEdit}
                            colorScheme="teal"
                            size="sm"
                            mr={2}
                        >
                            수정
                        </Button> */}
                        <Button
                            onClick={()=>{deleteChatbot(item.chatbotId);}}
                            colorScheme="red"
                            size="sm"
                        >
                            삭제
                        </Button>
                    </Td>
                </Tr>
            ))}
        </>
    );
}

export default ChatbotItem;
