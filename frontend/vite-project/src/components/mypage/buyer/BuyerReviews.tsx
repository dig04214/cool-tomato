import { Box, Text, Flex } from "@chakra-ui/layout";
import { Button } from "@chakra-ui/react";
// import { Image, Button, Card, Stack, CardBody, CardFooter, Heading } from "@chakra-ui/react";
// import { useState } from "react";
import { useNavigate } from "react-router-dom";
// import { useSelector } from "react-redux";
// import { RootState } from "../../../redux/stores/store";

export default function Reviews() {
    const navigate = useNavigate();
    // const user = useSelector((state: RootState) => state.user);
    // const accessToken = user.accessToken;
    // const [reviews] = useState([]);
    // const reviewData = reviews.map((item, index) => (
    //     <CardItem key={index} reviews={item} />
    // ));

    function onclick() {
        navigate(`/v1/items/list/0`);
    }

    // useEffect(() => {
    //     const fetchData = async () => {
    //         try {
    //             const response = await "임의의 API"(accessToken)
    //             setReviews(response.data.list)
    //         } catch (error) {
    //             console.error(error)
    //         }
    //     };
    //     fetchData();
    // }, [])

    return (
        <Box flexDirection="column" w="90%" h="full" mb="10">
            <Flex flexDir="column" h="full" m="auto">
                    {/* {reviewData.length ? reviewData : 
                        <Flex m="auto" flexDir="column">
                            <Text fontSize='5xl' color="gray.500" mb="5">구매한 이력이 없습니다!</Text>
                            <Button colorScheme="themeGreen" onClick={onclick}>상품 구경하러 가기</Button>
                        </Flex>
                    }                     */}
                        <Flex m="auto" flexDir="column">
                            <Text fontSize='5xl' color="gray.500" mb="5">구매한 이력이 없습니다!</Text>
                            <Button colorScheme="themeGreen" onClick={onclick}>상품 구경하러 가기</Button>
                        </Flex>
            </Flex>
        </Box>
    )
}

// function CardItem({ reviews }) {
//     return (
//         <Card
//             direction={{ base: 'column', sm: 'row' }}
//             variant='outline'
//         >
//             <Flex w="100%">
//                 <Image
//                     objectFit='contain'
//                     maxW={{ base: '20%', sm: '100px' }}
//                     src={reviews.product_img}
//                     alt={reviews.product_name}
//                 />
//                 <Stack>
//                     <CardBody>
//                         <Heading size='md'>{reviews.product_name}</Heading>
//                         <Text>가격 : {reviews.price}</Text>
//                     </CardBody>
//                     <CardFooter>
//                         <Button variant='solid' colorScheme='green'>
//                             {`${reviews.product_name} 리뷰쓰러가기`}
//                         </Button>
//                     </CardFooter>
//                 </Stack>
//             </Flex>
//         </Card>
//     );
// }