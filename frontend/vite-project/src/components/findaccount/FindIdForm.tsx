import React from "react";
import { useNavigate } from "react-router-dom";
import {
    Text,
    Input,
    Button,
    Center,
    FormControl,
    Container,
    FormLabel,
} from "@chakra-ui/react";
import { useState } from "react";
import { findIdAPI } from "../../api/user";


function FindIdForm() {
    const navigate = useNavigate();
    const [email, setEmail] = useState<string>("")
    const [sendcode, setSendcode] = useState<string>("")

    function handleEmail(e: React.ChangeEvent<HTMLInputElement>) {
        const inputValue = e.target.value;
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        // 올바른 이메일 형식일 때 이메일 전송버튼 활성화
        if (regex.test(inputValue)) {
            setSendcode(inputValue);
        }
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
        findIdAPI({email:email})
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
                    아이디 찾기
                </Text>
                <form onSubmit={onSubmit} style={{ width: "80%" }}>
                    <FormControl my={4}>
                        <FormLabel mb={2}>이메일 입력</FormLabel>
                        <Input
                            focusBorderColor="themeGreen.500"
                            placeholder="email"
                            type="email"
                            size="md"
                            autoComplete="email"
                            onChange={handleEmail}
                            onKeyPress={handleKeyPress}
                        />
                    </FormControl>
                    <Center>
                        <Button
                            mt={4}
                            w="95%"
                            colorScheme="themeGreen"
                            type="submit"
                            borderRadius="3xl"
                            py={1}
                            isDisabled={!sendcode}
                        >
                            전송
                        </Button>
                    </Center>
                </form>
            </Container>
        </>
    );
}

export default FindIdForm;
