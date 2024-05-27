import MainCarouselComponent from "../components/common/MainCarouselComponent";
import {
    SimpleGrid,
    CardHeader,
    Image,
    Box,
    Flex,
    Text,
    Card,
    Button,
    CardBody,
    CardFooter,
    Heading,
    Stack,
} from "@chakra-ui/react";
import { useNavigate } from "react-router-dom";
import CloudLeft from "/img/shape_02.png";
import RainLeft from "/img/shape_02_l.png";
import CloudLeftBottom from "/img/shape_03.png";
import CloudRightBottom from "/img/shape_04.png";
import CloudBottom1 from "/img/shape_05.png";
import Veges from "/img/VEGES.png";
import farmer from "/img/farmer.jpg";
import leaf from "/img/salad.jpg";
import "../css/MainPage.css";

export default function MainPage() {
    const navigate = useNavigate();
    return (
        <Box overflow="hidden">
            <Flex w={"100vw"} justifyContent={"center"}>
                <Box position="relative">
                    <MainCarouselComponent />
                    <Image
                        marginTop={8}
                        className="mainLeftCloud"
                        position="absolute"
                        top={-8}
                        left={-10}
                        w={"10vw"}
                        src={CloudLeft}
                        alt=""
                    />
                    <Image
                        className="mainLeft"
                        position="absolute"
                        top={4}
                        left={4}
                        w={"7vw"}
                        src={RainLeft}
                        alt=""
                    />
                    <Image
                        className="mainLeftBottom"
                        position="absolute"
                        bottom={-20}
                        left={-20}
                        w={"18vw"}
                        src={CloudLeftBottom}
                        zIndex={-1}
                        alt=""
                    />
                    <Image
                        className="mainRightBottom"
                        position="absolute"
                        bottom={-20}
                        right={-20}
                        w={"18vw"}
                        src={CloudRightBottom}
                        alt=""
                    />
                </Box>
            </Flex>

            <Flex className="scroll-downs" direction={"column"} gap={2}>
                <Text fontSize={"1.2rem"}>scroll</Text>
                <div className="mousey">
                    <div className="scroller"></div>
                </div>
            </Flex>

            <Box height={"10rem"}></Box>
            <Flex alignItems={"center"} direction={"column"} gap={10}>
                <Flex direction={"column"} gap={2}>
                    <Text fontSize={"1rem"} textAlign={"center"}>
                        멋쟁이 토마토가 소개하는
                    </Text>
                    <Text fontSize={"3rem"} fontWeight={"bold"}>
                        이달의 멋쟁이
                    </Text>
                </Flex>

                <Flex
                    wrap={"wrap"}
                    justifyContent={"center"}
                    gap={10}
                    maxW={"90%"}
                >
                    <Card
                        direction={{ base: "column", lg: "row" }}
                        overflow="hidden"
                        variant="outline"
                        maxW={"55%"}
                        borderRadius={"1rem"}
                    >
                        <Image
                            objectFit="cover"
                            maxW={{ base: "100%", sm: "200px" }}
                            src={farmer}
                            alt="Caffe Latte"
                        />

                        <Stack>
                            <CardBody>
                                <Text fontSize={"lg"} as={"b"}>
                                    이달의 멋쟁이 농부
                                </Text>
                                <Heading size="lg" mt={"0.7rem"} mb={"1rem"}>
                                    이병창
                                </Heading>

                                <Text mb={"1"}>
                                    동그란 모양이 아닌 감자를 주로 판매합니다
                                </Text>
                                <Text>
                                    강원도에서 재배하는 감자, 고구마, 옥수수
                                    등의 작물을 소개합니다
                                </Text>
                            </CardBody>

                            <CardFooter>
                                <Button
                                    variant="solid"
                                    colorScheme="green"
                                    onClick={() => {
                                        navigate("/v1/seller/profile/10");
                                    }}
                                >
                                    판매자 구경가기
                                </Button>
                            </CardFooter>
                        </Stack>
                    </Card>

                    <Card
                        direction={{ base: "column", lg: "row" }}
                        overflow="hidden"
                        variant="outline"
                        maxW={"55%"}
                        borderRadius={"1rem"}
                    >
                        <Image
                            objectFit="cover"
                            maxW={{ base: "100%", sm: "200px" }}
                            src={leaf}
                            alt="Caffe Latte"
                        />
                        <Stack>
                            <CardBody>
                                <Text fontSize={"lg"} as={"b"}>
                                    이달의 멋쟁이 채소
                                </Text>
                                <Heading size="lg" mt={"0.7rem"} mb={"1rem"}>
                                    청소년잎채소
                                </Heading>
                                <Text mb={"1"}>
                                    4cm가 넘은 어린잎채소는 판매할 수 없는
                                    상품이 됩니다.
                                </Text>
                                <Text mb={"1"}>
                                    하지만 조금 더 성장한 어린잎 채소는
                                </Text>
                                <Text>
                                    향이 더욱 진하고 맛의 풍미를 더욱
                                    강조해줍니다.
                                </Text>
                            </CardBody>
                            <CardFooter>
                                <Button
                                    variant="solid"
                                    colorScheme="green"
                                    onClick={() => {
                                        navigate("/v1/items/detail/24");
                                    }}
                                >
                                    상품 구경가기
                                </Button>
                            </CardFooter>
                        </Stack>
                    </Card>
                </Flex>
            </Flex>

            <Box height={"20rem"}></Box>

            <Flex h={"100vh"} direction={"column"}>
                <Box
                    w={"100%"}
                    background={
                        "url(https://cdn.imweb.me/upload/S201804055ac60211e0c1d/d049e61e55652.jpg) center/cover no-repeat"
                    }
                    h={"30%"}
                />
                <Flex
                    className="aboutus"
                    h={"70%"}
                    justifyContent={"center"}
                    alignItems={"center"}
                >
                    <Flex
                        direction={{ base: "column", lg: "row" }}
                        maxW={"120rem"}
                        h={"80%"}
                        gap={10}
                        color={"white"}
                    >
                        <Flex maxW={"100%"} direction={"column"} gap={5}>
                            <Text as={"b"} mb={5} fontSize={"3rem"}>
                                멋쟁이토마토!
                            </Text>
                            <Text fontSize={"1.5rem"}>
                                '멋쟁이 토마토'는 노래 가사에서 영감을
                                받았습니다
                            </Text>
                            <Text fontSize={"1.5rem"}>
                                채소가 어떤 모습이든 상관없이 그 품질은
                                변함없다는 것을 의미합니다
                            </Text>
                            <Text fontSize={"1.5rem"}>
                                못난이 채소들이라도 외관에 상관없이 모든
                                채소들은 멋쟁이입니다
                            </Text>
                            <Text fontSize={"1.5rem"}>
                                우리 사이트의 취지는 못난이 채소의 아름다움을
                                직접 확인하고
                            </Text>
                            <Text fontSize={"1.5rem"}>
                                믿을 수 있는 가격에 건강한 먹거리를 챙기는
                                것입니다
                            </Text>
                            <Text fontSize={"1.5rem"}>
                                멋쟁이토마토는 당신에게 저렴한 가격에 최상의
                                품질의 채소를 제공합니다
                            </Text>
                            <Text fontSize={"1.5rem"}>
                                함께 건강한 삶을 시작해보세요!
                            </Text>
                        </Flex>
                        <Image src={Veges}></Image>
                    </Flex>
                </Flex>
                <Flex w="100%" h="5%" className="aboutus-end" />
            </Flex>

            <Box height={"10rem"}></Box>

            <Flex mt={"2rem"} mb={"2rem"} w={"100vw"} justifyContent={"center"}>
                <Box position="relative">
                    <SimpleGrid columns={2} gap={2}>
                        <Card mr={"3rem"}>
                            <CardHeader>
                                <Heading size="md">
                                    판매에 관심이 생겼나요?
                                </Heading>
                            </CardHeader>
                            <CardBody>
                                <Text>
                                    회원가입하고 직접 상품을 등록해보세요!
                                </Text>
                            </CardBody>
                            <CardFooter>
                                <Button
                                    onClick={() => {
                                        navigate("/v1/signup");
                                    }}
                                >
                                    회원가입
                                </Button>
                            </CardFooter>
                        </Card>
                        <Card>
                            <CardHeader>
                                <Heading size="md">
                                    상품을 구경하고 싶나요?
                                </Heading>
                            </CardHeader>
                            <CardBody>
                                <Text>
                                    현재 방송중인 멋쟁이 채소들을 확인해보세요!
                                </Text>
                            </CardBody>
                            <CardFooter>
                                <Button
                                    onClick={() => {
                                        navigate("/v1/live/list");
                                    }}
                                >
                                    라이브확인
                                </Button>
                            </CardFooter>
                        </Card>
                    </SimpleGrid>
                    <Image
                        className="mainLeftBottom"
                        position="absolute"
                        bottom={-20}
                        left={-20}
                        w={"18vw"}
                        src={CloudBottom1}
                        opacity={"0.2"}
                        zIndex={-1}
                        alt=""
                    />
                    <Image
                        className="mainRightBottom"
                        position="absolute"
                        bottom={-40}
                        right={-20}
                        w={"18vw"}
                        src={CloudRightBottom}
                        opacity={"0.2"}
                        alt=""
                    />
                </Box>
            </Flex>
            <Box height={"10rem"}></Box>
        </Box>
    );
}
