import { Flex, Link } from "@chakra-ui/react";
import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { RootState, AppDispatch } from "../../../redux/stores/store";
import { logoutUserThunk } from "../../../redux/thunk/user/userThunk";

export default function LoginComponent() {
    const navigate = useNavigate();
    const dispatch: AppDispatch = useDispatch();
    const user = useSelector((state: RootState) => state.user);
    const accessToken = user.accessToken;
    const refreshToken = user.refreshToken;
    
    const handlelogout = () => {
        dispatch(logoutUserThunk({ accessToken: accessToken, refreshToken: refreshToken }));
        navigate("/v1/main");
    }

    return (
        <Flex alignItems="center" gap="3">
            <Link
                className="TopNavFont"
                _hover={{ color: "themeGreen.500" }}
                onClick={handlelogout}
            >
                로그아웃
            </Link>
            <br />
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
