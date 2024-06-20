const tf = require('@tensorflow/tfjs-node');
const fs = require('fs');
const path = require('path');

let model;

// Fungsi untuk memuat model dari direktori lokal
// async function loadModel() {
//     console.log("Loading model...");
//     const modelPath = path.resolve(__dirname, '../model_tfjs/model.json');
//     model = await tf.loadLayersModel(`file://${modelPath}`);
//     //model = await tf.loadLayersModel('https://storage.googleapis.com/model-requests-bucket/model2/model.json')
//     console.log("Model loaded successfully.");
// }

// // Fungsi untuk tokenisasi dan padding pesan
// function preprocessMessage(message, tokenizer) {
//     const sequences = message
//         .split(' ')
//         .map(word => tokenizer.word_index[word] || tokenizer.word_index['<OOV>']);

//     // Padding
//     const paddedSequences = Array(200).fill(0);
//     for (let i = 0; i < sequences.length && i < 200; i++) {
//         paddedSequences[i] = sequences[i];
//     }

//     // Convert to tensor dan tambahkan dimensi batch
//     return tf.tensor2d([paddedSequences], [1, 200]);  // Shape [1, 200] sesuai dengan input model
// }

// // Fungsi untuk memprediksi kategori pesan
// async function predictMessage(message) {
//     await loadModel();

//     // Load tokenizer
//     const tokenizerPath = path.resolve(__dirname, '../model_tfjs/tokenizer.json');
//     const tokenizerData = fs.readFileSync(tokenizerPath, 'utf8');
//     const tokenizer = JSON.parse(tokenizerData);

//     // Preprocess message
//     const input = preprocessMessage(message, tokenizer);

//     // Prediksi menggunakan model
//     const prediction = model.predict(input);
//     const predictedCategory = (prediction.dataSync()[0] > 0.5) ? 'Onsite' : 'Not Onsite';

//     return predictedCategory;
// }

const predictMessage = async (message) => {
    // Ubah pesan menjadi huruf kecil untuk perbandingan yang tidak peka huruf besar/kecil
    const lowerCaseMessage = message.toLowerCase();

    if (
        lowerCaseMessage.includes("send someone") ||
        lowerCaseMessage.includes("check") ||
        lowerCaseMessage.includes("visit") ||
        lowerCaseMessage.includes("come to") ||
        lowerCaseMessage.includes("fix") ||
        lowerCaseMessage.includes("repair") ||
        lowerCaseMessage.includes("inspect") ||
        lowerCaseMessage.includes("can you") ||
        lowerCaseMessage.includes("could you") ||
        lowerCaseMessage.includes("help") ||
        lowerCaseMessage.includes("can") ||
        lowerCaseMessage.includes("need") ||
        lowerCaseMessage.includes("maintenance") 
    ) {
        return "onsite";
    } else {
        return "not onsite";
    }
};

module.exports = { predictMessage };
