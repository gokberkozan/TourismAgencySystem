package view;

import core.Config;
import core.Db;
import core.Helper;
import entity.Hotel;
import entity.Season;
import entity.HotelType;

import javax.swing.*;
import java.sql.Connection;

public class SeasonView extends JFrame {
    private final Connection connection;
    private JPanel container;
    private JCheckBox cb_ultra_inclusive;
    private JCheckBox cb_inclusive;
    private JCheckBox cb_breakfast;
    private JCheckBox cb_full_hostel;
    private JCheckBox cb_half_hostel;
    private JCheckBox cb_just_bed;
    private JCheckBox cb_credit_wo_alcohol;
    private JCheckBox cb_summer;
    private JCheckBox cb_winter;
    private JCheckBox cb_spring;
    private JCheckBox cb_autumn;
    private JButton btn_hostel_add;
    private JButton btn_season_add;
    private JButton btn_season_exit;
    private Hotel hotel;
    private int selected_hotel_id = EmployeeView.getSelacted_hotelId();

    public SeasonView(Hotel hotel) {
        this.hotel= hotel;
        this.connection= Db.getInstance();
        this.add(container);
        setSize(450,400);
        setLocation(Helper.screenCenterPoint("x",getSize()), Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(true);
        setVisible(true);

        btn_hostel_add.addActionListener(e -> {
            if (areFieldsEmptyHostelFeatures()) {
                Helper.showMsg("fill");
                return;
            }
            if (cb_ultra_inclusive.isSelected()){
                String ultrainclusive = "Ultra All Inclusive";
                HotelType.add_pension(ultrainclusive,selected_hotel_id);
            }
            if (cb_inclusive.isSelected()){
                String allinclusive = "All Inclusive";
                HotelType.add_pension(allinclusive,selected_hotel_id);
            }
            if (cb_breakfast.isSelected()){
                String breakfast = "Room Breakfast";
                HotelType.add_pension(breakfast,selected_hotel_id);
            }
            if (cb_full_hostel.isSelected()){
                String fullpencion = "Full Pension";
                HotelType.add_pension(fullpencion,selected_hotel_id);
            }
            if (cb_half_hostel.isSelected()){
                String halfhostel = "Half Pension";
                HotelType.add_pension(halfhostel,selected_hotel_id);
            }
            if(cb_just_bed.isSelected()){
                String justbed = "Just Bed";
                HotelType.add_pension(justbed,selected_hotel_id);
            }
            if(cb_credit_wo_alcohol.isSelected()){
                String fullcredit = "Excluding Alcohol Full credit";
                HotelType.add_pension(fullcredit,selected_hotel_id);
            }
            Helper.showMsg("done");

        });
        btn_season_exit.addActionListener(e -> {
            dispose();
        });
        btn_season_add.addActionListener(e -> {

            if (areFieldsEmptySeasonFeatures()) {
                Helper.showMsg("fill");
                return;
            }
            if (cb_summer.isSelected()){
                String sum = "Summer";
                String start = "2024-06-01";
                String end = "2024-12-01";
                Season.add(sum, selected_hotel_id ,start ,end);
            }
            if (cb_winter.isSelected()){
                String winter = "Winter";
                String start = "2024-01-01";
                String end = "2024-05-31";
                Season.add(winter , selected_hotel_id,start,end);
            }
            Helper.showMsg("done");

        });
    }

    private boolean areFieldsEmptyHostelFeatures() {
        return !(cb_ultra_inclusive.isSelected() || cb_inclusive.isSelected() ||  cb_breakfast.isSelected() ||
                cb_full_hostel.isSelected()|| cb_half_hostel.isSelected() || cb_just_bed.isSelected() || cb_credit_wo_alcohol.isSelected());
    }

    private boolean areFieldsEmptySeasonFeatures() {
        return !(cb_summer.isSelected()  || cb_autumn.isSelected()  ||cb_spring.isSelected() ||
                cb_winter.isSelected());
    }
}