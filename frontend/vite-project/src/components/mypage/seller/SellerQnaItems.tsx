import { Box, Flex } from "@chakra-ui/layout";
import { Image, Badge, Button, Accordion, Input, InputGroup, InputRightElement, AccordionItem, AccordionButton, AccordionPanel, AccordionIcon } from "@chakra-ui/react";
import { useState } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../../redux/stores/store";
import { sellerPutQnaAPI } from "../../../api/itemQnA";

interface sellerQnaType {
    productQuestionBoardId: number;
    writerId: number;
    writerNickname: string;
    productId: number;
    imgSrc: string;
    productName: string;
    productContent: string;
    questionContent: string;
    answerContent: string | null;
    questionRegisterDate: string;
    answerRegisterDate: string | null;
    answer: number;
}

interface QnaItemsProps {
    sellerQnaList: sellerQnaType[];
    onAnswer: (id: number, answerContent: string) => void;
}

function QnaItems({ sellerQnaList, onAnswer }: QnaItemsProps) {
    const [inputValues, setInputValues] = useState<{ [key: number]: string }>({});
    const accessToken = useSelector((state: RootState) => {return state.user.accessToken})

    const handleInputChange = (id: number) => (event: React.ChangeEvent<HTMLInputElement>) => {
        setInputValues({ ...inputValues, [id]: event.target.value });
    }

    const handleSendresponse = (id: number) => () => {
        if(inputValues[id]) {
            sellerPutQnaAPI({ productQuestionBoardId: id, answerContent: inputValues[id] }, accessToken).then(() => {
                onAnswer(id, inputValues[id]);
            }).catch(error => {
                console.error(error);
            });
        } else {
            alert("답변을 입력해주세요")
        }
    }

    return (
        <Accordion allowMultiple>
            {sellerQnaList.map((qnaInfo, index) => (
                <AccordionItem key={index}>
                    <h2>
                        <AccordionButton>
                            <Image mr="2" boxSize="100px" src={qnaInfo.imgSrc} alt="Product Image" />
                            <Flex alignItems='baseline'>
                                {qnaInfo.answer ? (
                                    <Badge colorScheme='yellow'>답변완료</Badge>
                                ) : (
                                    <Badge colorScheme='red'>새문의</Badge>)}
                                <Box
                                    color='gray.500'
                                    fontWeight='semibold'
                                    letterSpacing='wide'
                                    fontSize='m'
                                    textTransform='uppercase'
                                    ml='2'
                                >
                                    {qnaInfo.productName}
                                </Box>
                            </Flex>
                            <AccordionIcon />
                        </AccordionButton>
                    </h2>
                    <AccordionPanel pb={4}>
                        {qnaInfo.questionContent}
                        {qnaInfo.answer === 1 ? (
                            <Box mt="3">
                                {qnaInfo.answerContent}
                            </Box>
                        ) : (
                            <InputGroup>
                                <Input 
                                type="text" 
                                placeholder="문의답변" 
                                value={inputValues[qnaInfo.productQuestionBoardId] || ''}
                                onChange={handleInputChange(qnaInfo.productQuestionBoardId)}
                            />
                                <InputRightElement pr="2" w="5rem">
                                    <Button
                                        h="1.75rem"
                                        size="m"
                                        colorScheme="themeGreen"
                                        variant="ghost"
                                        onClick={handleSendresponse(qnaInfo.productQuestionBoardId)}
                                        borderRadius="md"
                                        _hover={{
                                            bg: "themeGreen.500",
                                            color: "white",
                                        }}
                                    >
                                        답변하기
                                    </Button>
                                </InputRightElement>
                            </InputGroup>
                        )}
                    </AccordionPanel>
                </AccordionItem>
            ))}
        </Accordion>
    )
}

export default QnaItems;
