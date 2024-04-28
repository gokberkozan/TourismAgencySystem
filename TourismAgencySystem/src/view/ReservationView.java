package view;

import core.Config;
import core.Helper;
import entity.*;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.time.LocalDate;

public class ReservationView extends JFrame {
    private JPanel container;
    private JTextField fld_res_hotel_name;
    private JTextField fld_res_adress;
    private JTextField fld_res_facility;
    private JTextField fld_res_room_name;
    private JTextField fld_res_phone;
    private JTextField fld_res_season;
    private JTextField fld_res_room_features;
    private JTextField fld_res_hostel_type;
    private JTextField fld_res_adult_price;
    private JTextField fld_res_child_price;
    private JTextField fld_cust_name;
    private JTextField fld_cust_phone;
    private JTextField fld_cust_email;
    private JTextField fld_cust_note;
    private JLabel Day;
    private JTextField fld_cust_day;
    private JButton btn_res_add;
    private JTextField fld_res_total_price;
    private JComboBox cmb_cust_day;
    private JButton btn_price_cal;
    private JFormattedTextField fld;
    private JFormattedTextField fld_room_end;
    private JTextField fld_room_calcul;
    private JFormattedTextField fld_room_start;
    private JButton fld_room_rez_exit;
    private int selected_roomId = EmployeeView.getSelected_roomId();
    private  int getSelected_hotelId ;
    private int getSelected_roomId;
    private String seasonName = EmployeeView.getSelectedSeasonName();
    private String hostelType = EmployeeView.getSelectedHostelType();
    private int adultPrice = EmployeeView.getSelectedAdultPrice();
    private int childPrice = EmployeeView.getSelectedChildPrice();
    private int total ;
    DateTimeFormatter formatter;

    private int day;
    private Room  room;
    private Hotel reservation;

    public ReservationView(Room room){
        add(container);
        setSize(700,500);
        this.room=room;
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(true);
        setVisible(true);

        getSelected_hotelId = Room.getHotelIdByRoomId(selected_roomId);

        int i = room.getHotel_id();
        reservation =Hotel.getByID(i);

        fld_res_hotel_name.setText(reservation.getHotel_name());
        fld_res_adress.setText(reservation.getHotel_address());
        fld_res_phone.setText(reservation.getHotel_tel());
        fld_res_facility.setText(reservation.getHotel_features());
        fld_res_room_features.setText(Roomfeatures.getRoomfeaturesbyID(room.getId()));
        fld_res_season.setText(this.seasonName);
        fld_res_hostel_type.setText(hostelType);
        fld_res_adult_price.setText(String.valueOf(adultPrice));
        fld_res_child_price.setText(String.valueOf(childPrice));

        fld_res_room_name.setText(room.getRoom_type());

        // Adding a Reservation
        btn_res_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_cust_name) || Helper.isFieldEmpty(fld_cust_email)
                    || Helper.isFieldEmpty(fld_cust_phone)
                    || Helper.isFieldEmpty(fld_cust_note) || Helper.isFieldEmpty(fld_res_total_price)) {
                Helper.showMsg("fill");
            } else {
                String name = fld_cust_name.getText();
                String email = fld_cust_email.getText();
                String phone = fld_cust_phone.getText();
                int hotel_id = room.getHotel_id();
                String note = fld_cust_note.getText();
                int sum = Integer.parseInt(fld_res_total_price.getText());

                LocalDate start = LocalDate.parse(fld_room_start.getText(), formatter);
                LocalDate end =  LocalDate.parse(fld_room_end.getText(), formatter);
                Room.updateStock(room.getId(), 1);
                long daysBetween = ChronoUnit.DAYS.between(start, end);
                Reservation.add(name,phone,email,note,day,start,end,room.getId(),sum,hotel_id);
            }
            Helper.showMsg("done");
        });
        btn_price_cal.addActionListener(e -> {
            LocalDate start = LocalDate.parse(fld_room_start.getText(), formatter);
            LocalDate end =  LocalDate.parse(fld_room_end.getText(), formatter);

            int sum = adultPrice + childPrice;
            day =(int) ChronoUnit.DAYS.between(start, end);
            total = day * sum;
            fld_room_calcul.setText(String.valueOf(day));
            fld_res_total_price.setText(String.valueOf(total));

            Room.updateStock(room.getId(), 1);
        });
        fld_room_rez_exit.addActionListener(e -> {
            dispose();
        });
    }

    private void createUIComponents() throws ParseException {
        this.fld_room_start = new JFormattedTextField(new MaskFormatter("##/##/####"));
        fld_room_start.setText("01/01/2000");
        this.fld_room_end = new JFormattedTextField(new MaskFormatter("##/##/####"));
        fld_room_end.setText("01/01/2000");
    }
}