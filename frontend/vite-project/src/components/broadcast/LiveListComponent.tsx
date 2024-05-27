import {
    AspectRatio,
    Card,
    CardBody,
    Flex,
    Heading,
    Image,
    Stack,
    Text,
} from "@chakra-ui/react";
import { useNavigate } from "react-router-dom";
import { formatNumberWithComma } from "../common/Comma";

interface LiveComponent {
    url: string;
    title: string;
    price: number;
    liveFlatPrice:number;
    liveBroadcastId:number
}

const LiveListComponent = ({ url, title, price, liveFlatPrice,liveBroadcastId }: LiveComponent) => {
    const navigate = useNavigate();
    return (
        <Card w={"100%"}>
            <CardBody>
                <AspectRatio h="20rem" ratio={1 / 5}>
                    <Image
                        src={url}
                        aspectRatio="1/1"
                        objectFit="cover"
                        overflow={"hidden"}
                        position={"relative"}
                        borderRadius={"20px"}
                        onClick={() => navigate(`/v1/live/${liveBroadcastId}`)}
                        _hover={{ cursor: "pointer" }}
                    />
                </AspectRatio>
                <Stack mt="6" spacing="3">
                    <Heading size="md">
                        {title.length >= 9 ? title.slice(0, 9) + "..." : title}
                    </Heading>
                    <Flex>
                        <Text color="themeRed.500" fontSize="xl" mr={"1rem"}>{formatNumberWithComma(liveFlatPrice) + "원"}</Text>
                        <Text textDecorationLine={"line-through"} color="black" fontSize="xl">
                            {formatNumberWithComma(price) + "원"}
                        </Text>
                    </Flex>
                </Stack>
            </CardBody>
        </Card>
    );
};

export default LiveListComponent;

