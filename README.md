# LP7DPBO2024C2

Saya Shidiq Arifin Sudrajat [2202152] mengerjakan soal LP7 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin

## Desain Program

Program ini merupakan implementasi sederhana dari permainan klasik "Flappy Bird" yang memiliki beberapa kelas yang berikut akan dijelaskan.

## Penjelasan Program

### 1. Kelas App

- `main(String[] args)`: Titik masuk program. Memanggil metode `showMenu()` untuk menampilkan menu utama.
- `showMenu()`: Untuk menu utama dengan tombol "Start Game". Saat tombol diklik, menu ditutup dan permainan dibuka.
- `showGame()`: Untuk permainan dan menginisialisasi panel `FlappyBird`.

### 2. Kelas FlappyBird

- Inisialisasi: Menyiapkan panel permainan dengan atribut yang diperlukan dan memuat gambar latar belakang, burung, dan pipa. Juga menginisialisasi pemain, pipa, dan timer untuk logika permainan.
- Menggambar: Menggambar background, pemain, dan pipa. Juga menangani penulisan pesan game over jika diperlukan.
- Masukan: Untuk menangani masukan pemain (menekan tombol spasi untuk naik ke atas/terbang dan memulai ulang permainan dengan menekan 'R').
- Metode:
  - `move()`: Memperbarui posisi pemain dan pipa berdasarkan kecepatan dan gravitasi.
  - `checkCollision()`: Memeriksa tabrakan antara pemain dan pipa atau tanah.
  - `gameOver()`: Mengakhiri permainan, menghentikan timer, dan menampilkan pesan game over.
  - `restartGame()`: Mereset status permainan untuk permainan baru.
  - `placePipes()`: Menghasilkan pipa baru pada posisi acak.

### 3. Kelas Pipe

- Mewakili rintangan pipa dalam permainan.
- Atribut: Posisi (`posX` dan `posY`), ukuran (`width` dan `height`), gambar (`image`), kecepatan (`velocityX`), dan sebuah boolean untuk melacak apakah pemain telah melewatinya (`passed`).
- Memberikan getter dan setter untuk atribut.

### 4. Kelas Player

- Mewakili pemain (burung) dalam permainan.
- Atribut: Posisi (`posX` dan `posY`), ukuran (`width` dan `height`), gambar (`image`), dan kecepatan (`velocityY`).
- Memberikan getter dan setter untuk atribut.
