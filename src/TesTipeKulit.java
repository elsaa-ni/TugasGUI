import javax.swing.*; // untuk komponen non layout
import java.awt.*; // untuk layout manager dan warna
import java.awt.event.*; // untuk event handling

// mengimplements 2 interface
public class TesTipeKulit implements ActionListener, ItemListener {

    // data tes
    // urutan tipe kulit sama dengan urutan opsi jawaban di setiap soal
    // jadi opsi ke-0 = Normal, opsi ke-1 = Berminyak, dst
    String[] tipeKulit = {"Normal", "Berminyak", "Kering", "Kombinasi", "Sensitif"};

    String[] deskripsi = {
        "Kulit seimbang, tidak terlalu berminyak dan tidak terlalu kering. "
            + "Cukup rutin cuci muka, pakai pelembap ringan, dan tabir surya.",
        "Produksi minyak berlebih sehingga wajah mudah mengkilap. "
            + "Pilih produk berlabel oil-free dan tetap pakai pelembap yang ringan.",
        "Kulit mudah terasa kencang dan bersisik. "
            + "Pilih pembersih yang lembut dan pelembap yang lebih kaya.",
        "Area T-zone (dahi dan hidung) cenderung berminyak, sedangkan pipi normal atau kering. "
            + "Perawatan bisa dibedakan per area.",
        "Kulit mudah merah, perih, atau iritasi. Hindari produk beraroma kuat dan "
            + "coba produk baru sedikit demi sedikit. Kalau sering iritasi, sebaiknya konsultasi ke dokter kulit."
    };

    String[] soal = {
        "30 menit setelah cuci muka, kulitmu terasa...",
        "Pori-pori wajahmu terlihat...",
        "Menjelang sore, wajahmu biasanya...",
        "Saat mencoba produk skincare baru, kulitmu..."
    };

    String[][] opsi = {
        {"Nyaman, biasa saja", "Mulai berminyak", "Kencang / tertarik",
         "T-zone berminyak, pipi normal/kering", "Merah atau perih"},
        {"Kecil dan halus", "Besar di seluruh wajah", "Hampir tak terlihat, kulit kusam",
         "Besar di hidung & dahi saja", "Mudah kemerahan di sekitarnya"},
        {"Segar seperti biasa", "Mengkilap di mana-mana", "Kering dan bersisik",
         "Mengkilap di T-zone saja", "Gampang memerah"},
        {"Cocok-cocok saja", "Jadi lebih berminyak", "Jadi makin kering",
         "Sebagian area berminyak, sebagian kering", "Sering iritasi / gatal"}
    };

    int[] skor = new int[5]; // jumlah "suara" untuk tiap tipe kulit, awalnya 0 semua

    Color hijauTua   = new Color(46, 94, 78);
    Color krem       = new Color(255, 248, 220);
    Color pinkSoft   = new Color(255, 228, 235);
    Color hijauMint  = new Color(220, 245, 235);
    Color oranyeMuda = new Color(255, 204, 153);
    Color hijau      = new Color(76, 175, 80);
    Color merah      = new Color(229, 57, 53);

    // komponen
    JFrame frame;
    JPanel panelUtama, panelAtas, panelHeader, panelData, panelSoal, panelHasil, panelTombol;
    JPanel[] kotakSoal = new JPanel[4];
    JLabel labelJudul, labelNama, labelTema, labelDaftar, labelTipe;
    JTextField textNama;
    JComboBox<String> comboTema;
    JRadioButton[][] pilihan = new JRadioButton[4][5];
    ButtonGroup[] grup = new ButtonGroup[4];
    JButton tombolProses, tombolReset;
    JTextArea areaHasil;
    JScrollPane scroll;

    public TesTipeKulit() {
        // top level container
        frame = new JFrame("Sistem Tes Tipe Kulit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(950, 600);
        frame.setLocationRelativeTo(null); // frame muncul di tengah layar

        // intermediate containers
        // panel header
        panelHeader = new JPanel();
        panelHeader.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));
        panelHeader.setBackground(hijauTua);

