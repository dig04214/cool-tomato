// Broadcast.js
import { Flex } from "@chakra-ui/layout";
import Header from "../components/broadcast/Header";
import BroadcastScreen from "../components/broadcast/Screen";
import Chat from "../components/broadcast//Chat";
import Menu from "../components/broadcast//Menu";
import { useState } from "react";
import React from "react";

function Broadcast() {
    const [stream, setStream] = useState(true);
    return (
        <Flex direction="column" h="100vh">
            <Header setStream={setStream} />
            <Flex flex="1" overflow="hidden">
                <BroadcastScreen stream={stream} setStream={setStream} />
                <Chat />
                <Menu />
            </Flex>
        </Flex>
    );
}

export default Broadcast;
