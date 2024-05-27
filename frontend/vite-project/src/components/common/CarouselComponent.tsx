import {
    AspectRatio,
    Box,
    Flex,
    Text,
    Image,
    Icon,
    Center,
} from "@chakra-ui/react";
import "../../css/ItemListComponentcss.css";
import { useEffect, useState } from "react";
import { DisplayInterface } from "../../types/DataTypes";
import { fetchCalendarItem } from "../../api/liveProduct";
import { useNavigate } from "react-router-dom";
import { formatNumberWithComma } from "./Comma";
import { TbLivePhoto } from "react-icons/tb";

interface CarouselComponentInterface {
    fetchLiveData: Array<DisplayInterface> | undefined;
}

export default function CarouselComponent(
    fetchLiveData: CarouselComponentInterface
) {
    const navigate = useNavigate();
    const [offset, setOffset] = useState(0);
    const [elapsed, setElapsed] = useState(1);

    const [displayData, setDisplayData] = useState<Array<DisplayInterface>>([]);

    useEffect(() => {
        if (
            fetchLiveData.fetchLiveData &&
            fetchLiveData.fetchLiveData.length > 0
        ) {
            Promise.all(
                fetchLiveData.fetchLiveData.map((item) =>
                    fetchCalendarItem(0, 10, item.liveBroadcastId)
                )
            ).then((res) => {
                if (fetchLiveData.fetchLiveData !== undefined) {
                    const enrichedData = res.flatMap((res, index) => {
                        if (fetchLiveData.fetchLiveData === undefined) {
                            return {
                                price: 0,
                                liveFlatPrice: 0,
                                imgSrc: "",
                                liveBroadcastId: 0,
                                broadcastTitle: "",
                            };
                        }
                        const liveItem = fetchLiveData.fetchLiveData[index];
                        const detail = res.data.data.list[0];

                        return {
                            price: detail.price,
                            liveFlatPrice: detail.liveFlatPrice,
                            imgSrc: detail.imgSrc,
                            liveBroadcastId: liveItem.liveBroadcastId,
                            broadcastTitle: liveItem.broadcastTitle,
                        };
                    });
                    setDisplayData(enrichedData);
                }
            });
        }
    }, [fetchLiveData]);

    useEffect(() => {
        console.log(displayData);
    }, [displayData]);

    useEffect(() => {
        setOffset(10 * elapsed);
    }, [elapsed]);

    setInterval(() => {
        setElapsed(elapsed + 1);
    }, 10000);

    return (
        <>
            <Flex justify={"center"} className="MainText" mb={"2rem"}>
                <Text mr={"2rem"} color={"themeFontGreen.500"}>
                    현재 <span style={{ color: "red" }}>라이브</span> 중인 상품
                </Text>
            </Flex>

            <Box display={"block"} p={"1rem"} overflowX={"hidden"}>
                <Flex
                    overflowX={"hidden"}
                    wrap={"nowrap"}
                    style={{
                        width: `${19 * 22}vw`,
                        transitionDuration: "10s",
                        transform: `translateX(-${offset}%)`,
                        transition: "transform 10s linear",
                    }}
                >
                    {displayData?.map((data, index) => (
                        <Box
                            key={index}
                            p={2}
                            onClick={() => {
                                navigate(`/v1/live/${data.liveBroadcastId}`);
                            }}
                            borderRadius={"20px"}
                        >
                            <AspectRatio
                                w="19rem"
                                ratio={1 / 1}
                                mb={"0.5rem"}
                                position={"relative"}
                                overflow={"hidden"}
                            >
                                <Image
                                    src={data.imgSrc}
                                    aspectRatio="1/1"
                                    objectFit="cover"
                                    overflow={"hidden"}
                                    position={"relative"}
                                    borderRadius={"20px"}
                                    transition="all 0.2s ease-out"
                                    transform="translate(-0.5%, -0.5%)"
                                    style={{
                                        top: "0.5%",
                                        left: "0.5%",
                                        width: "100%",
                                        height: "100%",
                                    }}
                                    _hover={{ transform: "scale(1.1)" }}
                                />
                            </AspectRatio>
                            <Flex mt={"0.3rem"} mb={"0.3rem"}>
                                <Icon
                                    as={TbLivePhoto}
                                    w={"8"}
                                    h={"8"}
                                    color={"red.500"}
                                    mr={"0.5rem"}
                                />
                                <Center>
                                    <Text as={"b"} color={"red"}>
                                        ON LIVE
                                    </Text>
                                </Center>
                            </Flex>
                            <Text
                                color={"themeRed.500"}
                                as={"b"}
                                fontSize={"2xl"}
                            >
                                {formatNumberWithComma(data.liveFlatPrice) +
                                    "원"}
                            </Text>
                            <Text
                                fontSize={"xl"}
                                ml={"1.5rem"}
                                color={"black"}
                                as={"b"}
                                textDecorationLine={"line-through"}
                            >
                                {formatNumberWithComma(data.price) + "원"}
                            </Text>
                        </Box>
                    ))}
                </Flex>
            </Box>
            <Flex justify={"center"} mt={"4rem"}>
                <Box
                    w={"80%"}
                    h={"0.5px"}
                    backgroundColor={"themeGreen.500"}
                ></Box>
            </Flex>
        </>
    );
}

