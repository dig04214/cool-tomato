import {
    AlertDialog,
    AlertDialogOverlay,
    AlertDialogContent,
    AlertDialogHeader,
    AlertDialogCloseButton,
    AlertDialogBody,
    AlertDialogFooter,
    Button,
} from "@chakra-ui/react";
import { useRef } from "react";

function QnaDeleteAlertDialog({
    isOpen,
    handleCancel,
    handleDelete,
}: {
    isOpen: boolean;
    handleCancel: () => void;
    handleDelete: () => void;
}) {
    const cancelRef = useRef(null);
    return (
        <>
            {/* <Button hidden onClick={onOpen}></Button> */}
            <AlertDialog
                motionPreset="slideInBottom"
                leastDestructiveRef={cancelRef}
                onClose={handleCancel}
                isOpen={isOpen}
                isCentered
            >
                <AlertDialogOverlay />

                <AlertDialogContent>
                    <AlertDialogHeader>문의 삭제</AlertDialogHeader>
                    <AlertDialogCloseButton />
                    <AlertDialogBody>
                        정말 문의를 삭제하시겠습니까?
                    </AlertDialogBody>
                    <AlertDialogFooter>
                        <Button ref={cancelRef} onClick={handleCancel}>
                            취소
                        </Button>
                        <Button
                            colorScheme="themeRed"
                            ml={3}
                            onClick={handleDelete}
                        >
                            삭제
                        </Button>
                    </AlertDialogFooter>
                </AlertDialogContent>
            </AlertDialog>
        </>
    );
}

export default QnaDeleteAlertDialog;
