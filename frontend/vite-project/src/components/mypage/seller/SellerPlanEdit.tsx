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
import { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { RootState } from "../../../redux/stores/store";
import LiveItemAdd from "../../broadcast/LiveItemAdd";
import AddGoods from "./PlanEditAddGoods";
import {
    ItemDetailInterface,
    editbroadcastInfo,
    liveProduct,
    liveProductPrice,
    LiveProductAll
} from "../../../types/DataTypes";
import { editLivePlanAPI, getLiveDetailAPI } from "../../../api/openVidu";
import { postLiveProduct, getLiveProduct } from "../../../api/liveProduct";

interface broadcastDetailInfo {
    broadcastTitle: string;
    content: string;
    script: string;
    ttsSetting: boolean;
    chatbotSetting: boolean;
    broadcastStartDate: string;
    broadcastEndDate: string;
}

export default function LivePlanEditForm() {
    
    const { broadcastId } = useParams<{ broadcastId: string }>();
    const broadcastIdNumber = Number(broadcastId);
    const accessToken = useSelector((state: RootState) => state.user.accessToken);
    const navigate = useNavigate();
    
    const [title, setTitle] = useState("");
    const [memo, setMemo] = useState("");
    const [ttsSetting, setTtsSetting] = useState(false);
    const [faqSetting, setFaqSetting] = useState(false);
    const [startDate, setStartDate] = useState("");
    const [priceEndDate, setPriceEndDate] = useState("");
    const [isSelected, setSelected] = useState(false);
    const [selectedProductId, setSelectedProductId] = useState<Map<number, liveProductPrice>>(new Map());
    const [products, setProducts] = useState<Array<ItemDetailInterface>>([]);
    const [mainProductId, setMainProductId] = useState<number>(0);
    const [isOpen, setIsOpen] = useState(false);
    const [planDetail, setPlanDetail] = useState<broadcastDetailInfo | null>(null);
    const [page, setPage] = useState<number>(0);
    const size = 5;
    const [ liveproducts, setLiveproducts ] = useState<LiveProductAll[]>([])

    const onSetSelected = (x: boolean): void => {
        setSelected(x);
    };

    function addHoursToUTC(utcString: string | null): string | null {
        if (!utcString) {
            return null;
        }
        const [datePart, timePart] = utcString.split('T');
        const [hour, minute, second] = timePart.split(':');
        const newHour = (parseInt(hour) + 9) % 24;
        const newTimePart = `${newHour.toString().padStart(2, '0')}:${minute}`;
        const newUTCString = `${datePart}T${newTimePart}`;
        return newUTCString.split('.')[0];
    }

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await getLiveDetailAPI({ broadcastId: broadcastIdNumber }, accessToken);
                console.log(response)
                setPlanDetail(response);
                setTitle(response?.broadcastTitle || "");
                setMemo(response?.script || "");
                setTtsSetting(response?.ttsSetting || false);
                setFaqSetting(response?.chatbotSetting || false);
                const koreanStartDate = addHoursToUTC(response?.broadcastStartDate || "");
                if (koreanStartDate !== null) {
                    setStartDate(koreanStartDate);
                }
                setPriceEndDate(response?.broadcastEndDate || "");
            } catch (error) {
                console.error("Error fetching live detail:", error);
            }
        };
        fetchData();
    }, [accessToken, broadcastIdNumber]);
console.log(broadcastIdNumber)

    useEffect(() => {
        const fetchData = async () => {
            const response = await getLiveProduct({ "live-id": broadcastIdNumber }, accessToken)
            setLiveproducts(response.list)
        };
        fetchData();
    }, [])
    console.log(liveproducts)

    useEffect(() => {
        if (liveproducts.length > 0) {
            setSelected(true);
        }
    }, [liveproducts])

    async function onSubmit(event: React.SyntheticEvent): Promise<void> {
        event.preventDefault();

        const now_date = new Date();
        const start_date = new Date(startDate);
        const end_date = new Date(priceEndDate);

        if (priceEndDate === "" || now_date > end_date) {
            // 할인 종료 시간은 필수로 설정하도록 유도
            alert("할인 종료 시간을 설정해주세요");
            return;
        }
        
        const liveEditData: editbroadcastInfo = {
            broadcastId: broadcastIdNumber,
            accessToken,
            broadcastTitle: title,
            content: "라이브 방송",
            script: memo,
            ttsSetting,
            chatbotSetting: faqSetting,
            broadcastStartDate: start_date.toISOString(),
        };

        console.log(liveEditData)
        
        const liveProductArray = filterLiveProduct(
            0,
            start_date.toISOString().split("T")[0],
            end_date.toISOString().split("T")[0]
        );
        // 라이브 상품 등록
        postLiveProduct(liveProductArray, accessToken).catch((error) => {
            throw error;
        });

        editLivePlanAPI({editbroadcastInfo: liveEditData}, accessToken)
        navigate("/v1/seller");
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
                        라이브 수정
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
                                placeholder={planDetail?.broadcastTitle || ""}
                                value={title || ""}
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
                                placeholder={planDetail?.broadcastStartDate || ""}
                                size="md"
                                type="datetime-local"
                                value={startDate || ""}
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
                                        liveproducts={liveproducts}
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
                        {/* <Box p={"2rem"}>
                            <Text fontSize={"xl"} as={"b"}>
                                채팅을 자동으로 읽어주기 설정
                            </Text>
                            <Switch
                                ml={"2rem"}
                                size={"lg"}
                                isChecked={ttsSetting}
                                onChange={(e) =>
                                    setTtsSetting(e.target.checked)
                                }
                            />
                        </Box> */}
                    </Flex>

                    <Box p={"2rem"}>
                        <Text as={"b"} fontSize={"2xl"}>
                            중요한 메모 및 대본
                        </Text>
                        <Textarea
                            h={"10rem"}
                            placeholder={planDetail?.script || ""}
                            value={memo || ""}
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
