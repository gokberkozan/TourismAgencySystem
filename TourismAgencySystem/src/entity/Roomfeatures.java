package entity;

import core.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Roomfeatures {
    private int id;
    private String name;
    private int room_id;

    public Roomfeatures(int id, String name, int room_id) {
        this.id = id;
        this.name = name;
        this.room_id = room_id;
    }
    public Roomfeatures(){

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

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    // A static method to add a room property
    public static boolean add ( String name, int room_id ){
        String query = "INSERT INTO public.roomfeatures (name,room_id)" +
                " VALUES (?,?)";
        try {
            PreparedStatement pr = core.Db.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setInt(2,room_id);

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

    // A static method to retrieve properties for a specific room
    public static ArrayList<Roomfeatures> getFetch(int id){
        ArrayList<Roomfeatures> abc  = new ArrayList<>();
        Roomfeatures obj = null;
        String query = "SELECT name FROM public.roomfeatures WHERE room_id =?";
        try{
            PreparedStatement pr = core.Db.getInstance().prepareStatement(query);
            pr.setString(1, String.valueOf(id));
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                obj = new Roomfeatures();
                obj.setName(rs.getString("name"));
                abc.add(obj);
            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return abc;
    }

    public static String getRoomfeaturesbyID(int room_id) {
        String features = "";
        String query = "SELECT name FROM public.roomfeatures WHERE room_id =?";
        try {
            PreparedStatement pr = core.Db.getInstance().prepareStatement(query);
            pr.setInt(1, room_id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                features += name;
            }
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return features;
    }
}