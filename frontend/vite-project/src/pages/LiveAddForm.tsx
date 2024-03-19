import {
    Center,
    Input,
    Container,
    Box,
    FormControl,
    FormLabel,
    Text,
    Textarea,
    Button,
    Switch,
    Flex,
} from "@chakra-ui/react";
import { useState } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../redux/stores/store";
import LiveItemAdd from "../components/broadcast/LiveItemAdd";
import AddGoods from "../components/broadcast/AddGoods";
import { reserveLive } from "../api/openVidu";
import {
    ItemDetailInterface,
    broadcastInfo,
    liveProduct,
    liveProductPrice,
} from "../types/DataTypes";
import { useNavigate } from "react-router-dom";
import { postLiveProduct } from "../api/liveProduct";

export default function LiveAddForm() {
    const accessToken = useSelector(
        (state: RootState) => state.user.accessToken
    );
    const navigate = useNavigate();
    const [isSelected, isSelectedState] = useState(false);
    const [selectedProductId, setSelectedProductId] = useState<
        Map<number, liveProductPrice>
    >(new Map());
    const [products, setProducts] = useState<Array<ItemDetailInterface>>([]);
    const [mainProductId, setMainProductId] = useState<number>(0);
    const [isOpen, setIsOpen] = useState(false);
    const [page, setPage] = useState<number>(0);
    const size = 5;

    const [title, setTitle] = useState("");
    const [startDate, setStartDate] = useState("");
    const [priceEndDate, setPriceEndDate] = useState("");
    const [faqSetting, setFaqSetting] = useState(false);
    const [ttsSetting, setTtsSetting] = useState(false);
    const [memo, setMemo] = useState("");

    const onSetSelected = (x: boolean): void => {
        isSelectedState(x);
    };

    async function onSubmit(event: React.SyntheticEvent): Promise<void> {
        event.preventDefault();
        const now_date = new Date();
        let start_date = new Date(startDate);
        const end_date = new Date(priceEndDate);
        let now_broadcast = false;
        let liveId = 0;

        if (startDate === "" || now_date > start_date) {
            // 시작 시간을 지정하지 않으면 지금으로 설정
            start_date = now_date;
            now_broadcast = true;
        }
        if (priceEndDate === "" || now_date > end_date) {
            // 할인 종료 시간은 필수로 설정하도록 유도
            alert("할인 종료 시간을 설정해주세요");
            return;
        }

        const liveReservationData: broadcastInfo = {
            accessToken,
            broadcastTitle: title,
            content: "라이브 방송",
            script: memo,
            ttsSetting,
            chatbotSetting: faqSetting,
            broadcastStartDate: start_date.toISOString(),
        };

        liveId = await reserveLive(liveReservationData)
            .catch((error) => {
                console.log("LiveAddForm onSubmit reserveLive Error");
                throw error;
            })
            .then((res) => {
                console.log("LiveAddForm onSubmit reserveLive res");
                console.log(res);
                console.log(res.data.liveBroadcastId);
                return res.data.liveBroadcastId;
            });

        if (liveId === 0 || liveId === undefined) {
            alert("라이브 아이디가 0입니다.");
            console.log(liveId);
            return;
        } else {
            console.log("LiveAddForm onSubmit liveId");
            console.log(liveId);
        }

        const liveProductArray = filterLiveProduct(
            liveId,
            start_date.toISOString().split("T")[0],
            end_date.toISOString().split("T")[0]
        );
        // 라이브 상품 등록
        postLiveProduct(liveProductArray, accessToken).catch((error) => {
            console.log("LiveAddForm onSubmit postLiveProduct Error");
            throw error;
        });

        // 시작 시간이 지금보다 이전이면 방송 화면으로 이동
        if (now_broadcast) {
            navigate(`/v1/broadcast/${liveId}`);
        } else {
            navigate("/v1/seller");
        }
    }

    function filterLiveProduct(
        liveId: number,
        start_date: string,
        end_date: string
    ): Array<liveProduct> {
        const temp = Array.from(selectedProductId);
        let seq = 0;
        if (mainProductId === 0) {
            setMainProductId(temp[0][0]);
        }
        const result = temp.map((x) => {
            return {
                productId: x[0],
                liveId,
                liveFlatPrice: x[1].price,
                liveRatePrice: x[1].discount,
                livePriceStartDate: start_date,
                livePriceEndDate: end_date,
                mainProductSetting: x[0] === mainProductId,
                seq: seq++,
            };
        });
        return result;
    }

    function onOpen() {
        setIsOpen(true);
    }

    function handleCancel() {
        navigate("/v1/main");
    }

    return (
        <>
            <Container maxW={"container.xl"} p={"3rem"}>
                <Center>
                    <Text as={"b"} fontSize={"5xl"}>
                        라이브 등록
                    </Text>
                </Center>
                <Center p={"1rem"} display={"block"}>
                    <Box p={"2rem"}>
                        <Text fontSize={"xl"} as={"b"}>
                            라이브 제목
                        </Text>
                        <FormControl
                            mt={"1rem"}
                            variant="floating"
                            id="first-name"
                            isRequired
                            isInvalid
                        >
                            <Input
                                placeholder=" "
                                value={title}
                                onChange={(e) => setTitle(e.target.value)}
                            />
                            <FormLabel>제목을 입력해주세요</FormLabel>
                        </FormControl>
                    </Box>
                    <Box></Box>
                    <Box p={"2rem"}>
                        <Text fontSize={"xl"} as={"b"}>
                            라이브 시작 시간
                        </Text>
                        <FormControl isRequired>
                            <Input
                                mt={"1rem"}
                                placeholder="Select Date and Time"
                                size="md"
                                type="datetime-local"
                                value={startDate}
                                onChange={(e) => setStartDate(e.target.value)}
                            />
                        </FormControl>
                    </Box>

                    <Box p={"3rem"}>
                        <Box
                            minHeight={"initial"}
                            maxH={"80vh"}
                            borderWidth={"4px"}
                            borderRadius={"30px"}
                        >
                            {/* 나머지는 페이지네이션으로 넘겨라! */}

                            {isSelected ? (
                                <Box
                                    maxH={"100%"}
                                    minH={"40rem"}
                                    display="flex"
                                    flexDirection="column"
                                >
                                    <AddGoods
                                        isSelected={isSelected}
                                        isSelectedState={onSetSelected}
                                        products={products}
                                        setProducts={setProducts}
                                        selectedProductId={selectedProductId}
                                        setSelectedProductId={
                                            setSelectedProductId
                                        }
                                        mainProductId={mainProductId}
                                        setMainProductId={setMainProductId}
                                        setIsOpen={setIsOpen}
                                    />
                                </Box>
                            ) : (
                                <Center maxH={"100%"} minH={"40rem"}>
                                    <Button
                                        colorScheme="teal"
                                        variant="link"
                                        onClick={onOpen}
                                    >
                                        <Text as={"b"} fontSize={"3xl"}>
                                            라이브 할 상품 클릭
                                        </Text>
                                    </Button>
                                </Center>
                            )}
                            <LiveItemAdd
                                isSelected={isSelected}
                                isSelectedState={onSetSelected}
                                products={products}
                                setProducts={setProducts}
                                selectedProductId={selectedProductId}
                                setSelectedProductId={setSelectedProductId}
                                page={page}
                                setPage={setPage}
                                size={size}
                                isOpen={isOpen}
                                setIsOpen={setIsOpen}
                            />
                        </Box>
                    </Box>

                    <Box p={"2rem"}>
                        <Text fontSize={"xl"} as={"b"}>
                            라이브 할인이 끝나는 시간을 설정해주세요
                        </Text>
                        <FormControl isRequired>
                            <Input
                                mt={"1rem"}
                                placeholder="Select Date and Time"
                                size="md"
                                type="datetime-local"
                                value={priceEndDate}
                                onChange={(e) =>
                                    setPriceEndDate(e.target.value)
                                }
                            />
                        </FormControl>
                    </Box>

                    <Flex>
                        <Box p={"2rem"}>
                            <Text fontSize={"xl"} as={"b"}>
                                자주 묻는 질문 설정 (챗봇)
                            </Text>
                            <Switch
                                ml={"2rem"}
                                size={"lg"}
                                isChecked={faqSetting}
                                onChange={(e) =>
                                    setFaqSetting(e.target.checked)
                                }
                            />
                        </Box>
                        <Box p={"2rem"}>
                            {/* <Text fontSize={"xl"} as={"b"}>
                                채팅을 자동으로 읽어주기 설정
                            </Text>
                            <Switch
                                ml={"2rem"}
                                size={"lg"}
                                isChecked={ttsSetting}
                                onChange={(e) =>
                                    setTtsSetting(e.target.checked)
                                }
                            /> */}
                        </Box>
                    </Flex>

                    <Box p={"2rem"}>
                        <Text as={"b"} fontSize={"2xl"}>
                            중요한 메모 및 대본
                        </Text>
                        <Textarea
                            h={"10rem"}
                            placeholder="스크립트를 작성해주세요"
                            value={memo}
                            onChange={(e) => setMemo(e.target.value)}
                        ></Textarea>
                    </Box>

                    <Center mt={"1rem"}>
                        <Button
                            onClick={onSubmit}
                            bgColor={"themeGreen.500"}
                            color="white"
                            mr={3}
                        >
                            등록
                        </Button>
                        <Button
                            bgColor={"themeRed.500"}
                            color="white"
                            onClick={handleCancel}
                        >
                            취소
                        </Button>
                    </Center>
                </Center>
            </Container>
        </>
    );
}
