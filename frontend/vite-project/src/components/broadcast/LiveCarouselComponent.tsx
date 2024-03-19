import { Box, Flex } from "@chakra-ui/react";
import React, { useEffect, useState } from "react";
import LiveListComponent from "./LiveListComponent";
import { DisplayInterface, FirstDisplayInterface } from "../../types/DataTypes";
import { fetchCalendarItem } from "../../api/liveProduct";

interface CarouselComponentInterface {
    fetchLiveData: Array<DisplayInterface> | undefined;
}

export default function LiveCarouselComponent(
    fetchLiveData: CarouselComponentInterface
) {
    const [{ x }, setPosition] = useState({
        x: 0,
    });

    const [displayData, setDisplayData] = useState<Array<FirstDisplayInterface>>();

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
                const enrichedData = res.flatMap((res, index) => {
                    if (fetchLiveData.fetchLiveData === undefined) {
                        return {
                            price: 0,
                            liveFlatPrice: 0,
                            imgSrc: "",
                            liveBroadcastId: 0,
                            broadcastTitle: '',
                            productName: '',
                            liveRatePrice: 0,
                        };
                    }

                    const liveItem = fetchLiveData.fetchLiveData[index];
                    const detail = res.data.data.list[0];

                    return {
                        price: detail.price,
                        broadcastTitle: liveItem.broadcastTitle,
                        productName: detail.productName,
                        liveRatePrice: detail.liveRatePrice,
                        liveFlatPrice: detail.liveFlatPrice,
                        imgSrc: detail.imgSrc,
                        liveBroadcastId: liveItem.liveBroadcastId,
                    };
                });
                setDisplayData(enrichedData);
            });
        }
    }, [fetchLiveData]);

    return (
        <Box
            display={"block"}
            overflowX={"hidden"}
            overflowY={"hidden"}
            w={"80%"}
            mt={"1rem"}
            mb={"1rem"}
            h={"27rem"}
        >
            <Flex
                wrap={"nowrap"}
                direction={"row"}
                m={"auto"}
                overflowX={"hidden"}
                overflowY={"hidden"}
                w={`${9 * 14.5}rem`}
                style={{ transform: `translateX(${x}px)` }}
                onMouseDown={(
                    clickEvent: React.MouseEvent<Element, MouseEvent>
                ) => {
                    const startX = clickEvent.screenX;

                    const mouseMoveHandler = (moveEvent: MouseEvent) => {
                        const deltaX = moveEvent.screenX - startX;
                        const newX = x + deltaX;
                        if (newX > 0) {
                            setPosition({ x: 0 });
                        } else {
                            setPosition({ x: newX });
                        }
                    };

                    const mouseUpHandler = () => {
                        document.removeEventListener(
                            "mousemove",
                            mouseMoveHandler
                        );
                    };

                    document.addEventListener("mousemove", mouseMoveHandler);
                    document.addEventListener("mouseup", mouseUpHandler, {
                        once: true,
                    });
                }}
            >
                {displayData?.map((data, index) => (
                    <Box key={index} w="100%">
                        <LiveListComponent
                            liveFlatPrice={data.liveFlatPrice}
                            liveBroadcastId={data.liveBroadcastId}
                            url={data.imgSrc}
                            title={data.broadcastTitle}
                            price={data.price}
                        />
                    </Box>
                ))}
            </Flex>
        </Box>
    );
}
