import { Text, Center, Container } from "@chakra-ui/react";
import LoginForm from "../components/login/LoginForm";
import LoginNavigate from "../components/login/LoginNavigate";

function LoginPage() {
    return (
        <>
            <Center my="auto" h={"80vh"} w={"100%"}>
                <Container
                    // pt={3}
                    m={0}
                    maxW={{ xl: "35%", lg: "55%", sm: "90%" }}
                    centerContent
                >
                    <Text
                        pb={3}
                        fontSize="4xl"
                        fontWeight="bold"
                        color="themeFontGreen.500"
                    >
                        로그인
                    </Text>
                    <LoginForm />
                    <LoginNavigate />
                </Container>
            </Center>
        </>
    );
}

export default LoginPage;
