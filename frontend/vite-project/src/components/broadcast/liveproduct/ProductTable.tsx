import React from "react";
import {
    ItemDetailInterface,
    liveProductPrice,
} from "../../../types/DataTypes";
import { Wrap, WrapItem, Center } from "@chakra-ui/react";
import ProductTableBody from "./ProductTableBody";

interface ProductTableProps {
    products: Array<ItemDetailInterface>;
    selectedProductId: Map<number, liveProductPrice>;
    handleCheck: (
        e: React.ChangeEvent<HTMLInputElement>,
        id: number,
        price: number
    ) => void;
}

function ProductTable({
    products,
    selectedProductId,
    handleCheck,
}: ProductTableProps) {
    return (
        <>
            {/* 상품 표 헤더 */}
            <Wrap borderBottom="1px solid grey" spacing="0.1rem">
                <WrapItem>
                    <Center w="6rem" h="2.5rem">
                        상품번호
                    </Center>
                </WrapItem>
                <WrapItem>
                    <Center w="22rem" h="2.5rem">
                        상품이름
                    </Center>
                </WrapItem>
                <WrapItem>
                    <Center w="6rem" h="2.5rem">
                        수량
                    </Center>
                </WrapItem>
                <WrapItem>
                    <Center w="9rem" h="2.5rem">
                        가격
                    </Center>
                </WrapItem>
                <WrapItem>
                    <Center w="4rem" h="2.5rem">
                        선택
                    </Center>
                </WrapItem>
            </Wrap>
            {/* 상품 표 내용 */}
            {products.map((product) => {
                return (
                    <ProductTableBody
                        key={product.productId}
                        product={product}
                        selectedProductId={selectedProductId}
                        handleCheck={handleCheck}
                    />
                );
            })}
        </>
    );
}

export default ProductTable;
