import { Flex, Text, Box, Container, Center } from "@chakra-ui/react";
import { Avatar, Image, Button, Divider, Highlight } from "@chakra-ui/react";
import { CloseIcon } from "@chakra-ui/icons";
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend,
} from "chart.js";
// import { Line } from "react-chartjs-2";
import { useNavigate, useParams } from "react-router-dom";
// import { useNavigate, useParams, useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { postmortemAPI } from "../api/analyze";
import { getLiveProduct } from "../api/liveProduct";
import { RootState } from "../redux/stores/store";
import { LiveProductAll } from "../types/DataTypes";
import logo from "/img/newlogo.png";

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend
);

interface ProductType {
    imgSrc: string;
    productId: number;
    productName: string;
    price: number;
    liveFlatPrice: number;
    mainProductSetting: boolean;
    livePriceStartDate: string;
}

export default function LiveResultPage() {
    const navigate = useNavigate();
    const user = useSelector((state: RootState) => state.user);
    const accessToken = useSelector(
        (state: RootState) => state.user.accessToken
    );
    const params = new URLSearchParams(window.location.search);
    const { broadcastId } = useParams<{ broadcastId: string }>();
    const broadcastTitle: string | null = params.get("broadcastTitle");
    const viewCount: string | null = params.get("viewCount");
    // const startDate: string | null = params.get("startDate");
    const broadcastIdNumber = Number(broadcastId);
    const viewCountNumber = Number(viewCount);

    // const [liveResult, setLiveResult] = useState([]);
    const [products, setProducts] = useState<ProductType[]>([]);
    const [keywordlist, setKeywordlist] = useState<string[]>([]);

    useEffect(() => {
        const fetchData = async () => {
            const response = await postmortemAPI(
                broadcastIdNumber,
                accessToken
            );
            const data = JSON.parse(response); // JSON 문자열을 JavaScript 객체로 변환
            // setLiveResult(data.connNum);
            setKeywordlist(data.hotKeywords);
        };
        fetchData();
    }, []);

    useEffect(() => {
        const fetchData = async () => {
            const response = await getLiveProduct(
                { "live-id": broadcastIdNumber },
                accessToken
            );
            const selectedProducts = response.list.map(
                (item: LiveProductAll) => ({
                    imgSrc: item.imgSrc,
                    productId: item.productId,
                    productName: item.productName,
                    price: item.price,
                    liveFlatPrice: item.liveFlatPrice,
                    mainProductSetting: item.mainProductSetting,
                    livePriceStartDate: item.livePriceStartDate,
                })
            );
            setProducts(selectedProducts);
        };
        fetchData();
    }, []);

    return (
        <>
            <Container centerContent>
                <Button
                    p={"1.5rem"}
                    size={"lg"}
                    leftIcon={<CloseIcon />}
                    colorScheme="teal"
                    variant="link"
                    onClick={() => {
                        navigate("/v1/seller");
                    }}
                >
                    <Center>
                        <Text as={"b"}>나가기</Text>
                    </Center>
                </Button>
            </Container>
            <Flex
                justifyContent="center"
                direction="column"
                alignItems="center"
                width={"100%"}
            >
                <Box
                    mb="3"
                    width={"12rem"}
                    height={"8rem"}
                    onClick={() => {
                        navigate("./main");
                    }}
                    _hover={{
                        opacity: 1,
                        cursor: "pointer",
                    }}
                >
                    <Image
                        width={"100%"}
                        height={"100%"}
                        objectFit={"cover"}
                        src={logo}
                    />
                </Box>

                <Flex
                    w="80vw"
                    direction="column"
                    align="center"
                    py={8}
                    p="5"
                    border="2px"
                    mb="20"
                    borderColor="themeLightGreen.500"
                >
                    <Avatar mt="4" size="xl" src={user.profileImg} />

                    <Text mt={4} mb={4} fontSize="3xl" fontWeight="bold">
                        "{user.nickname}"님의 "{broadcastTitle}"
                    </Text>

                    <Divider mt={3} mb={7} />

                    {/* <Text
                        mt="2"
                        fontSize="2xl"
                        fontWeight="semibold"
                        mb={2}
                        textAlign="center"
                    >
                        라이브 정보!
                    </Text>
                    <Text mt="2" mb="4" textAlign="center">
                        예정 방송시간: 2024년 2월 12일 오후 8시
                        <br />
                        실제 방송시간: {product.livePriceStartDate}
                        <br />
                        방송종료시간: 2024년 2월 12일 오후 10시 34분
                    </Text> */}

                    {/* <Divider mt={3} mb={7} /> */}

                    <Text
                        mt="2"
                        fontSize="2xl"
                        fontWeight="semibold"
                        mb={2}
                        textAlign="center"
                    >
                        판매한 상품!
                    </Text>

                    {products.slice(0, 5).map((product) => (
                        <Flex key={product.productId} direction="column">
                            <Avatar
                                size="xl"
                                src={product.imgSrc}
                                width="100%"
                            />
                            <Text width="100%" textAlign="center">
                                {product.productName}
                            </Text>
                        </Flex>
                    ))}

                    <Divider mt={3} mb={7} />

                    <Flex justify="space-around" py={8}>
                        <Box textAlign="center" mr="5">
                            <Text fontSize="xl" fontWeight="semibold">
                                전체 조회수
                            </Text>
                            <Text fontSize="5xl" fontWeight="bold">
                                {viewCountNumber}
                            </Text>
                        </Box>
                        {/* <Box textAlign="center" mr="5">
                            <Text fontSize="xl" fontWeight="semibold">
                                전체 좋아요수
                            </Text>
                            <Text fontSize="5xl" fontWeight="bold">
                                1,268
                            </Text>
                        </Box>
                        <Box textAlign="center">
                            <Text fontSize="xl" fontWeight="semibold">
                                남은 재고량 상품
                            </Text>
                            <Text fontSize="5xl" fontWeight="bold">
                                남은량
                            </Text>
                        </Box> */}
                    </Flex>

                    <Divider mt={3} mb={7} />

                    <Box mb="4">
                        <Text
                            fontSize="2xl"
                            fontWeight="semibold"
                            mb={4}
                            textAlign="center"
                        >
                            채팅에서 많이 나온 키워드 5개!
                        </Text>
                        <Flex justify="space-around" mb="4">
                            {keywordlist.map((keyword, index) => (
                                <Text key={index} fontSize="3xl" mr={3}>
                                    <Highlight
                                        query={keyword}
                                        styles={{
                                            px: "2",
                                            py: "1",
                                            rounded: "full",
                                            bg: "red.100",
                                        }}
                                    >
                                        {keyword}
                                    </Highlight>
                                </Text>
                            ))}
                        </Flex>
                    </Box>

                    <Divider mt={3} mb={7} />
                    {/* 추후 수정이 된다면...
                    <Box>
                        <Text
                            fontSize="2xl"
                            fontWeight="semibold"
                            mb={4}
                            textAlign="center"
                        >
                            시간대별 접속자 추이
                        </Text>
                        <LineLine />
                    </Box> */}
                </Flex>
            </Flex>
        </>
    );
}

// export function LineLine() {
//     const labels = [
//         "30분",
//         "1시간",
//         "1시간 30분",
//         "2시간",
//         "2시간 30분",
//         "3시간",
//     ];
//     // const [viewerData, setViewerData] = useState([]);

//     const data = {
//         labels,
//         datasets: [
//             {
//                 label: "30분 간격 시청자수",
//                 // data: viewerData.map((data) => data.e),
//                 data: 0,
//                 borderColor: "rgb(255, 99, 132)",
//                 backgroundColor: "rgba(255, 99, 132, 0.5)",
//             },
//         ],
//     };

//     const options = {
//         responsive: true,
//         plugins: {
//             legend: {
//                 position: "top" as const,
//             },
//         },
//     };

//     return <Line options={options} data={data} height={400} width={600} />;
// }
