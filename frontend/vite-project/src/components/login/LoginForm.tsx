import { useState, useEffect } from "react";
import {
    Input,
    InputGroup,
    InputRightElement,
    Button,
    Center,
    FormControl,
    FormErrorMessage,
} from "@chakra-ui/react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { AppDispatch, RootState } from "../../redux/stores/store";
import { loginUserThunk } from "../../redux/thunk/user/userThunk";
import { ViewIcon, ViewOffIcon } from "@chakra-ui/icons";
// import { resetAuth } from "../../redux/reducers/user/userSlice";

function LoginForm() {
    const [show, setShow] = useState(false);
    const [id, setId] = useState("");
    const [password, setPassword] = useState("");

    const dispatch: AppDispatch = useDispatch();
    const navigate = useNavigate();
    const user = useSelector((state: RootState) => state.user);

    const handleClick = () => setShow(!show);
    const isFail = user.auth === "FAIL";

    async function onSubmit(event: React.SyntheticEvent): Promise<void> {
        event.preventDefault();
        // console.log("LoginForm onSubmit ID: " + id);
        // console.log("LoginForm onSubmit Password: " + password);

        // console.log("Login Submit");
        await dispatch(loginUserThunk({ loginId:id, password }));
    }

    // useEffect(() => {
    //     dispatch(resetAuth());
    // }, [dispatch])

    useEffect(() => {
        // console.log("Login Result: " + user.auth);
        // console.log("Login Result type: " + typeof user.auth);
        if (user.auth !== "FAIL" && user.auth !== "INIT") {
            // console.log("Login Success");
            navigate("/v1/main");
        } else if (user.auth === "FAIL") {
            // console.log("Login Fail");
        }
    }, [user, navigate]);

    return (
        <form onSubmit={onSubmit} style={{ width: "80%" }}>
            <FormControl my={4} isInvalid={isFail}>
                <Input
                    focusBorderColor="themeGreen.500"
                    placeholder="ID"
                    size="md"
                    autoComplete="username"
                    value={id}
                    onChange={(e) => setId(e.target.value)}
                />
            </FormControl>
            <FormControl my={2} isInvalid={isFail}>
                <InputGroup size="md">
                    <Input
                        pr="4.5rem"
                        type={show ? "text" : "password"}
                        placeholder="Password"
                        focusBorderColor="themeGreen.500"
                        autoComplete="current-password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <InputRightElement>
                        <Button
                            h="2rem"
                            size="sm"
                            // colorScheme="teal"
                            colorScheme="themeGreen"
                            variant="ghost"
                            // color="themeGreen.500"
                            onClick={handleClick}
                            borderRadius="lg"
                        >
                            {show ? (
                                <ViewIcon color="grey" />
                            ) : (
                                <ViewOffIcon color="grey" />
                            )}
                        </Button>
                    </InputRightElement>
                </InputGroup>
                <FormErrorMessage>
                    아이디, 비밀번호를 확인해 주세요
                </FormErrorMessage>
            </FormControl>
            <Center>
                <Button
                    mt={4}
                    w="100%"
                    colorScheme="themeGreen"
                    type="submit"
                    borderRadius="3xl"
                    py={1}
                >
                    Login
                </Button>
            </Center>
        </form>
    );
}

export default LoginForm;
