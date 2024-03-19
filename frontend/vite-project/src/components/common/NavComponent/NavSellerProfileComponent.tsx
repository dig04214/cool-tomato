import { Search2Icon } from "@chakra-ui/icons";
import { Avatar, Box, Flex, Link } from "@chakra-ui/react";
import { useNavigate } from "react-router-dom";
import Nofitication from "../notification/Nofitication";
import { RootState } from "../../../redux/stores/store";
import { useSelector } from "react-redux";

export default function ProfileSellerComponent() {
    const navigate = useNavigate();
    const user = useSelector((state: RootState) => state.user);
    return (
        <Box>
            <Flex minWidth="max-content" alignItems="center" gap="3">
                <Link>
                    <Search2Icon
                        onClick={() => {
                            navigate("/v1/search");
                        }}
                        color={"#126F54"}
                        boxSize={6}
                    />
                </Link>
                <Link>
                    <Nofitication />
                </Link>
                <Avatar
                    onClick={() => {
                        navigate("/v1/seller");
                    }}
                    size="sm"
                    src={user.profileImg}
                />
            </Flex>
        </Box>
    );
}
