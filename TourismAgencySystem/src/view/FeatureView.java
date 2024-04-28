package view;

import core.Config;
import core.Helper;
import entity.Room;
import entity.Roomfeatures;

import javax.swing.*;

public class FeatureView extends JFrame{
    private JPanel container;
    private JCheckBox cb_romm_tv;
    private JCheckBox cb_room_minibar;
    private JCheckBox cb_room_till;
    private JCheckBox cb_room_projection;
    private JCheckBox cb_room_game_console;
    private JButton btn_room_features_add;
    private JTextField room_test;
    private JButton btn_room_features_exit;
    private int selected_room_id = EmployeeView.getSelected_roomId();
    private Room room;

    public FeatureView(Room room) {
        this.room=room;
        add(container);
        setSize(350, 300);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
        String id = String.valueOf(room.getId());
        room_test.setText(id);

        btn_room_features_add.addActionListener(e -> {
            // Checks whether the user left an empty space or not
            if (areFieldsEmptyRoomFeatures()) {
                Helper.showMsg("fill");
                return;
            }
            if (cb_romm_tv.isSelected()){
                String tv = "TV";
                Roomfeatures.add(tv, Integer.parseInt(room_test.getText()));
            }
            if (cb_room_minibar.isSelected()){
                String minibar = "Minibar";
                Roomfeatures.add(minibar, Integer.parseInt(room_test.getText()));
            }
            if (cb_room_game_console.isSelected()){
                String game = "Game Console";
                Roomfeatures.add(game,Integer.parseInt(room_test.getText()));
            }
            if (cb_room_till.isSelected()){
                String till = "Safe";
                Roomfeatures.add(till,Integer.parseInt(room_test.getText()));
            }
            if (cb_room_projection.isSelected()){
                String projectin = "Projection";
                Roomfeatures.add(projectin,Integer.parseInt(room_test.getText()));
            }
            Helper.showMsg("done");
        });
        btn_room_features_exit.addActionListener(e -> {
            dispose();
        });
    }

    private boolean areFieldsEmptyRoomFeatures() {
        return !(cb_romm_tv.isSelected()  ||cb_room_minibar.isSelected() || cb_room_till.isSelected()||
                cb_room_projection.isSelected()  ||cb_room_game_console.isSelected());
    }
}