import { Search2Icon } from "@chakra-ui/icons";
import { Avatar, Box, Flex, Link } from "@chakra-ui/react";
import { useNavigate } from "react-router-dom";
import Nofitication from "../notification/Nofitication";

export default function ProfileAdminComponent() {
    const navigate = useNavigate();
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
                        navigate("/v1/admin");
                    }}
                    size="sm"
                    src="/img/profile.png"
                />
            </Flex>
        </Box>
    );
}
