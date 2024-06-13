const connection = require('../config/database');
const { predictMessage } = require('../models/requestModel');

exports.addRequest = async (req, res) => {
    const { message } = req.body;
    
    try {
        const category = await predictMessage(message); 

        const query = "INSERT INTO requests (message, category, status) VALUES (?, ?, 'new')";
        connection.query(query, [message, category], (err, results) => {
            if (err) {
                console.error("Error adding request:", err.message);
                return res.status(500).json({ success: false, error: "Internal Server Error" });
            }
            console.log("Request successfully added:", results);
            res.json({ success: true });
        });
    } catch (err) {
        console.error("Error predicting message:", err.message);
        return res.status(500).json({ success: false, error: "Internal Server Error" });
    }
};

exports.getAllRequests = (req, res) => {
    const query = "SELECT * FROM requests WHERE category = 'onsite_request'";
    connection.query(query, (err, results) => {
        if (err) {
            console.error("Error fetching requests:", err.message);
            return res.status(500).json({ error: "Internal Server Error" });
        }
        res.json(results);
    });
};

exports.getRequestById = (req, res) => {
    const id = req.params.id;
    const query = "SELECT * FROM requests WHERE id = ?";
    connection.query(query, [id], (err, results) => {
        if (err) {
            console.error("Error fetching request:", err.message);
            return res.status(500).json({ error: "Internal Server Error" });
        }
        console.log("Request details:", results);
        res.json(results);
    });
};

exports.updateRequestStatus = (req, res) => {
    const id = req.params.id;
    const { status } = req.body;
    const query = "UPDATE requests SET status = ? WHERE id = ?";
    connection.query(query, [status, id], (err, results) => {
        if (err) {
            console.error("Error updating status:", err.message);
            return res.status(500).json({ success: false, error: "Internal Server Error" });
        }
        console.log("Request's status updated:", results);
        res.json({ success: true });
    });
};
