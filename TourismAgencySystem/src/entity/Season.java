package entity;

import core.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// Method of finding seasons of hotels
public class Season {
    private int id;
    private String name;
    private String start_date;
    private String end_date;
    private int hotel_id;

    public Season(int id, String name, String start_date, String end_date, int hotel_id) {
        this.id = id;
        this.name=name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.hotel_id = hotel_id;
    }

    public static boolean add ( String name, int hotel_id,String start_date,String end_date){
        String query = "INSERT INTO public.season (name,start_date,end_date,hotel_id)" +
                " VALUES (?,?,?,?)";
        try {
            PreparedStatement pr = core.Db.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,start_date);
            pr.setString(3,end_date);
            pr.setInt(4,hotel_id);

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

    public static ArrayList<Season> getListByid(int hotelId){
        ArrayList<Season> Listseason = new ArrayList<>();
        String query = "SELECT * FROM public.season WHERE hotel_id =? ";
        Season obj;
        try {
            PreparedStatement pr = core.Db.getInstance().prepareStatement(query);
            pr.setInt(1,hotelId);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String start_date = rs.getString("start_date");
                String end_date = rs.getString("end_date");
                int hotel_id = rs.getInt("hotel_id");
                obj = new Season(id, name, start_date, end_date,hotel_id);
                Listseason.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Listseason;
    }

    public static Season getByID (int id) {
        Season object =null ;
        String query = "SELECT * FROM public.season WHERE id = ?";
        try {
            PreparedStatement statement = core.Db.getInstance().prepareStatement(query);
            statement.setInt(1,id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                object = new Season();
                object.setId(result.getInt("id"));
                object.setName(result.getString("name"));
                object.setStart_date(result.getString("start_date"));
                object.setEnd_date(result.getString("end_date"));
                object.setHotel_id(result.getInt("hotel_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static String seasonName(int id){
        String query = "SELECT name FROM public.season WHERE id = ?";
        String name = "";
        Season obj;
        try {
            PreparedStatement pr = core.Db.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                obj = new Season();
                obj.setName(rs.getString("name"));
                name += obj.getName();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return name;
    }
    public Season(){

}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }
}