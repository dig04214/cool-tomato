// Menu.js
import {
    Box,
    Text,
    Tabs,
    TabList,
    Tab,
    Flex,
    Spacer,
    AccordionItem,
    AccordionIcon,
    AccordionPanel,
    AccordionButton,
    Accordion,
} from "@chakra-ui/react";

import LiveInfo from "./menu/LiveInfo";
import Feedback from "./menu/Feedback";
import NewProduct from "./menu/NewProduct";
import ProductList from "./menu/ProductList";
import Prompter from "./menu/Prompter";
import { useState } from "react";

function Menu() {
    const [activeTab, SetTab] = useState("Feedback");

    const StateChangeFeedback = () => {
        SetTab("Feedback");
    };

    const StateChangeProductList = () => {
        SetTab("ProductList");
    };

    const StateChangeNewProduct = () => {
        SetTab("NewProduct");
    };

    const StateChangeLiveInfo = () => {
        SetTab("LiveInfo");
    };

    const StateChangePrompter = () => {
        SetTab("Prompter");
    };

    return (
        <Box w={"33%"} borderLeft="1px" overflow="auto" p={6}>
            <Accordion allowToggle mb={"2rem"}>
                <AccordionItem>
                    <AccordionButton>
                        <Box as="span" flex="1" textAlign="left">
                            <Text as={"b"}>메뉴 목록 보기</Text>
                        </Box>
                        <AccordionIcon />
                    </AccordionButton>

                    <AccordionPanel pb={4}>
                        <Tabs variant="soft-rounded" colorScheme="green">
                            <TabList>
                                <Flex w={"100%"}>
                                    <Tab onClick={StateChangeFeedback}>
                                        실시간 피드백
                                    </Tab>
                                    <Spacer />
                                    <Tab onClick={StateChangeProductList}>
                                        상품 목록
                                    </Tab>
                                </Flex>
                            </TabList>
                            <TabList mt={"0.7rem"}>
                                <Flex w={"100%"}>
                                    <Tab onClick={StateChangeNewProduct}>
                                        새상품 등록하기
                                    </Tab>
                                    <Spacer />
                                    <Tab onClick={StateChangeLiveInfo}>
                                        방송 정보 수정
                                    </Tab>
                                    <Spacer />
                                    <Tab onClick={StateChangePrompter}>
                                        대본 보기
                                    </Tab>
                                </Flex>
                            </TabList>
                        </Tabs>
                    </AccordionPanel>
                </AccordionItem>
            </Accordion>
            {activeTab === "Feedback" ? <Feedback /> : null}
            {activeTab === "ProductList" ? <ProductList /> : null}
            {activeTab === "NewProduct" ? <NewProduct /> : null}
            {activeTab === "LiveInfo" ? <LiveInfo /> : null}
            {activeTab === "Prompter" ? <Prompter /> : null}
        </Box>
    );
}

export default Menu;
