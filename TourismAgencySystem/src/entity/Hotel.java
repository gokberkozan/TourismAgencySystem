package entity;

import core.Helper;

import java.sql.*;
import java.util.ArrayList;

// This is the section where we get Hotel Information.
public class Hotel {
    private int hotel_id;
    private String hotel_name;
    private String hotel_address;
    private String hotel_city;
    private String hotel_region;
    private String hotel_mail;
    private String hotel_tel;
    private String hotel_stars;
    private String hotel_features;
    private String hotel_board;
    private Connection connection;

    public Hotel(int hotel_id, String hotel_name, String hotel_address, String hotel_city, String hotel_region, String hotel_mail, String hotel_tel, String hotel_stars, String hotel_features, String hotel_board) {
        this.hotel_id = hotel_id;
        this.hotel_name = hotel_name;
        this.hotel_address = hotel_address;
        this.hotel_city = hotel_city;
        this.hotel_region = hotel_region;
        this.hotel_mail = hotel_mail;
        this.hotel_tel = hotel_tel;
        this.hotel_stars = hotel_stars;
        this.hotel_features = hotel_features;
        this.hotel_board = hotel_board;
    }

    public Hotel( String hotel_name, String hotel_address, String hotel_city, String hotel_region, String hotel_mail, String hotel_tel, String hotel_stars, String hotel_features, String hotel_board){
        this.hotel_name = hotel_name;
        this.hotel_address = hotel_address;
        this.hotel_mail = hotel_mail;
        this.hotel_tel = hotel_tel;
        this.hotel_region = hotel_region;
        this.hotel_city = hotel_city;
        this.hotel_stars = hotel_stars;
        this.hotel_features = hotel_features;
        this.hotel_board = hotel_board;
    }

    public Hotel() {

    }

