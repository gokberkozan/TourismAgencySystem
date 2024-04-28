package view;

import core.Config;
import core.Db;
import core.Helper;
import entity.Hotel;

import javax.swing.*;
import java.sql.Connection;

public class HotelView extends JFrame {
    private final Connection connection;
    private JPanel container;
    private JTextField fld_hotel_info_city;
    private JTextField fld_hotel_info_name;
    private JTextField fld_hotel_info_id;
    private JTextField fld_hotel_info_region;
    private JTextField fld_hotel_info_address;
    private JTextField fld_hotel_info_stars;
    private JTextField fld_hotel_info_tel;
    private JTextField fld_hotel_info_details;
    private JTextField fld_hotel_info_mail;
    private JComboBox cmb_details_season;
    private JComboBox cmb_details_hotel;
    private Hotel hotel;

    public HotelView(Hotel hotel){
        this.hotel= hotel;
        this.connection= Db.getInstance();
        this.add(container);
        setSize(650,450);
        setLocation(Helper.screenCenterPoint("x",getSize()), Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(true);
        setVisible(true);

        this.fld_hotel_info_city.setText(hotel.getHotel_city());
        this.fld_hotel_info_city.setEditable(false);
        this.fld_hotel_info_name.setText(hotel.getHotel_name());
        this.fld_hotel_info_name.setEditable(false);
        this.fld_hotel_info_id.setText(String.valueOf(hotel.getHotel_id()));
        this.fld_hotel_info_id.setEditable(false);
        this.fld_hotel_info_region.setText(hotel.getHotel_region());
        this.fld_hotel_info_region.setEditable(false);
        this.fld_hotel_info_address.setText(hotel.getHotel_address());
        this.fld_hotel_info_address.setEditable(false);
        this.fld_hotel_info_stars.setText(hotel.getHotel_stars());
        this.fld_hotel_info_stars.setEditable(false);
        this.fld_hotel_info_tel.setText(hotel.getHotel_tel());
        this.fld_hotel_info_tel.setEditable(false);
        this.fld_hotel_info_details.setText(hotel.getHotel_features());
        this.fld_hotel_info_details.setEditable(false);
        this.fld_hotel_info_mail.setText(hotel.getHotel_mail());
        this.fld_hotel_info_mail.setEditable(false);

        for (int i=0; i< cmb_details_season.getItemCount(); i++){
            if (cmb_details_season.getItemAt(i).toString().equals(hotel.getHotel_stars())){
                cmb_details_season.setSelectedIndex(i);
            }
        }
        this.cmb_details_season.setEditable(false);
        //this.cmb_details_hotel = cmb_details_hotel;
        this.cmb_details_hotel.setEditable(false);
    }
}