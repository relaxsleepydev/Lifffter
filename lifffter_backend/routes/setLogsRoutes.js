import express from 'express';
import authenticateJWT from "../middleware/authMiddleware.js";
import { logSet, getSetsBySession } from "../controllers/setLogsController.js";

const router = express.Router();
router.post('/', authenticateJWT, logSet);
router.get('/:session_id', authenticateJWT, getSetsBySession); 
// we need to extract id from url as we are
// fetching sets for a specific session, so setting
// the path to /:session_id

export default router;