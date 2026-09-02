import express from 'express';
import authenticateJWT from '../middleware/authMiddleware.js';
import getAllExercises from '../controllers/exerciseController.js';

const router = express.Router();
router.get('/', authenticateJWT, getAllExercises);

export default router;