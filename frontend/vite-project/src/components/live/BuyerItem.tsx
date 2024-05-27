import { Box, Center, Divider, Flex, Text } from "@chakra-ui/react";
import { LiveProductAll } from "../../types/DataTypes";
import BuyerLiveItem from "./BuyerLiveItem";

interface BuyerItemProps {
    liveproducts: LiveProductAll[];
}

export default function BuyerItem({ liveproducts }: BuyerItemProps) {
    return (
        <>
            <Flex direction={"column"}>
                <Center p={"1rem"}>
                    <Text as={"b"} fontSize={"2xl"}>
                        라이브 특별 가격!
                    </Text>
                </Center>
                <Divider></Divider>
                <Box p={"0.5rem"}>
                    {liveproducts.map((item) => (
                        <BuyerLiveItem
                            key={item.liveProductId}
                            id={item.liveProductId}
                            img={item.imgSrc}
                            title={item.productName}
                            price={item.price}
                            liveFlatPrice={item.liveFlatPrice}
                        />
                    ))}
                </Box>
            </Flex>
        </>
    );
}
