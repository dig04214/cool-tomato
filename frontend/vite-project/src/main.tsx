import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import { BrowserRouter } from "react-router-dom";
import { Provider } from "react-redux";
import { PersistGate } from "redux-persist/integration/react";
import { store, persistor } from "./redux/stores/store";

ReactDOM.createRoot(document.getElementById("root")!).render(
    <Provider store={store}>
        {/* PersisGate를 통해 리액트가 로딩되기 전에 반드시 로딩되어야 할 컴포넌트가 있는 경우 loading에 해당 컴포넌트를 넣어준다 */}
        <PersistGate loading={null} persistor={persistor}>
            <BrowserRouter>
                <App />
            </BrowserRouter>
        </PersistGate>
    </Provider>
);
