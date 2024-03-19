import { Text, Flex, Image, Center } from "@chakra-ui/react";
import { formatNumberWithComma } from "../common/Comma";

interface Items {
    id: number;
    img: string;
    title: string;
    price: number;
    liveFlatPrice: number;
}

const BuyerLiveItem = ({ id, img, title, price, liveFlatPrice }: Items) => {
    return (
        <Flex
            direction={"row"}
            key={id}
            p={"0.3rem"}
            justifyContent={"space-between"}
        >
            <Image borderRadius={"20px"} objectFit={"cover"} boxSize={"6rem"} src={`${img}`} />
            <Center>
                <Flex direction={"column"} gap={1}>
                    <Text as={"b"} fontSize={"lg"}>
                        {title}
                    </Text>
                    <Text as={"b"} fontSize={"lg"} color={"red"}>
                        {formatNumberWithComma(liveFlatPrice)}
                    </Text>
                    <Text as={"s"} fontSize={"lg"}>
                        {formatNumberWithComma(price)}
                    </Text>
                </Flex>
            </Center>
        </Flex>
    );
};

export default BuyerLiveItem;

