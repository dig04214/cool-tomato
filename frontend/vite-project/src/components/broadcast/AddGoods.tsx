import { TableContainer } from "@chakra-ui/table";
import { Button, Flex, Text } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { ItemDetailInterface, liveProductPrice } from "../../types/DataTypes";
import LiveProductTable from "./liveproduct/LiveProductTable";

type Typeprops = {
    isSelected: boolean;
    isSelectedState: (value: boolean) => void;
    products: Array<ItemDetailInterface>;
    setProducts: React.Dispatch<React.SetStateAction<ItemDetailInterface[]>>;
    selectedProductId: Map<number, liveProductPrice>;
    setSelectedProductId: React.Dispatch<
        React.SetStateAction<Map<number, liveProductPrice>>
    >;
    mainProductId: number;
    setMainProductId: React.Dispatch<React.SetStateAction<number>>;
    setIsOpen: React.Dispatch<React.SetStateAction<boolean>>;
};

export default function AddGoods({
    isSelected,
    isSelectedState,
    products,
    setProducts,
    selectedProductId,
    setSelectedProductId,
    mainProductId,
    setMainProductId,
    setIsOpen,
}: Typeprops) {
    const [selected, setSelected] = useState<Array<ItemDetailInterface>>([]);
    useEffect(() => {
        const temp = products.filter((x) => selectedProductId.has(x.productId));
        setSelected(temp);
    }, [selectedProductId]);

    function handleDelete(e: React.MouseEvent<HTMLButtonElement>) {
        const id = parseInt(e.currentTarget.value);
        selectedProductId.delete(id);
        setSelectedProductId(new Map(selectedProductId));
    }
    function handleMainProduct(e: React.ChangeEvent<HTMLInputElement>) {
        const id = parseInt(e.currentTarget.value);
        setMainProductId(id);
    }
    function onOpen() {
        setIsOpen(true);
    }
    function handlePriceBlur(e: React.FocusEvent<HTMLInputElement>) {
        console.log("handlePriceBlur");
        if (e.target.dataset.productid === undefined) return;
        const id = parseInt(e.target.dataset.productid);
        const price = parseInt(e.target.value);
        const temp = selectedProductId.get(id);
        if (temp === undefined) return;
        temp.price = price;
        temp.discount = Math.floor((1 - price / temp.originalPrice) * 100);
        selectedProductId.set(id, temp);
        setSelectedProductId(new Map(selectedProductId));
    }
    function handlePriceChange(e: React.ChangeEvent<HTMLInputElement>) {
        console.log("handlePriceChange");
        if (e.target.dataset.productid === undefined) return;
        const id = parseInt(e.target.dataset.productid);
        const price = parseInt(e.target.value);
        const temp = selectedProductId.get(id);
        if (temp === undefined) return;
        temp.price = price;
        selectedProductId.set(id, temp);
        setSelectedProductId(new Map(selectedProductId));
    }
    function handleDiscountBlur(e: React.FocusEvent<HTMLInputElement>) {
        console.log("handleDiscountBlur");
        if (e.target.dataset.productid === undefined) return;
        const id = parseInt(e.target.dataset.productid);
        const discount = parseInt(e.target.value);
        const temp = selectedProductId.get(id);
        if (temp === undefined) return;
        temp.discount = discount;
        temp.price = Math.floor((temp.originalPrice * (100 - discount)) / 100);
        selectedProductId.set(id, temp);
        setSelectedProductId(new Map(selectedProductId));
    }
    function handleDiscountChange(e: React.ChangeEvent<HTMLInputElement>) {
        console.log("handleDiscountChange");
        if (e.target.dataset.productid === undefined) return;
        const id = parseInt(e.target.dataset.productid);
        const discount = parseInt(e.target.value);
        const temp = selectedProductId.get(id);
        if (temp === undefined) return;
        temp.discount = discount;
        selectedProductId.set(id, temp);
        setSelectedProductId(new Map(selectedProductId));
    }
    return (
        <TableContainer p={"0.1rem"}>
            {isSelected ? (
                <Flex justifyContent={"flex-end"} mr={"0.5rem"}>
                    <Button
                        colorScheme="teal"
                        variant="outline"
                        onClick={onOpen}
                    >
                        <Text as={"b"} fontSize={"md"}>
                            상품 추가
                        </Text>
                    </Button>
                </Flex>
            ) : null}
            <LiveProductTable
                handleDelete={handleDelete}
                handleMainProduct={handleMainProduct}
                handlePriceBlur={handlePriceBlur}
                handlePriceChange={handlePriceChange}
                handleDiscountBlur={handleDiscountBlur}
                handleDiscountChange={handleDiscountChange}
                selected={selected}
                mainProductId={mainProductId}
                selectedProductId={selectedProductId}
            />
        </TableContainer>
    );
}
