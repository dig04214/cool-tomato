import { Button } from "@chakra-ui/react";

interface ItemCategoryInterface {
    setCategory: React.Dispatch<React.SetStateAction<number>>;
    setTrigger: React.Dispatch<React.SetStateAction<boolean>>;
    trigger: boolean;
    categorylist: Array<number>;
}

export default function ItemCategory({
    setCategory,
    setTrigger,
    trigger,
    categorylist,
}: ItemCategoryInterface) {
    const clickfunction = (id: number) => {
        setCategory(id);
        {
            trigger ? setTrigger(false) : setTrigger(true);
        }
    };

    return (
        <>
            {categorylist.includes(4) ? (
                <Button
                    onClick={() => clickfunction(4)}
                    colorScheme="teal"
                    variant="solid"
                    mr={"1rem"}
                >
                    과일
                </Button>
            ) : (
                <Button
                    onClick={() => clickfunction(4)}
                    colorScheme="teal"
                    variant="outline"
                    mr={"1rem"}
                >
                    과일
                </Button>
            )}

            {categorylist.includes(5) ? (
                <Button
                    onClick={() => clickfunction(5)}
                    colorScheme="teal"
                    variant="solid"
                    mr={"1rem"}
                >
                    채소
                </Button>
            ) : (
                <Button
                    onClick={() => clickfunction(5)}
                    colorScheme="teal"
                    variant="outline"
                    mr={"1rem"}
                >
                    채소
                </Button>
            )}

            {categorylist.includes(6) ? (
                <Button
                    onClick={() => clickfunction(6)}
                    colorScheme="teal"
                    variant="solid"
                    mr={"1rem"}
                >
                    건류
                </Button>
            ) : (
                <Button
                    onClick={() => clickfunction(6)}
                    colorScheme="teal"
                    variant="outline"
                    mr={"1rem"}
                >
                    건류
                </Button>
            )}

            {categorylist.includes(7) ? (
                <Button
                    onClick={() => clickfunction(7)}
                    colorScheme="teal"
                    variant="solid"
                    mr={"1rem"}
                >
                    견과류
                </Button>
            ) : (
                <Button
                    onClick={() => clickfunction(7)}
                    colorScheme="teal"
                    variant="outline"
                    mr={"1rem"}
                >
                    견과류
                </Button>
            )}

            {categorylist.includes(1) ? (
                <Button
                    onClick={() => clickfunction(1)}
                    colorScheme="teal"
                    variant="solid"
                    mr={"1rem"}
                >
                    해산물
                </Button>
            ) : (
                <Button
                    onClick={() => clickfunction(1)}
                    colorScheme="teal"
                    variant="outline"
                    mr={"1rem"}
                >
                    해산물
                </Button>
            )}

            {categorylist.includes(2) ? (
                <Button
                    onClick={() => clickfunction(2)}
                    colorScheme="teal"
                    variant="solid"
                    mr={"1rem"}
                >
                    생선
                </Button>
            ) : (
                <Button
                    onClick={() => clickfunction(2)}
                    colorScheme="teal"
                    variant="outline"
                    mr={"1rem"}
                >
                    생선
                </Button>
            )}

            {categorylist.includes(3) ? (
                <Button
                    onClick={() => clickfunction(3)}
                    colorScheme="teal"
                    variant="solid"
                    mr={"1rem"}
                >
                    건어물
                </Button>
            ) : (
                <Button
                    onClick={() => clickfunction(3)}
                    colorScheme="teal"
                    variant="outline"
                    mr={"1rem"}
                >
                    건어물
                </Button>
            )}
        </>
    );
}
