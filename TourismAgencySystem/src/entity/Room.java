package entity;

import core.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

// The part that allows controlling the rooms in the hotel
public class Room {
    private int id;
    private String room_type;
    private int stock;
    private int season_id;
    private int adult_price;
    private int child_price;
    private int type_hotel_id;
    private int hotel_id;

    private HotelType hoteltype;
    private Hotel hotel;
    private Season season;
    private String roomProperties;

    public Room(){

    }

    public Room(int id, String room_type, int stock, int season_id, int adult_price, int child_price, int type_hotel_id, int hotel_id,String roomProperties) {
        this.id = id;
        this.room_type = room_type;
        this.stock = stock;
        this.season_id = season_id;
        this.adult_price = adult_price;
        this.child_price = child_price;
        this.type_hotel_id = type_hotel_id;
        this.hotel_id = hotel_id;
        this.hotel=hotel;
        this.hoteltype=hoteltype;
        this.roomProperties=roomProperties;
    }

    // Our ArrayList is what we will need when we want to list the rooms.
    public static ArrayList<Room> getList(){
        ArrayList<Room> roomList = new ArrayList<>();
        Room obj;
        try {
            Statement st = core.Db.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM room");
            while (rs.next()){
                int id = rs.getInt("id");
                String room_type = rs.getString("room_type");
                int stock = rs.getInt("stock");
                int season_id = rs.getInt("season_id");
                int adult_price = rs.getInt("adult_price");
                int child_price = rs.getInt("child_price");
                int type_hotel_id = rs.getInt("type_hotel_id");
                int hotel_id = rs.getInt("hotel_id");
                String roomProperties = rs.getString("room_properties");
                obj = new Room(id,room_type,stock,season_id,adult_price,child_price,type_hotel_id,hotel_id,roomProperties);
                roomList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return roomList;
    }

    public static boolean add(String room_type, int stock, int season_id, int adult_price, int child_price, int type_hotel_id, int hotel_id) {
        String query = "INSERT INTO public.room (room_type,stock,season_id,adult_price,child_price,type_hotel_id,hotel_id)"
                + "VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pr = core.Db.getInstance().prepareStatement(query);
            pr.setString(1, room_type);
            pr.setInt(2, stock);
            pr.setInt(3, season_id);
            pr.setInt(4, adult_price);
            pr.setInt(5, child_price);
            pr.setInt(6, type_hotel_id);
            pr.setInt(7, hotel_id);

            int responce = pr.executeUpdate();
            if (responce == -1) {
                Helper.showMsg("error");
            }
            return responce != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static String searchQuery(String name, String city, String region, String star){
        String query = "SELECT * FROM public.hotel INNER JOIN public.room ON hotel.hotel_id=room.hotel_id WHERE hotel_name LIKE '%{{hotel_name}}%' AND hotel_city LIKE '%{{hotel_city}}%'" +
                " AND hotel_region LIKE '%{{hotel_region}}%' AND hotel_stars LIKE '%{{hotel_stars}}%'";
        query = query.replace("{{hotel_name}}" ,name);
        query = query.replace("{{hotel_city}}" ,city);
        query = query.replace("{{hotel_region}}" ,region);
        query = query.replace("{{hotel_stars}}" ,star);
        System.out.println(query);
        return query;
    }

    public static ArrayList<Room> search_room(String query){
        ArrayList<Room> roomList = new ArrayList<>();
        Room obj;
        try {
            Statement st = core.Db.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj = new Room();
                obj.setId(rs.getInt("id"));
                obj.setRoom_type(rs.getString("room_type"));
                obj.setStock(rs.getInt("stock"));
                obj.setSeason_id(rs.getInt("season_id"));
                obj.setAdult_price(rs.getInt("adult_price"));
                obj.setChild_price(rs.getInt("child_price"));
                obj.setType_hotel_id(rs.getInt("type_hotel_id"));
                obj.setHotel_id(rs.getInt("hotel_id"));
                obj.setRoomProperties(rs.getString("room_properties"));

                roomList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return roomList;
    }

    // Our searchQuery method required for the Search Section
    public static String searchQuery (String hotel_name, String hotel_city) {
        String query = "SELECT * FROM public.hotel RIGHT OUTER JOIN public.room ON hotel_id = hotel_id " +
                "WHERE hotel_name LIKE '%{{hotel_name}}%' AND " +
                "hotel_city LIKE '%{{hotel_city}}%'";

        query = query.replace("{{hotel_name}}", hotel_name);
        query = query.replace("{{hotel_city}}", hotel_city);

        System.out.println(query);
        return query;
    }

    // Our getFetch method that we will call when we want to use the room
    public static Room getFetch(int id){
        Room obj = null;
        String query = "SELECT * FROM public.room WHERE id = ?";
        try {
            PreparedStatement pr = core.Db.getInstance().prepareStatement(query);
            pr.setInt(1, id); // id'yi integer olarak ayarla
            ResultSet rs = pr.executeQuery();
            while(rs.next()) {
                obj = new Room();
                obj.setId(rs.getInt("id"));
                obj.setRoom_type(rs.getString("room_type"));
                obj.setStock(rs.getInt("stock"));
                obj.setSeason_id(rs.getInt("season_id"));
                obj.setAdult_price(rs.getInt("adult_price"));
                obj.setChild_price(rs.getInt("child_price"));
                obj.setType_hotel_id(rs.getInt("type_hotel_id"));
                obj.setHotel_id(rs.getInt("hotel_id"));
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return obj;
    }

    // Room name
    public static String roomName(int id){
        String query = "SELECT room_type FROM public.room WHERE id = ?";
        String room_type = "";
        Room obj;
        try {
            PreparedStatement pr = core.Db.getInstance().prepareStatement(query);
            pr.setInt(1,id); // id'yi integer olarak ayarla
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                obj = new Room();
                obj.setRoom_type(rs.getString("room_type"));
                room_type += obj.getRoom_type();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return room_type;
    }

    public static void updateStock(int roomId, int bookedQuantity) {
        Room room = getFetch(roomId);
        if (room != null) {
            int currentStock = room.getStock();
            int updatedStock = currentStock - bookedQuantity;
            // Güncelleme sorgusu
            String updateQuery = "UPDATE public.room SET stock = ? WHERE id = ?";
            try {
                PreparedStatement pr = core.Db.getInstance().prepareStatement(updateQuery);
                pr.setInt(1, updatedStock);
                pr.setInt(2, roomId);
                pr.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Room not found.");
        }
    }

    public static int getHotelIdByRoomId(int room_id) {
        int i = 0;
        String query = "SELECT hotel_id FROM public.room WHERE id =?";
        try {
            PreparedStatement pr = core.Db.getInstance().prepareStatement(query);
            pr.setInt(1, room_id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                int hotel_id = rs.getInt("hotel_id");
                i = hotel_id;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;
    }

    // Room delete
    public static boolean delete(int id) {
        String query = "DELETE FROM public.room WHERE id =?";
        try {
            PreparedStatement pr = core.Db.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public Room getByID ( int id) {
        Room object =null ;
        String query = "SELECT * FROM public.room WHERE id = ?";
        try {
            PreparedStatement statement = core.Db.getInstance().prepareStatement(query);
            statement.setInt(1,id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                object = new Room ();
                object.setId(result.getInt("id"));
                object.setRoom_type(result.getString("room_type"));
                object.setStock(result.getInt("stock"));
                object.setSeason_id(result.getInt("season_id"));
                object.setAdult_price(result.getInt("adult_price"));
                object.setChild_price(result.getInt("child_price"));
                object.setType_hotel_id(result.getInt("type_hotel_id"));
                object.setHotel_id(result.getInt("hotel_id"));
                object.setRoomProperties(result.getString("room_properties"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static Room getById ( int id) {
        Room object =null ;
        String query = "SELECT * FROM public.room WHERE id = ?";
        try {
            PreparedStatement statement = core.Db.getInstance().prepareStatement(query);
            statement.setInt(1,id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                object = new Room ();
                object.setId(result.getInt("id"));
                object.setRoom_type(result.getString("room_type"));
                object.setStock(result.getInt("stock"));
                object.setSeason_id(result.getInt("season_id"));
                object.setAdult_price(result.getInt("adult_price"));
                object.setChild_price(result.getInt("child_price"));
                object.setType_hotel_id(result.getInt("type_hotel_id"));
                object.setHotel_id(result.getInt("hotel_id"));
                object.setRoomProperties(result.getString("room_properties"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return object;
    }

    public String getRoomProperties() {
        return roomProperties;
    }

    public void setRoomProperties(String roomProperties) {
        this.roomProperties = roomProperties;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSeason_id() {
        return season_id;
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }

    public int getAdult_price() {
        return adult_price;
    }

    public void setAdult_price(int adult_price) {
        this.adult_price = adult_price;
    }

    public int getChild_price() {
        return child_price;
    }

    public void setChild_price(int child_price) {
        this.child_price = child_price;
    }

    public int getType_hotel_id() {
        return type_hotel_id;
    }

    public void setType_hotel_id(int type_hotel_id) {
        this.type_hotel_id = type_hotel_id;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public HotelType getHoteltype() {
        return hoteltype;
    }

    public void setHoteltype(HotelType hoteltype) {
        this.hoteltype = hoteltype;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }
}