package usecase;

import model.Student;

/**
 * Implementasi Binary Search Tree (BST) untuk menyimpan data mahasiswa
 * yang diurutkan berdasarkan nilai IPK.
 *
 * <p>Kompleksitas operasi:
 * <ul>
 *   <li>Insert, Search, Delete: O(log n) rata-rata, O(n) terburuk (pohon miring)
 *   <li>Traversal: O(n)
 * </ul>
 */
public class StudentBST {

  /**
   * Node internal BST yang menyimpan satu data mahasiswa beserta
   * referensi ke anak kiri dan kanan.
   */
  private static class Node {
    Student data;
    Node left;
    Node right;

    Node(Student data) {
      this.data = data;
      this.left = null;
      this.right = null;
    }
  }

  /** Simpul akar (root) dari BST. */
  private Node root;

  /**
   * Membuat BST kosong.
   */
  public StudentBST() {
    this.root = null;
  }

  /**
   * Menyisipkan mahasiswa ke dalam BST berdasarkan nilai IPK.
   * Mahasiswa dengan IPK lebih kecil ditempatkan di subtree kiri,
   * IPK lebih besar atau sama di subtree kanan.
   *
   * @param student Objek Student yang akan disisipkan
   */
  public void insert(Student student) {
    root = insertRec(root, student);
  }

  /**
   * Rekursi pembantu untuk menyisipkan node baru ke posisi yang tepat.
   *
   * @param root    Node saat ini dalam rekursi
   * @param student Mahasiswa yang akan disisipkan
   * @return Node hasil penyisipan
   */
  private Node insertRec(Node root, Student student) {
    if (root == null) {
      return new Node(student);
    }
    if (student.getIpk() < root.data.getIpk()) {
      root.left = insertRec(root.left, student);
    } else {
      root.right = insertRec(root.right, student);
    }
    return root;
  }

  /**
   * Mencari mahasiswa berdasarkan nilai IPK.
   *
   * @param ipk Nilai IPK yang dicari
   * @return Objek Student jika ditemukan, null jika tidak ada
   */
  public Student search(double ipk) {
    Node result = searchRec(root, ipk);
    return result != null ? result.data : null;
  }

  /**
   * Rekursi pembantu untuk mencari node dengan IPK tertentu.
   *
   * @param root Node saat ini dalam rekursi
   * @param ipk  Nilai IPK yang dicari
   * @return Node yang cocok, atau null jika tidak ditemukan
   */
  private Node searchRec(Node root, double ipk) {
    if (root == null || root.data.getIpk() == ipk) {
      return root;
    }
    if (ipk < root.data.getIpk()) {
      return searchRec(root.left, ipk);
    }
    return searchRec(root.right, ipk);
  }

  /**
   * Menghapus mahasiswa dari BST berdasarkan nilai IPK.
   * Jika node memiliki dua anak, diganti dengan successor (nilai terkecil
   * dari subtree kanan).
   *
   * @param ipk Nilai IPK mahasiswa yang akan dihapus
   */
  public void delete(double ipk) {
    root = deleteRec(root, ipk);
  }

  /**
   * Rekursi pembantu untuk menghapus node dengan IPK tertentu.
   *
   * @param root Node saat ini dalam rekursi
   * @param ipk  Nilai IPK node yang dihapus
   * @return Node hasil setelah penghapusan
   */
  private Node deleteRec(Node root, double ipk) {
    if (root == null) {
      return null;
    }
    if (ipk < root.data.getIpk()) {
      root.left = deleteRec(root.left, ipk);
    } else if (ipk > root.data.getIpk()) {
      root.right = deleteRec(root.right, ipk);
    } else {
      // Node ditemukan: tangani tiga kasus penghapusan
      if (root.left == null) return root.right;
      if (root.right == null) return root.left;
      // Dua anak: ganti dengan successor terkecil dari subtree kanan
      root.data = minValue(root.right);
      root.right = deleteRec(root.right, root.data.getIpk());
    }
    return root;
  }

  /**
   * Menemukan nilai mahasiswa dengan IPK terkecil dalam subtree yang diberikan
   * (node paling kiri).
   *
   * @param root Akar subtree yang diperiksa
   * @return Student dengan IPK terkecil
   */
  private Student minValue(Node root) {
    Student minv = root.data;
    while (root.left != null) {
      minv = root.left.data;
      root = root.left;
    }
    return minv;
  }

  /**
   * Menampilkan seluruh data mahasiswa dalam urutan Inorder (Kiri-Akar-Kanan).
   * Menghasilkan output terurut dari IPK terkecil ke terbesar.
   */
  public void inorderTraversal() {
    System.out.println("--- Inorder Traversal: Kiri -> Akar -> Kanan (IPK Ascending) ---");
    inorderRec(root);
  }

  /**
   * Rekursi pembantu untuk traversal Inorder.
   *
   * @param root Node saat ini dalam rekursi
   */
  private void inorderRec(Node root) {
    if (root != null) {
      inorderRec(root.left);
      System.out.println("  " + root.data);
      inorderRec(root.right);
    }
  }

  /**
   * Menampilkan seluruh data mahasiswa dalam urutan Preorder (Akar-Kiri-Kanan).
   * Berguna untuk menyalin atau merekonstruksi struktur pohon.
   */
  public void preorderTraversal() {
    System.out.println("--- Preorder Traversal: Akar -> Kiri -> Kanan ---");
    preorderRec(root);
  }

  /**
   * Rekursi pembantu untuk traversal Preorder.
   *
   * @param root Node saat ini dalam rekursi
   */
  private void preorderRec(Node root) {
    if (root != null) {
      System.out.println("  " + root.data);
      preorderRec(root.left);
      preorderRec(root.right);
    }
  }

  /**
   * Menampilkan seluruh data mahasiswa dalam urutan Postorder (Kiri-Kanan-Akar).
   * Berguna untuk menghapus pohon secara aman dari daun ke akar.
   */
  public void postorderTraversal() {
    System.out.println("--- Postorder Traversal: Kiri -> Kanan -> Akar ---");
    postorderRec(root);
  }

  /**
   * Rekursi pembantu untuk traversal Postorder.
   *
   * @param root Node saat ini dalam rekursi
   */
  private void postorderRec(Node root) {
    if (root != null) {
      postorderRec(root.left);
      postorderRec(root.right);
      System.out.println("  " + root.data);
    }
  }
}
