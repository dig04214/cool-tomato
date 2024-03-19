import {
    Button,
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalCloseButton,
    ModalBody,
    ModalFooter,
    Text,
    Textarea,
} from "@chakra-ui/react";
import { useState, ChangeEvent } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../../redux/stores/store";
import { useParams } from "react-router-dom";
import { postItemQnA } from "../../../api/itemQnA";

function QnaRegistrationModal({
    isOpen,
    handleModalOpen,
    refreshQnA,
}: {
    isOpen: boolean;
    handleModalOpen: () => void;
    refreshQnA: (newPage: number, newSize: number, isReset: boolean) => void;
}) {
    const [questionContent, setQuestionContent] = useState("");
    const accessToken = useSelector(
        (state: RootState) => state.user.accessToken
    );
    const { id } = useParams() as { id: string };
    const productId = parseInt(id);

    const handleInputChange = (e: ChangeEvent<HTMLTextAreaElement>) =>
        setQuestionContent(e.target.value);

    const handleClose = () => {
        setQuestionContent("");
        handleModalOpen();
    };

    const registerQuestion = () => {
        const data = {
            productId,
            questionContent,
        };
        postItemQnA(data, accessToken)
            .then(() => {
                refreshQnA(0, 1, false);
                handleClose();
            })
            .catch((err) => {
                console.log(err);
            });
    };

    return (
        <>
            <Modal
                isOpen={isOpen}
                onClose={handleClose}
                motionPreset="slideInBottom"
                isCentered
                size="2xl"
            >
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>문의하기</ModalHeader>
                    <ModalCloseButton onClick={handleModalOpen} />
                    <ModalBody>
                        <Textarea
                            value={questionContent}
                            onChange={handleInputChange}
                            placeholder="문의하실 내용을 입력해 주세요"
                            focusBorderColor="themeGreen.500"
                            size="md"
                            resize="none"
                            mb="1.5rem"
                            h={"30rem"}
                        />
                        <Text textAlign={"center"}>
                            문의에 대한 답변은 상품페이지나 마이페이지에서
                            확인하실 수 있습니다
                        </Text>
                    </ModalBody>

                    <ModalFooter>
                        <Button
                            colorScheme="blue"
                            variant={"outline"}
                            mr={3}
                            onClick={handleClose}
                        >
                            취소
                        </Button>
                        <Button
                            colorScheme="themeGreen"
                            onClick={registerQuestion}
                        >
                            등록
                        </Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    );
}

export default QnaRegistrationModal;
