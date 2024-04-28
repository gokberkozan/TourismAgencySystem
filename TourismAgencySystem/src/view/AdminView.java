package view;

import core.Config;
import core.Helper;
import entity.Admin;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminView extends JFrame {
    private JPanel container;
    private JButton btn_admin_exit;
    private JTabbedPane tab_operator;
    private JTable tbl_user_list;
    private JTextField fld_user_name;
    private JTextField fld_user_uname;
    private JTextField fld_user_pass;
    private JComboBox<String> cmb_user_type;
    private JButton btn_user_add;
    private JTextField fld_user_id;
    private JButton btn_admin_delete;
    private JTextField fld_sh_user_name;
    private JTextField fld_sh_user_uname;
    private JComboBox<String> cmb_sh_user_type;
    private JButton btn_user_sh;
    private JScrollPane scrl_user_list;
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;
    private final Admin operator;
    private final String[] userTypes = {"admin", "employee"};

    public AdminView(Admin operator) {
        this.operator = operator;

        add(container);
        setSize(650, 650);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        // Kullanıcı Sekmesi Kodları
        mdl_user_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0 && super.isCellEditable(row, column);
            }
        };

        Object[] col_user_list = {"ID", "Name", "Username", "Password", "Role"};
        mdl_user_list.setColumnIdentifiers(col_user_list);
        row_user_list = new Object[col_user_list.length];
        loadUserModel();
        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);
        tbl_user_list.getColumnModel().getColumn(0).setMaxWidth(75);

        // The part that will run when the Add User button is pressed
        btn_user_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_name) || Helper.isFieldEmpty(fld_user_uname) || Helper.isFieldEmpty(fld_user_pass)) {
                Helper.showMsg("fill");
            } else {
                String name = fld_user_name.getText();
                String uname = fld_user_uname.getText();
                String pass = fld_user_pass.getText();
                String type = cmb_user_type.getSelectedItem().toString();
                if (User.add(name, uname, pass, type)) {
                    Helper.showMsg("done");
                    loadUserModel();
                    fld_user_name.setText(null);
                    fld_user_uname.setText(null);
                    fld_user_pass.setText(null);
                    // cmb_sh_user_type.setSelectedIndex(0);
                }
            }
        });

        // The part that will run when selected from the User list
        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Bu satır eklenmiştir
                int selectedRow = tbl_user_list.getSelectedRow();
                if (selectedRow != -1) {
                    try {
                        String select_user_id = tbl_user_list.getValueAt(selectedRow, 0).toString();
                        fld_user_id.setText(select_user_id);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });

        // Admin exit
        btn_admin_exit.addActionListener(e -> dispose());

        // User delete
        btn_admin_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_id)) {
                Helper.showMsg("fill");
            } else {
                int user_id = Integer.parseInt(fld_user_id.getText());
                if (User.delete(user_id)) {
                    Helper.showMsg("done");
                    loadUserModel(); // silme işleminden sonra tablonun güncellenir.
                } else {
                    Helper.showMsg("error");
                }
            }
        });

        fld_sh_user_name.addActionListener(e -> {});

        // User search
        btn_user_sh.addActionListener(e -> searchUsers());

        cmb_sh_user_type.setModel(new DefaultComboBoxModel<>(userTypes));

        cmb_user_type.setModel(new DefaultComboBoxModel<>(userTypes));
    }

    // Method to load users
    private void loadUserModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (User obj : User.getList()) {
            i = 0;
            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getName();
            row_user_list[i++] = obj.getUname();
            row_user_list[i++] = obj.getPass();
            row_user_list[i++] = obj.getType();
            mdl_user_list.addRow(row_user_list);
        }
    }

    // Search result users loading method
    private void loadUserModel(ArrayList<User> list) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (User obj : list) {
            i = 0;
            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getName();
            row_user_list[i++] = obj.getUname();
            row_user_list[i++] = obj.getPass();
            row_user_list[i++] = obj.getType();
            mdl_user_list.addRow(row_user_list);
        }
    }

    // Method that performs the user search
    private void searchUsers() {
        String name = fld_sh_user_name.getText();
        String uname = fld_sh_user_uname.getText();
        Object selectedType = cmb_sh_user_type.getSelectedItem();

        // Show error message if no type is selected
        if (selectedType == null) {
            Helper.showMsg("Please select a user type.");
            return;
        }
        String type = selectedType.toString();
        String query = User.searchQuery(name, uname, type);
        ArrayList<User> searching = User.searchUserList(query);
        loadUserModel(searching);
    }
}