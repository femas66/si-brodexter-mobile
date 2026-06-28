# Bro Dexter - Asisten Keuangan UMKM 🚀

**Bro Dexter** adalah aplikasi pencatatan keuangan sederhana yang dirancang khusus untuk pemilik usaha kecil (UMKM) atau pedagang. Aplikasi ini fokus pada kemudahan penggunaan, kejelasan metrik utama, dan kecepatan dalam mencatat transaksi harian.

## ✨ Fitur Utama
- **Dashboard Ringkas**: Tampilan saldo total, pemasukan, dan pengeluaran secara real-time.
- **Riwayat Lengkap**: Halaman khusus riwayat transaksi dengan fitur filter (Semua, Pemasukan, Pengeluaran) dan sistem "Lihat lainnya" untuk performa optimal.
- **Pencatatan Cepat**: Form tambah transaksi yang intuitif dengan dukungan pemilihan tanggal.
- **Visualisasi Tren**: Grafik garis interaktif yang menunjukkan tren keuangan dalam 30 hari terakhir.
- **Format Rupiah Otomatis**: Semua angka diformat secara otomatis ke mata uang Rupiah untuk memudahkan pembacaan.

## 🛠️ Tech Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Database**: SQLite via Room Persistence Library
- **Architecture**: MVVM (Model-View-ViewModel)
- **Data Flow**: Reactive flows with Kotlin Coroutines & Flow/StateFlow

## 📱 Spesifikasi Minimal
Agar aplikasi berjalan dengan lancar, berikut adalah spesifikasi minimum yang disarankan:

### Perangkat (Smartphone)
- **Sistem Operasi**: Android 7.0 (API Level 24 - Nougat) atau lebih baru.
- **RAM**: Minimal 2GB (Disarankan 3GB+ untuk performa mulus).
- **Penyimpanan**: Minimal 50MB ruang kosong (Penyimpanan database SQLite sangat efisien).
- **Layar**: Resolusi minimal 720p (Aplikasi didesain dengan kontras tinggi agar mudah dibaca di bawah sinar matahari/pasar).

### Pengembangan (Developer)
- **Android Studio**: Android Studio Koala | 2024.1.1 atau lebih baru.
- **Java**: JDK 11 atau 17.
- **Gradle**: Versi 8.0+.

## 🚀 Cara Instalasi (Untuk Developer)
1. **Clone Repository**:
   ```bash
   git clone https://github.com/username/BroDexter.git
   ```
2. **Buka di Android Studio**:
   Pilih `File > Open` lalu arahkan ke folder project.
3. **Sync Gradle**:
   Tunggu hingga proses sinkronisasi Gradle selesai.
4. **Run Application**:
   Klik tombol `Run` (Segitiga hijau) untuk menginstal aplikasi ke emulator atau perangkat fisik.

## 🎨 Prinsip Desain
Aplikasi ini mengikuti prinsip **"Clarity over Complexity"**:
- **Big & Bold Metrics**: Metrik utama (Saldo) ditampilkan dengan ukuran besar.
- **High Contrast**: Menggunakan warna-warna tegas (Merah untuk pengeluaran, Hijau untuk pemasukan).
- **Clean Aesthetic**: Banyak menggunakan whitespace untuk mengurangi beban kognitif pengguna saat sibuk melayani pelanggan.

---
*Dibuat dengan ❤️ untuk kemajuan UMKM Indonesia.*