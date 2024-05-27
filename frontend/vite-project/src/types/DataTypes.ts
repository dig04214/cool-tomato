export type userInfo = {
    profileImgFile: File | null;
    profileImg: string | null;
    password: string | null;
    newPassword: string | null;
    nickname: string;
    sex: string;
    birthday: string;
};

export type goodsField = {
    categoryId: number | string;
    productName: string;
    productContent: string;
    paymentLink: string;
    price: number | string;
    deliveryCharge: number | string;
    quantity: number | string;
    images: string;
};

export type UserState = {
    userId: number;
    nickname: string;
    profileImg: string;
    auth: "BUYER" | "SELLER" | "ADMIN" | "FAIL" | "INIT";
    accessToken: string;
    refreshToken: string;
};

export type RegisterUser = {
    loginId: string;
    password: string;
    email: string;
    nickname: string;
    sex: string;
    birthday: string;
};

export type RegisterSeller = {
    businessNumber: string;
    businessContent: string;
    mailOrderSalesNumber: string;
    businessAddress: string;
    phoneNumber: string;
};

export interface ItemQnA {
    productQuestionBoardId: number;
    writerId: number;
    writerNickname: string;
    productId: number;
    questionContent: string;
    answerContent: string | null;
    questionRegisterDate: string;
    answerRegisterDate: string | null;
    answer: number;
    isMine: number;
}

export interface ItemDetailInterface {
    productId: number;
    imgSrc: string;
    sellerId: number;
    categoryId: number;
    categoryName: string;
    productName: string;
    productContent: string;
    paymentLink: string;
    price: number;
    deliveryCharge: number;
    quantity: number;
    registerDate: string;
}

export interface AddItemInterface {
    categoryId: number;
    productName: string;
    productContent: string;
    paymentLink: string;
    price: number;
    deliveryCharge: number;
    quantity: number;
}

export interface ItemWholeFetchInterface extends ItemDetailInterface {
    sellerProfile: string;
}

export interface PutItemInterface {
    categoryId: number;
    productName: string;
    productContent: string;
    paymentLink: string;
    price: number;
    deliveryCharge: number;
    quantity: number;
    productId: number | undefined;
}

export interface ItemInfo {
    categoryId: number;
    productName: string;
    productContent: string;
    paymentLink: string;
    price: number;
    deliveryCharge: number;
    quantity: number;
    imgSrc: string;
}

export type broadcastInfo = {
    accessToken: string;
    broadcastTitle: string;
    content: string;
    script: string;
    ttsSetting: boolean;
    chatbotSetting: boolean;
    broadcastStartDate: string;
};

export type editbroadcastInfo = {
    broadcastId: number;
    accessToken: string;
    broadcastTitle: string;
    content: string;
    script: string;
    ttsSetting: boolean;
    chatbotSetting: boolean;
    broadcastStartDate: string;
};

export type followerItem = {
    id: number;
    loginId: string;
    nickname: string;
    profileImg: string;
    userId: number;
};

export type UploadImage = {
    file: File;
    type: string;
};

export type SellerInfo = {
    auth: string;
    bitrhday: string;
    followerCount: number;
    joinDate: string;
    loginId: string;
    nickname: string;
    profileImg: string;
    sellerInfoId: number;
    sex: string;
    userId: number;
};

export type MyQna = {
    answer: number;
    answerContent: string;
    answerRegisterDate: string;
    productContent: string;
    productId: number;
    productName: string;
    productQuestionBoardId: number;
    questionContent: string;
    questionRegisterDate: string;
    writerId: number;
    writerNickname: string;
};

export type AdminUserInfo = {
    id: number;
    auth: string;
    loginId: string;
    email: string;
    nickname: string;
    sex: string;
    birthday: string;
    profileImg: string;
    joinDate: string;
};

export interface liveProductPrice {
    productId: number;
    originalPrice: number;
    price: number;
    discount: number;
}

export interface liveProduct {
    productId: number;
    liveId: number;
    liveFlatPrice: number;
    liveRatePrice: number;
    livePriceStartDate: string;
    livePriceEndDate: string;
    mainProductSetting: boolean;
    seq: number;
}

export interface AcceptSeller {
    approvalStatus: boolean;
    loginId: string;
    nickname: string;
    profileImg: string;
    sellerInfoId: number;
    userId: number;
}

export interface UserProfileEdit {
    profileImg: string | null | undefined;
    password: string;
    newPassword: string;
    nickname: string;
    sex: string;
    birthday: string;
}

export interface LiveFetchInterface {
    liveBroadcastId: number;
    broadcastTitle: string;
    nickName: string;
    viewCount: null;
    sellerId: number;
    broadcastStatus: false;
    startDate: string;
}

export interface LiveCalendarInterface {
    liveProductId: number;
    productId: number;
    imgSrc: string;
    sellerId: number;
    sellerName: string;
    sellerImg: string;
    categoryId: number;
    categoryName: string;
    productName: string;
    productContent: string;
    paymentLink: null;
    price: number;
    deliveryCharge: number;
    quantity: number;
    liveFlatPrice: number;
    liveRatePrice: number;
    livePriceStartDate: string;
    livePriceEndDate: string;
    mainProductSetting: true;
    registerDate: string;
    seq: number;
}

export interface LiveCalendarShownInterface {
    startDate: string;
    nickName: string;
    liveBroadcastId: number;
    broadcastTitle: string;
    imgSrc: string;
    sellerImg: string;
    categoryId: number;
    productName: string;
    price: number;
    liveFlatPrice: number;
    liveRatePrice: number;
}

export interface LiveProductAll {
    liveProductId: number;
    productId: number;
    imgSrc: string;
    sellerId: number;
    sellerName: string;
    sellerImg: string;
    categoryId: number;
    categoryName: string;
    productName: string;
    productContent: string;
    paymentLink: null;
    price: number;
    deliveryCharge: number;
    quantity: number;
    liveFlatPrice: number;
    liveRatePrice: number;
    livePriceStartDate: string;
    livePriceEndDate: string;
    mainProductSetting: boolean;
    registerDate: string;
    seq: null;
}
export interface chatMessageSend extends chatMessageRecv {
    roomId: number;
}

export interface chatMessageRecv {
    senderId: number;
    senderNickname: string;
    message: string;
}
export interface LiveCalendarShownInterface {
    startDate: string;
    nickName: string;
    liveBroadcastId: number;
    broadcastTitle: string;
    imgSrc: string;
    sellerImg: string;
    categoryId: number;
    productName: string;
    price: number;
    liveFlatPrice: number;
    liveRatePrice: number;
}

export interface chatbotMessage {
    roomId: number;
    message: string;
    writer: number;
}

export interface DisplayInterface {
    price: number;
    liveFlatPrice: number;
    imgSrc: string;
    liveBroadcastId: number;
    broadcastTitle: string;
}

export interface FirstDisplayInterface {
    price: number;
    liveBroadcastId: number;
    imgSrc: string;
    broadcastTitle: string;
    productName: string;
    liveFlatPrice: number;
    liveRatePrice: number;
}