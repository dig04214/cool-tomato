import { Box, Flex, Text } from "@chakra-ui/layout";
import { Button } from "@chakra-ui/react";
import { CalendarIcon } from "@chakra-ui/icons";
import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../../redux/stores/store";
import { getLivePlanAPI, deleteLivePlanAPI } from "../../../api/openVidu";
import PlanItems from "./SellerPlanItems";

interface broadcastInfo {
    liveBroadcastId: number;
    broadcastTitle: string;
    nickName: string;
    viewCount: number;
    sellerId: number;
    broadcastStatus: boolean;
}

function PlanList() {
    const navigate = useNavigate();
    const accessToken = useSelector((state: RootState) => state.user.accessToken);
    const [ livePlans, setLivePlans ] = useState<Array<broadcastInfo>>([]);

    useEffect(() => {
        const fetchData = async () => {
            const response = await getLivePlanAPI({page:0, size:10}, accessToken);
            setLivePlans(response.broadcastInfoList);
        };
        fetchData();
    }, [accessToken]);

    const deleteLivePlan = async (liveBroadcastId: number) => {
        try {
            await deleteLivePlanAPI({ broadcastId: liveBroadcastId }, accessToken);
            setLivePlans(prevLivePlans => prevLivePlans.filter(plan => plan.liveBroadcastId !== liveBroadcastId));
        } catch (error) {
            console.error("Error deleting live plan:", error);
        }
    };

    const liveplanData = livePlans.map((item, index) => (
        <PlanItems key={index} plans={item} onDelete={deleteLivePlan} />
    ));

    return (
        <>
        <Box flexDirection="column" w="90%" h="full" mb="10">
            <Flex>
                <Button leftIcon={<CalendarIcon />} mb="2" size="sm" colorScheme='red' variant='solid'
                onClick={() => {
                    navigate("/v1/live/form");
                }}>
                    라이브등록
                </Button>
                <Box
                        color='gray.500'
                        fontWeight='semibold'
                        letterSpacing='wide'
                        mt="2"
                        fontSize='xs'
                        textTransform='uppercase'
                        ml='2'
                        >예약라이브는 최대 5개까지 등록할 수 있습니다
                </Box>
            </Flex>
            {livePlans.length ? liveplanData : 
                <Flex mt="20" flexDir="column">
                    <Text fontSize='5xl' color="gray.500" mb="5">등록된 라이브 예약이 없습니다!</Text>
                    <Button colorScheme="themeGreen" onClick={() => navigate("/v1/live/form")}>라이브 예약하러 가기</Button>
                </Flex>
            }
        </Box>
        </>
    )
}

export default PlanList
