import { Box, Text, Flex } from "@chakra-ui/layout";
import { Button, Avatar } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { RootState } from "../../../redux/stores/store";
import { getFollowingListAPI, unfollowSellerAPI } from "../../../api/user";
import { followerItem } from "../../../types/DataTypes";

interface UpdateFollowingFunc {
    (userId: number): void;
}

export default function Following() {
    const navigate = useNavigate();
    const user = useSelector((state: RootState) => state.user);
    const accessToken = user.accessToken;
    const [following, setFollowing] = useState<followerItem[]>([]);
    

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await getFollowingListAPI(accessToken)
                if (response.data.follow) {
                  setFollowing(response.data.follow)
                } else {
                  console.error('조회는 성공했는데 팔로잉 목록이 엄서용');
                  setFollowing([]);  // set to empty array as fallback
                }
            } catch (error) {
                console.error(error)
                setFollowing([]);  // set to empty array as fallback
            }
        };
        fetchData();
    }, [])  // Remove 'following' from dependencies

    // 팔로잉 상태 업데이트 함수
    const updateFollowing = (userId: number) => {
        setFollowing(following.filter((item) => item.userId !== userId));
    };
    

    function onclick() { 
        navigate(`/v1/live/list`);
    }

    return (
        <Box flexDirection="column" w="90%" h="full" mb="10">
            <Flex flexDir="column" h="full" m="auto">
                    { following.length ? (
                        following.map((item, index) => (
                        <FollowingItem 
                            key={index} 
                            following={item} 
                            accessToken={accessToken}
                            i={index}
                            updateFollowing={updateFollowing} />)) 
                        ) : (
                        <Flex m="auto" flexDir="column">
                            <Text fontSize='5xl' color="gray.500" mb="5">팔로잉한 사람이 없습니다!</Text>
                            <Button colorScheme="themeGreen" onClick={onclick}>라이브 구경하러 가기</Button>
                        </Flex>
                    )}
            </Flex>
        </Box>
    )
}

function FollowingItem({following, accessToken, updateFollowing} : {following: followerItem, accessToken: string, i:number, updateFollowing: UpdateFollowingFunc}) {
    
    const onClickUnfollow = () => {
        unfollowSellerAPI(following.userId, accessToken).then((result) => {
            if (result === 1) {
                updateFollowing(following.userId);
            } else {
                // sellerId: number, alarmSetting: boolean, accessToken: string 
            }
        });
    };

    return (
        <Flex p="2" m="2" justifyContent="space-between">
            <Flex>
                <Avatar src={following.profileImg} />
                <Box ml='3'>
                    <Text fontWeight='bold'>
                    {following.nickname}
                    </Text>
                    <Text fontSize='sm'>등록한 상태메시지가 없습니다</Text>
                </Box>
            </Flex>

            <Button onClick={onClickUnfollow} color="white" backgroundColor="themeGreen.500" _hover={{ backgroundColor: "white", color: "red" }}>
                팔로잉중</Button>
        </Flex>

    );
}
