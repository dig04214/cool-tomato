import { Box, Flex, Icon } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { ChevronLeftIcon, ChevronRightIcon } from "@chakra-ui/icons";
import { FaRegCircle } from "react-icons/fa";

function FaRegCircleActive() {
    return <Icon display={"inline-block"} backgroundColor={"themeGreen.500"} borderRadius={"15px"} boxSize={"15px"} mr={"0.5rem"} as={FaRegCircle} />;
}

function FaRegCircleNone() {
    return <Icon display={"inline-block"} backgroundColor={"#ffffff"} borderRadius={"15px"} boxSize={"15px"} mr={"0.5rem"} as={FaRegCircle} />;
}

export default function MainCarouselComponent() {
    const [slideIndex, setSlideIndex] = useState(0);

    const dummylist = [
        { img: "/img/MainBanner_1.jpg" },
        { img: "/img/MainBanner_2.jpg" },
        { img: "/img/MainBanner_3.jpg" },
        { img: "/img/MainBanner_4.jpg" },
    ];

    // moveBar
    const moveLeft = () => {
        setSlideIndex(slideIndex === 0 ? dummylist.length - 1 : slideIndex - 1);
       
    };
    const moveRight = () => {
        setSlideIndex(slideIndex === dummylist.length - 1 ? 0 : slideIndex + 1);
    };

    if (slideIndex === dummylist.length) {
        setSlideIndex(0);
    }

    useEffect(() => {
        const totalSlides = dummylist.length;
        const interval = setInterval(() => {
            setSlideIndex((currentIndex) => (currentIndex + 1) % totalSlides);
        }, 20000);

        return () => clearInterval(interval);
    }, [dummylist.length]);

    return (
        <Box overflowX={"hidden"} position={"relative"} maxW={"100vw"}>
            <Flex
                overflowX={"hidden"}
                wrap={"nowrap"}
                style={{
                    width: `${100 * dummylist.length}vw`,
                    transition: "all 1000ms ease-in-out",
                    transitionDuration: "1s",
                    transform: `translateX(${
                        -1 * ((100 / dummylist.length) * slideIndex)
                    }%)`,
                }}
            >
                {dummylist.map((data, index) => (
                    <Box key={index} p={2} w={"100%"}>
                        <img className="img" src={data.img}></img>
                    </Box>
                ))}
            </Flex>
            <Box
                position={"absolute"}
                top={"50%"}
                transform={"translateY(-50%)"}
                left={"10%"}
                zIndex={2}
            >
                <ChevronLeftIcon boxSize={10} onClick={moveLeft} />
            </Box>
            <Box
                position={"absolute"}
                top={"50%"}
                transform={"translateY(-50%)"}
                right={"10%"}
                zIndex={2}
            >
                <ChevronRightIcon boxSize={10} onClick={moveRight} />
            </Box>
            <Box
                position={"absolute"}
                bottom={"0"}
                color={"white"}
                width={"100%"}
                textAlign={"center"}
                mb={"1rem"}
            >
                {slideIndex === 0 ? <FaRegCircleActive /> : <FaRegCircleNone />}
                {slideIndex === 1 ? <FaRegCircleActive /> : <FaRegCircleNone />}
                {slideIndex === 2 ? <FaRegCircleActive /> : <FaRegCircleNone />}
                {slideIndex === 3 ? <FaRegCircleActive /> : <FaRegCircleNone />}
            </Box>
        </Box>
    );
}
