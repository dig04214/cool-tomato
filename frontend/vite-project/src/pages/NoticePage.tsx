import { Center, Container } from "@chakra-ui/react";
import Notice from "../components/mypage/admin/Notice";

function NoticePage() {
    return (
        <>
            <Center my="auto" h={"80vh"} w={"100%"}>
            <Center fontFamily="GmkBold" fontSize={{ base: "4rem", md: "5rem", lg: "6rem" }} color={"purple"}>
                공지사항
            </Center>
                <Container
                    m={0}
                    maxW={{ xl: "35%", lg: "55%", sm: "90%" }}
                    centerContent
                    >
                    <Notice />
                </Container>
            </Center>
        </>
    );
}

export default NoticePage;
