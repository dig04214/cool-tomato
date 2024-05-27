import { Text, Button } from "@chakra-ui/react";
import { useNavigate } from "react-router-dom";

function SignComplete() {
    const navigate = useNavigate();

    function onclick() {
        navigate("/v1/login");
    }
    return (
        <>
            <Text
                pb={3}
                fontSize="2xl"
                fontWeight="bold"
                color="themeFontGreen.500"
            >
                회원가입이 완료되었습니다
            </Text>
            <Button
                my={4}
                w={"40%"}
                colorScheme="themeGreen"
                type="submit"
                borderRadius="3xl"
                _hover={{ bg: "white", color: "black" }}
                onClick={onclick}
            >
                로그인 하러 가기
            </Button>
        </>
    );
}

export default SignComplete;
