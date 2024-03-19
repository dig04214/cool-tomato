import { Box, Flex, Text } from "@chakra-ui/layout";
import { Button } from "@chakra-ui/react";
// import { Card, Image, Stack, CardBody, Heading, CardFooter, Button } from "@chakra-ui/react";
// import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function Recent() {
    const navigate = useNavigate();
    // const [ currentSee, setCurrentSee ] = useState([])

    function onclick() {
        navigate(`/v1/items/list/0`);
    }

    // function oneclick() {
    //     // navigate(`/v1/items/detail/${props.sellerItem.productId}`)
    // }

    // const recentData = currentSee.map((item, index) => (
    //     <CardItem key={index} product={item} />
    // ));

    return (
        <Box flexDirection="column" w="90%" h="full" mb="10">
            <Flex flexDir="column" h="full" m="auto">
                {/* {recentData.length ? recentData : 
                <Flex m="auto" flexDir="column">
                    <Text fontSize='5xl' color="gray.500" mb="5">최근 본 상품이 없습니다!</Text>
                    <Button colorScheme="themeGreen" onClick={onclick}>상품 구경하러 가기</Button>
                </Flex>
                } */}
                <Flex m="auto" flexDir="column">
                    <Text fontSize='5xl' color="gray.500" mb="5">최근 본 상품이 없습니다!</Text>
                    <Button colorScheme="themeGreen" onClick={onclick}>상품 구경하러 가기</Button>
                </Flex>
            </Flex>
        </Box>
    );
}

// function CardItem({ product }) {
//     return (
//         <Card
//             direction={{ base: 'column', sm: 'row' }}
//             variant='outline'
//         >
//             <Flex w="100%">
//                 <Image
//                     objectFit='contain'
//                     maxW={{ base: '20%', sm: '100px' }}
//                     src={product.product_img}
//                     alt={product.product_name}
//                 />
//                 <Stack>
//                     <CardBody>
//                         <Heading size='md'>{product.product_name}</Heading>
//                         <Text>가격 : {product.price}</Text>
//                     </CardBody>
//                     <CardFooter>
//                         <Button variant='solid' colorScheme='green'>
//                             {`${product.product_name} 사러가기`}
//                         </Button>
//                     </CardFooter>
//                 </Stack>
//             </Flex>
//         </Card>
//     );
// }