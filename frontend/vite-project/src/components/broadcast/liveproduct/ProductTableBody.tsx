import { Wrap, WrapItem, Center, Checkbox } from "@chakra-ui/react";
import {
    ItemDetailInterface,
    liveProductPrice,
} from "../../../types/DataTypes";
import React from "react";
import { formatNumberWithComma } from "../../common/Comma";

interface ProductTableBodyProps {
    product: ItemDetailInterface;
    selectedProductId: Map<number, liveProductPrice>;
    handleCheck: (
        e: React.ChangeEvent<HTMLInputElement>,
        id: number,
        price: number
    ) => void;
}

function ProductTableBody({
    product,
    selectedProductId,
    handleCheck,
}: ProductTableBodyProps) {
    const id = product.productId;
    return (
        <Wrap borderBottom="1px solid grey" spacing="0.1rem">
            <WrapItem>
                <Center w="6rem" h="2.5rem">
                    {id}
                </Center>
            </WrapItem>
            <WrapItem>
                <Center w="22rem" h="2.5rem">
                    {product.productName}
                </Center>
            </WrapItem>
            <WrapItem>
                <Center w="6rem" h="2.5rem">
                    {product.quantity}
                </Center>
            </WrapItem>
            <WrapItem>
                <Center w="9rem" h="2.5rem">
                    {formatNumberWithComma(product.price)}
                </Center>
            </WrapItem>
            <WrapItem>
                <Center w="4rem" h="2.5rem">
                    <Checkbox
                        size="lg"
                        colorScheme="green"
                        data-productid={id}
                        data-price={product.price}
                        checked={selectedProductId.has(id)}
                        onChange={(e) => handleCheck(e, id, product.price)}
                        defaultChecked={selectedProductId.has(id)}
                    ></Checkbox>
                </Center>
            </WrapItem>
        </Wrap>
    );
}

export default ProductTableBody;
