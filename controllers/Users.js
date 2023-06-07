import Users from "../models/UserModel.js";
import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";

export const getUsers = (async(req, res)=> {
    try {
        const users = await Users.findAll({
            attributes: ['id', 'name', 'email']
        });
        res.json(users);
    } catch (error) {
        console.log(error);
    }
});

export const Register = (async(req, res) => {
    
        const { name, email, password, confirmPassword } = req.body;
        const salt = await bcrypt.genSalt();

        // Periksa kecocokan password
        if (password !== confirmPassword) {
                return res.status(400).json({ msg: "Passwords do not match" });
        };

        // Periksa panjang password
        if (password.length < 8){
            return res.status(400).json({ msg: "Password must be at least 8 characters" });
        };

        // Buat hash dari password
        const hashedPassword = await bcrypt.hash(password, salt);
        try {
        // Simpan pengguna ke dalam basis data
        await Users.create({
            name: name,
            email: email,
            password: hashedPassword
        });
        res.json({ msg: "Registered successfully" });
    } catch (error) {
        console.log(error);
    }
});

export const Login = async (req, res) => {
    try {
      const user = await Users.findAll({
        where: {
          email: req.body.email
        }
      });
  
      if (user.length === 0) {
        return res.status(404).json({ msg: "Email not found" });
      }
  
      const match = await bcrypt.compare(req.body.password, user[0].password);
      if (!match) {
        return res.status(400).json({ msg: "Wrong password" });
      }
  
      const id = user[0].id;
      const name = user[0].name;
      const email = user[0].email;
  
      const userData = { id, name, email, accessToken: "" };
  
      const accessToken = jwt.sign(userData, process.env.ACCESS_TOKEN_SECRET, {
        expiresIn: "1d"
      });
      const refreshToken = jwt.sign(userData, process.env.REFRESH_TOKEN_SECRET, {
        expiresIn: "7d"
      });
  
      await Users.update(
        { refresh_token: refreshToken },
        {
          where: {
            id: id
          }
        }
      );
  
      res.cookie("refreshToken", refreshToken, {
        httpOnly: true,
        maxAge: 24 * 60 * 60 * 1000
      });
  
      userData.accessToken = accessToken;
      res.json(userData);
    } catch (error) {
      res.status(500).json({ msg: "Internal server error" });
    }
  };
  
    
    export const Logout = (async(req, res) => {
        const refreshToken = req.cookies.refreshToken;
        if(!refreshToken) return res.sendStatus(204);
        const user = await Users.findAll({
            where: {
                refresh_token: refreshToken
            }
        });
        if(!user[0]) return res.sendStatus(204);
        const userId = user[0].id;
        await Users.update({refresh_token:null}, {
            where:{
                id: userId
            }
        });
        res.clearCookie('refreshToken');
        return res.sendStatus(200);
    });
