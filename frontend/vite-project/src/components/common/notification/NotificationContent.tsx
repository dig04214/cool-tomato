import { Box, Divider, PopoverBody, Link } from "@chakra-ui/react";
import { NotificationContentTypes } from "./Nofitication";

function NotificationContent({ url, msg, time }: NotificationContentTypes) {
    return (
        <PopoverBody>
            <Box>
                <Link href={url}>
                    <Box as="span" flex="1">
                        {msg}
                    </Box>
                    <Box as="span" textAlign={"right"}>
                        {time}
                    </Box>
                </Link>
            </Box>
            <Divider borderColor={"themeLightGreen.500"} />
        </PopoverBody>
    );
}

export default NotificationContent;
