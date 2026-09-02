import poolInst from '../database/db.js';

export const createRoutine = async(req, res) => {
    try {
        const { routine_name, target_muscle } = req.body;
        const user = req.user.id;
        const result = await poolInst.query('INSERT INTO workout_routine (user_id, routine_name, target_muscle) VALUES ($1, $2, $3) RETURNING id', [user, routine_name, target_muscle]);
        if(result.rows.length === 0) {
            return res.status(400).json({
                success: false,
                message: "Failed to create workout routine",
                error: "The database insertion did not return any record. Check your input and try again!"
            });
        }
        return res.status(201).json({ message: "Created successfully" });
    } catch(err) {
        console.error(err);
        return res.status(500).json({ error: "Internal Server Error" });
    }
};

export const getRoutine = async(req, res) => {
    try {
        const user = req.user.id;
        const result = await poolInst.query('SELECT * FROM workout_routine WHERE user_id = $1 AND is_deleted = false', [user]);
        if(result.rows.length === 0) {
            return res.status(200).json([ ]);
        }
        return res.status(200).json( result.rows );
    } catch(err) {
        console.error(err);
        return res.status(500).json({ error: "Internal Server Error" });
    }
};