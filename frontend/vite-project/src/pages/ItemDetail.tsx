import {
    Box,
    Button,
    Flex,
    Text,
    Image,
    Tabs,
    TabList,
    Tab,
    TabPanels,
    TabPanel,
    AspectRatio,
} from "@chakra-ui/react";
import ItemDetailDetail from "../components/item/ItemDetailDetail";
import { useNavigate, useParams } from "react-router-dom";
import ItemDetailReview from "../components/item/ItemDetailReview";
import ItemDetailQnA from "../components/item/ItemDetailQnA";
import { useEffect, useState } from "react";
import { ItemDetailInterface } from "../types/DataTypes";
import { ItemDetailFetch } from "../api/Itemlist";
import { formatNumberWithComma } from "../components/common/Comma";

export default function ItemDetail() {
    const navigate = useNavigate();
    const BuyNow = () => {
        navigate("https://naver.com");
    };

    const [fetchData, setFetchData] = useState<
        ItemDetailInterface | undefined
    >();
    const { id } = useParams() as { id: string };

    useEffect(() => {
        ItemDetailFetch(Number(id)).then((res) => {
            setFetchData(res);
        });
    }, [id]);

    return (
        <>
            <Flex direction={"column"} alignItems={"center"} justify={"center"}>
                <Flex>
                    <Box maxW={"4xl"} mx={"auto"} p={6}>
                        <Flex grow={1}>
                            <Box maxW={"26rem"}>
                                <AspectRatio w="450px" ratio={1 / 1}>
                                    <Image
                                        src={fetchData?.imgSrc}
                                        aspectRatio="1/1"
                                        objectFit="cover"
                                        overflow={"hidden"}
                                        position={"relative"}
                                        borderRadius={"20px"}
                                    />
                                </AspectRatio>
                            </Box>

                            <Flex display={"block"} pl={"2.5rem"} ml={"5rem"}>
                                <Box mb={"1rem"}>
                                    <Text fontSize={"3xl"} as={"b"}>
                                        {fetchData?.productName}
                                    </Text>
                                </Box>
                                <Text fontSize={"lg"} mb={"0.2rem"}>
                                    <Text color={"black"} as={"b"}>
                                        {fetchData
                                            ? formatNumberWithComma(
                                                  fetchData?.price
                                              ) + "원"
                                            : ""}
                                    </Text>
                                </Text>
                                <Box mb={"1.5rem"}>
                                    <Text as={"b"} color={"themeGreen.500"}>
                                        라이브 시작시 특별가가 적용됩니다
                                    </Text>
                                </Box>
                                <Box mb={"1.5rem"}>
                                    <Text fontSize={"lg"} as={"b"} mb={2}>
                                        배송
                                    </Text>
                                    <Text>수도권 및 일부지역 당일배송</Text>
                                </Box>
                                <Box mb={"1.5rem"}>
                                    <Text fontSize={"lg"} as={"b"} mb={2}>
                                        포장타입
                                    </Text>
                                    <Text>선물용 / 가정용</Text>
                                </Box>
                                <Box mb={"1.5rem"}>
                                    <Text fontSize={"lg"} as={"b"} mb={2}>
                                        유통기한
                                    </Text>
                                    <Text>상품 별도 표기</Text>
                                </Box>
                                <Box mb={"1.5rem"}>
                                    <Text fontSize={"lg"} as={"b"} mb={"1rem"}>
                                        상품 문의
                                    </Text>
                                    <Text></Text>
                                </Box>
                                <Box mb={"6rem"} alignItems={"center"}>
                                    <Button
                                        borderRadius={"md"}
                                        bg={"themeGreen.500"}
                                        onClick={BuyNow}
                                    >
                                        <Text color={"white"}>바로 구매</Text>
                                    </Button>
                                </Box>
                            </Flex>
                        </Flex>
                    </Box>
                </Flex>
            </Flex>
            <Flex justifyContent={"center"}>
                <Tabs isFitted variant="enclosed" w={"4xl"}>
                    <TabList mb="1em">
                        <Tab
                            _selected={{ color: "white", bg: "themeGreen.500" }}
                        >
                            <Text as={"b"}>상품상세정보</Text>
                        </Tab>
                        <Tab
                            _selected={{ color: "white", bg: "themeGreen.500" }}
                        >
                            <Text as={"b"}>상품 리뷰</Text>
                        </Tab>
                        <Tab
                            _selected={{ color: "white", bg: "themeGreen.500" }}
                        >
                            <Text as={"b"}>상품 문의</Text>
                        </Tab>
                    </TabList>
                    <TabPanels mt={"5rem"} mb={"5rem"}>
                        <TabPanel>
                            <Flex
                                direction={"column"}
                                alignItems={"center"}
                                justify={"center"}
                            >
                                <ItemDetailDetail
                                    content={fetchData?.productContent}
                                />
                            </Flex>
                        </TabPanel>
                        <TabPanel>
                            <Flex
                                direction={"column"}
                                alignItems={"center"}
                                justify={"center"}
                            >
                                <ItemDetailReview />
                            </Flex>
                        </TabPanel>
                        <TabPanel>
                            <Flex
                                direction={"column"}
                                alignItems={"center"}
                                justify={"center"}
                            >
                                <ItemDetailQnA />
                            </Flex>
                        </TabPanel>
                    </TabPanels>
                </Tabs>
            </Flex>
        </>
    );
}

