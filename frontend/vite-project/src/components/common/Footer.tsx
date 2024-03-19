import { Box, Flex, VStack } from "@chakra-ui/react";

export default function FooterComponent() {
    return (
        <footer>
            <Box bg={"#FFFAF4"} padding={"7"}>
                <Flex direction={"row"} justify={"center"}>
                    <Flex
                        direction={"row"}
                        justify={"space-between"}
                        width={"67%"}
                    >
                        <VStack align="stretch">
                            <Box h="60px" fontSize={"2xl"}>
                                멋쟁이 토마토
                            </Box>
                            <Box h="20px" fontSize={"sm"}>
                                SSAFY 공통 프로젝트 낭비된 잠재력 | 대표 : A501
                            </Box>
                            <Box h="20px" fontSize={"sm"}>
                                본 사이트의 콘텐츠는 저작권법의 보호를 받는 바
                                무단 전재, 복사, 배포 등을 금합니다.
                            </Box>
                            <Box h="20x" fontSize={"sm"}>
                                Copyright @ SAMSUNG All Rights Reserved.
                            </Box>
                        </VStack>
                        <VStack align="stretch" fontSize={"sm"}>
                            <Box h="10px">
                                멋쟁이 토마토 소개 | 고객센터 | 이용약관 |
                                투자정보 | 이용안내
                            </Box>
                        </VStack>
                    </Flex>
                </Flex>
            </Box>
        </footer>
    );
}
