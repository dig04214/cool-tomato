import { Container, Link, HStack, StackDivider } from "@chakra-ui/react";
import { NavigateFunction, useNavigate } from "react-router-dom";

function LoginNavigate() {
    const navigate: NavigateFunction = useNavigate();
    return (
        <>
            <HStack
                pt={5}
                w="80%"
                divider={<StackDivider borderColor="grey" />}
            >
                <Container w="auto">
                    <Link
                        color={"themeFontGreen.500"}
                        _hover={{ color: "themeGreen.500" }}
                        onClick={() => navigate("/v1/findid")}
                    >
                        아이디 찾기
                    </Link>
                </Container>
                <Container w="auto">
                    <Link
                        color={"themeFontGreen.500"}
                        _hover={{ color: "themeGreen.500" }}
                        onClick={() => navigate("/v1/pwdrecover")}
                    >
                        비밀번호 찾기
                    </Link>
                </Container>
                <Container w="auto">
                    <Link
                        color={"themeFontGreen.500"}
                        _hover={{ color: "themeGreen.500" }}
                        onClick={() => navigate("/v1/signup")}
                    >
                        회원가입
                    </Link>
                </Container>
            </HStack>
        </>
    );
}

export default LoginNavigate;
