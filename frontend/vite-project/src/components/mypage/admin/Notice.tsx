import { Box, Flex } from "@chakra-ui/layout";
import {
    Button,
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalCloseButton,
    ModalBody,
    ModalFooter,
    FormControl,
    FormLabel,
    Input,
    Text,
    Textarea,
    Accordion,
    AccordionButton,
    AccordionItem,
    AccordionPanel,
    AccordionIcon,
  } from '@chakra-ui/react'
import { FaEdit } from "react-icons/fa";
import { useState } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../../redux/stores/store";

// 임시로 지정
interface ChatbotData {
    id: number;
    title: string;
    content: string;
}

function Notice() {
    const user = useSelector((state: RootState) => state.user);
    const [modalOpen, setModalOpen] = useState(false);
    const dummydata: ChatbotData[] = [
        {
            id: 1,
            title: "[사전안내] 라이브 서비스 점검 안내 (2/19,20)",
            content: `안녕하세요, 멋쟁이토마토 서비스팀입니다.
                10월 11일 수요일과 13일 금요일, 쇼핑라이브 서비스 점검이 예정되어 있어 사전 안내 말씀드립니다.
                        
                ■점검 기간
                2월 19일 (월) 24시 ~ 20일(화) 02시
                        
                서비스 점검 중에는 쇼핑라이브 시청, 송출 등 일부 기능에 간헐적인 오류가 발생할 수 있습니다. 
                서비스 이용에 미리 참고 부탁드립니다. 
                        
                더욱 안정적인 서비스 제공으로 보답하는 네이버 쇼핑라이브가 되겠습니다. 
                멋쟁이토마토 서비스팀 드림`
        },
        {
            id: 2,
            title: "[안내] 라이브/숏클립 뷰어 PC환경 개선 안내",
            content: `안녕하세요, 네이버 쇼핑라이브입니다.
                모바일 라이브/숏클립 시청화면에서 제공하는 기능은 동일하게 제공하되,
                PC 화면 크기에 맞게 기능들이 배치되었으며 몇가지 기능은 추가 개선되었습니다.
                
                ## 주요 개선
                - 상품목록 / 쿠폰 / 라이브소개 / 혜택 / 공지
                : 기존 모바일에서는 라이브 시청화면을 가리는 형태로 제공되던 정보들이 PC에서는 우측으로 이동되어, 라이브 시청과 동시에 필요한 정보를 확인할 수 있습니다. 
                
                - 실시간 채팅
                : 기존 모바일에서는 시청화면 아래에 채팅이 겹쳐보이지만, PC에서는 우측에 별도 채팅 영역을 두어 시청을 하며 자유로운 채팅을 하실 수 있습니다. 
                
                - 관련 콘텐츠
                : PC 우측 영역 하단에 지금 시청하고 있는 라이브/숏클립과 연관된 추천 콘텐츠를 함께 볼 수 있도록 제공합니다. 
                
                - 좋아요 / 쿠폰 / 공유 등 기능 버튼
                : 시청화면 우측 별도 영역에 기능 버튼을 두어 라이브 시청에 방해되지 않도록 구성하였습니다. 
                
                더욱 편안한 서비스를 제공할 수 있도록 노력하겠습니다. 
                멋쟁이토마토 서비스팀 드림`
        }
    ];
    
    function handleModalOpen() {
        setModalOpen(!modalOpen);
    }

    return (
        <Box flexDirection="column" w="90%" h="full">
            
            {user.auth === 'admin' && (
                <Flex justifyContent={"flex-end"} mb="5">
                    <NoticeRegister
                        isOpen={modalOpen}
                        handleModalOpen={handleModalOpen}
                    />
                    <Button
                        leftIcon={<FaEdit />}
                        colorScheme="themeGreen"
                        size={"sm"}
                        onClick={handleModalOpen}
                    >
                        새글쓰기
                    </Button>
                </Flex>
            )}

            <NoticeList dummydata={dummydata} />
        </Box>
    )
}

export default Notice;

function NoticeRegister({
    isOpen,
    handleModalOpen
}: {
    isOpen: boolean;
    handleModalOpen: () => void;
}) {
    const [chatbotKeyword, setChatbotKeyword] = useState("");
    const [chatbotContent, setChatbotContent] = useState("");
    // const accessToken = useSelector((state: RootState) => state.user.accessToken);

    const handleInputKeyword = (e: React.ChangeEvent<HTMLInputElement>) =>
        setChatbotKeyword(e.target.value);

    const handleInputChange = (e: React.ChangeEvent<HTMLTextAreaElement>) =>
        setChatbotContent(e.target.value);

    const handleClose = () => {
        setChatbotContent("");
        handleModalOpen();
    };

    return (
        <>
            <Modal
                isOpen={isOpen}
                onClose={handleClose}
                motionPreset="slideInBottom"
                isCentered
                size="2xl"
            >
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>공지등록하기</ModalHeader>
                    <ModalCloseButton onClick={handleModalOpen} />
                    <ModalBody>
                        <FormControl my={2}  isRequired>
                            <FormLabel mt="5">
                                <Text as={"b"}>제목</Text>
                            </FormLabel>
                            <Input
                                focusBorderColor="themeGreen.500"
                                placeholder="Keyword"
                                size="md"
                                mb="5"
                                value={chatbotKeyword}
                                onChange={handleInputKeyword}
                            />

                            <FormLabel>
                                <Text as={"b"}>내용</Text>
                            </FormLabel>
                            <Textarea
                                value={chatbotContent}
                                onChange={handleInputChange}
                                placeholder="공지사항"
                                focusBorderColor="themeGreen.500"
                                size="md"
                                resize="none"
                                mb="1.5rem"
                                h={"30rem"}
                                />
                        </FormControl>
                    </ModalBody>

                    <ModalFooter>
                        <Button
                            colorScheme="blue"
                            variant={"outline"}
                            mr={3}
                            onClick={handleClose}
                        >
                            취소
                        </Button>
                        <Button
                            colorScheme="themeGreen"
                        >
                            등록
                        </Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    );
}

function NoticeList({ dummydata }: { dummydata: ChatbotData[]}) {
    return (
        <>
        <Accordion allowMultiple>
            {dummydata.map((item, index) => (
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
                            {item.title}
                        </Box>
                        <AccordionIcon />
                    </AccordionButton>

                    <AccordionPanel>
                        {item.content}
                    </AccordionPanel>

                </AccordionItem>
            ))}
        </Accordion>
            
        </>
    );
}