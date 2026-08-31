import poolInst from '../database/db.js';
import bcrypt from 'bcrypt';
import jwt from 'jsonwebtoken';

// registration controller function
export const registerUser = async(req, res) => {
  try {
    const { email, password } = req.body;
    if(!email || !password) {
      return res.status(400).json({error: 'Email and Password are required'});
    }
    const salt = await bcrypt.genSalt(10);
    const hashedPassword = await bcrypt.hash(password, salt);
    const result = await poolInst.query('INSERT INTO users (email, password) VALUES ($1, $2) RETURNING id, email', [email, hashedPassword]);
    res.status(201).json({ user: result.rows[0] });
  }
  catch(err) {
    console.error('Registration Error: ', err);
    res.status(400).json({ error: "Registration failed" });
  }
};

// login controller function
export const loginUser = async(req, res) => {
  try {
    const { email, password } = req.body;
    if(!email || !password) {
      return res.status(400).json({ error: 'Email and Password are required' });
    }
    const result = await poolInst.query('SELECT id, email, password FROM users WHERE email = $1', [email]);
    if(result.rows.length === 0) {
      return res.status(401).json({ message: "Invalid Credentials" });
    }

    const user = result.rows[0];
    const isMatch = await bcrypt.compare(password, user.password);

    // rather than using result we can use the whole reuslt obbject 
    // to check for verification of password
    if(!isMatch) {
      return res.status(401).json({ message: 'Invalid credentials' });
    }

    // payload only needs ID here
    const payload = {
      id: user.id
    };
    const token = jwt.sign(payload, process.env.JWT_SECRET, { expiresIn: '1h' });
    res.status(200).json({message: 'Login successful', token});
  } catch(err) {
    console.error("Login Error: ", err);
    return res.status(500).json({ error: "Internal Server Error" });
  }
};
