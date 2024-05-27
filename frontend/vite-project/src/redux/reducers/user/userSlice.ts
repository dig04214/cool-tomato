import { createSlice } from "@reduxjs/toolkit";
import { UserState } from "../../../types/DataTypes";
// import { loginUserAction } from "../../actions/user/userAction";
import {
    loginUserThunk,
    logoutUserThunk,
    testUserThunk,
} from "../../thunk/user/userThunk";

const initialState: UserState = {
    userId: 0,
    nickname: "",
    profileImg: "",
    auth: "INIT",
    accessToken: "",
    refreshToken: "",
};

const userSlice = createSlice({
    name: "user",
    initialState,
    // reducers: {},
    reducers: {
        resetAuth: (state) => {
            state.auth = "INIT";
        },
        setAuthSeller: (state) => {
            state.auth = "SELLER";
        },
    },
    extraReducers: (builder) => {
        builder
            .addCase(loginUserThunk.fulfilled, (state, action) => {
                // console.log(
                //     "userSlice loginUserThunk.fulfilled action.payload: " +
                //         typeof action.payload.profileImg
                // );
                // console.log(
                //     "userSlice loginUserThunk.fulfilled action.payload: " +
                //         action.payload.profileImg
                // );
                // state = action.payload;
                state.userId = action.payload.userId;
                state.nickname = action.payload.nickname;
                state.profileImg = action.payload.profileImg;
                state.auth = action.payload.auth;
                state.accessToken = action.payload.accessToken;
                state.refreshToken = action.payload.refreshToken;
            })
            .addCase(loginUserThunk.rejected, (state, action) => {
                // console.log(
                //     "userSlice loginUserThunk.rejected action: " + action.type
                // );
                state.auth = "FAIL";
            })
            .addCase(testUserThunk.fulfilled, (state, action) => {
                // console.log(
                //     "userSlice testUserThunk.fulfilled action.payload: " +
                //         action.payload
                // );
                state.accessToken = action.payload;
            })
            .addCase(logoutUserThunk.fulfilled, (state, action) => {
                // console.log(
                //     "userSlice logoutUserThunk.fulfilled action.payload: " +
                //         action.payload
                // );
                state.userId = 0;
                state.nickname = "";
                state.profileImg = "";
                state.accessToken = "";
                state.auth = "INIT";
            });
        // .addCase(updateProfileThunk.fulfilled, (state, action) => {
        //     state.profileImg = action.payload.profileImg;
        //     state.auth = action.payload.auth;
        //     state.accessToken = action.payload.accessToken;
        //     state.refreshToken = action.payload.refreshToken;
        // });
    },
});

// export const { login } = userSlice.actions;
export const { resetAuth, setAuthSeller } = userSlice.actions;
export default userSlice.reducer;
