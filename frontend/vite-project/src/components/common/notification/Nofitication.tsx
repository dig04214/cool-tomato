import {
    Popover,
    PopoverTrigger,
    PopoverContent,
    PopoverHeader,
    PopoverBody,
    PopoverFooter,
    PopoverArrow,
    PopoverCloseButton,
    PopoverAnchor,
    Button,
    Portal,
    Box,
    Divider,
} from "@chakra-ui/react";
import { BellIcon } from "@chakra-ui/icons";
import NotificationContent from "./NotificationContent";

export type NotificationContentTypes = {
    key: string;
    url: string;
    msg: string;
    time: string;
};

function Nofitication() {
    const dummyContent: Array<NotificationContentTypes> = [
        {
            key: "1",
            url: "/v1",
            msg: "관심있는 상품의 라이브가 시작했어요!",
            time: "01/25",
        },
        {
            key: "2",
            url: "/v1",
            msg: "라이브가 곹 시작됩니다!",
            time: "01/25",
        },
        {
            key: "3",
            url: "/v1",
            msg: "라이브가 시작했어요!",
            time: "01/25",
        },
    ];
    return (
        <Popover closeOnBlur={true} placement="bottom">
            {({ isOpen, onClose }) => (
                <>
                    <PopoverTrigger>
                        <BellIcon color={"#126F54"} boxSize={6} />
                    </PopoverTrigger>
                    <Portal>
                        <PopoverContent>
                            {/* <PopoverHeader>This is the header</PopoverHeader> */}
                            {/* <PopoverCloseButton /> */}
                            {dummyContent.map((content) => (
                                <NotificationContent
                                    key={content.key}
                                    url={content.url}
                                    msg={content.msg}
                                    time={content.time}
                                />
                            ))}
                            {/* <PopoverFooter>This is the footer</PopoverFooter> */}
                        </PopoverContent>
                    </Portal>
                </>
            )}
        </Popover>
    );
}

export default Nofitication;
