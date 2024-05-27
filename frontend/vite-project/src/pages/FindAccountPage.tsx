import React from "react";
import { Center } from "@chakra-ui/react";
import FindIdForm from "../components/findaccount/FindIdForm";
import PwdRecoverForm from "../components/findaccount/PwdRecoverForm";
import FindAccountResult from "../components/findaccount/FindAccountResult";

function FindAccountPage(props: { type: string }) {
    if (props.type === "username") {
        return (
            <>
                <Center h={"80vh"}>
                    <FindIdForm />
                </Center>
            </>
        );
    } else if (props.type === "password") {
        return (
            <>
                <Center h={"80vh"}>
                    <PwdRecoverForm />
                </Center>
            </>
        );
    } else if (props.type === "result") {
        return (
            <>
                <Center h={"80vh"}>
                    <FindAccountResult />
                </Center>
            </>
        );
    }
}

export default FindAccountPage;
