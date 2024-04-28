package entity;

import core.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// Method where hotel types are found.
public class HotelType {
    private int id;
    private String type;
    private int hotel_id;

    public HotelType(int id, String type, int hotel_id) {
        this.id = id;
        this.type = type;
        this.hotel_id = hotel_id;
    }

    public HotelType() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    // Hotel type adding methods
    public static boolean add(String type, int hotel_id){
        String query = "INSERT INTO public.type_hotel (type, hotel_id) VALUES (?,?)";
        try {
            PreparedStatement pr = core.Db.getInstance().prepareStatement(query);
            pr.setString(1,type);
            pr.setInt(2, hotel_id);
            return pr.executeUpdate() !=-1;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    // Fetch method to be called when the Hotel Type will be added
    public static HotelType getFetch(int id){
        HotelType obj = null;
        String query = "SELECT * FROM public.type_hotel WHERE id = ?";
        try {
            PreparedStatement pr = core.Db.getInstance().prepareStatement(query);
            pr.setInt(1, id); // setInt kullanarak tamsayı değerini direkt olarak geçiriyoruz
            ResultSet rs = pr.executeQuery();
            while(rs.next()) {
                obj=new HotelType();
                obj.setId(rs.getInt("id"));
                obj.setType(rs.getString("type"));
                obj.setHotel_id(rs.getInt("hotel_id"));
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return obj;
    }

    public static boolean add_pension ( String type, int hotel_id ){
        String query = "INSERT INTO public.type_hotel (type,hotel_id)" +
                " VALUES (?,?)";
        try {
            PreparedStatement pr = core.Db.getInstance().prepareStatement(query);
            pr.setString(1,type);
            pr.setInt(2,hotel_id);

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

    public static ArrayList<HotelType> getListByid(int hotelId) {
        ArrayList<HotelType> hostelList = new ArrayList<>();
        String query = "SELECT * FROM public.type_hotel WHERE hotel_id =? ";
        HotelType obj;
        try {
            PreparedStatement pr = core.Db.getInstance().prepareStatement(query);
            pr.setInt(1,hotelId);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String type = rs.getString("type");
                int hotel_id = rs.getInt("hotel_id");
                obj = new HotelType(id,type,hotel_id);
                hostelList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return hostelList;
    }

    public static String pensionName(int id){
        String query = "SELECT type FROM public.type_hotel WHERE id = ?";
        String type = "";
        HotelType obj;
        try {
            PreparedStatement pr = core.Db.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                obj = new HotelType();
                obj.setType(rs.getString("type"));
                type += obj.getType();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return type;
    }
}