// resources/static/firebase-messaging-sw.js
importScripts('https://www.gstatic.com/firebasejs/4.8.1/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/4.8.1/firebase-messaging.js');
// Initialize Firebase
var config = {
    apiKey: "MASKING_KEY",
    authDomain: "MASKING_DOMAIN",
    projectId: "handsome-potato",
    storageBucket: "MASKING_BUCKET",
    messagingSenderId: "MASKING_SENDER_ID",
    appId: "MASKING_APP_ID",
    measurementId: "MASKING_MEASUREMENT_ID",
};
firebase.initializeApp(config);
const messaging = firebase.messaging();
messaging.setBackgroundMessageHandler(function (payload) {
    const title = "Hello World";
    const options = { body: payload.data.status };
    return self.registration.showNotification(title, options); }
);

