import { Flex, Box, Text, Grid, Image, AspectRatio, Center } from "@chakra-ui/react";
import { ItemDetailInterface } from "../../types/DataTypes";
import { useNavigate } from "react-router-dom";

export default function SellerPosts({
    products,
}: {
    products: ItemDetailInterface[];
}) {
    const navigate = useNavigate();
    return (
        <>
            <Grid
                templateColumns="repeat(3, 1fr)"
                gap={4}
                maxW="80%"
                mx="auto"
                mt={8}
            >
                {products.map((product, index) => (
                    <Center>
                        <Box key={index} position="relative">
                            <AspectRatio w="23rem" ratio={1 / 1}>
                                <Image
                                    src={product.imgSrc}
                                    aspectRatio="1/1"
                                    objectFit="cover"
                                    overflow={"hidden"}
                                    position={"relative"}
                                    borderRadius={"20px"}
                                    transition="transform 0.3s"
                                    _hover={{ transform: "scale(1.05)" }}
                                />
                            </AspectRatio>

                            {/* <Image
                            src={product.imgSrc}
                            w="100%"
                            h="auto"
                            borderRadius="md"
                            transition="transform 0.3s"
                            _hover={{ transform: "scale(1.05)" }}
                        /> */}
                            <Flex
                                position="absolute"
                                top="0"
                                left="0"
                                right="0"
                                bottom="0"
                                align="center"
                                justify="center"
                                opacity={0}
                                bgColor="rgba(226, 232, 240, 0.5)"
                                transition="opacity 0.3s"
                                onClick={() => {
                                    navigate(
                                        `/v1/items/detail/${product.productId}`
                                    );
                                }}
                                _hover={{
                                    opacity: 1,
                                    cursor: "pointer",
                                }}
                            >
                                <Text
                                    color="white"
                                    fontWeight="bold"
                                    fontSize="2rem"
                                >
                                    {product.productName}
                                </Text>
                            </Flex>
                        </Box>
                    </Center>
                ))}
            </Grid>
        </>
    );
}

