import { Button, Icon, Input, Wrap, WrapItem } from "@chakra-ui/react";
import { Text, Box, Center, Flex, Spacer } from "@chakra-ui/layout";
import {
    Modal,
    ModalCloseButton,
    ModalContent,
    ModalHeader,
    ModalOverlay,
    ModalFooter,
    ModalBody,
} from "@chakra-ui/modal";
import { Search2Icon } from "@chakra-ui/icons";
import { FaArrowDown } from "react-icons/fa";
import { useSelector } from "react-redux";
import { RootState } from "../../redux/stores/store";
import { useEffect, useState } from "react";
import { ItemDetailInterface, liveProductPrice } from "../../types/DataTypes";
import { ItemListSellerGet } from "../../api/Itemlist";
import ProductTable from "./liveproduct/ProductTable";

interface Typeprops {
    isSelected: boolean;
    isSelectedState: (value: boolean) => void;
    products: Array<ItemDetailInterface>;
    setProducts: React.Dispatch<React.SetStateAction<ItemDetailInterface[]>>;
    selectedProductId: Map<number, liveProductPrice>;
    setSelectedProductId: React.Dispatch<
        React.SetStateAction<Map<number, liveProductPrice>>
    >;
    page: number;
    setPage: React.Dispatch<React.SetStateAction<number>>;
    size: number;
    isOpen: boolean;
    setIsOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

export default function LiveItemAdd({
    isSelected,
    isSelectedState,
    products,
    setProducts,
    selectedProductId,
    setSelectedProductId,
    page,
    setPage,
    size,
    isOpen,
    setIsOpen,
}: Typeprops) {
    const accessToken = useSelector(
        (state: RootState) => state.user.accessToken
    );
    const [scrollButtonHidden, setScrollButtonHidden] =
        useState<boolean>(false);
    const [maxPage, setMaxPage] = useState<number>(0);

    useEffect(() => {
        ItemListSellerGet({ page, size }, accessToken)
            .then((res) => {
                setProducts([...products, ...res.data.data.list]);
                if (maxPage === 0) {
                    const totalCount = res.data.data.totalCount;
                    setMaxPage(Math.ceil(totalCount / size));
                }
            })
            .catch((error) => {
                console.log("useEffect ItemListSellerGer Error");
                console.log(error);
            });
    }, [page]);

    function handleCheck(
        e: React.ChangeEvent<HTMLInputElement>,
        id: number,
        price: number
    ) {
        console.log("LiveItemAdd handleCheck");
        // console.log(e.target);
        // console.log(e.target.checked);
        if (e.target.checked) {
            const initLivePrice: liveProductPrice = {
                productId: id,
                originalPrice: price,
                price: 0,
                discount: 0,
            };
            setSelectedProductId(
                new Map(selectedProductId.set(id, initLivePrice))
            );
        } else {
            selectedProductId.delete(id);
            setSelectedProductId(new Map(selectedProductId));
        }
    }

    function handleSubmit() {
        isSelectedState(true);
        onClose();
    }

    function onClose() {
        setIsOpen(false);
    }

    function handlePage() {
        if (page >= maxPage) {
            setScrollButtonHidden(true);
            return;
        }
        setPage(page + 1);
    }

    return (
        <Modal size={"5xl"} onClose={onClose} isOpen={isOpen} isCentered>
            <ModalOverlay />
            <ModalContent p={"2rem"}>
                <ModalHeader>
                    <Center>
                        <Text as={"b"} fontSize={"3xl"}>
                            판매중인 상품 목록
                        </Text>
                    </Center>
                </ModalHeader>
                <ModalCloseButton />

                <Flex minWidth={"max-content"} alignItems={"center"} gap={"2"}>
                    <Box />
                    <Spacer />
                    <Flex gap={"2"} p={"1rem"}>
                        <Search2Icon boxSize={"2rem"} />
                        <Input size={"md"} placeholder="검색" />
                    </Flex>
                </Flex>
                <ModalBody>
                    <Box p={"3rem"}>
                        <Box
                            h={"50vh"}
                            borderWidth={"4px"}
                            borderRadius={"30px"}
                        >
                            <Box
                                h={"50vh"}
                                display={"block"}
                                p={"1rem"}
                                overflowY={"scroll"}
                            >
                                <ProductTable
                                    products={products}
                                    selectedProductId={selectedProductId}
                                    handleCheck={handleCheck}
                                />

                                <Wrap
                                    spacing="0.25rem"
                                    hidden={scrollButtonHidden}
                                >
                                    <WrapItem w="100%">
                                        <Button
                                            w="100%"
                                            variant="ghost"
                                            onClick={handlePage}
                                        >
                                            <Icon as={FaArrowDown} />
                                        </Button>
                                    </WrapItem>
                                </Wrap>
                            </Box>
                        </Box>
                    </Box>
                </ModalBody>
                <ModalFooter p={"1rem"}>
                    <Button
                        bgColor={"themeGreen.500"}
                        mr={3}
                        onClick={handleSubmit}
                    >
                        <Text as={"samp"} color={"white"}>
                            등록
                        </Text>
                    </Button>
                    <Button bgColor={"themeRed.500"} onClick={onClose}>
                        <Text as={"samp"} color={"white"}>
                            닫기
                        </Text>
                    </Button>
                </ModalFooter>
            </ModalContent>
        </Modal>
    );
}
