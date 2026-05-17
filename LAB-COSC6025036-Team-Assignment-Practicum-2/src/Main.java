import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.Student;
import usecase.StudentBST;
import usecase.StudentHashTable;

/**
 * Titik masuk utama aplikasi Sistem Manajemen Data Mahasiswa.
 *
 * <p>Aplikasi ini mendemonstrasikan penggunaan dua struktur data:
 * <ul>
 *   <li>Hash Table (HashMap) untuk pencarian cepat O(1) berdasarkan NIM
 *   <li>Binary Search Tree (BST) untuk penyimpanan terurut berdasarkan IPK
 * </ul>
 *
 * <p>Data mahasiswa dimuat dari file CSV {@code students.csv}.
 */
public class Main {

    /**
     * Metode utama yang menjalankan seluruh alur demonstrasi aplikasi.
     * Urutan eksekusi:
     * <ol>
     *   <li>Muat data dari CSV
     *   <li>Masukkan semua mahasiswa ke Hash Table dan BST
     *   <li>Tampilkan semua traversal BST (Inorder, Preorder, Postorder)
     *   <li>Tampilkan semua data dari Hash Table
     *   <li>Demonstrasi delete pada BST
     *   <li>Analisis perbandingan waktu pencarian
     * </ol>
     *
     * @param args Argumen baris perintah (tidak digunakan)
     */
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   Sistem Manajemen Data Mahasiswa      ");
        System.out.println("   Hash Table + Binary Search Tree (BST)");
        System.out.println("========================================");

        StudentHashTable hashTable = new StudentHashTable();
        StudentBST bst = new StudentBST();

        List<Student> studentList = importFromCSV("students.csv");
        if (studentList.isEmpty()) {
            System.out.println("Data mahasiswa kosong atau file tidak ditemukan.");
            return;
        }
        Student[] students = studentList.toArray(new Student[0]);

        // ── 1. Hash Table ────────────────────────────────────────────────────
        System.out.println("\n========================================");
        System.out.println(" HASH TABLE: Menambah Data Mahasiswa    ");
        System.out.println("========================================");
        for (Student s : students) {
            hashTable.addStudent(s);
        }

        System.out.println("\n--- Seluruh Data dalam Hash Table ---");
        int no = 1;
        for (Student s : hashTable.getAllStudents()) {
            System.out.println("  " + no++ + ". " + s);
        }

        // Demonstrasi pencarian dan penghapusan pada Hash Table
        System.out.println("\n--- Operasi Hash Table: Cari & Hapus ---");
        String nimCari = students[2].getNim();
        Student found = hashTable.findStudent(nimCari);
        System.out.println("Cari NIM " + nimCari + ": " +
                (found != null ? "Ditemukan -> " + found : "Tidak ditemukan"));

        hashTable.removeStudent(nimCari);
        found = hashTable.findStudent(nimCari);
        System.out.println("Cari NIM " + nimCari + " setelah dihapus: " +
                (found != null ? "Masih ada" : "Tidak ditemukan (berhasil dihapus)"));

        // Tambahkan kembali agar data lengkap untuk analisis
        hashTable.addStudent(students[2]);

        // ── 2. BST ───────────────────────────────────────────────────────────
        System.out.println("\n========================================");
        System.out.println(" BST: Memasukkan Data (Berdasarkan IPK) ");
        System.out.println("========================================");
        for (Student s : students) {
            System.out.println("  Insert IPK=" + s.getIpk() + " -> " + s.getNama());
            bst.insert(s);
        }

        // Tiga jenis traversal
        System.out.println("\n========================================");
        System.out.println(" BST TRAVERSAL                          ");
        System.out.println("========================================");
        bst.inorderTraversal();
        System.out.println();
        bst.preorderTraversal();
        System.out.println();
        bst.postorderTraversal();

        // Demonstrasi pencarian dan penghapusan pada BST
        System.out.println("\n--- Operasi BST: Cari & Hapus ---");
        double ipkCari = students[4].getIpk();
        Student foundBst = bst.search(ipkCari);
        System.out.println("Cari IPK=" + ipkCari + ": " +
                (foundBst != null ? "Ditemukan -> " + foundBst : "Tidak ditemukan"));

