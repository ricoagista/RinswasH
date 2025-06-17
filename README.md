# 🧺 RinswasH - Aplikasi Manajemen Laundry FXML

Selamat datang di **RinswasH**, aplikasi desktop yang dibuat dengan JavaFX untuk mengelola bisnis laundry Anda! 🚀 Dengan antarmuka yang ramah pengguna, RinswasH mempermudah pencatatan transaksi, pemantauan status laundry, dan pembuatan laporan.

## ✨ Fitur Utama

* **📝 Pencatatan Transaksi**: Tambah dan perbarui data laundry pelanggan dengan mudah.
* **🔄 Status Laundry**: Lacak laundry yang sedang diproses dan yang sudah diambil oleh pelanggan.
* **📊 Laporan Dinamis**: Lihat ringkasan transaksi aktif, termasuk total berat dan estimasi pendapatan.
* **🧾 Cetak Nota**: Hasilkan nota transaksi dalam format PDF untuk pelanggan.
* **💾 Penyimpanan Data**: Semua data transaksi disimpan dalam file teks (`.txt`) untuk kemudahan pengelolaan.

## 🛠️ Teknologi yang Digunakan

* **JavaFX**: Untuk membangun antarmuka pengguna grafis (GUI) yang modern dan responsif.
* **Maven**: Untuk manajemen dependensi proyek.
* **iTextPDF**: Untuk membuat dan mengelola file PDF, khususnya untuk fitur cetak nota.
* **CSS**: Untuk memberikan tampilan yang menarik dan konsisten pada aplikasi.

## 📂 Struktur Proyek

```
RinswasH/
├── .idea/
├── .mvn/
├── nota_laporan/
│   ├── Nota_Dicky_2025-06-10.pdf
│   └── ... (nota lainnya)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/example/rinswash/
│   │   │   │   ├── LaporanController.java
│   │   │   │   ├── Laundry.java
│   │   │   │   ├── LaundryController.java
│   │   │   │   ├── LaundryService.java
│   │   │   │   └── MainApp.java
│   │   │   └── module-info.java
│   │   └── resources/
│   │       └── com/example/rinswash/
│   │           ├── laundry-view.fxml
│   │           ├── laporan-view.fxml
│   │           └── style.css
├── laundry_data.txt
├── laundry_diambil.txt
└── pom.xml
```

### 📜 Deskripsi File Penting

* `MainApp.java`: Titik masuk utama aplikasi JavaFX.
* `LaundryController.java`: Mengelola logika untuk tampilan utama, termasuk penambahan, pembaruan, dan pengambilan laundry.
* `LaporanController.java`: Mengatur logika untuk jendela laporan, termasuk menampilkan data dan mencetak nota.
* `Laundry.java`: Kelas model yang merepresentasikan data laundry.
* `LaundryService.java`: Menangani operasi baca/tulis data dari dan ke file teks.
* `laundry-view.fxml` & `laporan-view.fxml`: File FXML yang mendefinisikan tata letak antarmuka pengguna.
* `style.css`: File CSS untuk mempercantik tampilan aplikasi.
* `laundry_data.txt`: Menyimpan data laundry yang sedang dalam proses.
* `laundry_diambil.txt`: Menyimpan data laundry yang telah diambil oleh pelanggan.
* `pom.xml`: Berisi konfigurasi proyek Maven dan dependensi yang dibutuhkan.

## 🚀 Cara Menjalankan Aplikasi

1.  **Clone Repositori**:
    ```bash
    git clone https://github.com/ricoagista/rinswash.git
    ```
2.  **Buka dengan IDE**: Buka proyek menggunakan IntelliJ IDEA atau IDE lain yang mendukung Maven.
3.  **Install Dependensi**: Maven akan secara otomatis mengunduh semua dependensi yang diperlukan seperti yang tercantum dalam `pom.xml`.
4.  **Jalankan Aplikasi**: Temukan dan jalankan kelas `MainApp.java`.

Selamat mencoba RinswasH! Semoga aplikasi ini bermanfaat untuk bisnis laundry Anda. 🎉
