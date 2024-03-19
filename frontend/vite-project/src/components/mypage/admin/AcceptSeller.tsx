import { Box } from "@chakra-ui/layout";
import {
    Badge,
    Button,
    Accordion,
    AccordionButton,
    AccordionItem,
    AccordionPanel,
    AccordionIcon,
  } from '@chakra-ui/react'
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../../redux/stores/store";
import { getSellerApplicationsAPI, approveSellerApplicationAPI } from "../../../api/user";
import { AcceptSeller } from "../../../types/DataTypes";

function AcceptSeller() {
    const user = useSelector((state: RootState) => state.user);
    // const [totalsize, setTotalsize] = useState(0)
    const [allUserData, setAllUserData] = useState<AcceptSeller[]>([]);

    useEffect(() => {
        getSellerApplicationsAPI(0, 18, user.accessToken)
        .then((response) => {
            const sellers = [...response.data.sellers];  // 복사본을 만듭니다.
    
            // approvalStatus가 false인 요청을 상위에 위치시킵니다.
            sellers.sort((a, b) => a.approvalStatus - b.approvalStatus);
    
            setAllUserData(sellers);
        })
        .catch((error) => {
            console.log(error)
        })
    
    }, [])

    return (
        <Box flexDirection="column" w="90%" h="full">
            <UserList allUserData={allUserData} />
        </Box>
    )
}

export default AcceptSeller;


function UserList({ allUserData }: { allUserData: AcceptSeller[]}) {
    const user = useSelector((state: RootState) => state.user);

    const approvefunction = (sellerid : number, at : string) => {
        try {
            approveSellerApplicationAPI(sellerid, at).then((res) => {console.log(res)})
        } catch(err) {
            console.log(err)
        }
    }
    

    return (
        <>
        <Accordion allowMultiple>
            {allUserData.map((item, index) => (
                
                <AccordionItem key={index}>
                    {/* 아코디언 초기 상태 */}
                    <AccordionButton justifyContent="space-between">
                        <Box
                            color='gray.500'
                            fontWeight='semibold'
                            letterSpacing='wide'
                            fontSize='xl'
                            textTransform='uppercase'
                            ml='2'
                        >
                            {item.approvalStatus ? (
                                    <Badge colorScheme='green'>승인완료</Badge>
                                ) : (
                                    <Badge colorScheme='red'>새요청</Badge>)}
                            {item.userId} : {item.loginId} / {item.nickname}
                        </Box>
                        <AccordionIcon />
                    </AccordionButton>

                    <AccordionPanel>
                        {item.sellerInfoId}
                        
                        <Button onClick={()=>{approvefunction(item.sellerInfoId, user.accessToken)}}>
                            승인
                        </Button>
                    </AccordionPanel>

                    </AccordionItem>
            ))}
        </Accordion>
            
        </>
    );
}