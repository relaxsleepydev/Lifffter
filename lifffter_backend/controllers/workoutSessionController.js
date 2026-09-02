import poolInst from '../database/db.js';

const startSession = async(req, res) => {
    try {
        const { routine_id } = req.body;
        const user_id = req.user.id;
        const result = await poolInst.query('INSERT INTO workout_sessions (user_id, routine_id) VALUES ($1, $2) RETURNING id, start_time', [user_id, routine_id]);
        if(result.rows.length === 0) {
            return res.status(400).json({
                success: false,
                message: "Failed to create workout session",
                error: "Database insertion didnt returned any record. Please check input and try again"
            });
        }
        return res.status(201).json({
            success: true,
            message: "Workout session created successfully",
            data: result.rows
        })
    } catch(err) {
        console.log(err);
        return res.status(500).json({ 
            success: false,
            message: "Internal Server Error"
        });
    }
};

const endSession = async(req, res) => {
    try {
        const { session_id } = req.params;
        const user_id = req.user.id;
        const result = await poolInst.query('UPDATE workout_sessions SET end_time = now() '
            + 'WHERE id = $1 AND user_id = $2 RETURNING id, start_time, end_time', [session_id, user_id]);
        if(result.rows.length === 0) {
            return res.status(404).json({
                success: false,
                message: "Session id doesnt exists for this user"
            });
        }
        return res.status(200).json({ 
            success: true,
            message: "Session Ended Successfully",
            data: result.rows
        });
    } catch(err) {
        console.error(err);
        return res.status(500).json({
            success: false,
            message: "Internal Server Error"
        })
    }
};

export { startSession, endSession };