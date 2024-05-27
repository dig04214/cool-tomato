import { CloseIcon } from "@chakra-ui/icons";
import { Button, Center, Container, Text } from "@chakra-ui/react";
import { useNavigate } from "react-router-dom";

interface BuyerLiveNavProps {
    setStream: React.Dispatch<React.SetStateAction<boolean>>;
}

export default function BuyerLiveNav({ setStream }: BuyerLiveNavProps) {
    const navigate = useNavigate();
    return (
        <Container centerContent>
            <Button
                p={"1.5rem"}
                size={"lg"}
                leftIcon={<CloseIcon />}
                colorScheme="teal"
                variant="link"
                onClick={() => {
                    setStream(false);
                    navigate("/v1/main");
                }}
            >
                <Center>
                    <Text as={"b"}>라이브 나가기</Text>
                </Center>
            </Button>
        </Container>
    );
}
