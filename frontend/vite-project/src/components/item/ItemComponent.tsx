import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Box, Flex } from "@chakra-ui/react";
import Goods from "./ItemListComponent";
import ItemCategory from "./ItemCategory";
import { ItemListFetch } from "../../api/Itemlist";
import { ItemWholeFetchInterface } from "../../types/DataTypes";

export default function ItemComponent() {
    const [category, setCategory] = useState<number>(0);
    const [categorylist, setCategorylist] = useState<number[]>([0]);
    const [dummylist, setDummylist] = useState<ItemWholeFetchInterface[]>([]);
    const [filteredList, setFilteredDummylist] = useState<
        ItemWholeFetchInterface[]
    >([]);
    const { currentpage } = useParams<{ currentpage: string }>();
    const [trigger, setTrigger] = useState(false);

    useEffect(() => {
        ItemListFetch({ page: Number(currentpage), size: 16 }).then((res) => {
            setDummylist(res.list || []);
        });
    }, [currentpage]);

    useEffect(() => {
        
        const newFilteredList =
            categorylist.length > 0
                ? dummylist.filter((item) =>
                      categorylist.includes(item.categoryId)
                  )
                : dummylist;
        setFilteredDummylist(newFilteredList);
    }, [dummylist, categorylist]);

    useEffect(() => {
        setCategorylist((prev) =>
            prev.includes(category)
                ? prev.filter((id) => id !== category)
                : [...prev, category]
        );
    }, [category, trigger]);

    return (
        <Flex direction={"column"} width={"100%"}>
            <Box mb={"3rem"} textAlign={"right"}>
                <ItemCategory
                    setCategory={setCategory}
                    setTrigger={setTrigger}
                    trigger={trigger}
                    categorylist={categorylist}
                />
            </Box>
            <Flex wrap="wrap" gap={"4rem"} w={"100%"}>
                {filteredList.map((data) => (
                    <Flex
                        key={data.productId}
                        w="calc(25% - 3rem)"
                        mb={"3rem"}
                        justifyContent={"center"}
                    >
                        <Goods
                            id={data.productId}
                            profile={data.sellerProfile}
                            img={data.imgSrc}
                            title={data.productName}
                            price={data.price}
                            sellerId={data.sellerId}
                        />
                    </Flex>
                ))}
            </Flex>
        </Flex>
    );
}
