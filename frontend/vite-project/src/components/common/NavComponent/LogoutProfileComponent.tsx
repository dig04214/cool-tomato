import { Search2Icon } from "@chakra-ui/icons";
import { Box, Flex, Link } from "@chakra-ui/react";
import { useNavigate } from "react-router-dom";

export default function LogoutProfileComponent() {
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
            </Flex>
        </Box>
    );
}
