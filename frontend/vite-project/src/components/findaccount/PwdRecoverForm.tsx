import React from "react";
import { useNavigate } from "react-router-dom";
import {
    Text,
    Input,
    InputRightElement,
    InputGroup,
    Button,
    Center,
    FormControl,
    Container,
    FormLabel
} from "@chakra-ui/react";
import { CheckIcon } from "@chakra-ui/icons";
import { useState } from "react";
import { findPwAPI } from "../../api/user";

function PwdRecoverForm() {
    const navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [check, setCheck] = useState(false);
    const [check2, setCheck2] = useState(false);
    const [validMessage, setValidMessage] = useState({
        usernameMessage: "",
        emailMessage: "",
    })


    function handleUsername(e: React.ChangeEvent<HTMLInputElement>) {
        const inputValue = e.target.value;
        const cleanedValue = inputValue.replace(/[^A-Za-z0-9]/g, '');
        if (/[ㄱ-ㅎㅏ-ㅣ가-힣]/.test(inputValue)) {
            setValidMessage({ ...validMessage, usernameMessage: "한글을 입력할 수 없습니다." });
            return;
        }
        if (/[~!@#$%";'^,&*()_+|</>=>`?:{[\]}]/g.test(inputValue)) {
            setValidMessage({ ...validMessage, usernameMessage: "특수문자를 입력할 수 없습니다." });
            return;
        }
        const limitedValue = cleanedValue.substring(0, 16);
        if (cleanedValue.length > 16) {
            setValidMessage({ ...validMessage, usernameMessage: "아이디는 16글자를 넘어갈 수 없습니다." });
            return;
        }
        if (limitedValue.length > 0) {
            setCheck(true)
        } else {
            setCheck(false)
        }
        setValidMessage({ ...validMessage, usernameMessage: "" });
        setUsername(limitedValue);
    }

    function handleEmail(e: React.ChangeEvent<HTMLInputElement>) {
        const inputValue = e.target.value;
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        // 올바른 이메일 형식일 때 이메일 전송버튼 활성화
        setCheck2(regex.test(inputValue))
        setEmail(inputValue);
    }

    // 스페이스바 금지
    function handleKeyPress(e: React.KeyboardEvent<HTMLInputElement>) {
        if (e.key === ' ') {
            e.preventDefault();
        }
    }

    function onSubmit(event: React.SyntheticEvent): void {
        event.preventDefault();
        findPwAPI({loginId: username, email: email})
        // todo: 요청값 받고 값에 따라 네비게이터 처리해주기
        navigate("/v1/findaccount", { replace: false });
    }

    return (
        <>
            <Container
                pt={3}
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
                    비밀번호 찾기
                </Text>
                <form onSubmit={onSubmit} style={{ width: "80%" }}>
                    <FormControl my={4}>
                        <FormLabel>아이디 입력</FormLabel>
                        <InputGroup>
                            <Input
                                focusBorderColor="themeGreen.500"
                                placeholder="ID"
                                size="md"
                                autoComplete="username"
                                value={username}
                                onChange={handleUsername}
                                onKeyPress={handleKeyPress}
                            />
                            <InputRightElement>
                                {check ? (
                                    <CheckIcon color="green.500" mr={"1"} />
                                    ) : (
                                    ''
                                )}
                            </InputRightElement>
                        </InputGroup>
                    </FormControl>
                    <FormControl my={3}>
                        <FormLabel>이메일 입력</FormLabel>
                        <InputGroup>
                            <Input
                                focusBorderColor="themeGreen.500"
                                placeholder="email"
                                type="email"
                                size="md"
                                autoComplete="email"
                                value={email}
                                onChange={handleEmail}
                                onKeyPress={handleKeyPress}
                            />
                            <InputRightElement>
                                {check2 ? (
                                    <CheckIcon color="green.500" mr={"1"} />
                                    ) : (
                                    ''
                                )}
                            </InputRightElement>
                        </InputGroup> 
                    </FormControl>
                    <Center>
                        <Button
                            mt={4}
                            w="95%"
                            colorScheme="themeGreen"
                            type="submit"
                            borderRadius="3xl"
                            py={1}
                            isDisabled={!check || !check2}
                        >
                            전송
                        </Button>
                    </Center>
                </form>
            </Container>
        </>
    );
}

export default PwdRecoverForm;
