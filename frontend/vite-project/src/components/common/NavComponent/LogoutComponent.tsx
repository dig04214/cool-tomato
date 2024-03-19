import { Flex, Link } from "@chakra-ui/react";
import { useNavigate } from "react-router-dom";

export default function LogoutComponent() {
    const navigate = useNavigate();
    return (
        <Flex alignItems="center" gap="3">
            <Link
                className="TopNavFont"
                _hover={{ color: "themeGreen.500" }}
                onClick={() => {
                    navigate("./signup");
                }}
            >
                회원가입
            </Link>
            <Link
                className="TopNavFont"
                _hover={{ color: "themeGreen.500" }}
                onClick={() => {
                    navigate("./login");
                }}
            >
                로그인
            </Link>
            <Link
                className="TopNavFont"
                _hover={{ color: "themeGreen.500" }}
                onClick={() => {
                    navigate("./board/notice");
                }}
            >
                고객센터
            </Link>
        </Flex>
    );
}