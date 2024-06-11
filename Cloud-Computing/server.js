const express = require("express");
const mysql = require("mysql2");
const bodyParser = require("body-parser");

const app = express();
const PORT = 8080;

app.use(bodyParser.json());

const connection = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "",
  database: "guest_requests",
});

app.use((req, res, next) => {
  res.header("Access-Control-Allow-Origin", "http://localhost:8080");
  res.header(
    "Access-Control-Allow-Headers",
    "Origin, X-Requested-With, Content-Type, Accept"
  );
  next();
});

app.use(bodyParser.json());


app.post("/requests", (req, res) => {
    const {
      message,
      category,
      status,
    } = req.body;
  
    const query =
      "INSERT INTO requests (message, category, status) VALUES (?, ?, ?)";
    connection.query(
      query,
      [
        message,
        category,
        'new',
      ],
      (err, results) => {
        if (err) {
          console.error("Error adding request:", err.message);
          return res
            .status(500)
            .json({ success: false, error: "Internal Server Error" });
        }
  
        console.log("Request berhasil ditambahkan:", results);
        res.json({ success: true });
      }
    );
});

app.get("/requests", (req, res) => {
    const query = "SELECT * FROM requests WHERE category = 'request_onsite'";
    connection.query(query, (err, results) => {
      if (err) {
        console.error("Error fetching request:", err.message);
        return res.status(500).json({ error: "Internal Server Error" });
      }
      res.json(results);
    });
});

app.get("/requests/:id", (req, res) => {
    const id = req.params.id;
    const query =
      "SELECT * FROM requests WHERE requests.id = ?";
  
    connection.query(query, [id], (err, results) => {
      if (err) {
        console.error("Error fetching request:", err.message);
        return res.status(500).json({ error: "Internal Server Error" });
      }
  
      console.log("Detail Request:", results);
      res.json(results);
    });
});

app.put("/requests/:id", (req, res) => {
    const id= req.params.id;
    const updatedRequest = req.body;
  
    const query = "UPDATE requests SET status = ? WHERE id = ?";
    connection.query(
      query,
      [updatedRequest.status, id],
      (err, results) => {
        if (err) {
          console.error("Error updating status:", err.message);
          return res
            .status(500)
            .json({ success: false, error: "Internal Server Error" });
        }
  
        console.log("Request's status updated:", results);
        res.json({ success: true });
      }
    );
});

// Menjalankan server
app.listen(PORT, "0.0.0.0", () => {
  console.log(`Server is running on http://0.0.0.0:${PORT}`);
});
