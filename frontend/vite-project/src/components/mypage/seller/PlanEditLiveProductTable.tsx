import {
    Table,
    Thead,
    Tr,
    Th,
    Tbody,
    Td,
    FormControl,
    Input,
    Flex,
    Switch,
    CloseButton,
    Text,
} from "@chakra-ui/react";
import {
    ItemDetailInterface,
    liveProductPrice,
    LiveProductAll
} from "../../../types/DataTypes";

interface LiveProductTableProps {
    selected: Array<ItemDetailInterface>;
    selectedProductId: Map<number, liveProductPrice>;
    handlePriceBlur: (e: React.FocusEvent<HTMLInputElement>) => void;
    handlePriceChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    handleDiscountBlur: (e: React.FocusEvent<HTMLInputElement>) => void;
    handleDiscountChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
    handleMainProduct: (e: React.ChangeEvent<HTMLInputElement>) => void;
    mainProductId: number;
    handleDelete: (e: React.MouseEvent<HTMLButtonElement>) => void;
    liveproducts: LiveProductAll[];
}

function LiveProductTable({
    selected,
    selectedProductId,
    handleDelete,
    handleDiscountBlur,
    handleDiscountChange,
    handleMainProduct,
    handlePriceBlur,
    handlePriceChange,
    mainProductId,
}: LiveProductTableProps) {
    return (
        <Table variant="simple" size={"lg"}>
            <Thead>
                <Tr>
                    <Th width={"10rem"} textAlign="center">
                        <Text as={"b"} fontSize={"xl"}>
                            상품이름
                        </Text>
                    </Th>
                    <Th width={"4rem"} textAlign="center">
                        <Text as={"b"} fontSize={"xl"}>
                            가격
                        </Text>
                    </Th>
                    <Th width={"9rem"} textAlign="center">
                        <Text as={"b"} fontSize={"xl"}>
                            라이브 가격
                        </Text>
                    </Th>
                    <Th width={"9rem"} textAlign="center">
                        <Text as={"b"} fontSize={"xl"}>
                            할인율
                        </Text>
                    </Th>
                    <Th textAlign="center" width="">
                        <Text as={"b"} fontSize={"xl"}>
                            대표상품
                        </Text>
                    </Th>
                    <Th textAlign="center">
                        <Text as={"b"} fontSize={"xl"} textAlign="center">
                            취소
                        </Text>
                    </Th>
                </Tr>
            </Thead>

            <Tbody>
                {selected.map((product) => {
                    const id = product.productId;
                    return (
                        <Tr key={product.productId}>
                            <Td textAlign="center">
                                <Text
                                    as={"b"}
                                    fontSize={"xl"}
                                    textAlign="center"
                                >
                                    {product.productName.length > 5
                                        ? product.productName.slice(0, 5) +
                                          "..."
                                        : product.productName}
                                </Text>
                            </Td>
                            <Td textAlign="center">
                                <Text
                                    as={"b"}
                                    fontSize={"xl"}
                                    textAlign="center"
                                >
                                    {product.price}
                                </Text>
                            </Td>
                            <Td textAlign="center">
                                <FormControl
                                    variant="floating"
                                    isRequired
                                    isInvalid
                                >
                                    <Input
                                        type="number"
                                        data-productid={product.productId}
                                        value={selectedProductId.get(id)?.price}
                                        onBlur={handlePriceBlur}
                                        onChange={handlePriceChange}
                                    />
                                    {/* <FormLabel>가격 입력</FormLabel> */}
                                </FormControl>
                            </Td>
                            <Td textAlign="center">
                                <FormControl
                                    variant="floating"
                                    isRequired
                                    isInvalid
                                >
                                    <Input
                                        type="number"
                                        data-productid={product.productId}
                                        value={
                                            selectedProductId.get(id)?.discount
                                        }
                                        onBlur={handleDiscountBlur}
                                        onChange={handleDiscountChange}
                                    />
                                    {/* <FormLabel>할인율 입력</FormLabel> */}
                                </FormControl>
                            </Td>

                            <Td width={"1rem"} textAlign="center">
                                <Flex justifyContent={"right"}>
                                    <Switch
                                        value={id}
                                        onChange={handleMainProduct}
                                        isChecked={id === mainProductId}
                                        size={"md"}
                                    />
                                </Flex>
                            </Td>
                            <Td textAlign="center">
                                <Flex justifyContent={"right"}>
                                    <CloseButton
                                        size="md"
                                        onClick={handleDelete}
                                        value={id}
                                    />
                                </Flex>
                            </Td>
                        </Tr>
                    );
                })}
            </Tbody>
        </Table>
    );
}

export default LiveProductTable;