        labelJudul = new JLabel("TES TIPE KULIT");
        labelJudul.setFont(new Font("Arial", Font.BOLD, 26));
        labelJudul.setForeground(Color.WHITE);
        panelHeader.add(labelJudul);

        // panel data pengguna
        panelData = new JPanel();
        panelData.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        labelNama = new JLabel("Nama:");
        textNama = new JTextField(15);
        labelTema = new JLabel("Tema:");

        String[] daftarTema = {"Krem", "Pink Soft", "Hijau Mint"};
        comboTema = new JComboBox<String>(daftarTema);

        panelData.add(labelNama);
        panelData.add(textNama);
        panelData.add(labelTema);
        panelData.add(comboTema);

        // panel atas (header + data pengguna)
        panelAtas = new JPanel();
        panelAtas.setLayout(new GridLayout(2, 1));
        panelAtas.add(panelHeader);
        panelAtas.add(panelData);

        GridLayout layoutSoal = new GridLayout(2, 2);
        layoutSoal.setHgap(10);
        layoutSoal.setVgap(10);

        // panel soal
        panelSoal = new JPanel();
        panelSoal.setLayout(layoutSoal);
        panelSoal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // membuat 4 kotak soal dengan perulangan
        for (int i = 0; i < soal.length; i++) {
            kotakSoal[i] = new JPanel();
            kotakSoal[i].setLayout(new BoxLayout(kotakSoal[i], BoxLayout.Y_AXIS)); // opsi turun ke bawah
            kotakSoal[i].setBorder(BorderFactory.createTitledBorder((i + 1) + ". " + soal[i]));

            grup[i] = new ButtonGroup(); // supaya cuma bisa pilih 1 opsi per soal
            for (int j = 0; j < tipeKulit.length; j++) {
                pilihan[i][j] = new JRadioButton(opsi[i][j]);
                grup[i].add(pilihan[i][j]);      // masuk grup (logika pilih satu)
                kotakSoal[i].add(pilihan[i][j]); // masuk panel (tampilan)
            }
            panelSoal.add(kotakSoal[i]);
        }