        bst.delete(ipkCari);
        foundBst = bst.search(ipkCari);
        System.out.println("Cari IPK=" + ipkCari + " setelah dihapus: " +
                (foundBst != null ? "Masih ada" : "Tidak ditemukan (berhasil dihapus)"));

        // Tambahkan kembali agar data lengkap untuk analisis
        bst.insert(students[4]);

        // ── 3. Analisis Performa ────────────────────────────────────────────
        System.out.println("\n========================================");
        System.out.println(" ANALISIS PERBANDINGAN WAKTU PENCARIAN  ");
        System.out.println("========================================");
        analyzeSearchPerformance(hashTable, bst, students);
    }

    /**
     * Menganalisis dan membandingkan waktu pencarian antara Hash Table dan BST
     * untuk sejumlah mahasiswa sampel.
     *
     * <p>Setiap pencarian diukur menggunakan {@link System#nanoTime()} agar
     * resolusi waktu setinggi mungkin.
     *
     * @param hashTable Instansi Hash Table yang akan diuji
     * @param bst       Instansi BST yang akan diuji
     * @param students  Array seluruh mahasiswa sebagai pool data uji
     */
    private static void analyzeSearchPerformance(
            StudentHashTable hashTable, StudentBST bst, Student[] students) {

        // Pilih 6 indeks sampel yang tersebar merata
        int[] sampleIndices = {0, 2, 4, 7, 10, 13};

        System.out.printf("%-6s %-10s %-12s %20s %20s%n",
                "No", "NIM", "Nama", "Hash Table (ns)", "BST (ns)");
        System.out.println("-".repeat(72));

        long totalHash = 0, totalBst = 0;
        int count = 0;

        for (int idx : sampleIndices) {
            if (idx >= students.length) continue;
            Student target = students[idx];

            // Ukur Hash Table
            long t0 = System.nanoTime();
            hashTable.findStudent(target.getNim());
            long hashNs = System.nanoTime() - t0;

            // Ukur BST
            t0 = System.nanoTime();
            bst.search(target.getIpk());
            long bstNs = System.nanoTime() - t0;

            totalHash += hashNs;
            totalBst  += bstNs;
            count++;

            System.out.printf("%-6d %-10s %-12s %20d %20d%n",
                    count, target.getNim(), target.getNama(), hashNs, bstNs);
        }

        System.out.println("-".repeat(72));
        System.out.printf("%-6s %-10s %-12s %20d %20d%n",
                "RATA2", "", "", totalHash / count, totalBst / count);

        System.out.println();
        long avgHash = totalHash / count;
        long avgBst  = totalBst  / count;
        if (avgHash <= avgBst) {
            System.out.println("Kesimpulan: Hash Table lebih cepat (O(1) vs O(log n)).");
        } else {
            System.out.println("Kesimpulan: BST lebih cepat pada sampel ini.");
        }
        System.out.println("  Hash Table cocok untuk pencarian tunggal berdasarkan kunci (NIM).");
        System.out.println("  BST cocok untuk pencarian rentang dan traversal terurut (IPK).");
    }

    /**
     * Membaca data mahasiswa dari file CSV dengan format: NIM,Nama,IPK.
     * Baris pertama (header) dilewati secara otomatis.
     *
     * @param filename Nama atau path relatif file CSV
     * @return List berisi objek Student yang berhasil dimuat
     */
    private static List<Student> importFromCSV(String filename) {
        List<Student> students = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            br.readLine(); // lewati header
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 3) {
                    students.add(new Student(
                            values[0].trim(),
                            values[1].trim(),
                            Double.parseDouble(values[2].trim())));
                }
            }
            System.out.println("Berhasil memuat " + students.size() + " data dari " + filename);
        } catch (IOException e) {
            System.out.println("Gagal membaca file CSV: " + e.getMessage());
        }
        return students;
    }
}
