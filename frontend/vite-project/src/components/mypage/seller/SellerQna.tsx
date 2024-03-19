import { Box, Text, Flex } from "@chakra-ui/layout";
import { Button } from "@chakra-ui/react";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { RootState } from "../../../redux/stores/store";
import { sellerGetQnaAPI, sellerPutQnaAPI } from "../../../api/itemQnA";
import QnaItems from "./SellerQnaItems";

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

function Qna() {
    const navigate = useNavigate();
    const user = useSelector((state: RootState) => state.user);

    const [sellerQnaList, setSellerQnaList] = useState<sellerQnaType[]>([]);

    function onclick() {
        navigate(`/v1/ItemAdd`);
    }

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await sellerGetQnaAPI(0, 16, user.accessToken)
                // console.log(response)
                setSellerQnaList(response.data.list)
            } catch (error) {
                console.error(error)
            }
        };
        fetchData();
    }, [])

    const handleAnswer = (id: number, answerContent: string) => {
        sellerPutQnaAPI({ productQuestionBoardId: id, answerContent }, user.accessToken).then(() => {
            setSellerQnaList(prevList => prevList.map(item => 
                item.productQuestionBoardId === id 
                    ? {...item, answer: 1, answerContent} 
                    : item
            ));
        }).catch(error => {
            console.error(error);
        });
    }
    

    return (
        <Box flexDirection="column" w="90%" h="full">
            { sellerQnaList.length ? (
                <QnaItems sellerQnaList={sellerQnaList} onAnswer={handleAnswer} />
             ) : (
                <Flex m="auto" flexDir="column" mb="10">
                    <Text fontSize='5xl' color="gray.500" mb="5">받은 문의가 없습니다!</Text>
                    <Button colorScheme="themeGreen" onClick={onclick}>상품 등록하러 가기</Button>
                </Flex>
            )}
        </Box>
    )
}

export default Qna