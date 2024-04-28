package view;

import core.Db;
import core.Helper;
import entity.*;
import core.Config;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import static entity.Room.searchQuery;

public class EmployeeView extends JFrame {
    private final DefaultTableModel mdl_room_list;
    private final DefaultTableModel mdl_reservation_list;
    private static int selected_hotelId;
    private static int selected_reservationId;
    private  Object [] row_reservation_list;
    private JTabbedPane tab_admin;
    private JPanel container;
    private JTextField fld_hotel_region;
    private JTextField fld_hotel_city;
    private JTextField fld_hotel_adress;
    private JTextField fld_hotel_name;
    private JTextField fld_hotel_mail;
    private JTextField fld_hotel_tel;
    private JTextField fld_hotel_stars;
    private JTextField fld_hotel_features;
    private JButton btn_hotel_add;
    private JTextField fld_hotel_board;
    private JLabel ismi;
    private JTable tbl_hotel_list;
    private JButton btn_room_refresh;
    private JTextField fld_region_hotelName;
    private JTextField fld_chec_in;
    private JTextField fld_chec_out;
    private JButton odaAraButton;
    private JTextField fld_room_id;
    private JButton btn_room_delete;
    private JButton btn_exit;
    private JTable tbl_room_list;
    private JTextField fld_room_city;
    private JButton btn_room_search;
    private JTable tbl_reservation_list;
    private JButton btn_refresh;
    private JTextField fld_res_id;
    private JButton btn_res_delete;
    private JTextField fld_hotel_id;
    private JButton btn_hotel_delete;
    private JButton btn_reservation_add;
    private JTextField fld_sh_hotel_name;
    private JTextField fld_sh_hotel_city;
    private JTextField fld_sh_hotel_region;
    private JButton btn_hotel_sh;
    private JTextField fld_sh_hotel_star;
    private JButton btn_room_searc;
    private JTextField fld_room_hotel;
    private JTextField fld_room_hotel_stars;
    private JTextField fld_room_hotel_city;
    private JButton deleteRoomButton;
    private JButton btn_hotel_features;
    private JButton btn_hotel_room_add;
    private JButton btn_room_features;
    private JButton btn_room_deletee;
    private User user;
    private Hotel hotel;
    private final Connection connection;
    private static int selected_roomId;
    private int room_hotel_id_add;
    private static String selectedSeasonName ;
    private static String selectedHostelType;
    private static int selectedAdultPrice;
    private static int selectedChildPrice;
    private Room room;
    private Object[] row_room_list;

    private DefaultTableModel mdl_hotel_list = new DefaultTableModel();
    private Object[] row_hotel_list;

