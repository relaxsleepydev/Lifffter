import express from 'express';
import authenticateJWT from '../middleware/authMiddleware.js';
import { startSession, endSession } from '../controllers/workoutSessionController.js';

const router = express.Router();
router.post('/', authenticateJWT, startSession);
router.patch('/:session_id/end', authenticateJWT, endSession);
// using patch as we are updating the data

export default router;