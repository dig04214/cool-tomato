import { Box } from "@chakra-ui/layout";
import {
    Table,
    Thead,
    Tbody,
    Tr,
    Th,
    Td,
    TableContainer,
    Button,
    Popover, 
    PopoverTrigger, 
    PopoverContent, 
    FocusLock, 
    PopoverArrow, 
    PopoverCloseButton, 
    useDisclosure,
    Stack,
    Input,
    ButtonGroup
  } from '@chakra-ui/react'
import { FaEdit } from "react-icons/fa";

function BlockWord() {
    const { onOpen, onClose, isOpen } = useDisclosure()
    
    return (
        <Box flexDirection="column" w="90%" h="full">
            <Popover
                isOpen={isOpen}
                onOpen={onOpen}
                onClose={onClose}
                placement='right'
                closeOnBlur={false}
            >
                <PopoverTrigger>
                    <Button
                        leftIcon={<FaEdit />}
                        colorScheme="themeGreen"
                        size={"sm"}
                    >
                        금지어 추가하기
                    </Button>
                </PopoverTrigger>
                <PopoverContent p={10}>
                    <FocusLock persistentFocus={false}>
                        <PopoverArrow />
                        <PopoverCloseButton />
                        <Stack spacing={4}>
                            <Input
                                placeholder='어?'
                            />
                            <ButtonGroup display='flex' justifyContent='flex-end'>
                                <Button onClick={onClose} variant='outline'>
                                    취소
                                </Button>
                                <Button isDisabled colorScheme='teal'>
                                    저장
                                </Button>
                            </ButtonGroup>
                        </Stack>
                    </FocusLock>
                </PopoverContent>
            </Popover>

            <TableContainer mt="10">
                <Table variant='simple'>
                    <Thead>
                        <Tr>
                            <Th>금지어</Th>
                            <Th w="10%">관리</Th>
                        </Tr>
                    </Thead>
                    <Tbody>
                        <Tr>
                            <Td>싸피</Td>
                            <Td display="flex" justifyContent="space-around">
                                <Button colorScheme="red">삭제</Button>
                            </Td>
                        </Tr>
                    </Tbody>
                </Table>
            </TableContainer>
        </Box>
    )
}

export default BlockWord
