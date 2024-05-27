import {
    LiveFetchInterface,
    LiveCalendarShownInterface,
} from "../../types/DataTypes";
import { useEffect, useState } from "react";
import { fetchCalendarItem } from "../../api/liveProduct";
import { Flex, Text, Image, AspectRatio, Center, Box } from "@chakra-ui/react";
import utc from "dayjs/plugin/utc";
import timezone from "dayjs/plugin/timezone";
import dayjs from "dayjs";
import { fetchLiveCalendar } from "../../api/live";
import { formatNumberWithComma } from "../common/Comma";

dayjs.extend(utc);
dayjs.extend(timezone);

interface LiveCalendarInterface {
    date: number;
}

export default function LiveCalendar({ date }: LiveCalendarInterface) {
    const today = dayjs();
    const dates = today.add(date, "day").format("YY-MM-DD");

    const [enrichedLiveCalendar, setEnrichedLiveCalendar] = useState<
        Array<LiveCalendarShownInterface> | undefined
    >();

    const [LiveCalendar, setLiveCalendar] = useState<
        Array<LiveFetchInterface> | undefined
    >();

    // useEffect(() => {
    //     setIslive(!!LiveCalendar);
    // }, [dates]);

    useEffect(() => {
        fetchLiveCalendar(dates, 0, 10).then((res) => {
            setLiveCalendar(res.data.data.broadcastInfoList);
        });
    }, [dates]);

    useEffect(() => {
        if (LiveCalendar && LiveCalendar.length > 0) {
            Promise.all(
                LiveCalendar.map((item) =>
                    fetchCalendarItem(0, 10, item.liveBroadcastId)
                )
            ).then((results) => {
                const enrichedData = results.flatMap((res, index) => {
                    const item = LiveCalendar[index];
                    const detail = res.data.data.list[0];

                    return {
                        startDate: item.startDate,
                        nickName: item.nickName,
                        liveBroadcastId: item.liveBroadcastId,
                        broadcastTitle: item.broadcastTitle,
                        imgSrc: detail.imgSrc,
                        sellerImg: detail.sellerImg,
                        categoryId: detail.categoryId,
                        productName: detail.productName,
                        price: detail.price,
                        liveFlatPrice: detail.liveFlatPrice,
                        liveRatePrice: detail.liveRatePrice,
                    };
                });
                setEnrichedLiveCalendar(enrichedData);
            });
        }
    }, [LiveCalendar]);

    useEffect(() => {
        console.log(enrichedLiveCalendar)
    }, [enrichedLiveCalendar])

    return (
        <>
            {!enrichedLiveCalendar ? (
                <Box w={"100%"} h={"100%"}>
                    <Center mt={"8rem"}>
                        <Text as={"b"} fontSize={"3rem"}>
                            "라이브 예고가 없습니다"
                        </Text>
                    </Center>
                </Box>
            ) : (
                enrichedLiveCalendar?.map((res, index) => (
                    <>
                        <Flex
                            direction={"column"}
                            py={"4"}
                            gap={"4"}
                            overflowY={"hidden"}
                            key={index}
                            mb={"1rem"}
                        >
                            <Flex
                                alignItems={"center"}
                                gap={"4"}
                                key={index}
                                mb={"1rem"}
                            >
                                <Flex
                                    direction={"column"}
                                    justifyContent={"flex-start"}
                                    mr={"1rem"}
                                >
                                    <Text
                                        fontSize={"1.7rem"}
                                        fontFamily={"GmkBold"}
                                    >
                                        {dayjs
                                            .utc(res.startDate)
                                            .local()
                                            .format("HH:mm")}{" "}
                                    </Text>
                                </Flex>
                                <AspectRatio w="12rem" ratio={3 / 4}>
                                    <Image
                                        src={res.imgSrc}
                                        objectFit={"cover"}
                                    />
                                </AspectRatio>
                                <Flex
                                    direction={"column"}
                                    justifyContent={"flex-start"}
                                    key={index}
                                >
                                    <Text
                                        fontSize={"xl"}
                                        mb={"1.5"}
                                        as={"b"}
                                        color={"themeGreen.500"}
                                    >
                                        {res.productName}
                                    </Text>
                                    <Text fontSize={"2xl"} mb={"1.5"} as={"b"}>
                                        {res.broadcastTitle}
                                    </Text>
                                    <Text fontSize={"lg"} mb={2}>
                                        라이브 시간에만 적용되는 가격입니다
                                    </Text>
                                    <Text fontSize={"xl"} as={"s"}>
                                        {formatNumberWithComma(res.price)}
                                    </Text>
                                    <Flex alignItems={"center"} mt={"2"}>
                                        <Text
                                            fontSize={"2xl"}
                                            mr={3}
                                            color={"red"}
                                        >
                                            {res.liveRatePrice + "%"}
                                        </Text>
                                        <Text fontSize={"xl"} mr={5}>
                                            {formatNumberWithComma(
                                                res.liveFlatPrice
                                            )}
                                        </Text>
                                    </Flex>
                                </Flex>
                            </Flex>
                        </Flex>
                        
                    </>
                ))
            )}
        </>
    );
}
