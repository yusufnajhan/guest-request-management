const pool = require('../config/database');
const { predictMessage } = require('../models/requestModel');

exports.addRequest = async (req, res) => {
    const { message } = req.body;
    
    try {
        const category = await predictMessage(message);
        const query = "INSERT INTO requests (message, category, status) VALUES (?, ?, 'new')";
        pool.query(query, [message, category], (err, results) => {
            if (err) {
                console.error("Error adding request:", err.message);
                return res.status(500).json({ success: false, error: "Internal Server Error" });
            }
            console.log("Request successfully added:", results);
            res.status(201).json({ success: true , category});
        });
    } catch (err) {
        console.log(message)
        console.error("Error predicting message:", err.message);
        res.status(500).json({ success: false, error: "Internal Server Error" });
    }
};

exports.getAllRequests = (req, res) => {
    const query = "SELECT * FROM requests WHERE category='onsite'";
    pool.query(query, (err, results) => {
        if (err) {
            console.error("Error fetching requests:", err.message);
            return res.status(500).json({ error: "Internal Server Error" });
        }
        res.json({success: true, results});
    });
};

exports.getRequestById = (req, res) => {
    const id = req.params.id;
    const query = "SELECT * FROM requests WHERE id = ?";
    pool.query(query, [id], (err, results) => {
        if (err) {
            console.error("Error fetching request:", err.message);
            return res.status(500).json({ error: "Internal Server Error" });
        }
        if (results.length === 0) {
            return res.status(404).json({ error: "Request not found" });
        }
        console.log("Request details:", results);
        res.json(results[0]);
    });
};

exports.updateRequestStatus = (req, res) => {
    const id = req.params.id;
    const { status } = req.body;
    const query = "UPDATE requests SET status = ? WHERE id = ?";
    pool.query(query, [status, id], (err, results) => {
        if (err) {
            console.error("Error updating status:", err.message);
            return res.status(500).json({ success: false, error: "Internal Server Error" });
        }
        console.log("Request's status updated:", results);
        res.json({ success: true });
    });
};
