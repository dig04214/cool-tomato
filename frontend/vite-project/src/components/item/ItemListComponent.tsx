import {
    AspectRatio,
    Avatar,
    Box,
    Center,
    Flex,
    Image,
    Tooltip,
} from "@chakra-ui/react";
import "../../css/ItemListComponentcss.css";
import { useNavigate } from "react-router-dom";
import React from "react";
import { formatNumberWithComma } from "../common/Comma";

interface GoodsProps {
    id: number | undefined;
    img: string | undefined;
    title: string | undefined;
    price: number | undefined;
    profile: string | null | undefined;
    sellerId: number | undefined;
}

const Goods = ({ id, img, title, price, profile, sellerId }: GoodsProps) => {
    const navigate = useNavigate();
    return (
        <Box>
            <Box>
                <Box>
                    <Box
                        maxW={"100%"}
                        onClick={() => {
                            navigate(`/v1/items/detail/${id}`);
                        }}
                        _hover={{ cursor: "pointer" }}
                        borderRadius={"20px"}
                        
                    >
                        <AspectRatio w="23rem" ratio={1 / 1} overflow={"hidden"} position={"relative"}>
                            <Image
                                src={img}
                                objectFit="cover"
                                position="absolute"
                                borderRadius="20px"
                                transition="all 0.2s ease-out"
                                transform = "translate(-0.5%, -0.5%)"
                                style={{
                                    top: "0.5%",
                                    left: "0.5%",
                                    width: "100%",
                                    height: "100%",
                                }}
                                _hover={{ transform: "scale(1.1)" }}
                            />
                        </AspectRatio>
                    </Box>
                </Box>
                <Flex mt={"0.5rem"}>
                    <Center>
                        <Tooltip label="판매자 정보 보기">
                            <Avatar
                                size="md"
                                name="Ryan Florence"
                                onClick={() => {
                                    navigate(`/v1/seller/profile/${sellerId}`);
                                }}
                                _hover={{ cursor: "pointer" }}
                                src={
                                    profile === null
                                        ? "/img/default_profile.jpeg"
                                        : profile
                                }
                                mr={"1rem"}
                            />
                        </Tooltip>
                    </Center>

                    <Box
                        onClick={() => {
                            navigate(`/v1/items/detail/${id}`);
                        }}
                        _hover={{ cursor: "pointer" }}
                    >
                        <Box className="Text">
                            <Box className="TextTitle">{title}</Box>
                        </Box>
                        <Box className="tagWrap">
                            {price ? formatNumberWithComma(price) : ""}
                        </Box>
                    </Box>
                </Flex>
            </Box>
        </Box>
    );
};

export default Goods;

