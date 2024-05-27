import { TimeIcon, ViewIcon, CheckIcon } from "@chakra-ui/icons";
import { Flex, Text } from "@chakra-ui/layout";
import { FaWifi, FaVideoSlash } from "react-icons/fa";

import ProfileBuyerComponent from "../common/NavComponent/NavBuyerProfileComponent";
import { useState } from "react";

interface HeaderProps {
    setStream: React.Dispatch<React.SetStateAction<boolean>>;
}

function Header({ setStream }: HeaderProps) {
    const [isOpen, setIsOpen] = useState(false);
    const iconData = [
        { icon: <TimeIcon />, content: "2시간 30분", onclick: undefined },
        { icon: <ViewIcon />, content: "1,285 시청자수", onclick: undefined },
        { icon: <CheckIcon />, content: "Sales completed", onclick: undefined },
        { icon: <FaWifi />, content: "통신 상태 원활", onclick: undefined },
        {
            icon: <FaVideoSlash />,
            content: "방송 종료",
            onclick: handleClick,
        },
    ];

    function handleClick() {
        setIsOpen(!isOpen);
    }
    return (
        <Flex
            bg="themeLightGreen.500"
            justify="space-between"
            p="1rem"
            h="3rem"
            alignItems="center"
        >
            <Flex fontFamily="GmkBold">
                <Text>므째이터마터</Text>
            </Flex>
            <Flex>
                {/* {iconData.map((item, index) => (
                    <Flex
                        key={index}
                        alignItems="center"
                        marginRight="0.5rem"
                        onClick={item.onclick}
                    >
                        {item.icon}
                        <Text marginLeft="0.3rem">{item.content}</Text>
                    </Flex>
                ))} */}
            </Flex>

            <ProfileBuyerComponent />
        </Flex>
    );
}

export default Header;
