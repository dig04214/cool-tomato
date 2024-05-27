import { Box, Text, Flex } from "@chakra-ui/layout";
import { Avatar, Button } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../../redux/stores/store";
import { getBlockUserAPI, deleteBlockUserAPI } from "../../../api/chatblock";
import { followerItem } from "../../../types/DataTypes";

function BanUser() {
    const accessToken = useSelector((state: RootState) => { return state.user.accessToken })
    const [ blockList, setBlockList ] = useState<Array<followerItem>>([])

    useEffect(() => {
        getBlockUserAPI(accessToken).then((response) => {
            setBlockList(response)
        }).catch((error) => {
            console.log(error)
        })
    }, [])

    const onClickUnBlock = (blockedId: number) => {
        deleteBlockUserAPI(blockedId, accessToken).then(() => {
            setBlockList(prevList => prevList.filter(user => user.userId !== blockedId));
        });
    };

    return (
        <>
        { blockList.length > 0 ? 
            blockList.map((item) => (
                <Flex justifyContent="space-between" w="90%" h="full" key={item.id}>
                    <Flex>
                        <Avatar src={item.profileImg} />
                        <Box ml='3'>
                            <Text fontWeight='bold'>
                                {item.nickname}
                            </Text>
                        </Box>
                    </Flex>
            
                    <Button 
                        onClick={() => onClickUnBlock(item.userId)} 
                        color="white" 
                        backgroundColor="themeGreen.500" 
                        _hover={{ backgroundColor: "white", color: "red" }}>
                        차단해제
                    </Button>
                </Flex>
                )
            ) : (
                <Flex mt="20" flexDir="column" mb="10">
                    <Text fontSize='5xl' color="gray.500" mb="5">차단한 사람이 없습니다</Text>
                </Flex>
            )
        }
    </>

        

    )
}

export default BanUser
