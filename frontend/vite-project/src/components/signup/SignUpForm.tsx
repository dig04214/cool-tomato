import { useState } from "react";
import {
    Input,
    InputGroup,
    InputRightElement,
    Button,
    FormControl,
    FormHelperText,
    Select,
    FormLabel,
    Text
} from "@chakra-ui/react";
import { ViewIcon, ViewOffIcon, CheckIcon } from "@chakra-ui/icons";
import { Tooltip } from '@chakra-ui/react'
import { useNavigate } from "react-router";
import { signupUserAPI, checkIdAPI, sendEmailAPI, checkEmailAPI } from "../../api/user";

function SignUpForm() {
    const navigate = useNavigate();
    // 입력 받을 값: 아뒤, 비번, 이멜, 이멜인증, 닉넴, 성별, 생일
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [passwordAgain, setPasswordAgain] = useState("");
    const [email, setEmail] = useState("");
    const [emailVerification, setEmailVerification] = useState("");
    const [nickname, setNickname] = useState("");
    const [sex, setSex] = useState("M");
    const [birthday, setBirthday] = useState("");
    // 비밀번호 버튼들
    const [show, setShow] = useState(false);
    const handleClick = () => setShow(!show)
    const [show2, setShow2] = useState(false);
    const handleClick2 = () => setShow2(!show2)
    const [check, setCheck] = useState(false);
    const [check2, setCheck2] = useState(false);
    // 유효성 검사
    const [isUsernameValid, setIsUsernameValid] = useState(false);
    const [isPasswordValid, setIsPasswordValid] = useState(false);
    const [isEmailValid, setIsEmailValid] = useState(false);
    const [sendcode, setSendcode] = useState(false)

    // 안내메시지
    const [validMessage, setValidMessage] = useState({
        idMessage: "",
        passwordMessage: "",
        passwordConfirmMessage: "",
        emailMessage: "",
      });

    function handleUsername(e: React.ChangeEvent<HTMLInputElement>) {
        const inputValue = e.target.value;
        const cleanedValue = inputValue.replace(/[^A-Za-z0-9]/g, '');
        // 한글 입력 제한
        if (/[ㄱ-ㅎㅏ-ㅣ가-힣]/.test(inputValue)) {
            setValidMessage({ ...validMessage, idMessage: "한글을 입력할 수 없습니다." });
            return;
        }
        // 특문 제한
        if (/[~!@#$%";'^,&*()_+|</>=>`?:{[\]}]/g.test(inputValue)) {
            setValidMessage({ ...validMessage, idMessage: "특수문자를 입력할 수 없습니다." });
            return;
        }
        // 글자 수 제한
        const limitedValue = cleanedValue.substring(0, 10);
        if (cleanedValue.length > 10) {
            setValidMessage({ ...validMessage, idMessage: "아이디는 최대 10자까지 가능합니다." });
            return;
        }
        setValidMessage({ ...validMessage, idMessage: "" });
        setUsername(limitedValue);
    }

    // 중복확인 버튼 클릭 시
    const handleCheckId = () => {
        checkIdAPI({ id: username }).then((result) => {
            if (result === 1) {
                setIsUsernameValid(true)
                setValidMessage({ ...validMessage, idMessage: "사용할 수 있는 아이디입니다." });
            } else {
                setValidMessage({ ...validMessage, idMessage: "중복된 아이디입니다." });
            }
        });
    };

    function handlePassword(e: React.ChangeEvent<HTMLInputElement>) {
        const inputValue = e.target.value;
        const regex = /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*]).{8,}$/;
        // 체크아이콘 표시를 위해
        setCheck(regex.test(inputValue));
        setPassword(inputValue);
    }

    function handlePasswordConfirm(e: React.ChangeEvent<HTMLInputElement>) {
        const inputValue = e.target.value;
        const regex = /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*]).{8,}$/;
        // 동일한지 확인
        if (password === inputValue) {
            setCheck2(regex.test(inputValue));
            setIsPasswordValid(true);
            setValidMessage({ ...validMessage, passwordMessage: "" });
        } else {
            setValidMessage({ ...validMessage, passwordMessage: "비밀번호가 일치하지 않습니다" });
        }
        setPasswordAgain(inputValue)
    }

    function handleEmail(e: React.ChangeEvent<HTMLInputElement>) {
        const inputValue = e.target.value;
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        // 올바른 이메일 형식일 때 이메일 전송버튼 활성화
        setSendcode(regex.test(inputValue))
        setEmail(inputValue);
    }

    const handleCheckEmail = () => {
        sendEmailAPI({email: email}).then((result) => {
            if (result === 1) {
                setValidMessage({ ...validMessage, emailMessage: "인증번호가 전송되었습니다!" });
            }
        })
    }

    const handleCheckCode = () => {
        checkEmailAPI({email: email, code: emailVerification}).then((result) => {
            if (result === 1) {
                setValidMessage({ ...validMessage, emailMessage: "이메일 인증이 완료되었습니다" });
                setIsEmailValid(true)
            } else {
                setValidMessage({ ...validMessage, emailMessage: "인증번호를 다시 확인해주세요" });
            }
        })
    }

    async function onSubmit(event: React.SyntheticEvent): Promise<void> {
        event.preventDefault();
        // TODO: 회원가입 비동기 통신
        // 모든 조건이 True일 때 회원가입 제출
        if (isUsernameValid === false) {
            alert("아이디 중복확인해주세요!")
        } else if (isPasswordValid === false) {
            alert("비밀번호 확인해주세요!!")
        } else if (isEmailValid === false) {
            alert("이메일 인증해주세요!")
        } else {
            const userData = {
                loginId: username,
                password: password,
                email: email,
                nickname: nickname,
                sex: sex,
                birthday: birthday,
            };
            const response = await signupUserAPI(userData);
            if (response === 1) {
                navigate('/v1/sign')
            } else if (response === 33) {
                alert("이미 회원가입된 이메일입니다. 로그인해주세요")
            }
        }
    }
    
    return (
        <>
            <form
                onSubmit={onSubmit}
                style={{ width: "100%", height: "100vh" }}
            >
                <FormControl my={2}  isRequired>
                    <FormLabel>
                        <Text as={"b"}>아이디</Text>
                    </FormLabel>
                    <Tooltip label="아이디는 영문, 숫자 구성으로 30자 이내 작성할 수 있습니다" bg='gray.400' aria-label='A tooltip'>
                        <InputGroup size="md">
                            <Input
                                focusBorderColor="themeGreen.500"
                                placeholder="ID"
                                size="md"
                                // autoComplete="username"
                                value={username}
                                onChange={handleUsername}
                            />
                            <InputRightElement width="4.5rem" pr={"1"}>
                                <Button
                                    h="1.75rem"
                                    size="sm"
                                    colorScheme="themeGreen"
                                    variant="ghost"
                                    onClick={handleCheckId}
                                    borderRadius="md"
                                    _hover={{
                                        bg: "themeGreen.500",
                                        color: "white",
                                    }}
                                >
                                    중복확인
                                </Button>
                            </InputRightElement>
                        </InputGroup>
                    </Tooltip>
                    <FormHelperText>{validMessage.idMessage}</FormHelperText>
                </FormControl>
                
                <FormControl my={2} isRequired>
                    <FormLabel>
                        <Text as={"b"}>비밀번호</Text>
                    </FormLabel>
                    <Tooltip bg='gray.400' label="최소 8자 이상, 최소한 하나의 대문자, 하나의 소문자, 하나의 숫자, 하나의 특수문자를 포함, 공백 허용하지 않습니다" aria-label='A tooltip'>
                        <InputGroup size="md" mb={2}>
                            <Input
                                focusBorderColor="themeGreen.500"
                                placeholder="password"
                                size="md"
                                autoComplete="current-password"
                                value={password}
                                onChange={handlePassword}
                                type={show ? 'text' : 'password'}
                                id="password"
                            />
                            <InputRightElement>
                                {check ? (
                                    <CheckIcon color="green.500" mr={"1"} />
                                    ) : (
                                        ''
                                )}
                                <Button h='1.75rem' size='sm' variant="ghost" onClick={handleClick}>
                                {show ? (
                                    <ViewIcon color="grey" />
                                ) : (
                                    <ViewOffIcon color="grey" />
                                )}    
                                </Button>
                            </InputRightElement>
                        </InputGroup>
                    </Tooltip>

                    <InputGroup size="md">
                        <Input
                            focusBorderColor="themeGreen.500"
                            placeholder="password again"
                            size="md"
                            value={passwordAgain}
                            onChange={handlePasswordConfirm}
                            type={show2 ? 'text' : 'password'}
                            id="passwordAgain"
                        />
                        <InputRightElement>
                            {check2 ? (
                                <CheckIcon color="green.500" mr={"1"} />
                                ) : (
                                    ''
                            )}
                            <Button h='1.75rem' size='sm' variant="ghost" onClick={handleClick2}>
                                {show2 ? (
                                    <ViewIcon color="grey" />
                                    ) : (
                                    <ViewOffIcon color="grey" />
                                )}
                            </Button>
                        </InputRightElement>
                    </InputGroup>
                    <FormHelperText>{validMessage.passwordMessage}</FormHelperText>
                </FormControl>

                <FormControl my={2} isRequired>
                    <FormLabel>
                        <Text as={"b"}>이메일</Text>
                    </FormLabel>
                    <InputGroup size="md" mb={"2"}>
                        <Input
                            focusBorderColor="themeGreen.500"
                            placeholder="email"
                            size="md"
                            autoComplete="email"
                            value={email}
                            onChange={handleEmail}
                            id="email"
                        />
                        <InputRightElement pr={"1"} w="3.25rem">
                            <Button
                                h="1.75rem"
                                size="sm"
                                colorScheme="themeGreen"
                                variant="ghost"
                                borderRadius="md"
                                _hover={{
                                    bg: "themeGreen.500",
                                    color: "white",
                                }}  
                                isDisabled={!sendcode}
                                onClick={handleCheckEmail}
                            >전송
                            </Button>
                        </InputRightElement>
                    </InputGroup>
                    
                    <InputGroup size="md">
                        <Input
                            focusBorderColor="themeGreen.500"
                            placeholder="verification code"
                            size="md"
                            value={emailVerification}
                            onChange={(e) =>
                                setEmailVerification(e.target.value)
                            }
                        />
                        <InputRightElement pr={"1"}>
                            <Button
                                h="1.75rem"
                                size="sm"
                                colorScheme="themeGreen"
                                variant="ghost"
                                borderRadius="md"
                                _hover={{
                                    bg: "themeGreen.500",
                                    color: "white",
                                }}  
                                onClick={handleCheckCode}
                            >
                                확인
                            </Button>
                        </InputRightElement>
                    </InputGroup>
                    <FormHelperText>{validMessage.emailMessage}</FormHelperText>
                </FormControl>

                <FormControl my={2} isRequired>
                    <FormLabel>
                        <Text as={"b"}>닉네임</Text>
                    </FormLabel>
                    <Input
                        focusBorderColor="themeGreen.500"
                        placeholder="nickname"
                        size="md"
                        autoComplete="nickname"
                        value={nickname}
                        onChange={(e) => {setNickname(e.target.value);} }
                    />

                </FormControl>

                <FormControl my={2} isRequired>
                    <FormLabel>
                        <Text as={"b"}>성별</Text>
                    </FormLabel>
                    <Select
                        placeholder=""
                        value={sex}
                        onChange={(e) => setSex(e.target.value)}
                    >
                        <option value="M">남자</option>
                        <option value="F">여자</option>
                    </Select>
                    
                </FormControl>
                <FormControl my={2} isRequired>
                    <FormLabel>
                        <Text as={"b"}>생년월일</Text>
                    </FormLabel>
                    <Input
                        focusBorderColor="themeGreen.500"
                        placeholder="birthday"
                        size="md"
                        type="date"
                        autoComplete="bday"
                        value={birthday}
                        onChange={(e) => setBirthday(e.target.value)}
                    ></Input>
                </FormControl>
                <Button
                    my={4}
                    w="95%"
                    colorScheme="themeGreen"
                    type="submit"
                    borderRadius="3xl"
                >
                    회원가입
                </Button>
            </form>
        </>
    );
}

export default SignUpForm;
