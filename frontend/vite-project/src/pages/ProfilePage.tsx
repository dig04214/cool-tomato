import { Flex } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { getSellerDetailAPI } from "../api/user";
import { ItemListFetch } from "../api/Itemlist";
import { useParams } from "react-router-dom";
import { ItemDetailInterface } from "../types/DataTypes";
import SellerHeader from "../components/sellerprofile/Profileheader";
import SellerPosts from "../components/sellerprofile/ProfilePosts";

function ProfilePage() {
    const { sellerId } = useParams<{ sellerId: string }>();
    const sellerIdNumber = parseInt(sellerId!);
    const [ test, setTest ] = useState(false)
    const [sellerInfo, setSellerInfo] = useState({
        auth : "",
        bitrhday: "",
        followerCount: 0,
        joinDate: "",
        loginId: "",
        nickname: "",
        profileImg: "",
        sellerInfoId: 0,
        sex: "",
        userId: 0
    })
    const [products, setProducts] = useState<ItemDetailInterface[]>([])

    useEffect(() => {
        getSellerDetailAPI(sellerIdNumber)
        .then((response) => {
            setSellerInfo(response.data)
            setTest(true)
        }).catch((error) => {
            console.log(error)
        })

        ItemListFetch({sellerId: sellerIdNumber})
        .then((response) => {
            setProducts(response.list);
        }).catch((error) => {
            console.error(error)
        })
    }, [])

    return (
        <>
            <Flex
                direction={"column"}
                p={"1rem"}
                alignItems={"center"}
                mt={"2rem"}
                mb={"2rem"}
                maxW={"100vw"}
            >
                {
                    test ? 
                    <SellerHeader sellerId={sellerIdNumber} sellerInfo={sellerInfo} productsCount={products.length} />
                    :
                    <h1>loading</h1>
                }
                <SellerPosts products={products} />
            </Flex>
        </>
    );
}

export default ProfilePage;
