// this is our Express.js server running on Node.js.
import 'dotenv/config';
import express from 'express';
import poolInst from './database/db.js';
import authRoutes from './routes/authRoutes.js';
import userRouters from './routes/userRoutes.js';
import workoutRoutes from './routes/workoutRoutes.js';

const app = express();
const PORT = 3000;

app.use(express.json()); // built in middleware function 
// express.json(): parses incoming requests with JSON payloads
// (JSON payload carries actual *INFORMATION*).
// use() method is basically telling express to register 
// the function that express.json() returns whenever a request comes.

app.use('/api/v1/auth', authRoutes);
app.use('/api/v1/users', userRouters);
app.use('/api/v1/workouts', workoutRoutes);

app.get("/db-time", async (req, res) => {
  try {
    const result = await poolInst.query('SELECT NOW()');
    res.json({ currentTime: result.rows[0].now });
  } catch(err) {
    console.error('Database query error:', err);
    res.status(500).json({ error: 'Database error' });
  }
});

// starts a web server and bind the app to a specific network port
// so it can accept incoming HTTP requests.
app.listen(PORT, async () => { // since there is no need for callback function rn
  // This is important!
  // Without this, any startup errors will silently fail
  // instead of giving you a helpful error message.
  console.log(`My first Express app - listening on port ${PORT}!`);

  try {
    const dbTest = await poolInst.query('SELECT NOW()');
    console.log('PostgreSQL connected successfully at: ', dbTest.rows[0].now);
  } catch(err) {
    console.error('Failed to connect to PSQL:', err.message);
  }
});