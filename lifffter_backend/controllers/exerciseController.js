import poolInst from "../database/db.js";

const getAllExercises = async(req, res) => {
    try {
        const result = await poolInst.query('SELECT * FROM exercises');
        return res.status(200).json(result.rows);
    } catch(err) {
        console.error(err);
        return res.status(500).json({ error: "Internal Server Error" });
    }
};

export default getAllExercises;