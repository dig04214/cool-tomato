import { Input, Button, Flex, Text } from "@chakra-ui/react";
import { SearchIcon } from "@chakra-ui/icons";
import { useState } from "react";
import { getTitleSearchAPI, getSellerSearchAPI } from "../api/livesearch";
import SearchContents from "../components/search/SearchContents";
import Recommends from "../components/search/Recommends";

// 검색 전 추천상품 띄우기
export default function Search() {
    const [showResults, setShowResults] = useState(false);
    const [keywordResult, setKeywordResult] = useState("");
    const [searchKeyword, setSearchKeyword] = useState<string>("");
    const [searchtitleResults, setSearchtitleResults] = useState([]);
    const [searchsellerResults, setSearchsellerResults] = useState([]);

    const handleSearchKeyword = (e: React.ChangeEvent<HTMLInputElement>) => {
        const inputValue = e.target.value;
        setSearchKeyword(inputValue);
    };

    const submitHandler = async (event: React.FormEvent) => {
        event.preventDefault();

        try {
            const titleResults = await getTitleSearchAPI({
                keyword: searchKeyword,
                page: 0,
                size: 20,
            });
            const sellerResults = await getSellerSearchAPI({
                name: searchKeyword,
                page: 0,
                size: 20,
            });
            setSearchtitleResults(titleResults.data.broadcastInfoList);
            setSearchsellerResults(sellerResults.data.broadcastInfoList);
            setShowResults(true);
            setKeywordResult(searchKeyword);
        } catch (error) {
            console.error("Search request failed", error);
        }
    };

    return (
        <Flex direction="column" align="center">
            <Flex
                justify={"center"}
                className="MainText"
                color={"themeGreen.500"}
                mt="0.5rem"
            >
                검색하기
            </Flex>

            <Flex justify="center" w="90vw">
                <Flex as="form" w="80vw" onSubmit={submitHandler}>
                    <Input
                        color="themeFontGreen.500"
                        placeholder="궁금한 야채 찾으러 가깅"
                        _placeholder={{ opacity: 0.4, color: "gray" }}
                        focusBorderColor="themeRed.500"
                        mr="2"
                        value={searchKeyword}
                        onChange={handleSearchKeyword}
                    />
                    <Button colorScheme="red" color="white" type="submit">
                        <SearchIcon />
                    </Button>
                </Flex>
            </Flex>

            <Flex direction="column" align="center">
                {showResults && searchKeyword.length > 0 ? (
                    <>
                        <Text
                            mt={10}
                            mb={4}
                            fontWeight="semibold"
                            textAlign="center"
                            color="gray.700"
                        >
                            {keywordResult}의 검색결과
                        </Text>
                        {searchtitleResults.length > 0 ||
                        searchsellerResults.length > 0 ? (
                            <SearchContents
                                searchtitleResults={searchtitleResults}
                                searchsellerResults={searchsellerResults}
                            />
                        ) : (
                            <Text
                                mt={2}
                                mb={2}
                                fontSize="md"
                                fontWeight="normal"
                                textAlign="center"
                                color="gray.500"
                            >
                                검색 결과가 없습니다.
                            </Text>
                        )}

                        {/* <SearchContents searchKeyword={searchKeyword} searchtitleResults={searchtitleResults} searchsellerResults={searchsellerResults} />     */}
                    </>
                ) : (
                    <>
                        <Text
                            mt={10}
                            mb={5}
                            p={5}
                            fontSize="xl"
                            fontWeight="semibold"
                            textAlign="center"
                            color="gray.700"
                        >
                            💥따끈따끈 신상품 구경하기💥
                        </Text>
                        <Recommends />
                    </>
                )}
            </Flex>
        </Flex>
    );
}
