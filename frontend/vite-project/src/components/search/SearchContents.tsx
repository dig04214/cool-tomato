import { Box, Grid, GridItem, Text } from "@chakra-ui/layout";
import { Card, CardBody, Tag, TagLabel, Badge } from "@chakra-ui/react";
import { useNavigate } from "react-router";

interface searchtitleResults {
    liveBroadcastId: number;
    broadcastTitle: string;
    nickName: string;
    viewCount: number;
    sellerId: number;
    broadcastStatus: boolean;
}

interface searchsellerResults {
    liveBroadcastId: number;
    broadcastTitle: string;
    nickName: string;
    viewCount: number;
    sellerId: number;
    broadcastStatus: boolean;
}

export default function SearchContents({
    searchtitleResults,
    searchsellerResults,
}: {
    searchtitleResults: searchtitleResults[];
    searchsellerResults: searchsellerResults[];
}) {
    const navigate = useNavigate();

    return (
        <Box ml="10" mr="10" mb="15">
            <Tag size="lg" variant="subtle" colorScheme="green" mb="10">
                <TagLabel>라이브 검색 결과</TagLabel>
            </Tag>
            <Grid templateColumns="repeat(4, 1fr)" gap={4}>
                {searchtitleResults.length > 0 ? (
                    searchtitleResults.map((item) => (
                        <GridItem key={item.liveBroadcastId}>
                            <Card
                                onClick={() => {
                                    if (item.broadcastStatus) {
                                        navigate(
                                            `/v1/live/${item.liveBroadcastId}`
                                        );
                                    }
                                }}
                                _hover={{
                                    cursor: item.broadcastStatus
                                        ? "pointer"
                                        : "default",
                                    border: item.broadcastStatus
                                        ? "2px"
                                        : "none",
                                    borderRadius: "5px",
                                    borderColor: item.broadcastStatus
                                        ? "themeRed.500"
                                        : "transparent",
                                }}
                            >
                                <CardBody p={6}>
                                    {item.broadcastStatus ? (
                                        <Badge mb="5" colorScheme="red">
                                            "방송중!"
                                        </Badge>
                                    ) : (
                                        <Badge
                                            variant="outline"
                                            colorScheme="red"
                                        >
                                            "방송예정"
                                        </Badge>
                                    )}
                                    <Text
                                        fontSize="lg"
                                        fontWeight="semibold"
                                        mb={2}
                                    >
                                        라이브명: {item.broadcastTitle}
                                    </Text>
                                    <Text color="gray.500">
                                        {item.nickName} 판매자
                                    </Text>
                                </CardBody>
                            </Card>
                        </GridItem>
                    ))
                ) : (
                    <Text>검색 결과가 없습니다.</Text>
                )}
            </Grid>

            <Tag size="lg" variant="subtle" colorScheme="green" mt="10" mb="10">
                <TagLabel>판매자 검색 결과</TagLabel>
            </Tag>
            <Grid templateColumns="repeat(4, 1fr)" gap={4}>
                {searchsellerResults.length > 0 ? (
                    searchsellerResults.map((item) => (
                        <GridItem key={item.liveBroadcastId}>
                            <Card
                                onClick={() => {
                                    if (item.broadcastStatus) {
                                        navigate(
                                            `/v1/live/${item.liveBroadcastId}`
                                        );
                                    } else {
                                        navigate(
                                            `/v1/seller/profile/${item.sellerId}`
                                        );
                                    }
                                }}
                                _hover={{
                                    cursor: "pointer",
                                    border: item.broadcastStatus
                                        ? "2px"
                                        : "none",
                                    borderRadius: "5px",
                                    borderColor: item.broadcastStatus
                                        ? "themeRed.500"
                                        : "transparent",
                                }}
                            >
                                <CardBody p={6}>
                                    {item.broadcastStatus ? (
                                        <Badge mb="5" colorScheme="red">
                                            "방송중!"
                                        </Badge>
                                    ) : (
                                        <Badge
                                            mb="5"
                                            variant="outline"
                                            colorScheme="red"
                                        >
                                            "방송예정"
                                        </Badge>
                                    )}
                                    <Text
                                        fontSize="lg"
                                        fontWeight="semibold"
                                        mb={2}
                                    >
                                        {item.broadcastTitle}
                                    </Text>
                                    <Text color="gray.500">
                                        {item.nickName} 판매자
                                    </Text>
                                </CardBody>
                            </Card>
                        </GridItem>
                    ))
                ) : (
                    <Text>검색 결과가 없습니다.</Text>
                )}
            </Grid>
        </Box>
    );
}
