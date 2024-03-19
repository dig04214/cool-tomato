import { Text } from "@chakra-ui/layout";
import {
    Box,
    Button,
    Center,
    Container,
    Flex,
    FormControl,
    FormLabel,
    Input,
    Switch,
    Textarea,
} from "@chakra-ui/react";
import { useState } from "react";

export default function LiveInfo() {
    const [Title, setTitle] = useState()
    const [Script, setScript] = useState()

    return (
        <Box>
            <Center mb={"2rem"}>
                <Text fontSize={"4xl"} as={"b"}>
                    방송 정보 수정
                </Text>
            </Center>

            <Container maxW={"container.xl"}>
                <Center display={"block"}>
                    <Box mb={"5rem"}>
                        <Text fontSize={"xl"} as={"b"}>
                            라이브 제목 수정
                        </Text>
                        <FormControl
                            mt={"0.5rem"}
                            variant="floating"
                            id="first-name"
                            isRequired
                        >
                            <Input placeholder=" " />
                            <FormLabel>제목을 수정해주세요</FormLabel>
                        </FormControl>
                    </Box>

                    <Box mb={"5rem"}>
                        <Text fontSize={"xl"} as={"b"} mb={"0.5rem"}>
                            라이브 할인이 끝나는 시간을 수정해주세요
                        </Text>
                        <Input
                            mt={"1rem"}
                            placeholder="Select Date and Time"
                            size="md"
                            type="datetime-local"
                        />
                    </Box>

                    <Flex direction={"column"} mb={"5rem"}>
                        <Flex justifyContent={"space-between"} mb={"2rem"}>
                            <Text fontSize={"xl"} as={"b"}>
                                자주 묻는 질문 설정 (챗봇)
                            </Text>
                            <Switch ml={"2rem"} size={"lg"} />
                        </Flex>
                        <Flex justifyContent={"space-between"}>
                            <Text fontSize={"xl"} as={"b"}>
                                채팅을 자동으로 읽어주기 설정
                            </Text>
                            <Switch ml={"2rem"} size={"lg"} />
                        </Flex>
                    </Flex>

                    <Box>
                        <Text as={"b"} fontSize={"2xl"}>
                            중요한 메모 및 대본
                        </Text>
                        <Textarea
                            h={"10rem"}
                            placeholder="스크립트를 작성해주세요"
                            mt={"1rem"}
                        ></Textarea>
                    </Box>

                    <Center mt={"3rem"}>
                        <Button bgColor={"themeGreen.500"} color="white" mr={3}>
                            등록
                        </Button>
                        <Button bgColor={"themeRed.500"} color="white">
                            취소
                        </Button>
                    </Center>
                </Center>
            </Container>
        </Box>
    );
}
