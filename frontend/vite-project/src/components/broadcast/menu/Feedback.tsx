import { Text } from "@chakra-ui/layout";
import { Box, Button, Center, Flex } from "@chakra-ui/react";
import { useParams } from "react-router-dom";
import { fetchKeyword } from "../../../api/liverealtime";
import { useEffect, useState } from "react";

export default function Feedback() {
    const params = useParams();
    const [keyword, setKeyword] = useState<Array<string>>([]);
    const [isButton, setIsButton] = useState(0);

    useEffect(() => {
       
        fetchKeyword(Number(params.roomId)).then((res) => {
            setKeyword(res.data.data.slice(0,5));
            
        });
    }, [isButton]);

    const buttonClick = () => {
        setIsButton(isButton + 1);
    };

    return (
        <>
            <Center mb={"1.5rem"}>
                <Text fontSize={"4xl"} as={"b"}>
                    실시간 피드백
                </Text>
            </Center>

            <Center mb={"2rem"}>
                <Button onClick={buttonClick}>실시간 키워드 분석</Button>
            </Center>

            <Center>
                <Flex direction={"column"} textAlign={"center"}>
                    <Text as={"b"} fontSize={"3xl"} color={"red"}>
                        {"< 실시간 키워드 >"}
                    </Text>
                    <Box mt={"1rem"}></Box>
                    {keyword.map((res, index) => (
                        <Box key={index} mb={"1rem"}>
                            <Text as={"b"} fontSize={"5xl"}>
                                {res}
                            </Text>
                        </Box>
                    ))}
                </Flex>
            </Center>
        </>
    );
}
