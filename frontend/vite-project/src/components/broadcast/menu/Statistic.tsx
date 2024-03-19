import { Doughnut } from "react-chartjs-2";
import { Chart as ChartJS, ArcElement, Tooltip, Legend, CategoryScale, LinearScale, PointElement, LineElement, Title } from "chart.js";
import { Box, Center, Text } from "@chakra-ui/react";

ChartJS.register(ArcElement, Tooltip, Legend);
ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend
);

export default function Statistic() {
    const dataDoughnut = {
        labels: ["남성", "여성", "20대", "30대", "40대", "50대"],
        datasets: [
            {
                label: "# of Votes",
                data: [1, 1, 3, 5, 2, 3],
                backgroundColor: [
                    "rgba(255, 99, 132, 0.2)",
                    "rgba(54, 162, 235, 0.2)",
                    "rgba(255, 206, 86, 0.2)",
                    "rgba(75, 192, 192, 0.2)",
                    "rgba(153, 102, 255, 0.2)",
                    "rgba(255, 159, 64, 0.2)",
                ],
                borderColor: [
                    "rgba(255, 99, 132, 1)",
                    "rgba(54, 162, 235, 1)",
                    "rgba(255, 206, 86, 1)",
                    "rgba(75, 192, 192, 1)",
                    "rgba(153, 102, 255, 1)",
                    "rgba(255, 159, 64, 1)",
                ],
                borderWidth: 1,
            },
        ],
    };

    return (
        <Box overflowY={"hidden"}>
            <Center mb={"1.5rem"}>
                <Text fontSize={"4xl"} as={"b"}>
                    실시간 통계
                </Text>
            </Center>
            
            <Box bgSize={"1rem"}>
                <Doughnut data={dataDoughnut} />;
            </Box>
        </Box>
    );
}
