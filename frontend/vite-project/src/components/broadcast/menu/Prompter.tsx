import { Text } from "@chakra-ui/layout";
import { Center, Flex } from "@chakra-ui/react";

export default function Prompter() {
    return (
        <>
            <Center mb={"1.5rem"}>
                <Text fontSize={"4xl"} as={"b"}>
                    스크립트
                </Text>
            </Center>
            <Flex direction={"column"}>
                <Text fontSize={"2xl"} mb={"1rem"}>고구마 제품 이상 없다는 것 증명</Text>
                <Text fontSize={"2xl"} mb={"1rem"}>고구마 산지 직송 강조</Text>
                <Text fontSize={"2xl"} mb={"1rem"}>고구마 가격 강조</Text>
            </Flex>
        </>
    );
}
