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


#Reflection 2
##You have implemented a CI/CD process that automatically runs the test suites, analyzes code quality, and deploys to a PaaS. Try to answer the following questions in order to reflect on your attempt completing the tutorial and exercise.
**1. List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.**
Saya memperbaiki beberapa *code quality issues*. Beberapa diantaranya adalah:
- **Penamaan controller dan template**
  Sebelumnya, `HomePageController` return “homePage", sedangkan unit test mengharapkan “HomePage". Hal ini menyebabkan test gagal karena perbedaan case-sensitive. Strategi perbaikannya adalah menyamakan return value di controller dan unit test sehingga test lulus.
- **File template**
  Beberapa template yang saja buat memiliki perbedaan nama dibandingkan yang expected di unit test. Yang saya lakukan untuk memperbaikinya adalah dengan mengganti nama file agar sesuai dengan return value di controller, sehingga *Thymeleaf* dapat menemukan template dan test jadi berhasil.
- **Jacoco test report**
  Awalnya, SonarCloud menunjukkan 0% coverage, yang membuat *Quality Gate* gagal. Saya memperbaikinya dengan mengaktifkan `XML report Jacoco` di `build.gradle.kts` dan memastikan task `jacocoTestReport` dijalankan setelah test, sehingga SonarCloud bisa membaca hasil coverage kemudian Quality Gate dapat dipenuhi.
  Beberapa yang terdeteksi oleh SonarQube:
- Method test yang melempar throws Exception (Code smell)
  Awalnya pada `ProductControllerTest ` dan beberapa test lainnya menuliskan `throws Exception` yang ditandai sebagai bad practice di SonarQube. Saya memperbaikinya dengan menggantinya dengan *try-catch* di dalam test kemudian menambahkan `fail()` jika ada exception
- Package mismatch pada test (Code smell)
  Sebelumnya, SonarQube memberikan warning karena ada beberapa file yang pathnya tidak sesuai dengan package declarationnya. Saya memperbaikinya dengan mengganti nama foldernya supaya sesuai dan konsisten.

**2. Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!**
Menurut saya, implementasi saya sekarang sudah termasuk kategori CI/CD karena:
- Setiap kali saya melakukan pull request ke GitHub, akan otomatis menjalankan workflows GitHub Actions yang menjalankan test, cek kualitas dari kode dengan SonarQube, serta melakukan analisis keamanan dengan OSSF Scorecard. Setiap kode yang di push dapat diverifikasi secara otomatis sehingga memenuhi prinsip Continuous Integration (CI)
- Setelah pull request dimerge ke branch main, aplikasi secara otomatis diupdate di platform PaaS (Koyeb). Platform tersebut mendeteksi perubahan pada branch main, melakukan build menggunakan Dockerfile atau buildpack, dan memperbarui instance aplikasi hingga statusnya menjadi Healthy. Dengan begitu, setiap kode yang sudah lulus tahap pengujian dapat langsung tersedia sehingga memenuhi prinsip Continuous Deployment (CD)
  Intinya menurut saya, sudah memenuhi kategori CI/CD. CI dijalankan ketika melakukan pull request untuk melakukan verifikasi kode dan keamanannya, dan CD terjadi otomatis setelah merge ke main.