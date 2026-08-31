import express from 'express';
import authenticateJWT from "../middleware/authMiddleware.js";
import getUserProfile from "../controllers/userController.js";

const router = express.Router();
router.get('/profile', authenticateJWT, getUserProfile);

export default router;