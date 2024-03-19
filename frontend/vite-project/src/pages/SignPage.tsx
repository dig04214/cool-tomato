import { Center, Container, Text } from "@chakra-ui/react";
import SignComplete from "../components/signup/SignComplete";

function SignPage() {
    return (
        <Center h={"80vh"}>
            <Container
                // pt={3}
                m={0}
                maxW={{ xl: "35%", lg: "60%", sm: "90%" }}
                border={"1px"}
                borderRadius={"3xl"}
                borderColor={"themeGreen.500"}
                py={"3rem"}
                centerContent
            >
                <Text
                    pb={3}
                    fontSize="4xl"
                    fontWeight="bold"
                    color="themeFontGreen.500"
                >
                    회원가입 완료
                </Text>
                <SignComplete />
            </Container>
        </Center>
    );
}

export default SignPage;
