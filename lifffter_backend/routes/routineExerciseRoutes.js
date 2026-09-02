import express from 'express';
import authenticateJWT from '../middleware/authMiddleware.js';
import { addExercise, getExercisesByRoutine } from '../controllers/routineExerciseController.js';

const router = express.Router();
router.post('/', authenticateJWT, addExercise);
router.get('/:routine_id', authenticateJWT, getExercisesByRoutine);
// :routine_id to tell express that capture the id typed by client
// and put it inside req.params.routine_id

export default router;