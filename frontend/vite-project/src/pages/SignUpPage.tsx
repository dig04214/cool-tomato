import React from "react";
import { Center, Container, Text } from "@chakra-ui/react";
import SignUpForm from "../components/signup/SignUpForm";

function SignUpPage() {
    return (
        <>
            <Center my="auto">
                <Container
                    pt={3}
                    m={0}
                    maxW={{ xl: "35%", lg: "60%", sm: "90%" }}
                    centerContent
                >
                    <Text
                        pb={3}
                        fontSize="4xl"
                        fontWeight="bold"
                        color="themeFontGreen.500"
                    >
                        회원가입
                    </Text>
                    <SignUpForm />
                </Container>
            </Center>
        </>
    );
}

export default SignUpPage;
