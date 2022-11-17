/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package controller;

import java.sql.SQLException;
import view.Form;

/**
 *
 * @author lotso
 */
public interface ControllerKaryawan {
    public void Simpan(Form data) throws SQLException;
    public void Ubah(Form data) throws SQLException;
    public void Hapus(Form data) throws SQLException;
    public void Batal(Form data) throws SQLException;
    public void KlikTable(Form data) throws SQLException;
    public void Tampil(Form data) throws SQLException;
}
