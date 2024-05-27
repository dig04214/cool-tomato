import { Text } from "@chakra-ui/layout";
import { Center, Box } from "@chakra-ui/react";
import { sellersMyproductsAPI } from "../../../api/Itemlist";
import { useEffect, useState } from "react";
import { ItemInfo } from "../../../types/DataTypes";
import SellerProductList from "../SellerProductList";
import { useSelector } from "react-redux";
import { RootState } from "../../../redux/stores/store";

export default function ProductList() {
    const [FetchData, setData] = useState<Array<ItemInfo> | undefined>();
    const accessToken = useSelector((state: RootState) => { return state.user.accessToken })

    useEffect(() => {
        sellersMyproductsAPI(0, 4, accessToken).then((res) => {
            
            setData(res.data.list)
        }).catch((err) => {
            console.log(err)
        });
    }, []);

    return (
        <>
            <Center mb={"1.5rem"}>
                <Text fontSize={"4xl"} as={"b"}>
                    상품 목록
                </Text>
            </Center>
            {FetchData ? FetchData.map((res: ItemInfo | undefined, index: number) => (
                <Box key={index}>
                    <SellerProductList
                        img={`${res?.imgSrc}`}
                        price={Number(`${res?.price}`)}
                        title={`${res?.productName}`}
                    />
                </Box>
            )) : (<Text>등록된 상품이 없습니다.</Text>) }


        </>
    );
}
