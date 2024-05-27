import { ChevronLeftIcon, ChevronRightIcon } from "@chakra-ui/icons";
import {
    Breadcrumb,
    BreadcrumbItem,
    BreadcrumbLink,
    Flex,
} from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { ItemListFetch } from "../../api/Itemlist";

export default function ItemListpagenation() {
    const [totalPage, setTotalPage] = useState(0);
    const pages = Array.from({ length: totalPage }, (_, i) => i);

    useEffect(() => {
        ItemListFetch({ page: 0, size: 16 }).then((res) => {
            setTotalPage(Math.ceil(res.totalCount / 16));
        });
    });

    // const prevPage = () => {
    //     const { currentpage } = useParams();
    //     Number(cur_page) !== 0 ? (
    //         <Link to={`/v1/items/list/${Number(cur_page) - 1}`} />
    //     ) : (
    //         <Link to={`/v1/items/list/${Number(cur_page)}`} />
    //     );
    // };

    return (
        <Flex direction={"row"}>
            <ChevronLeftIcon onClick={() => {}} />
            <Breadcrumb separator={""} ml={"1rem"} mr={"1rem"}>
                {pages.map((i) => (
                    <BreadcrumbItem ml={"0.5rem"} mr={"0.5rem"} key={i}>
                        <BreadcrumbLink as={Link} to={`/v1/items/list/${i}`}>
                            {i + 1}
                        </BreadcrumbLink>
                    </BreadcrumbItem>
                ))}
            </Breadcrumb>
            <ChevronRightIcon onClick={() => {}} />
        </Flex>
    );
}
