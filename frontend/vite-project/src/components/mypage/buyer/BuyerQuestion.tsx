import { Box, Text, Flex } from "@chakra-ui/layout";
import { Button } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { RootState } from "../../../redux/stores/store";
import { buyerGetQnaAPI } from "../../../api/itemQnA";
import BuyerQnaItems from "./BuyerQuestionItems";

export default function Question() {
    const navigate = useNavigate();
    const user = useSelector((state: RootState) => state.user);
    const accessToken = user.accessToken;
    const [questions, setQuestions] = useState([]);

    function onclick() {
        navigate(`/v1/items/list/0`);
    }

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await buyerGetQnaAPI(0, 32, accessToken);
                setQuestions(response.data.list);
            } catch (error) {
                console.error(error);
            }
        };
        fetchData();
    }, [accessToken]);

    return (
        <Box flexDirection="column" w="90%" h="full" mb="10">
            <Flex flexDir="column" h="full" m="auto">
                {questions.length ? (
                    questions.map((item, index) => (
                        <BuyerQnaItems key={index} questions={item} />
                    ))
                ) : (
                    <Flex m="auto" flexDir="column">
                        <Text fontSize="5xl" color="gray.500" mb="5">
                            문의한 내역이 없습니다!
                        </Text>
                        <Button colorScheme="themeGreen" onClick={onclick}>
                            상품 구경하러 가기
                        </Button>
                    </Flex>
                )}
            </Flex>
        </Box>
    );
}
