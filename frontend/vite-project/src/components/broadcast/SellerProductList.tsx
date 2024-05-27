import { Text, Flex, Image, Center } from "@chakra-ui/react";

interface SellerProductListInterface {
    img: string;
    price: number;
    title: string;
}

export default function SellerProductList({
    img,
    price,
    title,
}: SellerProductListInterface) {
    return (
        <Flex direction={"row"} gap={3} p={"0.3rem"} justifyContent={"space-between"}>
            <Image objectFit={"cover"} boxSize={"6rem"} src={`${img}`} />
            <Center>
                <Text as={"b"} fontSize={"lg"} mr={"2.5rem"}>
                    {title}
                </Text>
                <Text as={"b"} fontSize={"lg"}>
                    {price}
                </Text>
            </Center>
        </Flex>
    );
}