    // The method we need for Hotel, Room and Reservation transactions
    public EmployeeView() {
        this.hotel=new Hotel();
        this.room=new Room();
        this.connection= Db.getInstance();
        this.add(container);
        this.startGUI(900, 950);
        setLocation(Helper.screenCenterPoint("x",getSize()), Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(true);
        this.user = user;

        // The part that will run when the add hotel button is pressed
        btn_hotel_add.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_hotel_name) ||
                    Helper.isFieldEmpty(fld_hotel_city)||
                    Helper.isFieldEmpty(fld_hotel_region)||
                    Helper.isFieldEmpty(fld_hotel_tel)||
                    Helper.isFieldEmpty(fld_hotel_adress)||
                    Helper.isFieldEmpty(fld_hotel_mail)||
                    Helper.isFieldEmpty(fld_hotel_features)||
                    Helper.isFieldEmpty(fld_hotel_board) ||
                    Helper.isFieldEmpty(fld_hotel_stars)) {
                Helper.showMsg("fill");
            } else{
                String hotel_name = fld_hotel_name.getText();
                String hotel_city = fld_hotel_city.getText();
                String hotel_region = fld_hotel_region.getText();
                String hotel_address = fld_hotel_adress.getText();
                String hotel_mail = fld_hotel_mail.getText();
                String hotel_tel = fld_hotel_tel.getText();
                String hotel_stars = fld_hotel_stars.getText();
                String hotel_features = fld_hotel_features.getText();
                String hotel_board = fld_hotel_board.getText();
                Hotel.add(hotel_name, hotel_address, hotel_city, hotel_region, hotel_mail, hotel_tel, hotel_stars, hotel_features, hotel_board);
                Helper.showMsg("done");
                loadHotelList();
                fld_hotel_name.setText(null);
                fld_hotel_city.setText(null);
                fld_hotel_name.setText(null);
                fld_hotel_adress.setText(null);
                fld_hotel_mail.setText(null);
                fld_hotel_tel.setText(null);
                fld_hotel_stars.setText(null);
            }
        });
        mdl_room_list = new DefaultTableModel();
        Object[] col_roomList = {"ID", "Room Name", "Stock", "Season", "Adult Price", "Child Price", "Pension Type", "Hotel Name"};
        mdl_room_list.setColumnIdentifiers(col_roomList);
        row_room_list = new Object[col_roomList.length];
        loadRoomList();
        tbl_room_list.setModel(mdl_room_list);
        tbl_room_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_room_list.getTableHeader().setReorderingAllowed(false);

        // Code to run when pressing the Room list
        tbl_room_list.getSelectionModel().addListSelectionListener(e -> {
            try{
                String selected_room_id = tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(),0).toString();
                selectedSeasonName = tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(),3).toString();
                selectedHostelType = tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(),1).toString();
                selectedAdultPrice = (int) tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(),4);
                selectedChildPrice = (int) tbl_room_list.getValueAt(tbl_room_list.getSelectedRow(),5);
                selected_hotelId = Integer.parseInt(selected_room_id);
                fld_room_id.setText(selected_room_id);
            } catch (Exception exception){
            }
        });

        mdl_hotel_list = new DefaultTableModel();
        // The section containing the features of the added hotel
        Object[] col_hotel_list = {"ID", "Hotel Name", "City", "Region", "Star", "Phone Number", "Email"};
        mdl_hotel_list.setColumnIdentifiers(col_hotel_list);
        row_hotel_list = new Object[col_hotel_list.length];
        loadHotelList();
        tbl_hotel_list.setModel(mdl_hotel_list);
        tbl_hotel_list.getTableHeader().setReorderingAllowed(false);
        tbl_hotel_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_hotel_id = tbl_hotel_list.getValueAt(tbl_hotel_list.getSelectedRow(),0).toString();
                selected_hotelId = Integer.parseInt(selected_hotel_id);
                fld_hotel_id.setText(selected_hotel_id);
            } catch (Exception exception){
            }
        });
        tbl_hotel_list.addMouseListener
                        (new MouseAdapter() {
                            @Override
                            public void mousePressed(MouseEvent e) {
                                Point point = e.getPoint();
                                int selected_row = tbl_hotel_list.rowAtPoint(point);
                                tbl_hotel_list.setRowSelectionInterval(selected_row, selected_row);
                            }
                        });
        btn_room_search.addActionListener(e -> {
            String hotel_name = fld_region_hotelName.getText();
            String hotel_city =fld_room_city.getText();
            String query = searchQuery(hotel_name, hotel_city);
            ArrayList<Hotel> filterHotels = hotel.search_hotel(query);
            loadHotelTable(filterHotels);
        });
        mdl_reservation_list = new DefaultTableModel();
        // This is the place where the sections we want to be written on the reservation screen are created.
        Object[] col_reservationList = {"ID", "Customer Name", "Phone Number", "Email", "Customer Note", "Room Name", "Total Price", "Hotel Name"};
        mdl_reservation_list.setColumnIdentifiers(col_reservationList);
        row_reservation_list = new Object[col_reservationList.length];
        loadReservationList();
        tbl_reservation_list.setModel(mdl_reservation_list);
        tbl_reservation_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_reservation_list.getTableHeader().setReorderingAllowed(false);
        // Ability to operate on a selected row
        tbl_reservation_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_reservation_id = tbl_reservation_list.getValueAt(tbl_reservation_list.getSelectedRow(),0).toString();
                selected_reservationId = Integer.parseInt(selected_reservation_id); //seÃ§tiÄŸim otel
                fld_res_id.setText(selected_reservation_id);
            } catch (Exception exception){
            }
        });

        // Deleting the Selected Reservation
        btn_res_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_res_id)){
                Helper.showMsg("error");
            }else{
                int reservation_id = Integer.parseInt(fld_res_id.getText());
                if (Reservation.delete(reservation_id)){
                    Helper.showMsg("done");
                    loadReservationModel();
                }
            }
        });

        // Refreshing the Reservation List
        btn_refresh.addActionListener(e -> {
            loadReservationModel();
        });
        btn_exit.addActionListener(e -> dispose());

        btn_hotel_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_hotel_id)){
                Helper.showMsg("fill");
            } else{
                int hotel_id = Integer.parseInt(fld_hotel_id.getText());
                if ( Hotel.delete(hotel_id)){
                    Helper.showMsg("done");
                    loadHotelList();
                } else {
                    Helper.showMsg("error");
                }
            }
        });
        // When the room delete button is pressed
        btn_room_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_room_id)){
                Helper.showMsg("fill");
            } else {
                int room_id = Integer.parseInt(fld_room_id.getText());
                if (Room.delete(room_id)){
                    Helper.showMsg("done");
                    loadRoomList();
                } else {
                    Helper.showMsg("error");
                }
            }
        });
        btn_room_refresh.addActionListener(e -> {
            loadRoomList();
        });
        // The part that will run when you click on add reservation
        btn_reservation_add.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_room_id)){
                Helper.showMsg("error");
            } else{
                int id = Integer.parseInt(fld_room_id.getText());
                ReservationView reservationView = new ReservationView(room.getByID(id));
            }
        });
        btn_hotel_sh.addActionListener(e -> {
            String hotel_name = fld_sh_hotel_name.getText();
            String hotel_city = fld_sh_hotel_city.getText();
            String hotel_region = fld_sh_hotel_region.getText();
            String hotel_stars = fld_sh_hotel_star.getText();
            String query = Hotel.searchQuery(hotel_name,hotel_city,hotel_region,hotel_stars);
            ArrayList<Hotel> searchingHotel = Hotel.search_hotel(query);
            loadHotelModel(searchingHotel);
        });
        btn_hotel_features.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_hotel_id)){
                Helper.showMsg("fill");
            } else {
                SeasonView features = new SeasonView(hotel);
            }

        });
        btn_hotel_room_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_hotel_id)){
                Helper.showMsg("error");
            } else {
                RoomView rm = new RoomView();
                loadRoomModel();
                loadRoomList();
            }
        });
        btn_room_features.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_room_id)){
                Helper.showMsg("fill");
                System.out.println("error");
            } else {
                int x = Integer.parseInt(fld_room_id.getText().toString());
                Room roomfeatures = Room.getById(x);
                FeatureView features = new FeatureView(roomfeatures);
            }
        });
        fld_room_hotel.addActionListener(e -> {
        });
        btn_room_searc.addActionListener(e -> {
            String hotel_name = fld_room_hotel.getText();
            String hotel_city = fld_room_hotel_city.getText();
            String hotel_region = fld_room_hotel_city.getText();
            String hotel_stars = fld_room_hotel_stars.getText();
            String query = Room.searchQuery(hotel_name,hotel_city,hotel_region,hotel_stars);
            ArrayList<Room> loadroom = Room.search_room(query);
            loadRoomModel(loadroom);
        });
        fld_room_hotel_stars.addActionListener(e -> {
        });
    }

    public void loadHotelModel(ArrayList<Hotel> list){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_hotel_list.getModel();
        clearModel.setRowCount(0);
        for (Hotel obj : list) {
            int i = 0;
            row_hotel_list[i++] = obj.getHotel_id();
            row_hotel_list[i++] = obj.getHotel_name();
            row_hotel_list[i++] = obj.getHotel_city();
            row_hotel_list[i++] = obj.getHotel_region();
            row_hotel_list[i++] = obj.getHotel_stars();
            row_hotel_list[i++] = obj.getHotel_tel();
            row_hotel_list[i++] = obj.getHotel_mail();
            //row_hotel_list[i++] = obj.getHotel_address();
            mdl_hotel_list.addRow(row_hotel_list);
        }
    }

    public void loadRoomModel(ArrayList<Room> list){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_room_list.getModel();
        clearModel.setRowCount(0);
        for (Room obj : list) {
            int i = 0;
            row_room_list[i++] = obj.getId();
            row_room_list[i++] = obj.getRoom_type();
            row_room_list[i++] = obj.getStock();
            int y = obj.getSeason_id();
            Season object = Season.getByID(y);
            row_room_list[i++] = object.getName();
            row_room_list[i++] = obj.getAdult_price();
            row_room_list[i++] = obj.getChild_price();
            row_room_list[i++] = obj.getType_hotel_id();
            int x = obj.getHotel_id();
            Hotel hotelobj = Hotel.getByID(x);
            row_room_list[i++] = hotelobj.getHotel_name();
            //row_hotel_list[i++] = obj.getHotel_address();
            mdl_room_list.addRow(row_room_list);
        }
    }

    // Room models
    private void loadRoomModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_room_list.getModel();
        clearModel.setRowCount(0);
        for (Room obj : Room.getList()) {
            int i = 0;
            row_room_list[i++] = obj.getId();
            row_room_list[i++] = obj.getRoom_type();
            row_room_list[i++] = obj.getStock();
            int y = obj.getSeason_id();
            Season object = Season.getByID(y);
            row_room_list[i++] = object.getName();
            row_room_list[i++] = obj.getAdult_price();
            row_room_list[i++] = obj.getChild_price();
            row_room_list[i++] = obj.getType_hotel_id();
            row_room_list[i++] = obj.getHotel_id();

            mdl_room_list.addRow(row_room_list);
        }
    }

    // Code required to load the created hotels
    private void loadReservationModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_reservation_list.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for(Reservation obj : Reservation.getList()){
            i = 0;
            row_reservation_list[i++] = obj.getId();
            row_reservation_list[i++] = obj.getGuest_name();
            row_reservation_list[i++] = obj.getGuest_phone();
            row_reservation_list[i++] = obj.getGuest_email();
            row_reservation_list[i++] = obj.getGuest_note();
            row_reservation_list[i++] = obj.getRoom() != null ? obj.getRoom().getRoom_type():" ";
            row_reservation_list[i++] = obj.getTotal_price();
            row_reservation_list[i++] = obj.getHotel() != null ? obj.getHotel().getHotel_name():" ";
            mdl_reservation_list.addRow(row_reservation_list);
        }
    }

    // List Section required to List the Created Reservation
    private void loadReservationList() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_reservation_list.getModel();
        clearModel.setRowCount(0);
        for(Reservation obj : Reservation.getList()){
            int i = 0;
            row_reservation_list[i++] = obj.getId();
            row_reservation_list[i++] = obj.getGuest_name();
            row_reservation_list[i++] = obj.getGuest_phone();
            row_reservation_list[i++] = obj.getGuest_email();
            row_reservation_list[i++] = obj.getGuest_note();
            row_reservation_list[i++] = Room.roomName(obj.getRoom_id());
            row_reservation_list[i++] = obj.getTotal_price();
            row_reservation_list[i++] = Hotel.hotelName(obj.getHotel_id());
            mdl_reservation_list.addRow(row_reservation_list);
        }
    }

    // The part of the screen where we want to write the features of the hotels
    public void loadHotelTable(ArrayList<Hotel> hotelList) {
        Object[] hotel_column = {"ID", "Hotel Name", "City", "Star", "Region", "Phone Number", "Email"};
        ArrayList<Object[]> hotelArrayList =
                this.hotel.getForTableSearch(hotel_column.length, hotelList);
        this.createTable(this.mdl_hotel_list, tbl_hotel_list,
                hotel_column, hotelArrayList);
    }

    // Table's properties
    public void createTable (DefaultTableModel model, JTable table, Object [] columns, ArrayList<Object []> rows) {
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setMaxWidth(75);
        table.setEnabled(false);

        DefaultTableModel clearModel = (DefaultTableModel) table.getModel();
        clearModel.setRowCount(0);

        if (rows == null) {
            rows = new ArrayList<>();
        }
        for (Object[] row: rows) {
            model.addRow(row);
        }
    }

    // Room Lists
    private void loadRoomList() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_room_list.getModel();
        clearModel.setRowCount(0);
        for (Room obj : Room.getList()) {
            int i = 0;
            row_room_list[i++] = obj.getId();
            row_room_list[i++] = obj.getRoom_type();
            row_room_list[i++] = obj.getStock();
            row_room_list[i++] = Season.seasonName(obj.getSeason_id());
            row_room_list[i++] = obj.getAdult_price();
            row_room_list[i++] = obj.getChild_price();
            row_room_list[i++] = HotelType.pensionName(obj.getType_hotel_id());
            row_room_list[i++] = Hotel.hotelName(obj.getHotel_id());

            mdl_room_list.addRow(row_room_list);
        }
    }

    //Parts of the Start View such as project name and interface
    public void startGUI ( int width, int height){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle(Config.PROJECT_TITLE);
        this.setSize(width, height);
        setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public boolean save (Hotel hotel){
        String query = "INSERT INTO public.hotel (hotel_name, hotel_address, hotel_city," +
                "hotel_region, hotel_mail, hotel_tel, hotel_stars, hotel_features ,hotel_board) VALUES (?, ?, ?, ?, ?, ?,?,?,?)";
        try {
            PreparedStatement statement = Db.getInstance().prepareStatement(query);
            statement.setString(1, hotel.getHotel_name());
            statement.setString(2, hotel.getHotel_address());
            statement.setString(3, hotel.getHotel_city());
            statement.setString(4, hotel.getHotel_region());
            statement.setString(5, hotel.getHotel_mail());
            statement.setString(6, hotel.getHotel_tel());
            statement.setString(7, hotel.getHotel_stars());
            statement.setString(8, hotel.getHotel_features());
            statement.setString(9, hotel.getHotel_board());

            return statement.executeUpdate() != -1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void loadHotelList() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_hotel_list.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for (Hotel obj : Hotel.getList()) {
            i = 0;
            row_hotel_list[i++] = obj.getHotel_id();
            row_hotel_list[i++] = obj.getHotel_name();
            row_hotel_list[i++] = obj.getHotel_city();
            row_hotel_list[i++] = obj.getHotel_region();
            row_hotel_list[i++] = obj.getHotel_stars();
            row_hotel_list[i++] = obj.getHotel_tel();
            row_hotel_list[i++] = obj.getHotel_mail();
            mdl_hotel_list.addRow(row_hotel_list);
        }
    }

    public static int getSelacted_hotelId(){
        return selected_hotelId;
    }

    public int getRoom_hotel_id_add() {
        return room_hotel_id_add;
    }

    public void setRoom_hotel_id_add(int room_hotel_id_add) {
        this.room_hotel_id_add = room_hotel_id_add;
    }

    public static int getSelected_hotelId() {
        return selected_hotelId;
    }

    public static void setSelected_hotelId(int selected_hotelId) {
        EmployeeView.selected_hotelId = selected_hotelId;
    }

    public static int getSelected_roomId() {
        return selected_roomId;
    }

    public static void setSelected_roomId(int selected_roomId) {
        EmployeeView.selected_roomId = selected_roomId;
    }

    public static String getSelectedSeasonName() {
        return selectedSeasonName;
    }

    public static void setSelectedSeasonName(String selectedSeasonName) {
        EmployeeView.selectedSeasonName = selectedSeasonName;
    }

    public static int getSelectedAdultPrice() {
        return selectedAdultPrice;
    }

    public static void setSelectedAdultPrice(int selectedAdultPrice) {
        EmployeeView.selectedAdultPrice = selectedAdultPrice;
    }

    public static int getSelectedChildPrice() {
        return selectedChildPrice;
    }

    public static void setSelectedChildPrice(int selectedChildPrice) {
        EmployeeView.selectedChildPrice = selectedChildPrice;

    }

    public static String getSelectedHostelType() {
        return selectedHostelType;
    }

    public static void setSelectedHostelType(String selectedHostelType) {
        EmployeeView.selectedHostelType = selectedHostelType;
    }
}