package usecase;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import model.Student;

/**
 * Implementasi Hash Table untuk menyimpan dan mencari data mahasiswa.
 * Menggunakan HashMap dengan NIM sebagai kunci untuk pencarian O(1) rata-rata.
 */
public class StudentHashTable {
  /** Peta internal yang menyimpan mahasiswa dengan NIM sebagai kunci. */
  private Map<String, Student> studentMap;

  /**
   * Membuat Hash Table kosong untuk menyimpan data mahasiswa.
   */
  public StudentHashTable() {
    this.studentMap = new HashMap<>();
  }

  /**
   * Menambahkan mahasiswa ke dalam Hash Table menggunakan NIM sebagai kunci.
   * Kompleksitas rata-rata: O(1).
   *
   * @param student Objek Student yang akan ditambahkan
   */
  public void addStudent(Student student) {
    studentMap.put(student.getNim(), student);
    System.out.println("Tambah: " + student);
  }

  /**
   * Mencari mahasiswa berdasarkan NIM.
   * Kompleksitas rata-rata: O(1).
   *
   * @param nim NIM mahasiswa yang dicari
   * @return Objek Student jika ditemukan, null jika tidak ada
   */
  public Student findStudent(String nim) {
    return studentMap.get(nim);
  }

  /**
   * Menghapus mahasiswa dari Hash Table berdasarkan NIM.
   * Kompleksitas rata-rata: O(1).
   *
   * @param nim NIM mahasiswa yang akan dihapus
   */
  public void removeStudent(String nim) {
    Student s = studentMap.remove(nim);
    if (s != null) {
      System.out.println("Mahasiswa dengan NIM " + nim + " berhasil dihapus.");
    } else {
      System.out.println("Mahasiswa dengan NIM " + nim + " tidak ditemukan.");
    }
  }

  /**
   * Mengembalikan seluruh koleksi mahasiswa yang tersimpan di Hash Table.
   *
   * @return Collection berisi semua objek Student
   */
  public Collection<Student> getAllStudents() {
    return studentMap.values();
  }
}