    // Hotel ArrayList that should be called when you want to retrieve created or created Hotels
    public static ArrayList<Hotel> getList () {
        ArrayList <Hotel> courseArrayList = new ArrayList<>();
        Hotel course;

        try {
            Statement statement = core.Db.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM hotel");
            while (resultSet.next()) {
                course = new Hotel(
                        resultSet.getInt("hotel_id"),
                        resultSet.getString("hotel_name"),
                        resultSet.getString("hotel_address"),
                        resultSet.getString("hotel_city"),
                        resultSet.getString("hotel_region"),
                        resultSet.getString("hotel_mail"),
                        resultSet.getString("hotel_tel"),
                        resultSet.getString("hotel_stars"),
                        resultSet.getString("hotel_features"),
                        resultSet.getString("hotel_board")
                );
                courseArrayList.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courseArrayList;
    }

    //getFetch Method when calling hotel information via id
    public static Hotel getFetch(int id){
        Hotel obj = null;
        String query = "SELECT * FROM public.hotel WHERE hotel_id = ?";
        try {
            PreparedStatement pr = core.Db.getInstance().prepareStatement(query);
            pr.setInt(1, id); // We pass the integer value directly using setInt
            ResultSet rs = pr.executeQuery();
            while(rs.next()) {
                obj = new Hotel();
                obj.setHotel_id(rs.getInt("hotel_id"));
                obj.setHotel_name(rs.getString("hotel_name"));
                obj.setHotel_address(rs.getString("hotel_address"));
                obj.setHotel_city(rs.getString("hotel_city"));
                obj.setHotel_region(rs.getString("hotel_region"));
                obj.setHotel_mail(rs.getString("hotel_mail"));
                obj.setHotel_tel(rs.getString("hotel_tel"));
                obj.setHotel_stars(rs.getString("hotel_stars"));
                obj.setHotel_features(rs.getString("hotel_features"));
                obj.setHotel_board(rs.getString("hotel_board"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return obj;
    }

    // Link section of the hotel name and information coming from the database
    public static String hotelName(int hotel_id){
        String query = "SELECT hotel_name FROM public.hotel WHERE hotel_id = ?";
        String hotelname = "";
        Hotel obj;
        try {
            PreparedStatement pr = core.Db.getInstance().prepareStatement(query);
            pr.setInt(1,hotel_id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                obj = new Hotel();
                obj.setHotel_name(rs.getString("hotel_name"));
                hotelname += obj.getHotel_name();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hotelname;
    }

    private void hotel_name(String hotelName) {
    }

    // Our method to display the created hotels as a list
    public ArrayList <Object []> getForTableSearch (int size, ArrayList<Hotel> hotelList) {
        ArrayList<Object[]> hotelRowList = new ArrayList<>();
        for (Hotel hotel : hotelList) {
            Object [] rowObject = new Object[size];
            int i = 0;
            rowObject[i++] = hotel.getHotel_id();
            rowObject[i++] = hotel.getHotel_name();
            rowObject[i++] = hotel.getHotel_address();
            rowObject[i++] = hotel.getHotel_city();
            rowObject[i++] = hotel.getHotel_region();
            rowObject[i++] = hotel.getHotel_mail();
            rowObject[i++] = hotel.getHotel_tel();
            rowObject[i++] = hotel.getHotel_stars();
            rowObject[i++] = hotel.getHotel_features();
            rowObject[i++] = hotel.getHotel_board();
            hotelRowList.add(rowObject);
        }
        return  hotelRowList;
    }

    // Match method is written to be used in hotel transactions
    public Hotel match(ResultSet resultSet) throws SQLException {
        Hotel object = new Hotel();
        object.setHotel_id(resultSet.getInt("hotel_id"));
        object.setHotel_name(resultSet.getString("hotel_name"));
        object.setHotel_address(resultSet.getString("hotel_address"));
        object.setHotel_address(resultSet.getString("hotel_city"));
        object.setHotel_address(resultSet.getString("hotel_region"));
        object.setHotel_address(resultSet.getString("hotel_mail"));
        object.setHotel_address(resultSet.getString("hotel_tel"));
        object.setHotel_address(resultSet.getString("hotel_stars"));
        object.setHotel_address(resultSet.getString("hotel_features"));
        object.setHotel_address(resultSet.getString("hotel_board"));

        return object;
    }

    // Hotel Addition Information Section (Database connections have been made)
    public static boolean add(String hotel_name,String hotel_address, String hotel_city, String hotel_region, String hotel_mail,
                              String hotel_tel, String hotel_stars, String hotel_features,String hotel_board) {
        String query = "INSERT INTO public.hotel (hotel_name, hotel_address, hotel_city, hotel_region, hotel_mail, hotel_tel, hotel_stars, hotel_features, hotel_board)"+
                " VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pr = core.Db.getInstance().prepareStatement(query);
            pr.setString(1,hotel_name);
            pr.setString(2,hotel_address);
            pr.setString(3,hotel_city);
            pr.setString(4,hotel_region);
            pr.setString(5,hotel_mail);
            pr.setString(6,hotel_tel);
            pr.setString(7,hotel_stars);
            pr.setString(8,hotel_features);
            pr.setString(9,hotel_board);

            int responce = pr.executeUpdate();
            if (responce == -1){
                Helper.showMsg("error");
            }
            return responce != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static String searchQuery(String name, String city, String region, String star){
        String query = "SELECT * FROM public.hotel WHERE hotel_name LIKE '%{{hotel_name}}%' AND hotel_city LIKE '%{{hotel_city}}%'" +
                " AND hotel_region LIKE '%{{hotel_region}}%' AND hotel_stars LIKE '%{{hotel_stars}}%'";
        query = query.replace("{{hotel_name}}" ,name);
        query = query.replace("{{hotel_city}}" ,city);
        query = query.replace("{{hotel_region}}" ,region);
        query = query.replace("{{hotel_stars}}" ,star);
        System.out.println(query);
        return query;
    }

    public static ArrayList<Hotel> search_hotel(String query){
        ArrayList<Hotel> hotelList = new ArrayList<>();
        Hotel obj;
        try {
            Statement st = core.Db.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj = new Hotel();
                obj.setHotel_id(rs.getInt("hotel_id"));
                obj.setHotel_name(rs.getString("hotel_name"));
                obj.setHotel_address(rs.getString("hotel_address"));
                obj.setHotel_region(rs.getString("hotel_region"));
                obj.setHotel_city(rs.getString("hotel_city"));
                obj.setHotel_mail(rs.getString("hotel_mail"));
                obj.setHotel_tel(rs.getString("hotel_tel"));
                obj.setHotel_stars(rs.getString("hotel_stars"));
                obj.setHotel_features(rs.getString("hotel_features"));
                obj.setHotel_board(rs.getString("hotel_board"));

                hotelList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return hotelList;
    }

    public static ArrayList<Hotel> getlistByid(int hotelId){
        ArrayList<Hotel> hotelList = new ArrayList<>();
        String query = "SELECT * FROM public.hotel WHERE hotel_id =? ";
        Hotel obj;
        try {
            PreparedStatement pr = core.Db.getInstance().prepareStatement(query);
            pr.setInt(1,hotelId);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                int hotel_id = rs.getInt("hotel_id");
                String hotel_name = rs.getString("hotel_name");
                String hotel_address = rs.getString("hotel_address");
                String hotel_region = rs.getString("hotel_region");
                String hotel_city = rs.getString("hotel_city");
                String hotel_mail = rs.getString("hotel_mail");
                String hotel_tel = rs.getString("hotel_tel");
                String hotel_stars = rs.getString("hotel_stars");
                String hotel_features = rs.getString("hotel_features");
                String hotel_board = rs.getString("hotel_board");
                obj = new Hotel(hotel_id, hotel_name,hotel_address,hotel_region,hotel_city,hotel_mail,hotel_tel,hotel_stars,hotel_features,hotel_board);
                hotelList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return hotelList;
    }

    public static Hotel getByID ( int id) {
        Hotel object =null ;
        String query = "SELECT * FROM public.hotel WHERE hotel_id = ?";
        try {
            PreparedStatement statement = core.Db.getInstance().prepareStatement(query);
            statement.setInt(1,id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                object = new Hotel ();
                object.setHotel_id(result.getInt("hotel_id"));
                object.setHotel_name(result.getString("hotel_name"));
                object.setHotel_address(result.getString("hotel_address"));
                object.setHotel_city(result.getString("hotel_city"));
                object.setHotel_region(result.getString("hotel_region"));
                object.setHotel_mail(result.getString("hotel_mail"));
                object.setHotel_tel(result.getString("hotel_tel"));
                object.setHotel_stars(result.getString("hotel_stars"));
                object.setHotel_features(result.getString("hotel_features"));
                object.setHotel_board(result.getString("hotel_board"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return object;
    }

    // Deleting created hotels section
    public static boolean delete(int hotel_id) {
        String query = "DELETE FROM public.hotel WHERE hotel_id =?";
        try {
            PreparedStatement pr = core.Db.getInstance().prepareStatement(query);
            pr.setInt(1, hotel_id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getHotel_name() {

        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getHotel_address() {
        return hotel_address;
    }

    public void setHotel_address(String hotel_address) {
        this.hotel_address = hotel_address;
    }

    public String getHotel_city() {
        return hotel_city;
    }

    public void setHotel_city(String hotel_city) {
        this.hotel_city = hotel_city;
    }

    public String getHotel_region() {
        return hotel_region;
    }

    public void setHotel_region(String hotel_region) {
        this.hotel_region = hotel_region;
    }

    public String getHotel_mail() {
        return hotel_mail;
    }

    public void setHotel_mail(String hotel_mail) {
        this.hotel_mail = hotel_mail;
    }

    public String getHotel_tel() {
        return hotel_tel;
    }

    public void setHotel_tel(String hotel_tel) {
        this.hotel_tel = hotel_tel;
    }

    public String getHotel_stars() {
        return hotel_stars;
    }

    public void setHotel_stars(String hotel_stars) {
        this.hotel_stars = hotel_stars;
    }

    public String getHotel_features() {
        return hotel_features;
    }

    public void setHotel_features(String hotel_features) {
        this.hotel_features = hotel_features;
    }

    public String getHotel_board() {
        return hotel_board;
    }

    public void setHotel_board(String hotel_board) {
        this.hotel_board = hotel_board;
    }
}