import { Routes, Route, Navigate } from "react-router-dom";
import { ChakraProvider } from "@chakra-ui/react";
import { Theme, Fonts } from "./theme/Theme";
import LayOut from "./components/common/Layout";
import Search from "./pages/Search";
import LiveList from "./pages/LiveList";
import ItemList from "./pages/ItemList";
import Calendar from "./pages/Calendar";
import BuyerPage from "./pages/BuyerPage";
import SellerPage from "./pages/SellerPage";
import UserinfoPage from "./pages/UserinfoPage";
import LoginPage from "./pages/LoginPage";
import ItemDetail from "./pages/ItemDetail";
import Broadcast from "./pages/BroadcastPage";
import SignUpPage from "./pages/SignUpPage";
import SignPage from "./pages/SignPage";
import FindAccountPage from "./pages/FindAccountPage";
import MainPage from "./pages/MainPage";
import LiveAddForm from "./pages/LiveAddForm";
import BuyerLive from "./pages/BuyerLive";
import ItemAdd from "./pages/ItemAdd";
import NoticePage from "./pages/NoticePage";
import ProfilePage from "./pages/ProfilePage";
import AdminPage from "./pages/AdminPage";
import ItemEditPage from "./pages/ItemEditPage";
import LivePlanEditForm from "./components/mypage/seller/SellerPlanEdit";
import LiveResultPage from "./pages/LiveResultsPage";

function App() {
    return (
        <>
            <ChakraProvider theme={Theme}>
                <Fonts />

                <Routes>
                    <Route path="/" element={<Navigate to={"/v1/main"} />} />
                    <Route path="/v1" element={<LayOut />}>
                        <Route path="main" element={<MainPage />} />
                        <Route path="search" element={<Search />} />
                        <Route path="live/list" element={<LiveList />} />
                        <Route
                            path="items/list/:currentpage"
                            element={<ItemList />}
                        />
                        <Route path="calendar" element={<Calendar />} />
                        <Route path="buyer" element={<BuyerPage />} />
                        <Route path="seller" element={<SellerPage />} />
                        <Route path="admin" element={<AdminPage />} />
                        <Route path="userinfo" element={<UserinfoPage />} />
                        <Route path="login" element={<LoginPage />} />
                        <Route
                            path="items/detail/:id"
                            element={<ItemDetail />}
                        />
                        <Route path="signup" element={<SignUpPage />} />
                        <Route path="signup" element={<SignUpPage />} />
                        <Route path="sign" element={<SignPage />} />
                        <Route
                            path="findid"
                            element={<FindAccountPage type="username" />}
                        />
                        <Route
                            path="pwdrecover"
                            element={<FindAccountPage type="password" />}
                        />
                        <Route path="live/form" element={<LiveAddForm />} />
                        <Route
                            path="live/edit/:broadcastId"
                            element={<LivePlanEditForm />}
                        />
                        <Route
                            path="findaccount"
                            element={<FindAccountPage type="result" />}
                        />
                        <Route path="ItemAdd" element={<ItemAdd />}></Route>
                        <Route path="board/notice" element={<NoticePage />} />
                        <Route
                            path="seller/profile/:sellerId"
                            element={<ProfilePage />}
                        />
                        <Route
                            path="items/edit/:productParams"
                            element={<ItemEditPage />}
                        ></Route>
                    </Route>
                    <Route
                        path="v1/broadcast/:roomId"
                        element={<Broadcast />}
                    />
                    <Route
                        path="v1/live/:roomId"
                        element={<BuyerLive />}
                    ></Route>
                    <Route
                        path="v1/live/result/:broadcastId"
                        element={<LiveResultPage />}
                    />
                </Routes>
            </ChakraProvider>
        </>
    );
}

export default App;
