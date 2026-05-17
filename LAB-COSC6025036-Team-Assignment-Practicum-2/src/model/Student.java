package model;

/**
 * Model data mahasiswa yang menyimpan NIM, nama, dan IPK.
 */
public class Student {
  private String nim;
  private String nama;
  private double ipk;

  /**
   * Membuat objek Student baru dengan NIM, nama, dan IPK yang diberikan.
   *
   * @param nim  Nomor Induk Mahasiswa (kunci unik)
   * @param nama Nama lengkap mahasiswa
   * @param ipk  Indeks Prestasi Kumulatif mahasiswa
   */
  public Student(String nim, String nama, double ipk) {
    this.nim = nim;
    this.nama = nama;
    this.ipk = ipk;
  }

  /**
   * Mengembalikan NIM mahasiswa.
   *
   * @return NIM sebagai String
   */
  public String getNim() {
    return nim;
  }

  /**
   * Mengembalikan nama mahasiswa.
   *
   * @return Nama sebagai String
   */
  public String getNama() {
    return nama;
  }

  /**
   * Mengembalikan IPK mahasiswa.
   *
   * @return IPK sebagai double
   */
  public double getIpk() {
    return ipk;
  }

  /**
   * Mengembalikan representasi String dari objek Student.
   *
   * @return String berformat "NIM: ..., Nama: ..., IPK: ..."
   */
  @Override
  public String toString() {
    return "NIM: " + nim + ", Nama: " + nama + ", IPK: " + ipk;
  }
}
