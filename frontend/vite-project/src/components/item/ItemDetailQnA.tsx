import { Accordion, Button, Flex, IconButton, Text } from "@chakra-ui/react";
import QnaAccordion from "./qna/QnaAccordion";
import QnaRegistrationModal from "./qna/QnaRegistrationModal";
import { useParams } from "react-router-dom";
import { FaChevronDown, FaEdit } from "react-icons/fa";
import { getQnAList } from "../../api/itemQnA";
import { useEffect, useState } from "react";
import { ItemQnA } from "../../types/DataTypes";
import { AxiosResponse } from "axios";

function ItemDetailQnA() {
    const size = 5;
    const { id } = useParams() as { id: string };
    const productID = parseInt(id);
    const [accortionList, setAccortionList] = useState<Array<ItemQnA>>([]);
    const [page, setPage] = useState(0);
    const [maxPage, setMaxPage] = useState(0);
    const [modalOpen, setModalOpen] = useState(false);

    function handleModalOpen() {
        setModalOpen(!modalOpen);
    }
    function getMoreQnA() {
        if (page >= maxPage) {
            console.log("max page");
            return;
        }
        setPage(page + 1);
    }
    function refreshQnA(): void;
    function refreshQnA(
        newPage: number,
        newSize: number,
        isReset: boolean
    ): void;
    function refreshQnA(
        newPage?: number,
        newSize?: number,
        isReset?: boolean
    ): void {
        if (
            newPage === undefined ||
            newSize === undefined ||
            isReset === undefined
        ) {
            console.log("refreshQnA()");
            refreshQnA(0, (page + 1) * size, true);
        } else {
            console.log("refreshQnA(newPage, newSize, isReset)");
            getQnAList({
                page: newPage,
                size: newSize,
                "product-id": productID,
            }).then((res: AxiosResponse) => {
                if (isReset) {
                    setAccortionList([...res.data.data.list]);
                } else {
                    setAccortionList([...res.data.data.list, ...accortionList]);
                }
            });
        }
    }

    useEffect(() => {
        getQnAList({ page, size, "product-id": productID }).then(
            (res: AxiosResponse) => {
                setAccortionList([...accortionList, ...res.data.data.list]);
                if (maxPage === 0) {
                    const pages = res.data.data.totalCount / size;
                    if (Number.isInteger(pages)) {
                        setMaxPage(pages);
                    } else {
                        setMaxPage(Math.floor(pages) + 1);
                    }
                }
            }
        );
    }, [page, productID]);
    return (
        <>
            <Flex w={"90%"} justifyContent={"flex-end"}>
                <QnaRegistrationModal
                    isOpen={modalOpen}
                    handleModalOpen={handleModalOpen}
                    refreshQnA={refreshQnA}
                />
                <Button
                    leftIcon={<FaEdit />}
                    colorScheme="themeGreen"
                    size={"sm"}
                    onClick={handleModalOpen}
                >
                    문의하기
                </Button>
            </Flex>

            <Accordion allowMultiple w={"90%"} borderColor={"themeWhite.500"}>
                {accortionList.map((data, index) => {
                    return (
                        <QnaAccordion
                            data={data}
                            key={index}
                            refreshQnA={refreshQnA}
                        />
                    );
                })}
            </Accordion>
            <Flex w={"90%"} justifyContent={"center"}>
                {accortionList.length === 0 ? (
                    <Text textAlign={"center"}>문의가 없습니다</Text>
                ) : (
                    <IconButton
                        aria-label=""
                        hidden={page >= maxPage}
                        icon={<FaChevronDown />}
                        variant={"ghost"}
                        w={"100%"}
                        onClick={getMoreQnA}
                    />
                )}
            </Flex>
        </>
    );
}

export default ItemDetailQnA;
