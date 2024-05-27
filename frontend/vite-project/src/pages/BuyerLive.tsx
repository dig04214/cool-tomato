import { Avatar, Box, Center, Flex, Text } from "@chakra-ui/react";

import BuyerItem from "../components/live/BuyerItem";
import BuyerChat from "../components/live/BuyerChat";

import { CiHeart } from "react-icons/ci";
import { Icon } from "@chakra-ui/react";
import BuyerLiveNav from "../components/common/BuyerLiveNav";

import OpenViduComponent from "../components/openvidu/OpenViduComponent";
import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../redux/stores/store";
import { LiveProductAll, SellerInfo } from "../types/DataTypes";
import { useParams } from "react-router-dom";
import { getLiveProduct } from "../api/liveProduct";
import { getSellerDetailAPI } from "../api/user";

function UnfilledHeart() {
    return <Icon as={CiHeart} boxSize={"3rem"} ml={"3px"} mb={"3px"} />;
}

export default function BuyerLive() {
    const accessToken = useSelector((state: RootState) => {
        return state.user.accessToken;
    });
    const [liveproducts, setLiveproducts] = useState<LiveProductAll[]>([]);
    const [sellerImg, setSellerImg] = useState<string>("");
    const [sellerName, setSellerName] = useState<string>("");
    const [stream, setStream] = useState<boolean>(true);
    const roomId = useParams().roomId!;
    const id = parseInt(roomId);

    useEffect(() => {
        const fetchData = async () => {
            const response = await getLiveProduct(
                { "live-id": id },
                accessToken
            );
            setLiveproducts(response.list);
            if (response.list.length === 0) return;
            const sellerId = response.list[0].sellerId;
            const res = await getSellerDetailAPI(sellerId);
            const sellerInfo: SellerInfo = res.data;
            if (
                sellerInfo.profileImg === null ||
                sellerInfo.profileImg === undefined
            )
                sellerInfo.profileImg = "";
            if (
                sellerInfo.nickname === null ||
                sellerInfo.nickname === undefined
            )
                sellerInfo.nickname = "";
            setSellerImg(sellerInfo.profileImg);
            setSellerName(sellerInfo.nickname);
        };
        fetchData();
    }, [roomId, accessToken]);

    return (
        <>
            <BuyerLiveNav setStream={setStream} />
            <Flex direction={"column"} backgroundColor={"themeWhite.500"}>
                <Center mt={"1rem"} p={"1rem"}>
                    <Flex
                        w={"60%"}
                        h={"2rem"}
                        direction={"row"}
                        alignItems={"center"}
                    >
                        <Avatar bg="teal.500" src={sellerImg} />
                        <Text ml={"1rem"} as={"b"} fontSize={"xl"}>
                            {sellerName ? sellerName : "라이브"}
                        </Text>
                    </Flex>
                </Center>
                <Flex
                    direction={"row"}
                    justify={"center"}
                    h={"90vh"}
                    p={"2rem"}
                >
                    <Box
                        w={"lg"}
                        borderRadius={"20px"}
                        backgroundColor={"#ffffff"}
                    >
                        <OpenViduComponent
                            type="live"
                            stream={stream}
                            setStream={setStream}
                        />
                    </Box>
                    <Flex direction={"column"} alignSelf={"flex-end"}>
                        <UnfilledHeart />
                    </Flex>
                    <Flex direction={"column"} ml={"2rem"}>
                        <Box
                            w={"sm"}
                            h={"50%"}
                            overflowY={"auto"}
                            backgroundColor={"#ffffff"}
                            borderRadius={"20px"}
                        >
                            <BuyerItem liveproducts={liveproducts} />
                        </Box>
                        <Box
                            w={"sm"}
                            backgroundColor={"#ffffff"}
                            h={"50%"}
                            borderRadius={"20px"}
                            mt={"1rem"}
                        >
                            <BuyerChat />
                        </Box>
                    </Flex>
                </Flex>
            </Flex>
        </>
    );
}
