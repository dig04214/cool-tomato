import { Flex, Box, Text, Button, Avatar } from "@chakra-ui/react"
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../redux/stores/store";
import { followSellerAPI, unfollowSellerAPI, checkFollowAPI } from "../../api/user";
import { SellerInfo } from "../../types/DataTypes";
import { useNavigate } from "react-router-dom";

export default function SellerHeader( { sellerId , sellerInfo, productsCount } : {sellerId : number, sellerInfo: SellerInfo, productsCount: number}) {
    const user = useSelector((state: RootState) => state.user);
    const count = productsCount
    const navigate = useNavigate();
    const [ following, setFollowing ] = useState(false)
    const [ isLoading, setIsLoading ] = useState(false)
    const [ followerCount, setFollowerCount ] = useState(0);
    const [ daysSinceJoin, setDaysSinceJoin ] = useState(0); // 추가

    // sellerInfo.joinDate와 오늘 날짜와의 차이 계산 함수
    const calculateDaysSinceJoin = () => {
        const today = new Date();
        const joinDate = new Date(sellerInfo.joinDate);
        const timeDiff = Math.abs(today.getTime() - joinDate.getTime());
        const days = Math.ceil(timeDiff / (1000 * 3600 * 24));
        setDaysSinceJoin(days);
    };


    const onClickFollow = () => {
        followSellerAPI(sellerId, true, user.accessToken).then((result) => {
            if (result === 1) {
                setFollowing(true)
                setFollowerCount((followerCount) => followerCount + 1);
            } else {
                // sellerId: number, alarmSetting: boolean, accessToken: string 
            }
        });
    };

    const onClickUnfollow = () => {
        unfollowSellerAPI(sellerId, user.accessToken).then((result) => {
            if (result === 1) {
                setFollowing(false)
                setFollowerCount((followerCount) => followerCount - 1);
            } else {
                // sellerId: number, alarmSetting: boolean, accessToken: string 
            }
        });
    };

    useEffect(() => {
      if (sellerInfo.followerCount != 0) {
        setFollowerCount(sellerInfo.followerCount);
        setIsLoading(true);
      }
      calculateDaysSinceJoin();
    }, []);

    // 초기에 팔로잉 여부 조회 함수: 왜안먹지?????
    useEffect(() => {
        checkFollowAPI(sellerId, user.accessToken).then((result) => {
            if (result === 1) {
                setFollowing(true)
            } else {
                setFollowing(false)
            }
        })
    }, [following])

    return (
        <>
            <Flex align="center" mb={4}>

                <Avatar size="xl" name="Username" src={sellerInfo.profileImg} />

                <Box ml={5} mr={10}>
                    <Text fontSize="3xl" fontWeight="bold">
                        {sellerInfo.nickname}
                    </Text>
                </Box>
                <Box flex="1" textAlign="center" mr="5" w="7rem">
                    <Text fontWeight="bold">판매상품수</Text>
                    <Text>{count}</Text>
                </Box>
                <Box flex="1" textAlign="center" mr="5">
                    <Text fontWeight="bold">팔로워</Text>
                    {
                        isLoading ?
                        <Text>{followerCount}</Text>
                        : 
                        <Text>{sellerInfo.followerCount}</Text>
                    }
                </Box>

                {user.accessToken ? (
                    // user.accessToken이 있을 때
                    !following ? (
                        <Button onClick={onClickFollow} _hover={{ backgroundColor: "themeGreen.500", color: "white" }}>팔로우하기</Button>
                    ) : (
                        <Button onClick={onClickUnfollow} color="white" backgroundColor="themeGreen.500" _hover={{ backgroundColor: "white", color: "red" }}>팔로잉중</Button>    
                    )
                ) : (
                    // user.accessToken이 없을 때
                    <Button onClick={()=>{navigate('/v1/login')}}>로그인하고 판매자 알림받기</Button>
                )}


            </Flex>

            <Text color="gray.500">멋쟁이가 된지 +{daysSinceJoin}일째</Text>
        </>
    )
}
