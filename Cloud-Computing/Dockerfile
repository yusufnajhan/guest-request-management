# Gunakan gambar Node.js resmi sebagai base image
FROM node:18.20.2

# Buat direktori kerja untuk aplikasi Anda
WORKDIR /app

# Salin package.json dan package-lock.json ke direktori kerja
COPY package*.json ./

# Install dependencies aplikasi
RUN npm install --only=production

# Salin sisa file aplikasi ke direktori kerja
COPY . .

# Ekspos port yang digunakan aplikasi (8080)
EXPOSE 8080

# Jalankan aplikasi saat container mulai
CMD [ "node", "server.js" ]
