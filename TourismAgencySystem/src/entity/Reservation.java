package entity;

import core.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

//Reservasyonların yapılacagı metot
public class Reservation {
    private int id;
    private String guest_name;
    private String guest_phone;
    private String guest_email;
    private String guest_note;
    private int room_id;
    private int total_price;
    private int hotel_id;
    private Hotel hotel;
    private Room room;
    private LocalDate startDate;
    private LocalDate endDate;
    private int day;

    public Reservation(){
    }

    public Reservation(int id, String guest_name, String guest_phone, String guest_email, String guest_note,int day,LocalDate startDate,LocalDate endDate,int room_id, int total_price,int hotel_id) {
        this.id = id;
        this.guest_name = guest_name;
        this.guest_phone = guest_phone;
        this.guest_email = guest_email;
        this.guest_note = guest_note;
        this.day = day;
        this.startDate = startDate;
        this.endDate = endDate;
        this.room_id = room_id;
        this.total_price = total_price;
        this.hotel_id = hotel_id;
        this.hotel = hotel;
        this.room = room;
    }

    public Reservation(String guest_name, String guest_phone, String guest_email, String guest_note, int day, LocalDate startDate, LocalDate endDate, int room_id, int total_price,int hotel_id) {
        this.guest_name = guest_name;
        this.guest_phone = guest_phone;
        this.guest_email = guest_email;
        this.guest_note = guest_note;
        this.day = day;
        this.startDate = startDate;
        this.endDate = endDate;
        this.room_id = room_id;
        this.total_price = total_price;
        this.hotel_id = hotel_id;
        this.hotel = Hotel.getFetch(hotel_id);
        this.room = Room.getFetch(room_id);
    }

    //Reservation ArrayList to list the reservation
    public static ArrayList<Reservation> getList(){
        ArrayList<Reservation> reservationList = new ArrayList<>();
        Reservation obj;
        try {
            Statement st = core.Db.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM public.reservation");
            while (rs.next()){
                int id = rs.getInt("id");
                String client_name = rs.getString("guest_name");
                String client_phone = rs.getString("guest_phone");
                String client_email = rs.getString("guest_email");
                String client_note = rs.getString("guest_note");
                int day = rs.getInt("day");
                LocalDate start= LocalDate.parse(rs.getString("reservation_start"));
                LocalDate end = LocalDate.parse(rs.getString("reservation_end"));
                int room_id = rs.getInt("room_id");
                int total_price = rs.getInt("total_price");
                int hotel_id = rs.getInt("hotel_id");
                obj = new Reservation(id,client_name,client_phone,client_email,
                        client_note, day,start,end,room_id,total_price,hotel_id);
                reservationList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return reservationList;
    }

    public static boolean add(String guest_name, String guest_phone, String guest_email,
                              String guest_note, int day,LocalDate startDate,LocalDate endDate,int room_id ,int total_price,int hotel_id) {
        String query = "INSERT INTO public.reservation (guest_name, guest_phone, guest_email, guest_note, day, reservation_start, " +
                "reservation_end, room_id, total_price, hotel_id)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pr = core.Db.getInstance().prepareStatement(query);
            pr.setString(1, guest_name);
            pr.setString(2, guest_phone);
            pr.setString(3, guest_email);
            pr.setString(4, guest_note);
            pr.setInt(5, day);
            pr.setDate(6, java.sql.Date.valueOf(startDate));
            pr.setDate(7, java.sql.Date.valueOf(endDate));
            pr.setInt(9, room_id);
            pr.setInt(8, total_price);
            pr.setInt(10, hotel_id);

            int responce = pr.executeUpdate();
            if(responce == -1){
                Helper.showMsg("error");
            }
            return responce != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    // Method to Delete the Created Reservation
    public static boolean delete(int id) {
        String query = "DELETE FROM public.reservation WHERE id =?";
        try {
            PreparedStatement pr = core.Db.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public boolean save (Reservation reservation ){
        String query = "INSERT INTO public.reservation (guest_name, guest_phone, guest_email, " +
                " guest_note,room_id, total_price, hotel_id)" +
                " VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement = core.Db.getInstance().prepareStatement(query);
            statement.setString(1, reservation.getGuest_name());
            statement.setString(2, reservation.getGuest_phone());
            statement.setString(3, reservation.getGuest_phone());
            statement.setString(4, reservation.getGuest_note());

            return statement.executeUpdate() != -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGuest_name() {
        return guest_name;
    }

    public void setGuest_name(String guest_name) {
        this.guest_name = guest_name;
    }

    public String getGuest_phone() {
        return guest_phone;
    }

    public void setGuest_phone(String guest_phone) {
        this.guest_phone = guest_phone;
    }

    public String getGuest_email() {
        return guest_email;
    }

    public void setGuest_email(String guest_email) {
        this.guest_email = guest_email;
    }

    public String getGuest_note() {
        return guest_note;
    }

    public void setGuest_note(String guest_note) {
        this.guest_note = guest_note;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}