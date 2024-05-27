import { Box, Text, Flex } from "@chakra-ui/layout";
import { Button } from "@chakra-ui/react";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { RootState } from "../../../redux/stores/store";
import { getEndedLiveAPI } from "../../../api/openVidu";
import LiveItems from "./SellerLiveItems";

function LiveList() {
    const navigate = useNavigate();
    const [livedItem, setLivedItem] = useState([]);
    const accessToken = useSelector(
        (state: RootState) => state.user.accessToken
    );

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await getEndedLiveAPI(
                    { page: 0, size: 15 },
                    accessToken
                );
                if (response) {
                    setLivedItem(response);
                }
            } catch (error) {
                console.error(error);
            }
        };
        fetchData();
    }, []);

    const endedliveData = livedItem.map((item, index) => (
        <LiveItems key={index} lives={item} />
    ));

    return (
        <Box flexDirection="column" w="90%" h="full">
            {livedItem.length ? (
                endedliveData
            ) : (
                <Flex mt="20" flexDir="column">
                    <Text fontSize="5xl" color="gray.500" mb="5">
                        완료한 라이브 방송이 없습니다!
                    </Text>
                    <Button
                        colorScheme="themeGreen"
                        onClick={() => navigate("/v1/live/form")}
                    >
                        라이브 예약하러 가기
                    </Button>
                </Flex>
            )}
        </Box>
    );
}

export default LiveList;
