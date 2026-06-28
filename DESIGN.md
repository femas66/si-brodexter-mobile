# Design

## Visual Theme
**Modern Ledger**: Bersih, fungsional, dan memberikan kesan profesionalitas yang kuat. Menggunakan whitespace yang luas untuk memisahkan informasi tanpa perlu banyak garis pembatas (borders).

## Color Palette (OKLCH)
- **Primary**: `oklch(0.55 0.20 3)` (Deep Crimson) — Digunakan untuk branding utama, tombol aksi positif, dan elemen identitas.
- **Background**: `oklch(1.00 0.00 0)` (Pure White) — Untuk kejelasan maksimal.
- **Surface**: `oklch(0.98 0.01 3)` (Off-white) — Untuk kartu (cards) dan area konten sekunder.
- **Ink**: `oklch(0.15 0.02 3)` (Deep Charcoal) — Untuk teks utama agar nyaman dibaca.
- **Accent (Success)**: `oklch(0.62 0.17 145)` (Emerald Green) — Digunakan khusus untuk angka pemasukan dan indikator positif.
- **Accent (Alert)**: `oklch(0.60 0.20 25)` (Coral Red) — Digunakan untuk angka pengeluaran dan peringatan.
- **Muted**: `oklch(0.55 0.02 3)` (Cool Gray) — Untuk teks keterangan dan ikon non-aktif.

## Typography
- **Headlines**: Sans-serif (Inter/Roboto), Bold, Tracking -0.02em. Digunakan untuk angka saldo besar.
- **Body**: Sans-serif, Regular, Line-height 1.5. Fokus pada keterbacaan.
- **Numbers**: Tabular figures jika memungkinkan, untuk memastikan angka-angka keuangan sejajar secara vertikal.

## Components
- **SummaryCard**: Kartu besar di bagian atas untuk Saldo Utama. Menggunakan latar belakang Primary dengan teks Putih.
- **MetricPill**: Elemen kecil untuk Pemasukan/Pengeluaran dengan ikon panah.
- **TransactionList**: Daftar bersih tanpa garis tepi, hanya dipisahkan oleh whitespace dan grouping tanggal.

## Layout & Rhythm
- Spasi antar elemen menggunakan kelipatan 8dp (8, 16, 24, 32).
- Padding horizontal standar 20dp untuk memberikan nafas pada konten di layar HP.
- Penggunaan bayangan (shadow) yang sangat halus (soft) untuk memberikan kedalaman tanpa kesan kotor.
