import { Box, Flex } from "@chakra-ui/layout";
import { Badge, Accordion,
    AccordionItem,
    AccordionButton,
    AccordionPanel,
    AccordionIcon,
    Divider
} from "@chakra-ui/react";
import { MyQna } from "../../../types/DataTypes";

function BuyerQnaItems({questions} : {questions : MyQna}) {
  
    return (
        <>
        <Accordion allowMultiple>
            <AccordionItem>
                <h2>
                    <AccordionButton>
                        <Flex alignItems='baseline'>
                            {questions.answer === 0 && (
                                    <Badge colorScheme="yellow">미답변</Badge>
                                )}
                                {questions.answer === 1 && (
                                    <Badge colorScheme="green">답변완료</Badge>
                                )}

                            <Box
                                color='gray.500'
                                fontWeight='semibold'
                                letterSpacing='wide'
                                fontSize='m'
                                textTransform='uppercase'
                                ml='2'
                                >
                                문의한 상품: {questions.productName}
                            </Box>
                        </Flex>
                        <AccordionIcon />
                    </AccordionButton>
                </h2>
                <AccordionPanel pb={4}>
                    {questions.questionContent}
                    <Divider />
                    {questions.answerContent}
                </AccordionPanel>
            </AccordionItem>
        </Accordion>
    </>
    )
}

export default BuyerQnaItems