import { Flex, Text } from "@chakra-ui/react";
import LiveCarouselComponent from "../components/broadcast/LiveCarouselComponent";
import CarouselComponent from "../components/common/CarouselComponent";
import { fetchLiveCarousel } from "../api/live";
import { useEffect, useState } from "react";
import { DisplayInterface,  } from "../types/DataTypes";

export default function LiveList() {
    const [fetchLiveData, setFetchLiveData] = useState<Array<DisplayInterface>>([])
    const [shuffledData, setRandomLiveData] = useState<Array<DisplayInterface>>([])

    useEffect(() => {
        try {
            fetchLiveCarousel().then((res) => {
                setFetchLiveData(res.data.data.broadcastInfoList)
            })
        } catch (err) {
            console.log(err)
        }
    }, [])

    useEffect(() => {
        if (fetchLiveData?.length > 0) {
            const shuffledData = [...fetchLiveData].sort(() => Math.random() - 0.5).slice(0, 9);
            setRandomLiveData(shuffledData);
        }
    }, [fetchLiveData]);

    useEffect(() => {
        console.log(shuffledData)
    }, [shuffledData])

    return (
        <>
            <CarouselComponent fetchLiveData={fetchLiveData} />

            <Flex
                direction={"column"}
                alignItems={"center"}
                mt={"4rem"}
                mb={"10rem"}
                maxW={"100vw"}
            >
                <Text
                    color={"themeGreen.500"}
                    fontSize={"3xl"}
                    as={"b"}
                    mt={"1rem"}
                >
                    지금 가장 핫한 라이브
                </Text>

                <LiveCarouselComponent fetchLiveData={shuffledData} />

                
            </Flex>
        </>
    );
}
