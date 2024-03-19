import { Box, Flex, Center } from "@chakra-ui/layout";
import {
    Avatar,
    Button,
    Text,
    FormControl,
    FormLabel,
    InputGroup,
    Input,
} from "@chakra-ui/react";
import { useNavigate } from "react-router-dom";
import { UploadImage, UserProfileEdit } from "../types/DataTypes";
import { useEffect, useState } from "react";
import { fetchProfile, setProfile } from "../api/user";
import { useSelector } from "react-redux";
import { RootState } from "../redux/stores/store";

export default function UserinfoPage() {
    const navigate = useNavigate();
    const [editProfile, setEditProfile] = useState<
        UserProfileEdit | undefined
    >();
    const at = useSelector((state: RootState) => {
        return state.user.accessToken;
    });
    const rt = useSelector((state: RootState) => {
        return state.user.refreshToken;
    });

    useEffect(() => {
        fetchProfile(at, rt).then((res) => {
            setEditProfile({
                profileImg: `${res.data.data.profileImg}`,
                password: "",
                newPassword: "",
                nickname: `${res.data.data.nickname}`,
                sex: `${res.data.data.sex}`,
                birthday: `${res.data.data.birthday}`,
            });
            setPreviewUrl(res.data.data.profileImg);
        });
    }, []);

    const [fileName, setFileName] = useState<UploadImage | undefined>();
    const [previewURL, setPreviewUrl] = useState<string | null>(null);
    const formData = new FormData();

    const handleProfilePicture = (target: string) => {
        setEditProfile({
            profileImg: target,
            password: editProfile?.password ?? "",
            newPassword: editProfile?.newPassword ?? "",
            nickname: editProfile?.nickname ?? "",
            sex: editProfile?.sex ?? "",
            birthday: editProfile?.birthday ?? "",
        });
    };

    const handlePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
        const password = e.target.value;
        setEditProfile({
            profileImg: editProfile?.profileImg ?? null,
            password: password,
            newPassword: editProfile?.newPassword ?? "",
            nickname: editProfile?.nickname ?? "",
            sex: editProfile?.sex ?? "",
            birthday: editProfile?.birthday ?? "",
        });
    };

    const handleNewPassword = (e: React.ChangeEvent<HTMLInputElement>) => {
        const password = e.target.value;
        setEditProfile({
            profileImg: editProfile?.profileImg ?? null,
            password: editProfile?.password ?? "",
            newPassword: password,
            nickname: editProfile?.nickname ?? "",
            sex: editProfile?.sex ?? "",
            birthday: editProfile?.birthday ?? "",
        });
    };

    const handleNickname = (e: React.ChangeEvent<HTMLInputElement>) => {
        const nickname = e.target.value;
        setEditProfile({
            profileImg: editProfile?.profileImg ?? null,
            password: editProfile?.password ?? "",
            newPassword: editProfile?.newPassword ?? "",
            nickname: nickname,
            sex: editProfile?.sex ?? "",
            birthday: editProfile?.birthday ?? "",
        });
    };

    useEffect(() => {
        if (fileName?.file) {
            const fileURL = URL.createObjectURL(fileName.file);
            setPreviewUrl(fileURL);

            return () => {
                URL.revokeObjectURL(fileURL);
            };
        } else {
            setPreviewUrl(null);
        }
    }, [fileName]);

    const profileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const files = e.target.files;

        if (files && files[0]) {
            setFileName({
                file: files[0],
                type: files[0].name,
            });
            handleProfilePicture(files[0].name);
        }
    };

    const clearProfile = () => {
        setEditProfile({
            profileImg: null,
            password: editProfile?.password ?? "",
            newPassword: editProfile?.newPassword ?? "",
            nickname: editProfile?.nickname ?? "",
            sex: editProfile?.sex ?? "",
            birthday: editProfile?.birthday ?? "",
        });
        setPreviewUrl(null);
    };

    // const updateTokens = (at : string, rt : string) => {
    //     localStorage.setItem('accessToken', at)
    //     localStorage.setItem('refreshToken', rt)

    //     store.dispatch({
    //         type: 'UPDATE_TOKENS',
    //         payload: {at, rt},
    //     })
    // }

    const profileSubmit = async () => {
        if (fileName !== undefined) {
            formData.append("modifyUserRequest", JSON.stringify(editProfile));
            formData.append("profileImgFile", fileName.file);

            try {
                const response = await setProfile(formData, at, rt);

                console.log(response.data);

                // dispatch(updateProfileThunk({response.accessToken, response.refreshToken}))

                navigate("/v1/main");
            } catch (err) {
                console.log(err);
            }
        }
    };

    return (
        <Box minH="100vh" mb="10" paddingBlock="6rem">
            <Center
                className="response_title"
                fontFamily="GmkBold"
                fontSize={{ base: "4rem", md: "5rem", lg: "6rem" }}
                color={"themeFontGreen.500"}
            >
                회원정보수정
            </Center>

            <Flex
                m="auto"
                border="2px"
                borderColor="themeFontGreen.500"
                overflow="scroll"
                rounded="lg"
                w="85vw"
                minH="85vh"
            >
                <Flex
                    m="auto"
                    direction={{ base: "column", lg: "row" }}
                    rounded="lg"
                    w="80vw"
                    maxH={{ base: "auto", lg: "80vh" }}
                    px="2"
                >
                    <Box w={{ base: "100%", lg: "25%" }} pr="4">
                        <Box w="full" bg="white" rounded="lg" overflow="hidden">
                            <Flex direction="column" align="center" py="6">
                                <Button
                                    borderRadius="3xl"
                                    onClick={() => {
                                        navigate("/v1/buyer/");
                                    }}
                                    mb={"1rem"}
                                >
                                    마이페이지로 돌아가기
                                </Button>
                                <Avatar
                                    mt={"1rem"}
                                    mb={"1rem"}
                                    size="xl"
                                    src={
                                        previewURL
                                            ? `${previewURL}`
                                            : "/img/default_profile.jpeg"
                                    }
                                />
                                <Button
                                    mt={"1rem"}
                                    px={4}
                                    py={2}
                                    w={"10rem"}
                                    transition="all 0.2s"
                                    borderRadius="md"
                                    borderWidth="3px"
                                    _hover={{
                                        bg: "themeGreen.500",
                                        textColor: "white",
                                    }}
                                    mb={"1.5rem"}
                                >
                                    <input
                                        type="file"
                                        style={{ display: "none" }}
                                        id="profileImg"
                                        accept="image/png, image/jpeg, image/jpg"
                                        onChange={profileChange}
                                    />
                                    <label htmlFor="profileImg">
                                        프로필 이미지 추가
                                    </label>
                                </Button>
                                <Button
                                    px={4}
                                    py={2}
                                    w={"10rem"}
                                    transition="all 0.2s"
                                    borderRadius="md"
                                    borderWidth="3px"
                                    _hover={{ bg: "red", textColor: "white" }}
                                    onClick={clearProfile}
                                >
                                    프로필 사진 삭제
                                </Button>
                            </Flex>
                        </Box>
                    </Box>

                    <Box
                        w="75%"
                        bg="white"
                        rounded="lg"
                        className="custom-scrollbar"
                    >
                        <Flex justify="center" align="center" h="full">
                            <form
                                onSubmit={(event) => {
                                    event.preventDefault();
                                    profileSubmit();
                                }}
                                style={{ width: "100%" }}
                            >
                                <FormControl my={2} mb={"3rem"}>
                                    <FormLabel>
                                        <Text as={"b"}>현재 비밀번호</Text>
                                    </FormLabel>

                                    <InputGroup size="md" mb={2}>
                                        <Input
                                            focusBorderColor="themeGreen.500"
                                            placeholder="password"
                                            size="md"
                                            autoComplete="current-password"
                                            onChange={handlePassword}
                                            id="password"
                                        />
                                    </InputGroup>
                                </FormControl>

                                <FormControl my={2} mb={"3rem"}>
                                    <FormLabel>
                                        <Text as={"b"}>새 비밀번호</Text>
                                    </FormLabel>
                                    <InputGroup size="md">
                                        <Input
                                            focusBorderColor="themeGreen.500"
                                            placeholder="new password"
                                            size="md"
                                            id="newPassword"
                                            onChange={handleNewPassword}
                                        />
                                    </InputGroup>
                                </FormControl>

                                <FormControl my={2} mb={"3rem"}>
                                    <FormLabel>
                                        <Text as={"b"}>닉네임</Text>
                                    </FormLabel>
                                    <Input
                                        focusBorderColor="themeGreen.500"
                                        placeholder="nickname"
                                        size="md"
                                        autoComplete="nickname"
                                        onChange={handleNickname}
                                    />
                                </FormControl>
                                <Flex justifyContent={"space-between"}>
                                    <Button
                                        my={4}
                                        w="25%"
                                        colorScheme="themeGreen"
                                        type="submit"
                                        borderRadius="3xl"
                                    >
                                        정보수정하기
                                    </Button>
                                    <Button
                                        my={4}
                                        w="25%"
                                        type="submit"
                                        colorScheme="red"
                                        borderRadius="3xl"
                                    >
                                        회원 탈퇴
                                    </Button>
                                </Flex>
                            </form>
                        </Flex>
                    </Box>
                </Flex>
            </Flex>
        </Box>
    );
}
