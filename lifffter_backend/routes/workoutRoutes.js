import express from 'express';
import authenticateJWT from '../middleware/authMiddleware.js';
import { createRoutine, getRoutine } from '../controllers/workoutController.js';

const router = express.Router();
router.post('/', authenticateJWT, createRoutine);
router.get('/', authenticateJWT, getRoutine);

export default router;