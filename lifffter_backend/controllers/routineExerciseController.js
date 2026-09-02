import poolInst from "../database/db.js";

const addExercise = async(req, res) => {
    try {
        const { routine_id, exercise_id, order_index } = req.body;
        const result = await poolInst.query('INSERT INTO routine_exercises (routine_id, exercise_id, order_index) VALUES ($1, $2, $3) RETURNING id', [routine_id, exercise_id, order_index]);
        if(result.rows.length === 0)
        {
            return res.status(400).json({
                success: false,
                message: "Failed to add exercise in the routine",
                error: "Database insertion didnt return any record.Please check your input and try again!"
            });    
        }
        return res.status(201).json({
            success: true,
            message: "Exercise successfully added in the routine"
        });
    } catch(err) {
        console.error(err);
        return res.status(500).json({ 
            message: "Internal Server Error"
        });
    }
};

const getExercisesByRoutine = async(req, res) => {
    try {
        const { routine_id } = req.params;
        const result = await poolInst.query('SELECT * FROM routine_exercises WHERE routine_id = $1 AND is_deleted = false ORDER BY order_index ASC', [routine_id]);
        // if(result.rows.length === 0) {
        //     return res.status(400).json({
        //         success: false,
        //         message: "Failed to fetch the routine exercises list"
        //     });
        // } not needed as while creating a new routine the DB exactly returns 0 rows
        return res.status(200).json({
            success: true,
            message: "Routine exercises fetched successfully",
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

export { addExercise, getExercisesByRoutine };