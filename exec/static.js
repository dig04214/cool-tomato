const express = require("express")
const fs = require("fs")
const http = require("http")
const https = require("https")
const app = express();

const HTTP_PORT = 80
const HTTPS_PORT = 443

const options = {
    key: fs.readFileSync("./cert/privkey.pem"),
    cert: fs.readFileSync("./cert/fullchain.pem")
}

app.use(express.static('dist'));

// http.createServer(app).listen(HTTP_PORT)
https.createServer(options, app).listen(HTTPS_PORT)
