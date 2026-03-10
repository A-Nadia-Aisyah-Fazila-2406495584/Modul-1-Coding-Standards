**Nadia Aisyah Fazila**

**2406495584**

**ADPRO-A**

Deployment link -> [https://permanent-thomasa-a-nadiaaisyahfazila-2406495584-f82740e8.koyeb.app/]

<details>
<summary>
<b>Reflection Modul 1</b>
</summary>

# Reflection 1

Pada modul ini, saya mempelajari tentang konsep clean code dan secure coding. Sebelumnya, saya cenderung hanya fokus pada pembuatan program tanpa memperhatikan nama dari variabel maupun kerapihan pada kode yang saya tulis, serta sering membuat satu function yang terlalu panjang.
Setelah saya mempelajari materi dari modul dan pembelajaran di kelas, saya jadi lebih memahami terkait dengan pentingnya menerapkan prinsip clean code. Contoh dari penulisan clean code yang saya terapkan adalah sebagai berikut:
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
Sebelum menerapkan prinsip clean code, saya akan menulis nama function tersebut hanya dengan `findId` dan seringkali penulisan itu membuat saya bingung apakah *function* tersebut untuk mencari id atau untuk mencari product berdasarkan id. Dengan menerapkan prinsip clean code, kode yang saya tulis akan lebih mudah untuk dipahami baik 
oleh saya ke depannya, maupun oleh orang lain.
Selain clean code, saya juga belajar mengenai secure coding yang dipraktikkan pada tutorial ini, yaitu dengan menerapkan secure design yaitu dengan memisah `controller, service, dan repository`, sehingga akses data tidak langsung dilakukan dari controller supaya logic pada kode yang bersifat sensitif tidak tersebar.

# Reflection 2
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
</details>

<details>
<summary>
<b>Reflection Modul 2</b>
</summary>

# Reflection 

## You have implemented a CI/CD process that automatically runs the test suites, analyzes code quality, and deploys to a PaaS. Try to answer the following questions in order to reflect on your attempt completing the tutorial and exercise.

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
</details>

<details>
<summary>
<b>Reflection Modul 3</b>
</summary>

# Reflection

## Apply the SOLID principles you have learned. You are allowed to modify the source code according to the principles you want to implement. Please answer the following questions:

**1) Explain what principles you apply to your project!**
- S (Single Responsibility Principle):
    Dengan memisahkan `CarController` dari `ProductController`. 
    - Sebelumnya, dengan menggabungkan keduanya dalam satu class dan `CarController extends ProductController` menjadikan seperti `CarController` adalah bagian dari `Product`. 
    - Dengan memisahkannya, `CarController` hanya memiliki satu tanggung jawab yaitu mengelola request yang berhubungan dengan `Car` saja. Ketika ada perubahan logic pada `Product` tidak pengaruh ke `CarController` dan sebaliknya juga.
- O (Open-Close Principle)
    Dengan menggunakan interface yaitu `CarService` dan `CarRepository`.
    - Open to extension = ketika butuh jenis car service yang berbeda, misal: punya discount logic khusus, maka jadi cukup untuk membuat class baru yang `implements CarService` dan tidak perlu untuk menghapus `CarServiceImpl` yang lama.
    - Closed to modification = karena `CarController` bergantung dengan `CarService` maka tidak perlu untuk mengubah `CarController` ketika ingin menambah implementasi service yang baru.
- L (Liskov Substitution Principle)
    Dengan menghapus `extends ProductController`.
    - Sebelumnya, `CarController` harus mengikuti `ProductController` seperti harus menerima `ProductService` sebagai constructornya.
    - Setelah dihapus, `CarController` tidak lagi dipaksa untuk ikut dengan `ProductController` sehingga subclass bisa menggantikan superclass tanpa merusak logicnya.
- I (Interface Segregation Principle
    Dengan memisahkan interface dan implementasi pada `Car` dan `Product`
    - Pemisahan tersebut memastikan bahwa tidak ada yang memaksa class `Car` untuk bergantung pada methods di `Product`yang tidak digunakannya, dan sebaliknya.
- D (Dependency Inversion Principle)
    Dengan membuat interface `CarRepository` yang di implemets oleh `CarRepositoryImpl`, serta menggunakan interface `CarService` pada `CarController`. 
    - Perubahan ini memastikan bahwa modul tingkat tinggi (Controller) bergantung pada abstraksi dan bukan pada implementasi concrete. Hal ini membuat kode lebih fleksibel.

**2) Explain the advantages of applying SOLID principles to your project with examples.**
- Memudahkan dalam maintainability
    - Karena setiap class memiliki single responsibilitu sehingga kita lebih mudah untuk tahu dimana harus mencari kode ketika terjadi masalah dan bugs.
    - Contoh: ketika ada kesalahan pada logic penyimpanan mobil, cukup untuk periksa `CarRepositoryImpl` tanpa takut untuk merusak logic dari fitur lainnya misal `ProductRepositoryImpl`.
- Lebih flexible
    - Karena dengan dependency inversion principle, modul tingkat tinggi tidak lagi bergantung pada detail implementasi.
    - Contoh: karena `CarController` memanggil interface `CarService` dan bukan implementasinya, bisa lebih mudah untuk mengganti logic pada `CarServiceImpl` tanpa harus mengubah code pada `CarController`.
