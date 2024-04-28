package view;

import core.Helper;
import entity.Hotel;
import core.Config;
import entity.Season;
import entity.HotelType;
import entity.Room;

import javax.swing.*;

public class RoomView extends JFrame {
    private JPanel container;
    private JTextField fld_room_hotel_id;
    private JTextField fld_room_name;
    private JComboBox<String> cmb_room_season_id; // JComboBox tipi belirtildi
    private JComboBox<String> cmb_room_hostel_id;
    private JTextField fld_adult_price;
    private JTextField fld_child_price;
    private JButton btn_add;
    private JTextField fld_room_stock;
    private JButton btn_roomadd_exit;
    private int hotel_id = EmployeeView.getSelacted_hotelId();
    private int addHotelid;
    private  int addSeasonid;
    private int addHostelid;

    public RoomView(){
        add(container);
        setSize(350, 400);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);

        for (Hotel obj : Hotel.getlistByid(hotel_id)) {
            String hotel_name = obj.getHotel_name();
            addHotelid = obj.getHotel_id();
            fld_room_hotel_id.setText(hotel_name);
        }
        for (Season obj : Season.getListByid(hotel_id)) {
            String season_name = obj.getName();
            addSeasonid = obj.getId();
            cmb_room_season_id.addItem(season_name); // Mevsimler JComboBox'a eklendi
        }
        for (HotelType obj : HotelType.getListByid(hotel_id)) {
            String hoteltype = obj.getType();
            addHostelid = obj.getId();
            cmb_room_hostel_id.addItem(hoteltype);
        }

        btn_roomadd_exit.addActionListener(e -> {
            dispose();
        });
        btn_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_room_name)
                    || Helper.isFieldEmpty(fld_room_stock)
                    ||Helper.isFieldEmpty(fld_adult_price)
                    || Helper.isFieldEmpty(fld_child_price)) {
                Helper.showMsg("fill");
            } else {
                String name = fld_room_name.getText();
                int stock = Integer.parseInt(fld_room_stock.getText());
                int adult_price = Integer.parseInt(fld_adult_price.getText());
                int child_price = Integer.parseInt(fld_child_price.getText());
                Room.add(name,stock,addSeasonid,adult_price,child_price,addHostelid,addHotelid);

                Helper.showMsg("done");
            }
        });
    }
}