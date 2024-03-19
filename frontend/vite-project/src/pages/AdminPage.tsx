import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Box, Flex, Center } from "@chakra-ui/layout";
import { Button, Avatar, List, ListItem, Breadcrumb, BreadcrumbItem, BreadcrumbLink } from "@chakra-ui/react";
import { ChevronRightIcon } from "@chakra-ui/icons";
import { useSelector } from "react-redux";
import { RootState } from "../redux/stores/store";
import "../css/SellerPage.css"

import Notice from "../components/mypage/admin/Notice";
import AcceptSeller from "../components/mypage/admin/AcceptSeller";
import ManagingAll from "../components/mypage/admin/ManagingAll";

export default function AdminPage() {
    const user = useSelector((state: RootState) => state.user);
    const navigate = useNavigate();
    const [ boxHeight, setBoxHeight ] = useState("85vh");
    const [ tab, setTab ] = useState(0);
    const [ categoryTabs, setCategoryTabs ] = useState([
        { id: 0, isSelected: true , name: '공지사항 관리', component: <Notice /> },
        { id: 1, isSelected: false , name: '회원전체조회', component: <ManagingAll /> },
        { id: 2, isSelected: false , name: '판매자 신청 조회', component: <AcceptSeller /> },
    ]);

    const changeSelect = (e: React.MouseEvent<HTMLLIElement, MouseEvent>) => {
        const selectedIndex = parseInt(e.currentTarget.getAttribute("data-index") || "");
        setTab(selectedIndex);
        setCategoryTabs(categoryTabs.map((item, index) => {
            return {
                ...item,
                isSelected: index === selectedIndex,
            };
        }));
    }

    useEffect(() => {
            setBoxHeight("80vh"); // 탭 변경 시 초기 높이로 리셋
        }, [tab]);

    return (
        <Box minH="100vh" mb="10" paddingBlock="6rem">

            <Center fontFamily="GmkBold" fontSize={{ base: "4rem", md: "5rem", lg: "6rem" }} color={"purple"}>
                관리자 페이지
            </Center>

            <Flex m="auto" border="2px" borderColor="purple" rounded="lg" w="85vw" overflow="hidden" minH={boxHeight}>
                <Flex m="auto" direction={{ base: "column", lg: "row"}} rounded="lg" w="80vw" maxH={{ base:"auto", lg: "80vh"}} px="2">
                    <Box w={{ base: "100%", lg: "25%" }} pr="4">
                        <Box w="full" bg="white" rounded="lg" overflow="hidden">
                            <Flex direction="column" align="center" py="6">

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
                                        data-index={category.id} // 데이터 속성 추가
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
                                    <BreadcrumbLink>관리자</BreadcrumbLink>
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