- Memudahkan ketika melakukan test 
    - Karena dependensi bersifat abstract (interface) sehingga kita dapat melakukan unit test dengan mudah dengan mock.
    - Contoh: ketika ingin test `CarController` cukup inject mock `CarService`.

**3) Explain the disadvantages of not applying SOLID principles to your project with examples.**
- Kode akan lebih kompleks sehingga susah untuk dipahami dan dikembangkan
    - Tanpa interface segregation principle, sebuah kelas mungkin dapat dipaksa untuk mengimplementasikan method yang tidak diperlukannya.
    - Contoh: misal hanya ada satu class `GeneralService` untuk semuanya, maka class `Car` mungkin harus untuk mengimplementasikan method yang tidak relevan untuk mobil misal method pada class `Product`.
- Tight coupling atau ketergantungan pada implementasi concrete
    - Tanpa dependency inversion principle, modul tingkat tinggi akan langsung bergantung dengan modul tingkat rendah.
    - Contoh: Jika `CarController` langsung memanggil `CarServiceImpl` maka tiap ada perubahan di implementasinya (`CarServiceImpl`) harus mengubah kode di controllernya (`CarController`) juga.
</details>


<details>
<summary>
<b>Reflection Modul 4</b>
</summary>

# Reflection

## You have followed the Test-Driven Development workflow in the Exercise. Now answer these questions:

**1. Reflect based on Percival (2017) proposed self-reflective questions (in “Principles and Best Practice of Testing” submodule, chapter “Evaluating Your Testing Objectives”), whether this TDD flow is useful enough for you or not. If not, explain things that you need to do next time you make more tests.**
- Menurut saya, TDD yang saya ikuti pada tutorial ini berguna. Dengan menulis test sebelum memulai coding, saya jadi lebih memahami requirement dan code yang saya buat. Proses coding yang saya lakukan juga jadi lebih terarah. 
- RED-GREEN-REFACTOR membantu saya dalam memastikan fitur berjalan sebelum melanjutkan ke fitur yang selanjutnya.
- Meskipun membantu, ada beberapa hal yang menurut saya perlu untuk saya perbaiki yaitu adalah membuat test case yang lebih beragam (tidak hanya happy dan unhappy tetapi edge case yang lebih specific juga) 

**2. You have created unit tests in Tutorial. Now reflect whether your tests have successfully followed F.I.R.S.T. principle or not. If not, explain things that you need to do the next time you create more tests.**
- Fast: test berjalan dengan cepat karena menggunakan `mock`.
- Independent: setiap test independent karena menggunakan `setUp()` yang dijalankan ulang setiap test.
- Repeatable: test bisa dijalankan berulang-ulang dengan hasil yang sama.
- Self-validating: menggunakan `assertions` sehingga hasilnya jelas.
- Timely: test ditulis sebelum implementasi sesuai dengan TDD.

# Bonus 2
**1. Explain what you think about your partner’s code? Are there any aspects that are still lacking from your partner’s code?**
Secara keseluruhan, kode partner sudah terstruktur dengan cukup baik. Pemisahan antara service dan repository juga sudah dilakukan dengan benar. Namun, ada beberapa aspek yang masih kurang. 
- PaymentServiceImpl menggunakan konstanta string private (STATUS_SUCCESS, STATUS_REJECTED) dan tdk memanfaatkan enum yang lebih terstruktur, sehingga rentan terhadap typo dan tidak konsisten. 
- PaymentService langsung memanipulasi status Order melalui payment.getOrder().setStatus(...), yang berarti service ini terlalu tahu tentang internal model lain dan melanggar prinsip encapsulation.

**2. What did you do to contribute to your partner’s code?**
- Mengidentifikasi code smells yang ada pada kode partner 
- Melakukan refactoring untuk memperbaikinya. 
  - Saya membuat enum PaymentStatus untuk menggantikan penggunaan konstanta string
  - Menambahkan method contains() untuk validasi status
  - Menambahkan OrderService ke dalam PaymentServiceImpl 
  - Memindahkan logic update status Order ke method terpisah.

**3. What code smells did you find on your partner’s code?**
- Magic String: PaymentServiceImpl mendefinisikan STATUS_SUCCESS dan STATUS_REJECTED sebagai konstanta string private dan tdk menggunakan enum, sehingga tidak type-safe dan rentan typo.
- Inappropriate Intimacy: PaymentService langsung memanggil payment.getOrder().setStatus(...) untuk mengubah status Order. Ini berarti PaymentService terlalu dalam masuk ke internal model Order, melanggar encapsulation dan prinsip Single Responsibility.

**4. What refactoring steps did you suggest and execute to fix those smells?**
- Magic String: Membuat enum PaymentStatus dengan method contains() untuk validasi, lalu mengganti semua penggunaan konstanta string dengan PaymentStatus.SUCCESS.getValue() dan PaymentStatus.REJECTED.getValue().
- Inappropriate Intimacy: Menambahkan OrderService ke PaymentServiceImpl dan memisahkan logika update Order ke method updateRelatedOrderStatus() yang mendelegasikan ke orderService.updateStatus(), sehingga PaymentService tidak lagi langsung menyentuh internal Order.
</details>