import poolInst from '../database/db.js';

const logSet = async(req, res) => {
    try {
        const { session_id, exercise_id, set_number, weight, reps, rir } = req.body;
        const result = await poolInst.query('INSERT INTO set_logs (session_id, exercise_id, set_number, weight, reps, rir) VALUES ($1, $2, $3, $4, $5, $6) RETURNING id', [session_id, exercise_id, set_number, weight, reps, rir]);
        if(result.rows.length === 0) {
            return res.status(400).json({ 
                success: false,
                message: "Failed to log set",
                error: "Database insertion didnt returned record. Please try again!"
            });
        }
        return res.status(201).json({
            success: true,
            message: "Logged set successfully",
            data: result.rows
        });
    } catch(err) {
        console.error(err);
        return res.status(500).json({
            success: false,
            message: "Internal Server Error"
        });
    }
};

const getSetsBySession = async(req, res) => {
    try {
        const { session_id } = req.params;// putting curly braces aas req.params is an object
        const result = await poolInst.query('SELECT * FROM set_logs WHERE session_id = $1 AND is_deleted = false ORDER BY completed_at ASC', [session_id]);
        if(result.rows.length === 0) {
            return res.status(200).json([]);
        }
        return res.status(200).json( result.rows );
    } catch(err) {
        console.error(err);
        return res.status(500).json({
            success: false,
            message: "Internal Server Error"
        });
    }
};

export { logSet, getSetsBySession };