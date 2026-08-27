import { Pool } from 'pg';
// curly braces used to get the exact item Pool from pg package and import it
const poolInst = new Pool({
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    host: process.env.DB_HOST,
    port: process.env.DB_PORT,
    database: process.env.DB_NAME
});

export default poolInst;