import "../../css/Navbar.css";
import { Image, Box, Flex, Spacer } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { RootState } from "../../redux/stores/store";
import LoginComponent from "./NavComponent/LoginComponent";
import LogoutComponent from "./NavComponent/LogoutComponent";
import SellerComponent from "./NavComponent/SellerComponent";
import BuyerComponent from "./NavComponent/BuyerComponent";
import ProfileBuyerComponent from "./NavComponent/NavBuyerProfileComponent";
import ProfileSellerComponent from "./NavComponent/NavSellerProfileComponent";
import LogoutProfileComponent from "./NavComponent/LogoutProfileComponent";
import ProfileAdminComponent from "./NavComponent/NavAdminProfileComponent";
import logo from "/img/newlogo.png";

function NavBar() {
    const navigate = useNavigate();
    const user = useSelector((state: RootState) => state.user);
    const [myAuth, setMyAuth] = useState("");

    useEffect(() => {
        setMyAuth(user.auth);
    }, [user.accessToken]);

    return (
        <Box className="paddingNavBar">
            <Flex minWidth={"max-content"} alignItems="center" gap="2">
                <Box />
                <Spacer />
                {user.accessToken ? <LoginComponent /> : <LogoutComponent />}
            </Flex>
            <Flex minWidth="max-content" alignItems="center" gap="3">
                <Box
                    width={"8rem"}
                    height={"5rem"}
                    onClick={() => {
                        navigate("./main");
                    }}
                    _hover={{ cursor: "pointer" }}
                >
                    <Image width={"100%"} height={"100%"} src={logo}></Image>
                </Box>

                <Spacer />

                {myAuth === "SELLER" ? <SellerComponent /> : <BuyerComponent />}

                <Spacer />

                {user.accessToken && myAuth === "BUYER" ? (
                    <ProfileBuyerComponent />
                ) : user.accessToken && myAuth === "SELLER" ? (
                    <ProfileSellerComponent />
                ) : user.accessToken && myAuth === "ADMIN" ? (
                    <ProfileAdminComponent />
                ) : (
                    <LogoutProfileComponent />
                )}
            </Flex>
        </Box>
    );
}

export default NavBar;
