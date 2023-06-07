import express from "express";
import dotenv from "dotenv";
import bodyParser from "body-parser";
import cookieParser from "cookie-parser";
import cors from "cors";
import db from "./config/Database.js";
import router from "./routes/index.js";
dotenv.config();
const app = express();
try {
    await db.authenticate();
    console.log('Database Connceted..')
} catch (error) {
    console.log(error);
}

app.use(cors({ credentials: true, origin: '0.0.0.0'}))
app.use(express.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(router);
app.use(cookieParser());

app.listen(8080, () => 
    console.log('Server running at port 8080'));
