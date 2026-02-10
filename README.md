**Nadia Aisyah Fazila**

**2406495584**

**ADPRO-A**

Tutorial Modul 1
---

**Reflection 1**
    Pada modul ini, saya mempelajari tentang konsep clean code dan secure coding. Sebelumnya, saya 
cenderung hanya fokus pada pembuatan program tanpa memperhatikan nama dari variabel maupun kerapihan pada kode
yang saya tulis, serta sering membuat satu function yang terlalu panjang.
    Setelah saya mempelajari materi dari modul dan pembelajaran di kelas, saya jadi lebih memahami terkait
dengan pentingnya menerapkan prinsip clean code. Contoh dari penulisan clean code yang saya terapkan
adalah sebagai berikut:
```
public Product findProductById(String id) {
    for (Product product : productData) {
        if (product.getProductId().equals(id)) {
            return product;
        }
    }
    return null;
}
```
Sebelum menerapkan prinsip clean code, saya akan menulis nama function tersebut hanya dengan 
`findId` dan seringkali penulisan itu membuat saya bingung apakah *function* tersebut untuk mencari id atau untuk
mencari product berdasarkan id. 
    Dengan menerapkan prinsip clean code, kode yang saya tulis akan lebih mudah untuk dipahami baik 
oleh saya ke depannya, maupun oleh orang lain.
    Selain clean code, saya juga belajar mengenai secure coding yang dipraktikkan pada tutorial ini, yaitu dengan 
menerapkan secure design yaitu dengan memisah `controller, service, dan repository`, sehingga akses data tidak langsung
dilakukan dari controller supaya logic pada kode yang bersifat sensitif tidak tersebar.

**Reflection 2**
1. Setelah menulis unit test, saya merasa lebih yakin dengan kode yang saya buat karena setiap *logic* pada kode
diverifikasi. Unit test membantu saya dalam mendeteksi kesalahan *logic* pada kode yang saya buat. Jumlah unit test dalam 
satu kelas tidak memiliki angka yang pasti, tetapi yang menurut saya harus diperhatikan adalah skenario yang akan di 
test, yaitu positif dan negatif skenario. Meskipun *code coverage* untuk menunjukkan berapa banyak bagian kode yang telah 
diuji. Namun, 100% *coverage* tidak menjamin bahwa kode bebas dari kesalahan/bug, karena *coverage* diukur berdasarkan 
baris kode yang dieksekusi.
2. Menurut saya, kodenya akan jadi *less clean* karena akan ada duplikasi kode pada bagian setup, dimana itu 
akan menyebabkan repetition yang tidak sesuai dengan prinsip *clean code*. Ya, akan menurunkan kualitas kode karena kode 
akan lebih sulit untuk di *maintenance* karena misal ada perubahan setup yang harus dilakukan maka akan harus dilakukan di 
beberapa file, dimana itu akan meingkatkan resiko inkonsistensi. Untuk membuat kode tetap *clean*, bisa dilakukan dengan 
menambahkan skenario tersebut ke `CreateProductFunctionalTest.java` saja supaya tidak terjadi *repetition*.


