# Deskripsi
LokaTravel adalah aplikasi mobile berbasis Android yang dirancang untuk membantu wisatawan dalam menjelajahi keindahan alam dan budaya Indonesia. Aplikasi ini menyediakan informasi lengkap tentang destinasi wisata populer di Indonesia, berita terkini seputar pariwisata, dan fitur manajemen profil pengguna.

# Tujuan
Tujuan aplikasi ini adalah untuk mempermudah pengguna dalam menemukan destinasi wisata populer serta mendapatkan informasi terbaru tentang pariwisata di Indonesia.

# Fitur Aplikasi
1.  Login & Register
2. Home : Rekomendasi wisata
3. News : Berita terkini 
4. Profil serta Logout

# Teknologi yang digunakan
Bahasa: Kotlin
IDE: Android Studio
Library:
 - Retrofit & Gson – untuk mengambil dan memparsing data berita dari API
 - RecyclerView – menampilkan daftar artikel berita
 - Glide – menampilkan gambar berita
Model Data:
 - NewsResponse – mewakili response utama dari API
 - ArticlesItem – menyimpan detail tiap berita
 - Source – menyimpan informasi sumber berita
Catatan:
Data berita tidak disimpan di Firebase, hanya ditampilkan langsung dari API secara real-time.
