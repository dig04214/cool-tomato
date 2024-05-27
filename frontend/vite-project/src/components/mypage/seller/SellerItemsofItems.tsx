// 자식 props

import { Box, Flex } from "@chakra-ui/layout";
import { Accordion, AccordionButton, AccordionIcon, AccordionItem, AccordionPanel, Button, Image } from "@chakra-ui/react";
import { useNavigate } from "react-router-dom";
import { ItemDetailInterface } from "../../../types/DataTypes";
import { ItemDetailDelete } from "../../../api/Itemlist";
import React from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../../redux/stores/store";
import ItemDetailDetail from "../../item/ItemDetailDetail";

function ItemsofItems({ sellerItem, onDelete }: { sellerItem: ItemDetailInterface, onDelete: (productId: number) => void }) {
    const navigate = useNavigate();
    const accessToken = useSelector((state: RootState) => {
        return state.user.accessToken
    })

    function onclick() {
        navigate(`/v1/items/detail/${sellerItem.productId}`);
    }

    const DeleteFunction = () => {
        ItemDetailDelete(sellerItem.productId, accessToken).then(() => {
            onDelete(sellerItem.productId)
        }).catch((err) => { console.log(err) })
    };

    const EditFunction = () => {
        navigate(`/v1/items/edit/${sellerItem.productId}`)
    }

    return (
        <Flex
            justifyContent="space-between"
            alignItems="center"
            my="4"
            mx="auto"
            p="2"
            borderWidth="1px"
            borderRadius="lg"
            overflow="hidden"
        >
            <Flex p="2">
                <Image mr="2" boxSize="100px" src={sellerItem.imgSrc} />

                <Box ml="2" pt="4">
                    <Box
                        mt="1"
                        fontWeight="semibold"
                        as="h4"
                        lineHeight="tight"
                        noOfLines={1}
                        mb={"0.7rem"}
                    >
                        {sellerItem.productName}
                    </Box>

                    <Accordion allowToggle mb={"0.7rem"}>
                        <AccordionItem>
                            <h2>
                                <AccordionButton>
                                    <Box as="span" flex='1' textAlign='left'>
                                        제품 내용 상세보기
                                    </Box>
                                    <AccordionIcon />
                                </AccordionButton>
                            </h2>
                            <AccordionPanel pb={4}>
                                <ItemDetailDetail content={sellerItem?.productContent} />
                            </AccordionPanel>
                        </AccordionItem>
                    </Accordion>

                    <Box display="flex" alignItems="baseline">
                        <Box
                            color="gray.500"
                            fontWeight="semibold"
                            letterSpacing="wide"
                            fontSize="xs"
                            textTransform="uppercase"
                        >
                            가격 {sellerItem.price} &bull; 배송비{" "}
                            {sellerItem.deliveryCharge} &bull; 수량{" "}
                            {sellerItem.quantity}
                            &nbsp; 판매링크 {sellerItem.paymentLink}
                        </Box>
                    </Box>
                </Box>
            </Flex>

            <Flex direction={"column"}>
                <Button colorScheme="red" mb={"0.5rem"} onClick={DeleteFunction}>
                    삭제
                </Button>
                <Button mb={"0.5rem"} onClick={EditFunction}>수정</Button>
                <Button onClick={onclick}>상세</Button>
            </Flex>
        </Flex>
    );
}

export default ItemsofItems;
