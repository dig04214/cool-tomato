import { Box, Grid, GridItem, Text } from "@chakra-ui/layout";
import { Card, CardBody } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { ItemListFetch } from "../../api/Itemlist";
import { ItemDetailInterface } from "../../types/DataTypes";

export default function Recommends() {
    const navigate = useNavigate();
    const [ recommendList, setRecommendList ] = useState<ItemDetailInterface[]>([])
    useEffect(() => {
        const fetchData = async () => {
            const response = await ItemListFetch({ page: 0, size: 4 });
            setRecommendList(response.list)
        };
        fetchData();
    }, []);

    return (
        <Box>
            <Grid templateColumns="repeat(2, 1fr)" gap={4}>
                {recommendList.map((item) => (
                    <GridItem 
                        key={item.productId} 
                        onClick={()=>{navigate(`/v1/items/detail/${item.productId}`)}}
                        // _hover={{ cursor: "pointer", border: "2px", borderRadius:"5", borderColor: "themeRed.500" }}
                    >
                        <Card>
                            <CardBody p={6}
                            _hover={{ cursor: "pointer", border: "2px", borderRadius:"5", borderColor: "themeRed.500" }}
                            >
                            <img
                                alt={`Product ${item} Image`}
                                height="200"
                                style={{
                                aspectRatio: "200/200",
                                objectFit: "cover",
                                }}
                                width="200"
                                src={item.imgSrc}
                            />
                            <Box w="200px">
                            <Text fontSize="lg" fontWeight="semibold" m={2}>
                                {item.productName}
                            </Text>
                            </Box>
                            </CardBody>
                        </Card>
                    </GridItem>
                ))}
            </Grid>
        </Box>

    )
}
