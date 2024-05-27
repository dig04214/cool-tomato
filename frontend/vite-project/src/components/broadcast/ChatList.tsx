import { Flex, Text } from "@chakra-ui/layout";
import { IconButton, List, ListItem } from "@chakra-ui/react";
import { FaBan } from "react-icons/fa";
import { chatMessageRecv } from "../../types/DataTypes";
import { postBlockUser } from "../../api/chatting";
import { useSelector } from "react-redux";
import { RootState } from "../../redux/stores/store";

interface ChatListProps {
    recv: Array<chatMessageRecv>;
    setRecv: React.Dispatch<React.SetStateAction<chatMessageRecv[]>>;
}

function ChatList({ recv }: ChatListProps) {
    const accessToken = useSelector(
        (state: RootState) => state.user.accessToken
    );
    const userId = useSelector((state: RootState) => state.user.userId);
    function handleClick(id: number) {
        if (id === 0 || id === userId) return;
        if (confirm("정말로 차단하시겠습니까?") === false) return;
        postBlockUser(id, accessToken).then(() => {
            console.log("block user success");
        });
    }
    return (
        <>
            <Flex
                alignItems="center"
                justifyContent="space-between"
                w="90%"
                my="4"
                mx="auto"
                borderRadius="1rem"
            >
                <List spacing={2}>
                    {recv.map((msg, index) => (
                        <ListItem key={index}>
                            <Text whiteSpace={"normal"}>
                                <IconButton
                                    aria-label="block user"
                                    icon={<FaBan color="red" />}
                                    onClick={() => handleClick(msg.senderId)}
                                    variant={"ghost"}
                                ></IconButton>
                                {msg.senderNickname} : {msg.message}
                            </Text>
                        </ListItem>
                    ))}
                </List>
            </Flex>
        </>
    );
}

export default ChatList;
