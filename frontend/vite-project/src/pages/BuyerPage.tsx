import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Box, Flex, Center } from "@chakra-ui/layout";
import { Button, Avatar, List, ListItem, Breadcrumb, BreadcrumbItem, BreadcrumbLink } from "@chakra-ui/react";
import { ChevronRightIcon } from "@chakra-ui/icons";
import { useSelector } from "react-redux";
import { RootState } from "../redux/stores/store";
import "../css/SellerPage.css"

import Recent from "../components/mypage/buyer/BuyerRecent";
import Following from "../components/mypage/buyer/BuyerFollowing";
import Reviews from "../components/mypage/buyer/BuyerReviews";
import Reviewed from "../components/mypage/buyer/BuyerReviewed";
import Question from "../components/mypage/buyer/BuyerQuestion";
import Sellerform from "../components/mypage/sellerform";

export default function BuyerPage() {
    const user = useSelector((state: RootState) => state.user);
    const navigate = useNavigate();
    const [ tab, setTab ] = useState(0);
    const [ categoryTabs, setCategoryTabs ] = useState([
        { id: 0, isSelected: true , name: '최근 본 상품', component: <Recent /> },
        { id: 1, isSelected: false , name: '팔로잉 목록', component: <Following /> },
        { id: 2, isSelected: false , name: '작성 가능한 리뷰', component: <Reviews /> },
        { id: 3, isSelected: false , name: '작성한 리뷰', component: <Reviewed /> },
        { id: 4, isSelected: false , name: '내가 한 문의', component: <Question /> },
        { id: 5, isSelected: false , name: '판매자 신청', component: <Sellerform /> },
    ]);

    const changeSelect = (e: React.MouseEvent<HTMLLIElement, MouseEvent>) => {
        const selectedIndex = parseInt(e.currentTarget.getAttribute('data-index') || "0");
        setTab(selectedIndex);
        setCategoryTabs(categoryTabs.map((item, index) => {
            return {
              ...item,
              isSelected: index === selectedIndex,
            };
          })
        );
    }

    return (
        <Box minH="100vh" mb="10">

            <Center fontFamily="GmkBold" fontSize={{ base: "4rem", md: "5rem", lg: "6rem" }} color={"themeGreen.500"}>
                마이페이지
            </Center>

            <Flex m="auto" overflow="hidden" rounded="lg" w="85vw" minH={{ base:"85vh", lg: "85vh"}}>
                <Flex direction={{ base: "column", lg: "row"}} rounded="lg" w="80vw" maxH={{ base:"auto"}} px="2">                    
                    <Box w={{ base: "100%", lg: "25%" }} pr="4">
                        <Box w="full" bg="white" rounded="lg" overflow="hidden">
                            <Flex direction="column" align="center" py="6">

                                {user.auth === 'SELLER' && (
                                    <Button
                                        mb="4"
                                        onClick={() => {
                                            navigate("/v1/seller/");
                                        }}
                                    >
                                    판매자 정보 보기
                                    </Button>
                                )}
                                <Avatar mt="4" size="xl" src={user.profileImg} />

                                <Button
                                    mt="4"
                                    onClick={() => {
                                    navigate("/v1/userinfo");
                                    }}
                                >계정정보수정
                                </Button>

                                <Box w="full" mt="6" pt="6">
                                    <List spacing="4">
                                    {categoryTabs.map((category) => (
                                        <ListItem
                                        key={category.id}
                                        value={category.id}
                                        padding=".5rem 1rem"
                                        className={category.isSelected ? "active" : ""}
                                        onClick={(e) => changeSelect(e)}
                                        _hover={{ color: "themeRed.500", cursor:"pointer" }}
                                        _active={{ color: "themeRed.500", bg: "themeRed.100" }}
                                        data-index={category.id}
                                        >{category.name}
                                        </ListItem>
                                    ))}
                                    </List>
                                </Box>
                            </Flex>
                        </Box>
                    </Box>

                    <Box w={{ base: "100%", lg: "75%" }} bg="white" rounded="lg">
                        <Flex direction="column" justify="center" align="center" h="full">
                            <Breadcrumb mb="10" spacing='8px' separator={<ChevronRightIcon color='gray.500' />}>
                                <BreadcrumbItem>
                                    <BreadcrumbLink>마이페이지</BreadcrumbLink>
                                </BreadcrumbItem>

                                <BreadcrumbItem isCurrentPage>
                                    <BreadcrumbLink>{categoryTabs[tab].name}</BreadcrumbLink>
                                </BreadcrumbItem>
                            </Breadcrumb>
                            {categoryTabs[tab].component}
                        </Flex>
                    </Box>
                </Flex>
            </Flex>
        </Box>
    );
}
