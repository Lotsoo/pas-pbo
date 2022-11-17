/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import controller.ControllerKaryawan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import koneksi.Koneksi;
import view.Form;

/**
 *
 * @author lotso
 */
public class ModelKaryawan implements ControllerKaryawan {

    String jk, status;

    @Override
    public void Simpan(Form data) throws SQLException {

        if (data.rbLaki.isSelected()) {
            jk = "Laki-Laki";
        } else {
            jk = "Perempuan";
        }

        if (data.rbMenikah.isSelected()) {
            status = "Menikah";
        } else {
            status = "Belum Menikah";
        }
        try {
            Connection con = Koneksi.getCon();
            String sql = "insert into karyawan values (?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, data.txtNik.getText());
            ps.setString(2, data.txtNama.getText());
            ps.setString(3, data.txtJabatan.getText());
            ps.setString(4, jk);
            ps.setString(5, (String) data.cbAgama.getSelectedItem());
            ps.setString(6, status);
            ps.setString(7, data.txtAlamat.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            ps.close();
            Batal(data);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data gagal disimpan");
            System.err.println(e);
        } finally {
            Tampil(data);
            data.setLebarKolom();
        }
    }

    @Override
    public void Ubah(Form data) throws SQLException {

        String jk;
        if (data.rbLaki.isSelected()) {
            jk = "Laki-Laki";
        } else {
            jk = "Perempuan";
        }

        String status;
        if (data.rbMenikah.isSelected()) {
            status = "Menikah";
        } else {
            status = "Belum Menikah";
        }

        try {
            Connection con = Koneksi.getCon();
            String sql = "update karyawan set nama=?, jabatan=?, jk=?, agama=?, status=?, alamat=? where nik=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, data.txtNama.getText());
            ps.setString(2, data.txtJabatan.getText());
            ps.setString(3, jk);
            ps.setString(4, (String) data.cbAgama.getSelectedItem());
            ps.setString(5, status);
            ps.setString(6, data.txtAlamat.getText());
            ps.setString(7, data.txtNik.getText());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
            ps.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data gagal diubah");
        } finally {
            Tampil(data);
            data.setLebarKolom();
            Batal(data);
        }
    }

    @Override
    public void Hapus(Form data) throws SQLException {
        try {
            Connection con = Koneksi.getCon();
            String sql = "delete from karyawan where nik= ?";
            PreparedStatement prepare = con.prepareStatement(sql);

            prepare.setString(1, data.txtNik.getText());
            prepare.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data Berhasil di Ubah");
            prepare.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            Tampil(data);
            data.setLebarKolom();
            Batal(data);
        }
    }

    @Override
    public void Batal(Form data) throws SQLException {
        data.txtNik.setText("");
        data.txtNama.setText("");
        data.txtJabatan.setText("");
        data.rbLaki.setSelected(true);
        data.cbAgama.setSelectedIndex(0);
        data.rbBelumMenikah.setSelected(true);
        data.txtAlamat.setText("");
    }

    @Override
    public void KlikTable(Form data) throws SQLException {
        try {
            int pilih = data.table.getSelectedRow();
            if (pilih == -1) {
                return;
            }
            data.txtNik.setText(data.tblmodel.getValueAt(pilih, 0).toString());
            data.txtNama.setText(data.tblmodel.getValueAt(pilih, 1).toString());
            data.txtJabatan.setText(data.tblmodel.getValueAt(pilih, 2).toString());
            jk = String.valueOf(data.tblmodel.getValueAt(pilih, 3));
            data.cbAgama.setSelectedItem(data.tblmodel.getValueAt(pilih, 4).toString());
            status = String.valueOf(data.tblmodel.getValueAt(pilih, 5));
            data.txtAlamat.setText((String) data.tblmodel.getValueAt(pilih, 6));

        } catch (Exception e) {
        }

        if (data.rbLaki.getText().equals(jk)) {
            data.rbLaki.setSelected(true);
        } else {
            data.rbPerempuan.setSelected(true);
        }

        if (data.rbMenikah.getText().equals(status)) {
            data.rbMenikah.setSelected(true);
        } else {
            data.rbBelumMenikah.setSelected(true);
        }
    }

    @Override
    public void Tampil(Form data) throws SQLException {
        data.tblmodel.getDataVector().removeAllElements();
        data.tblmodel.fireTableDataChanged();

        try {
            Connection con = Koneksi.getCon();
            Statement stt = con.createStatement();
            String sql = "select * from karyawan order by nik asc";
            ResultSet rs = stt.executeQuery(sql);
            while (rs.next()) {
                Object[] ob = new Object[7];
                ob[0] = rs.getString(1);
                ob[1] = rs.getString(2);
                ob[2] = rs.getString(3);
                ob[3] = rs.getString(4);
                ob[4] = rs.getString(5);
                ob[5] = rs.getString(6);
                ob[6] = rs.getString(7);
                data.tblmodel.addRow(ob);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
