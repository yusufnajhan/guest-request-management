const express = require("express");
const router = express.Router();
const requestController = require("../controllers/requestController");

router.post("/requests", requestController.addRequest);
router.get("/requests", requestController.getAllRequests);
router.get("/requests/:id", requestController.getRequestById);
router.put("/requests/:id", requestController.updateRequestStatus);

module.exports = router;
