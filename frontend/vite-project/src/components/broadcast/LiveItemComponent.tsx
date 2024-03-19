import { Box } from "@chakra-ui/react";
import "../../css/ItemListComponentcss.css";

interface GoodsProps {
    img: string;
    price: number;
}

const Goods = ({ img, price }: GoodsProps) => {
    return (
        <>
            <Box>
                <ul>
                    <div className="img">
                        <img src={img}></img>
                    </div>
                    <span className="tagWrap">{price}</span>
                </ul>
            </Box>
        </>
    );
};

export default Goods;
