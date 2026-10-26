# Sistem Tes Tipe Kulit (GUI Java Swing)

Aplikasi sederhana untuk menentukan tipe kulit (Normal, Berminyak, Kering, Kombinasi, Sensitif) lewat 4 pertanyaan. Setiap jawaban memberi 1 poin ke satu tipe kulit, lalu tipe dengan poin tertinggi jadi hasilnya.

## 1. Top Level Container
- `JFrame frame`: jendela utama aplikasi. Semua panel ditaruh di dalamnya.

## 2. Intermediate Component
- `JPanel`: `panelUtama`, `panelAtas`, `panelHeader`, `panelData`, `panelSoal`, `kotakSoal[]`, `panelHasil`, `panelTombol`. Dipakai untuk mengelompokkan komponen.
- `JScrollPane`: pembungkus `areaHasil` supaya muncul scrollbar kalau teks panjang.
- `TitledBorder`: border berjudul di tiap kotak soal.

## 3. Atomic Component
- `JLabel`: judul dan keterangan (Nama, Tema, Tipe).
- `JTextField`: input nama.
- `JComboBox`: pilihan tema warna.
- `JRadioButton` (+ `ButtonGroup`): pilihan jawaban, hanya bisa pilih satu per soal.
- `JButton`: tombol "Tentukan Tipe Kulit" dan "Reset".
- `JTextArea`: menampilkan rincian skor dan deskripsi hasil.

## 4. Layout Manager
- `BorderLayout`: `panelUtama` (atas, tengah, kanan, bawah) dan `panelHasil`.
- `GridLayout`: `panelAtas` (2 baris) dan `panelSoal` (kotak soal 2x2).
- `FlowLayout`: `panelHeader`, `panelData`, `panelTombol` (komponen berjajar ke samping).
- `BoxLayout`: `kotakSoal[]` (opsi jawaban tersusun ke bawah).

## 5. Event Handling (2 interface)
1. `ActionListener` → method `actionPerformed()`: dijalankan saat tombol **Tentukan Tipe Kulit** (hitung skor dan tampilkan hasil) atau **Reset** (kosongkan form) diklik.
2. `ItemListener` → method `itemStateChanged()`: dijalankan saat pilihan **Tema** di `JComboBox` diganti, lalu warna latar panel ikut berubah.

## Screenshot
**Tampilan awal**
![Tampilan awal](img/1.%20tampilan%20awal.png)

**Form terisi**
![Form terisi](img/2.%20form%20terisii.png)

**Dialog hasil (JOptionPane)**
![Dialog hasil](img/3.%20dialog%20hasil.png)

**Hasil di panel**
![Hasil tes](img/4.%20hasil%20tes.png)