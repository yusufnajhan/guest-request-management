const dotenv = require('dotenv')

dotenv.config()

const mysql = require("mysql2");

const pool = mysql.createPool({
  user: process.env.DB_USER,
  password: process.env.DB_PASS,
  database: process.env.DB_NAME,
  socketPath: `/cloudsql/${process.env.INSTANCE_CONNECTION_NAME}`,
});


module.exports = pool;
