const tf = require('@tensorflow/tfjs-node');
const axios = require('axios');

let model;

// Fungsi untuk mengunduh model dari Google Cloud Storage
async function loadModel() {
    if (!model) {
        console.log("Loading model...");
        const modelUrl = 'https://storage.googleapis.com/model-requests-bucket/model_tfjs/model.json';
        model = await tf.loadLayersModel(modelUrl);
        console.log("Model loaded successfully.");
    }
}

// Fungsi untuk memprediksi kategori pesan
async function predictMessage(message) {
    await loadModel();
    
    // Preprocessing: Tokenisasi dan padding
    const tokenizerUrl = 'https://storage.googleapis.com/model-requests-bucket/model_tfjs/tokenizer.json';
    const tokenizer = await axios.get(tokenizerUrl).then(res => res.data);

    const sequences = message.split(' ').map(word => tokenizer[word] || 0);
    const paddedSequences = tf.pad([[sequences]], [[0, 0], [0, 100 - sequences.length]], 0);
    
    const prediction = model.predict(paddedSequences);
    const predictedCategory = (prediction.arraySync()[0][0] > 0.5) ? 'Sent' : 'Not Sent';

    return predictedCategory;
}

module.exports = { predictMessage };
