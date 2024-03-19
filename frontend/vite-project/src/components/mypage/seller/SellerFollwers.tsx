import { Box, Text, Flex } from "@chakra-ui/layout";
import { Avatar, Badge, Button } from "@chakra-ui/react";
import { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../../redux/stores/store";
import { getFollowerListAPI } from "../../../api/user";
import { followerItem } from "../../../types/DataTypes";
import { useNavigate } from "react-router-dom";

function Followers() {
    const navigate = useNavigate();
    const user = useSelector((state: RootState) => state.user);
    const [followerItem, setFollowerItem] = useState([])

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await getFollowerListAPI(user.accessToken)
                setFollowerItem(response.data.follow)
            } catch (error) {
                console.error(error)
            }
        };
        fetchData();
    }, [])

    return (
        <Box flexDirection="column" w="90%" h="full">

            {followerItem.length > 0
                ? followerItem.map((item, index) => (
                    <FollowersItem key={index} followerItem={item} i={index} />
                ))
                : (
                    <Flex mt="20" flexDir="column" mb="10">
                        <Text fontSize='5xl' color="gray.500" mb="5">나를 팔로우한 사람이 없습니다</Text>
                        <Button colorScheme="themeGreen" onClick={() => navigate("/v1/live/form")}>라이브 예약하러가기</Button>
                    </Flex>
                )
            }

        </Box>
    )
}

export default Followers

function FollowersItem({ followerItem, i }: { followerItem: followerItem, i: number }) {
    // console.log(followerItem)

    return (
        <Flex p="2" m="2">
            <Avatar src={followerItem.profileImg} />
            <Box ml='3'>
                <Text fontWeight='bold'>
                    {followerItem.nickname}
                    {i >= 0 && i <= 2 && (
                        <Badge ml='5' colorScheme='green'>
                            New
                        </Badge>
                    )}
                </Text>
            </Box>
        </Flex>
    )
}