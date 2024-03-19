import { Box, Flex, Center } from "@chakra-ui/layout";
import { Avatar, Button, List, ListItem, Breadcrumb, BreadcrumbItem, BreadcrumbLink } from "@chakra-ui/react";
import { ChevronRightIcon } from "@chakra-ui/icons";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { useSelector } from "react-redux";

import { RootState } from "../redux/stores/store";
import PlanList from "../components/mypage/seller/SellerPlanList";
import LiveList from "../components/mypage/seller/SellerLiveList";
import Items from "../components/mypage/seller/SellerItemList";
import Follwers from "../components/mypage/seller/SellerFollwers";
import Qna from "../components/mypage/seller/SellerQna";
import Chatbot from "../components/mypage/seller/ChatbotTab";
import BanUser from "../components/mypage/seller/SellerBanUser";
import BlockWord from "../components/mypage/seller/SellerBlockWord";
import "../css/SellerPage.css";
// import { TestComponent } from "../components/mypage/TEST";

export default function SellerPage() {
    const navigate = useNavigate();
    const user = useSelector((state: RootState) => state.user);
    const [ tab, setTab ] = useState(0);
    const [categories, setCategories] = useState([
        { id: 0, title: "예고한 라이브", isSelected: true, component: <PlanList />},
        { id: 1, title: "완료한 라이브", isSelected: false, component: <LiveList />},
        { id: 2, title: "등록한 상품 목록", isSelected: false, component: <Items />},
        { id: 3, title: "상품 문의 확인", isSelected: false, component: <Qna />},
        { id: 4, title: "팔로워 목록", isSelected: false, component: <Follwers />},
        { id: 5, title: "차단한 사용자 목록", isSelected: false, component: <BanUser />},
        { id: 6, title: "챗봇 설정", isSelected: false, component: <Chatbot />},
        { id: 7, title: "금지어 설정", isSelected: false, component: <BlockWord />},
        // { id: 8, title: "테스트1", isSelected: false, component: <TestComponent title="테스트1"/>},
        // { id: 9, title: "테스트2", isSelected: false, component: <TestComponent title="테스트2"/>},
    ]);

    const changeSelect = (e: React.MouseEvent<HTMLLIElement, MouseEvent>) => {
        const selectedIndex = parseInt(e.currentTarget.getAttribute("data-index") || "");
        setTab(selectedIndex);
        setCategories(categories.map((item, index) => {
            return {
                ...item,
                isSelected: index === selectedIndex,
            };
        }));
    }

    const categoryList = categories.map((item, index) => {
        return (
            <ListItem
                key={index}
                value={index}
                padding=".5rem 1rem"    
                cursor="pointer"
                className={item.isSelected ? "active" : ""}
                onClick={(e) => changeSelect(e)}
                data-index={item.id}
            >{item.title}
            </ListItem>
        );
    });    

    return (
        <Box minH="100vh" mb="10">

            <Center fontFamily="GmkBold" fontSize={{ base: "4rem", md: "5rem", lg: "6rem" }} color={"themeRed.500"}>
                판매자 마이페이지
            </Center>
            
            
            <Flex m="auto" overflow="hidden" rounded="lg" w="85vw" minH={{ base:"85vh", lg: "85vh"}}>
                <Flex m="auto" direction={{ base: "column", lg: "row"}} rounded="lg" w="80vw" maxH={{ base:"auto"}} px="2">
                    <Box w={{ base: "100%", lg: "25%" }} pr="4" >
                        <Box w="full" bg="white" rounded="lg" overflow="hidden">
                            <Flex direction="column" align="center" py="6">
                                <Button
                                    mb="4"
                                    onClick={() => {
                                        navigate("/v1/buyer/");
                                    }}
                                >구매자 정보 보기
                                </Button>

                                <Avatar mt="4" size="xl" src={user.profileImg} />
                                
                                <Button
                                    mt="4"
                                    onClick={() => {
                                    navigate("/v1/userinfo");
                                    }}
                                >계정정보수정
                                </Button>

                                <Box w="full" mt="6" pt="6">
                                    <List spacing="4" h="100%">
                                        {categoryList}
                                    </List>
                                </Box>
                            </Flex>
                        </Box>
                    </Box>

                    <Box w={{ base: "100%", lg: "75%" }} bg="white" p="5" rounded="lg" className="custom-scrollbar">
                        <Flex direction="column" align="center" h="full">
                            
                            <Breadcrumb mb="10" spacing='8px' separator={<ChevronRightIcon color='gray.500' />}>
                                <BreadcrumbItem>
                                    <BreadcrumbLink>마이페이지</BreadcrumbLink>
                                </BreadcrumbItem>

                                <BreadcrumbItem isCurrentPage>
                                    <BreadcrumbLink>{categories[tab].title}</BreadcrumbLink>
                                </BreadcrumbItem>
                            </Breadcrumb>

                            {categories[tab].component}
                        </Flex>
                    </Box>
                </Flex>
            </Flex>
        </Box>
    );
}
