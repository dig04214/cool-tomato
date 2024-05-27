import {
    Flex,
    Text,
    Container,
    Tabs,
    TabList,
    Tab,
    TabPanels,
    TabPanel,
    Divider,
    Button,
} from "@chakra-ui/react";
import dayjs from "dayjs";
import LiveCalendar from "../components/broadcast/LiveCalendar";

export default function Calendar() {
    const today = dayjs();
    // const [activeTabIndex, setActiveTabIndex] = useState(0);
    // const [islive, setislive] = useState(false);

    // useEffect(() => {
    //     setislive(true);
    // }, [activeTabIndex]);

    return (
        <Container maxW={"7xl"} minH={"100vh"} p={"2rem"}>
            <Divider />
            <Tabs variant="unstyled">
                <TabList mt={"1.5rem"} mb={"1.5rem"}>
                    <Tab
                        _selected={{ color: "white", bg: "themeRed.500" }}
                        mr={"2rem"}
                    >
                        <Flex direction={"column"}>
                            <Text as={"b"}>Today</Text>
                            <Text fontFamily={"GmkBold"} as={"b"}>
                                {today.date()}
                            </Text>
                        </Flex>
                    </Tab>
                    <Tab
                        _selected={{ color: "white", bg: "themeRed.500" }}
                        mr={"2rem"}
                    >
                        <Flex direction={"column"}>
                            <Text as={"b"}>
                                {today.add(1, "day").format("ddd")}
                            </Text>
                            <Text fontFamily={"GmkBold"} as={"b"}>
                                {today.add(1, "day").date()}
                            </Text>
                        </Flex>
                    </Tab>
                    <Tab
                        _selected={{ color: "white", bg: "themeRed.500" }}
                        mr={"2rem"}
                    >
                        <Flex direction={"column"}>
                            <Text as={"b"}>
                                {today.add(2, "day").format("ddd")}
                            </Text>
                            <Text fontFamily={"GmkBold"} as={"b"}>
                                {today.add(2, "day").date()}
                            </Text>
                        </Flex>
                    </Tab>
                    <Tab
                        _selected={{ color: "white", bg: "themeRed.500" }}
                        mr={"2rem"}
                    >
                        <Flex direction={"column"}>
                            <Text as={"b"}>
                                {today.add(3, "day").format("ddd")}
                            </Text>
                            <Text fontFamily={"GmkBold"} as={"b"}>
                                {today.add(3, "day").date()}
                            </Text>
                        </Flex>
                    </Tab>
                </TabList>

                <Divider />

                <Flex
                    alignItems={"center"}
                    px={"6"}
                    py={"6"}
                    justify={"space-between"}
                >
                    <Flex alignItems={"center"} gap={"4"}>
                        <Flex direction={"row"} gap={"1"}>
                            <Button backgroundColor={"themeRed.500"}>
                                <Text color={"white"}>현재 라이브 중</Text>
                            </Button>
                        </Flex>
                    </Flex>
                </Flex>

                <TabPanels>
                    <TabPanel>
                        <LiveCalendar date={0} />
                    </TabPanel>
                    <TabPanel>
                        <LiveCalendar date={1} />
                    </TabPanel>
                    <TabPanel>
                        <LiveCalendar date={2} />
                    </TabPanel>
                    <TabPanel>
                        <LiveCalendar date={3} />
                    </TabPanel>
                </TabPanels>
            </Tabs>
        </Container>
    );
}
