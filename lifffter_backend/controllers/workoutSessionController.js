import poolInst from '../database/db.js';

const startSession = async(req, res) => {
    try {
        console.log("FULL SESSION PAYLOAD FROM ANDROID:", req.body);
        const { id, routine_id, start_time, end_time } = req.body;
        const user_id = req.user.id;

        const startDate = new Date(start_time);
        const endDate = end_time ? new Date(end_time) : null;

        const result = await poolInst.query(
            'INSERT INTO workout_sessions (id, user_id, routine_id, start_time, end_time) VALUES ($1, $2, $3, $4, $5) RETURNING id, start_time',
            [id, user_id, routine_id, startDate, endDate]
        );
        console.log("2. SESSION SAVED IN POSTGRES WITH ID:", result.rows[0].id);
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