        // panel hasil
        panelHasil = new JPanel();
        panelHasil.setLayout(new BorderLayout(5, 5));
        panelHasil.setPreferredSize(new Dimension(280, 0));
        panelHasil.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));

        labelDaftar = new JLabel("Hasil Tes:");
        labelDaftar.setFont(new Font("Arial", Font.BOLD, 14));
        labelDaftar.setForeground(hijauTua);

        areaHasil = new JTextArea();
        areaHasil.setEditable(false); // pengguna tidak bisa mengetik di sini
        areaHasil.setLineWrap(true);
        areaHasil.setWrapStyleWord(true);
        areaHasil.setFont(new Font("Monospaced", Font.PLAIN, 13));
        scroll = new JScrollPane(areaHasil); // supaya ada scrollbar kalau teks panjang

        labelTipe = new JLabel("Tipe: -");
        labelTipe.setFont(new Font("Arial", Font.BOLD, 18));
        labelTipe.setForeground(hijauTua);

        panelHasil.add(labelDaftar, BorderLayout.NORTH);
        panelHasil.add(scroll, BorderLayout.CENTER);
        panelHasil.add(labelTipe, BorderLayout.SOUTH);

        // panel tombol
        panelTombol = new JPanel();
        panelTombol.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelTombol.setBackground(hijauTua);

        tombolProses = new JButton("Tentukan Tipe Kulit");
        tombolProses.setBackground(hijau);
        tombolProses.setForeground(Color.WHITE);
        tombolProses.setOpaque(true);
        tombolProses.setBorderPainted(false);

        tombolReset = new JButton("Reset");
        tombolReset.setBackground(merah);
        tombolReset.setForeground(Color.WHITE);
        tombolReset.setOpaque(true);
        tombolReset.setBorderPainted(false);

        panelTombol.add(tombolProses);
        panelTombol.add(tombolReset);

        // panel utama (menggabungkan semua panel)
        panelUtama = new JPanel();
        panelUtama.setLayout(new BorderLayout());
        panelUtama.add(panelAtas, BorderLayout.NORTH);
        panelUtama.add(panelSoal, BorderLayout.CENTER);
        panelUtama.add(panelHasil, BorderLayout.EAST);
        panelUtama.add(panelTombol, BorderLayout.SOUTH);
        frame.getContentPane().add(panelUtama);

        // warna awal pakai tema krem
        gantiTema(krem);

        // event listener
        tombolProses.addActionListener(this);
        tombolReset.addActionListener(this);
        comboTema.addItemListener(this);

        frame.setVisible(true);
    }

    // method main hanya untuk menjalankan constructor
    public static void main(String[] args) {
        new TesTipeKulit();
    }

    // method bantu
    // mengganti warna latar semua panel (dipanggil saat awal dan saat tema diganti)
    public void gantiTema(Color warna) {
        panelData.setBackground(warna);
        panelSoal.setBackground(warna);
        panelHasil.setBackground(warna);

        for (int i = 0; i < soal.length; i++) {
            kotakSoal[i].setBackground(warna);
            for (int j = 0; j < tipeKulit.length; j++) {
                pilihan[i][j].setBackground(warna);
            }
        }
    }

    public void prosesTes() {
        String nama = textNama.getText().trim();

        // cek nama sudah diisi atau belum
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Isi nama dulu ya!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // cek semua soal sudah dijawab atau belum
        for (int i = 0; i < soal.length; i++) {
            if (grup[i].getSelection() == null) {
                JOptionPane.showMessageDialog(frame, "Pertanyaan no. " + (i + 1) + " belum dijawab!",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        // hitung skor, tiap jawaban memberi 1 suara ke tipe kulit yang sesuai
        skor = new int[tipeKulit.length];
        for (int i = 0; i < soal.length; i++) {
            for (int j = 0; j < tipeKulit.length; j++) {
                if (pilihan[i][j].isSelected()) {
                    skor[j] = skor[j] + 1;
                }
            }
        }

        // cari tipe dengan skor tertinggi
        // kalau skor sama, yang indexnya lebih kecil yang menang
        int menang = 0;
        for (int j = 1; j < tipeKulit.length; j++) {
            if (skor[j] > skor[menang]) {
                menang = j;
            }
        }

        // tampilkan hasil
        String isi = "Halo, " + nama + "!\n\nRincian jawaban:\n";
        for (int j = 0; j < tipeKulit.length; j++) {
            isi = isi + "- " + tipeKulit[j] + ": " + skor[j] + "\n";
        }
        isi = isi + "\n" + deskripsi[menang];

        areaHasil.setText(isi);
        areaHasil.setCaretPosition(0); // scroll balik ke atas
        labelTipe.setText("Tipe: " + tipeKulit[menang]);

        JOptionPane.showMessageDialog(frame,
                "Tipe kulit " + nama + ": " + tipeKulit[menang],
                "Hasil Tes", JOptionPane.INFORMATION_MESSAGE);
    }

    public void resetTes() {
        textNama.setText("");
        for (int i = 0; i < soal.length; i++) {
            grup[i].clearSelection(); // hapus pilihan radio button
        }
        areaHasil.setText("");
        labelTipe.setText("Tipe: -");
    }

    // interface 1
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == tombolProses) {
            prosesTes();
        } else if (e.getSource() == tombolReset) {
            resetTes();
        }
    }

    // interface 2
    @Override
    public void itemStateChanged(ItemEvent e) {
        // event ini muncul 2 kali (SELECTED dan DESELECTED), kita ambil yang SELECTED saja
        if (e.getSource() == comboTema && e.getStateChange() == ItemEvent.SELECTED) {
            String tema = (String) comboTema.getSelectedItem();

            if (tema.equals("Pink Soft")) {
                gantiTema(pinkSoft);
            } else if (tema.equals("Hijau Mint")) {
                gantiTema(hijauMint);
            } else {
                gantiTema(krem);
            }
        }
    }
}