
package hotelgui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class HotelManagementSystem extends JFrame {
    // Color Theme
    private static final Color KHAKI = new Color(255, 170, 128);
    private static final Color BEIGE = new Color(245, 245, 220);
    private static final Color DARK_KHAKI = new Color(189, 183, 107);
    private static final Color BROWN = new Color(139, 69, 19);

    private List<Room> rooms;
    private List<Guest> guests;
    private JTable roomTable, guestTable, bookingTable;

    public HotelManagementSystem() {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        initializeRooms();
        guests = new ArrayList<>();

        setTitle("Hotel Management System");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        getContentPane().setBackground(BEIGE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BEIGE);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 18));
        tabbedPane.setPreferredSize(new Dimension(400, 60));
        
        tabbedPane.setBackground(KHAKI);
        tabbedPane.setForeground(BROWN);
        
        tabbedPane.addTab("Bookings", createBookingPanel());
        tabbedPane.addTab("Rooms", createRoomPanel());
        tabbedPane.addTab("Guests", createGuestPanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);

        initializeGuestsAndBookings();
    }

    private void initializeRooms() {
        rooms = new ArrayList<>();
        rooms.add(new Room(101, "Single", 2, 1700.00));
        rooms.add(new Room(102, "Single", 2, 2300.00));
        rooms.add(new Room(201, "Double", 4, 5600.00));
        rooms.add(new Room(202, "Deluxe", 4, 4500.00));
        rooms.add(new Room(301, "Pent House", 8, 50000.00));
    }

    private void initializeGuestsAndBookings() {
        guests.add(new Guest("Fely", "09666546090",
                LocalDate.of(2024, 12, 3),
                LocalDate.of(2024, 12, 20),
                201));
        guests.add(new Guest("Keneth", "09562572352",
                LocalDate.of(2024, 12, 3),
                LocalDate.of(2024, 12, 17),
                301));
        guests.add(new Guest("Steeven", "09154751221",
                LocalDate.of(2024, 12, 3),
                LocalDate.of(2024, 12, 18),
                101));

        rooms.stream()
                .filter(room -> room.getRoomNumber() == 201 || room.getRoomNumber() == 301 || room.getRoomNumber() == 101)
                .forEach(room -> room.setOccupied(true));

        DefaultTableModel guestModel = (DefaultTableModel) guestTable.getModel();
        for (Guest guest : guests) {
            guestModel.addRow(new Object[]{
                    guest.getName(),
                    guest.getContactNumber(),
                    guest.getCheckInDate(),
                    guest.getCheckOutDate(),
                    guest.getRoomNumber()
            });
        }

        DefaultTableModel bookingModel = (DefaultTableModel) bookingTable.getModel();
        for (Guest guest : guests) {
            bookingModel.addRow(new Object[]{
                    guest.getName(),
                    guest.getRoomNumber(),
                    guest.getCheckInDate(),
                    guest.getCheckOutDate()
            });
        }

        updateRoomTable();
    }

    private JPanel createRoomPanel() {
        JPanel roomPanel = new JPanel(new BorderLayout());
        roomPanel.setBackground(BEIGE);

        String[] roomColumns = {"Room Number", "Room Type", "Capacity", "Price", "Occupied", "Booked Dates"};
        DefaultTableModel roomModel = new DefaultTableModel(roomColumns, 0);
        roomTable = new JTable(roomModel);
        
        roomTable.setBackground(Color.WHITE);
        roomTable.setForeground(BROWN);
        roomTable.setSelectionBackground(KHAKI);
        roomTable.setGridColor(DARK_KHAKI);
        roomTable.getTableHeader().setBackground(KHAKI);
        roomTable.getTableHeader().setForeground(BROWN);
        roomTable.setFont(new Font("Arial", Font.PLAIN, 14));
        roomTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JPanel roomButtonPanel = new JPanel();
        roomButtonPanel.setBackground(BEIGE);
        roomButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton[] buttons = {
            new JButton("Add Room"),
            new JButton("Edit Room"),
            new JButton("Delete Room")
        };

        for (JButton btn : buttons) {
            btn.setPreferredSize(new Dimension(200, 60));
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.setBackground(KHAKI);
            btn.setForeground(BROWN);
            btn.setFocusPainted(false);
            btn.setBorderPainted(true);
        }

        roomButtonPanel.add(buttons[0]);
        roomButtonPanel.add(buttons[1]);
        roomButtonPanel.add(buttons[2]);

        buttons[0].addActionListener(e -> addRoom());
        buttons[1].addActionListener(e -> editRoom());
        buttons[2].addActionListener(e -> deleteRoom());

        roomPanel.add(new JScrollPane(roomTable) {
            {
                getViewport().setBackground(Color.WHITE);
            }
        }, BorderLayout.CENTER);
        roomPanel.add(roomButtonPanel, BorderLayout.SOUTH);

        return roomPanel;
    }

    private JPanel createGuestPanel() {
        JPanel guestPanel = new JPanel(new BorderLayout());
        guestPanel.setBackground(BEIGE);

        String[] guestColumns = {"Name", "Contact", "Check-In", "Check-Out", "Room Number"};
        DefaultTableModel guestModel = new DefaultTableModel(guestColumns, 0);
        guestTable = new JTable(guestModel);
        
        guestTable.setBackground(Color.WHITE);
        guestTable.setForeground(BROWN);
        guestTable.setSelectionBackground(KHAKI);
        guestTable.setGridColor(DARK_KHAKI);
        guestTable.getTableHeader().setBackground(KHAKI);
        guestTable.getTableHeader().setForeground(BROWN);
        guestTable.setFont(new Font("Arial", Font.PLAIN, 14));
        guestTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JPanel guestButtonPanel = new JPanel();
        guestButtonPanel.setBackground(BEIGE);
        guestButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton[] buttons = {
            new JButton("Add Guest"),
            new JButton("Edit Guest"),
            new JButton("Delete Guest")
        };

        for (JButton btn : buttons) {
            btn.setPreferredSize(new Dimension(200, 60));
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.setBackground(KHAKI);
            btn.setForeground(BROWN);
            btn.setFocusPainted(false);
            btn.setBorderPainted(true);
        }

        guestButtonPanel.add(buttons[0]);
        guestButtonPanel.add(buttons[1]);
        guestButtonPanel.add(buttons[2]);

        buttons[0].addActionListener(e -> addGuest());
        buttons[1].addActionListener(e -> editGuest());
        buttons[2].addActionListener(e -> deleteGuest());

        guestPanel.add(new JScrollPane(guestTable) {
            {
                getViewport().setBackground(Color.WHITE);
            }
        }, BorderLayout.CENTER);
        guestPanel.add(guestButtonPanel, BorderLayout.SOUTH);

        return guestPanel;
    }

    private JPanel createBookingPanel() {
        JPanel bookingPanel = new JPanel(new BorderLayout());
        bookingPanel.setBackground(BEIGE);

        String[] bookingColumns = {"Guest Name", "Room Number", "Check-In", "Check-Out"};
        DefaultTableModel bookingModel = new DefaultTableModel(bookingColumns, 0);
        bookingTable = new JTable(bookingModel);
        
        bookingTable.setBackground(Color.WHITE);
        bookingTable.setForeground(BROWN);
        bookingTable.setSelectionBackground(KHAKI);
        bookingTable.setGridColor(DARK_KHAKI);
        bookingTable.getTableHeader().setBackground(KHAKI);
        bookingTable.getTableHeader().setForeground(BROWN);
        bookingTable.setFont(new Font("Arial", Font.PLAIN, 14));
        bookingTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JPanel bookingButtonPanel = new JPanel();
        bookingButtonPanel.setBackground(BEIGE);
        bookingButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton[] buttons = {
            new JButton("Make Booking"),
            new JButton("Modify Booking"),
            new JButton("Cancel Booking")
        };

        for (JButton btn : buttons) {
            btn.setPreferredSize(new Dimension(200, 60));
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.setBackground(KHAKI);
            btn.setForeground(BROWN);
            btn.setFocusPainted(false);
            btn.setBorderPainted(true);
        }

        bookingButtonPanel.add(buttons[0]);
        bookingButtonPanel.add(buttons[1]);
        bookingButtonPanel.add(buttons[2]);

        buttons[0].addActionListener(e -> makeBooking());
        buttons[1].addActionListener(e -> modifyBooking());
        buttons[2].addActionListener(e -> cancelBooking());

        bookingPanel.add(new JScrollPane(bookingTable) {
            {
                getViewport().setBackground(Color.WHITE);
            }
        }, BorderLayout.CENTER);
        bookingPanel.add(bookingButtonPanel, BorderLayout.SOUTH);

        return bookingPanel;
    }

    private String getBookedDatesForRoom(Room room) {
        StringBuilder dates = new StringBuilder();
        for (Guest guest : guests) {
            if (guest.getRoomNumber() == room.getRoomNumber()) {
                if (dates.length() > 0) {
                    dates.append(", ");
                }
                dates.append(guest.getCheckInDate()).append(" - ").append(guest.getCheckOutDate());
            }
        }
        return dates.toString();
    }

    private void updateRoomTable() {
        DefaultTableModel roomModel = (DefaultTableModel) roomTable.getModel();
        roomModel.setRowCount(0);
        for (Room room : rooms) {
            String bookedDates = getBookedDatesForRoom(room);
            roomModel.addRow(new Object[]{
                    room.getRoomNumber(),
                    room.getRoomType(),
                    room.getCapacity(),
                    room.getPrice(),
                    room.isOccupied() ? "Yes" : "No",
                    bookedDates
            });
        }
    }

   
    public void addRoom() {
    // Dialog for adding a new room
        JTextField roomNumberField = new JTextField();
        JComboBox<String> roomTypeCombo = new JComboBox<>(new String[]{"Single", "Double", "Suite","Deluxe","Pent House"});
        JTextField capacityField = new JTextField();
        JTextField priceField = new JTextField();

        Object[] message = {
            "Room Number:", roomNumberField,
            "Room Type:", roomTypeCombo,
            "Capacity:", capacityField,
            "Price per Night:", priceField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Room", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                int roomNumber = Integer.parseInt(roomNumberField.getText());
                String roomType = (String) roomTypeCombo.getSelectedItem();
                int capacity = Integer.parseInt(capacityField.getText());
                double price = Double.parseDouble(priceField.getText());

                // Check for duplicate room number
                boolean roomExists = rooms.stream().anyMatch(r -> r.getRoomNumber() == roomNumber);

                if (roomExists) {
                    JOptionPane.showMessageDialog(this, "Room number already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Room newRoom = new Room(roomNumber, roomType, capacity, price);
                rooms.add(newRoom);

                // Update room table
                DefaultTableModel model = (DefaultTableModel) roomTable.getModel();
                model.addRow(new Object[]{
                    newRoom.getRoomNumber(),
                    newRoom.getRoomType(),
                    newRoom.getCapacity(),
                    newRoom.getPrice(),
                    "No"
                });
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please check your entries.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

            public void editRoom() {
                int selectedRow = roomTable.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "Please select a room to edit", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Room selectedRoom = rooms.get(selectedRow);

                JTextField roomNumberField = new JTextField(String.valueOf(selectedRoom.getRoomNumber()));
                roomNumberField.setEditable(false); // Prevent changing room number
                JComboBox<String> roomTypeCombo = new JComboBox<>(new String[]{"Single", "Double", "Suite","Deluxe","Pent House"});
                roomTypeCombo.setSelectedItem(selectedRoom.getRoomType());
                JTextField capacityField = new JTextField(String.valueOf(selectedRoom.getCapacity()));
                JTextField priceField = new JTextField(String.valueOf(selectedRoom.getPrice()));

                Object[] message = {
                    "Room Number:", roomNumberField,
                    "Room Type:", roomTypeCombo,
                    "Capacity:", capacityField,
                    "Price per Night:", priceField
                };

                int option = JOptionPane.showConfirmDialog(this, message, "Edit Room", JOptionPane.OK_CANCEL_OPTION);

                if (option == JOptionPane.OK_OPTION) {
                    try {
                        String roomType = (String) roomTypeCombo.getSelectedItem();
                        int capacity = Integer.parseInt(capacityField.getText());
                        double price = Double.parseDouble(priceField.getText());

                        selectedRoom.setRoomType(roomType);
                        selectedRoom.setCapacity(capacity);
                        selectedRoom.setPrice(price);

                        // Update room table
                        DefaultTableModel model = (DefaultTableModel) roomTable.getModel();
                        model.setValueAt(roomType, selectedRow, 1);
                        model.setValueAt(capacity, selectedRow, 2);
                        model.setValueAt(price, selectedRow, 3);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid input. Please check your entries.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

            public void deleteRoom() {
                int selectedRow = roomTable.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "Please select a room to delete", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Room selectedRoom = rooms.get(selectedRow);

                // Prevent deletion of an occupied room
                if (selectedRoom.isOccupied()) {
                    JOptionPane.showMessageDialog(this, "Cannot delete an occupied room!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Are you sure you want to delete room " + selectedRoom.getRoomNumber() + "?", 
                    "Confirm Deletion", 
                    JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    rooms.remove(selectedRow);

                    // Update room table
                    DefaultTableModel model = (DefaultTableModel) roomTable.getModel();
                    model.removeRow(selectedRow);
                }
            }

    // Placeholder for guest methods
    public void addGuest() {
        JTextField nameField = new JTextField();
        JTextField contactField = new JTextField();
        JTextField checkInField = new JTextField(LocalDate.now().toString());
        JTextField checkOutField = new JTextField();
        JComboBox<Integer> roomCombo = new JComboBox<>(rooms.stream()
            .filter(room -> !room.isOccupied())
            .map(Room::getRoomNumber)
            .toArray(Integer[]::new)
        );

        Object[] message = {
            "Name:", nameField,
            "Contact:", contactField,
            "Check-In Date:", checkInField,
            "Check-Out Date:", checkOutField,
            "Room Number:", roomCombo
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add New Guest", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                String contact = contactField.getText();
                LocalDate checkIn = LocalDate.parse(checkInField.getText());
                LocalDate checkOut = LocalDate.parse(checkOutField.getText());

                // Validate that checkOut is after checkIn
                if (checkIn.isAfter(checkOut)) {
                    JOptionPane.showMessageDialog(this, "Check-Out date must be after Check-In date.", "Invalid Date", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int roomNumber = (Integer) roomCombo.getSelectedItem();

                // Find and occupy the room
                Room selectedRoom = rooms.stream()
                    .filter(room -> room.getRoomNumber() == roomNumber)
                    .findFirst()
                    .orElseThrow(() -> new Exception("Room not found"));
                selectedRoom.setOccupied(true);

                Guest newGuest = new Guest(name, contact, checkIn, checkOut, roomNumber);
                guests.add(newGuest);

                // Update guest table
                DefaultTableModel guestModel = (DefaultTableModel) guestTable.getModel();
                guestModel.addRow(new Object[] {
                    newGuest.getName(),
                    newGuest.getContactNumber(),
                    newGuest.getCheckInDate(),
                    newGuest.getCheckOutDate(),
                    newGuest.getRoomNumber()
                });

                // Update room table to show room is occupied
                DefaultTableModel roomModel = (DefaultTableModel) roomTable.getModel();
                for (int i = 0; i < roomModel.getRowCount(); i++) {
                    if ((int) roomModel.getValueAt(i, 0) == roomNumber) {
                        roomModel.setValueAt("Yes", i, 4);
                        break;
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        updateRoomTable();
    }

    public void editGuest() {
        int selectedRow = guestTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a guest to edit", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Guest selectedGuest = guests.get(selectedRow);

        JTextField nameField = new JTextField(selectedGuest.getName());
        JTextField contactField = new JTextField(selectedGuest.getContactNumber());
        JTextField checkInField = new JTextField(selectedGuest.getCheckInDate().toString());
        JTextField checkOutField = new JTextField(selectedGuest.getCheckOutDate().toString());
        JComboBox<Integer> roomCombo = new JComboBox<>(
            rooms.stream()
            .filter(room -> !room.isOccupied() || room.getRoomNumber() == selectedGuest.getRoomNumber())
            .map(Room::getRoomNumber)
            .toArray(Integer[]::new)
        );
        roomCombo.setSelectedItem(selectedGuest.getRoomNumber());

        Object[] message = {
            "Name:", nameField,
            "Contact:", contactField,
            "Check-In Date:", checkInField,
            "Check-Out Date:", checkOutField,
            "Room Number:", roomCombo
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Edit Guest", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                String contact = contactField.getText();
                LocalDate checkIn = LocalDate.parse(checkInField.getText());
                LocalDate checkOut = LocalDate.parse(checkOutField.getText());
                int newRoomNumber = (Integer) roomCombo.getSelectedItem();

                // Handle room changes
                if (newRoomNumber != selectedGuest.getRoomNumber()) {
                    // Free the old room
                    Room oldRoom = rooms.stream()
                        .filter(room -> room.getRoomNumber() == selectedGuest.getRoomNumber())
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Room not found"));
                    oldRoom.setOccupied(false);

                    // Occupy the new room
                    Room newRoom = rooms.stream()
                        .filter(room -> room.getRoomNumber() == newRoomNumber)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Room not found"));
                    newRoom.setOccupied(true);
                }

                // Update guest details
                selectedGuest.setName(name);
                selectedGuest.setContactNumber(contact);
                selectedGuest.setCheckInDate(checkIn);
                selectedGuest.setCheckOutDate(checkOut);
                selectedGuest.setRoomNumber(newRoomNumber);

                // Update guest table
                DefaultTableModel guestModel = (DefaultTableModel) guestTable.getModel();
                guestModel.setValueAt(name, selectedRow, 0);
                guestModel.setValueAt(contact, selectedRow, 1);
                guestModel.setValueAt(checkIn, selectedRow, 2);
                guestModel.setValueAt(checkOut, selectedRow, 3);
                guestModel.setValueAt(newRoomNumber, selectedRow, 4);

                // Update room table
                DefaultTableModel roomModel = (DefaultTableModel) roomTable.getModel();
                for (int i = 0; i < roomModel.getRowCount(); i++) {
                    int roomNumber = (int) roomModel.getValueAt(i, 0);
                    roomModel.setValueAt(
                        roomNumber == newRoomNumber || roomNumber == selectedGuest.getRoomNumber() ? "Yes" : "No", 
                        i, 4
                    );
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        updateRoomTable();
    }
        public void deleteGuest() {
        int selectedRow = guestTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a guest to delete", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Guest selectedGuest = guests.get(selectedRow);

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete guest " + selectedGuest.getName() + "?", 
            "Confirm Deletion", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Free the room
            Room occupiedRoom = rooms.stream()
                .filter(room -> room.getRoomNumber() == selectedGuest.getRoomNumber())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));
            occupiedRoom.setOccupied(false);

            // Remove guest
            guests.remove(selectedRow);

            // Update guest table
            DefaultTableModel guestModel = (DefaultTableModel) guestTable.getModel();
            guestModel.removeRow(selectedRow);

            // Update room table
            DefaultTableModel roomModel = (DefaultTableModel) roomTable.getModel();
            for (int i = 0; i < roomModel.getRowCount(); i++) {
                if ((int) roomModel.getValueAt(i, 0) == selectedGuest.getRoomNumber()) {
                    roomModel.setValueAt("No", i, 4);
                    break;
                }
            }
        }
    }


        public void makeBooking() {
        JTextField nameField = new JTextField();
        JTextField contactField = new JTextField();
        JTextField checkInField = new JTextField(LocalDate.now().toString());
        JTextField checkOutField = new JTextField();
        JComboBox<Integer> roomCombo = new JComboBox<>(
            rooms.stream()
            .filter(room -> isRoomAvailable(room, LocalDate.now(), LocalDate.now().plusDays(1))) // Check availability for default dates
            .map(Room::getRoomNumber)
            .toArray(Integer[]::new)
        );

        Object[] message = {
            "Guest Name:", nameField,
            "Contact:", contactField,
            "Check-In Date (YYYY-MM-DD):", checkInField,
            "Check-Out Date (YYYY-MM-DD):", checkOutField,
            "Room Number:", roomCombo
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Make Booking", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                String contact = contactField.getText();
                LocalDate checkIn = LocalDate.parse(checkInField.getText());
                LocalDate checkOut = LocalDate.parse(checkOutField.getText());
                int roomNumber = (Integer) roomCombo.getSelectedItem();

                // Validate input
                if (name.isEmpty() || contact.isEmpty()) {
                    throw new IllegalArgumentException("Name and contact cannot be empty");
                }
                if (checkIn.isAfter(checkOut)) {
                    throw new IllegalArgumentException("Check-out date must be after check-in date");
                }

                Room selectedRoom = rooms.stream()
                    .filter(room -> room.getRoomNumber() == roomNumber)
                    .findFirst()
                    .orElseThrow(() -> new Exception("Room not found"));

                if (!isRoomAvailable(selectedRoom, checkIn, checkOut)) {
                    throw new Exception("Room is not available for the selected dates.");
                }

                // Calculate total price
                long numDays = ChronoUnit.DAYS.between(checkIn, checkOut);
                double totalPrice = selectedRoom.getPrice() * numDays;

                // Create new guest and mark room as occupied
                Guest newGuest = new Guest(name, contact, checkIn, checkOut, roomNumber);
                guests.add(newGuest);
                selectedRoom.setOccupied(true);

                // Update guest table
                DefaultTableModel guestModel = (DefaultTableModel) guestTable.getModel();
                guestModel.addRow(new Object[]{
                    name, 
                    contact, 
                    checkIn, 
                    checkOut, 
                    roomNumber
                });

                // Update booking table
                DefaultTableModel bookingModel = (DefaultTableModel) bookingTable.getModel();
                bookingModel.addRow(new Object[]{
                    name, 
                    roomNumber, 
                    checkIn, 
                    checkOut
                });

                // Update room table
                updateRoomTable();

                // Show total price
                int book = (int) (totalPrice + 500);
                JOptionPane.showMessageDialog(this, 
                    "Booking successful!\nReservation Price: ₱500\nBooking Price: ₱"+ (int) totalPrice + "\nTotal Price: ₱" + book, 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Booking failed: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
 

    private boolean isRoomAvailable(Room room, LocalDate checkIn, LocalDate checkOut) {
        for (Guest guest : guests) {
            if (guest.getRoomNumber() == room.getRoomNumber()) {
                if (!(checkOut.isBefore(guest.getCheckInDate()) || checkIn.isAfter(guest.getCheckOutDate()))) {
                    return false; // Overlapping dates, room not available
                }
            }
        }
        return true; // No overlapping dates, room available
    }


public void modifyBooking() {
        int selectedBookingRow = bookingTable.getSelectedRow();

        if (selectedBookingRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to modify", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Find the corresponding guest
        String guestName = (String) bookingTable.getValueAt(selectedBookingRow, 0);
        Guest selectedGuest = guests.stream()
            .filter(g -> g.getName().equals(guestName))
            .findFirst()
            .orElse(null);

        if (selectedGuest == null) {
            JOptionPane.showMessageDialog(this, "Guest not found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField nameField = new JTextField(selectedGuest.getName());
        JTextField contactField = new JTextField(selectedGuest.getContactNumber());
        JTextField checkInField = new JTextField(selectedGuest.getCheckInDate().toString());
        JTextField checkOutField = new JTextField(selectedGuest.getCheckOutDate().toString());

        // Prepare room combo box
        JComboBox<Integer> roomCombo = new JComboBox<>(
            rooms.stream()
            .filter(room -> !room.isOccupied() || room.getRoomNumber() == selectedGuest.getRoomNumber())
            .map(Room::getRoomNumber)
            .toArray(Integer[]::new)
        );
        roomCombo.setSelectedItem(selectedGuest.getRoomNumber());

        Object[] message = {
            "Guest Name:", nameField,
            "Contact:", contactField,
            "Check-In Date:", checkInField,
            "Check-Out Date:", checkOutField,
            "Room Number:", roomCombo
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Modify Booking", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                String contact = contactField.getText();
                LocalDate checkIn = LocalDate.parse(checkInField.getText());
                LocalDate checkOut = LocalDate.parse(checkOutField.getText());
                int newRoomNumber = (Integer) roomCombo.getSelectedItem();

                // Validate input
                if (name.isEmpty() || contact.isEmpty()) {
                    throw new IllegalArgumentException("Name and contact cannot be empty");
                }
                if (checkIn.isAfter(checkOut)) {
                    throw new IllegalArgumentException("Check-out date must be after check-in date");
                }

                // Handle room changes
                if (newRoomNumber != selectedGuest.getRoomNumber()) {
                    // Free the old room
                    Room oldRoom = rooms.stream()
                        .filter(room -> room.getRoomNumber() == selectedGuest.getRoomNumber())
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Room not found"));
                    oldRoom.setOccupied(false);

                    // Occupy the new room
                    Room newRoom = rooms.stream()
                        .filter(room -> room.getRoomNumber() == newRoomNumber)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Room not found"));
                    newRoom.setOccupied(true);
                }

                // Update guest details
                selectedGuest.setName(name);
                selectedGuest.setContactNumber(contact);
                selectedGuest.setCheckInDate(checkIn);
                selectedGuest.setCheckOutDate(checkOut);
                selectedGuest.setRoomNumber(newRoomNumber);

                // Update booking table
                DefaultTableModel bookingModel = (DefaultTableModel) bookingTable.getModel();
                bookingModel.setValueAt(name, selectedBookingRow, 0);
                bookingModel.setValueAt(newRoomNumber, selectedBookingRow, 1);
                bookingModel.setValueAt(checkIn, selectedBookingRow, 2);
                bookingModel.setValueAt(checkOut, selectedBookingRow, 3);

                // Update guest table
                DefaultTableModel guestModel = (DefaultTableModel) guestTable.getModel();
                for (int i = 0; i < guestModel.getRowCount(); i++) {
                    if (guestModel.getValueAt(i, 0).equals(guestName)) {
                        guestModel.setValueAt(name, i, 0);
                        guestModel.setValueAt(contact, i, 1);
                        guestModel.setValueAt(checkIn, i, 2);
                        guestModel.setValueAt(checkOut, i, 3);
                        guestModel.setValueAt(newRoomNumber, i, 4);
                        break;
                    }
                }

                // Update room table
                DefaultTableModel roomModel = (DefaultTableModel) roomTable.getModel();
                for (int i = 0; i < roomModel.getRowCount(); i++) {
                    int roomNumber = (int) roomModel.getValueAt(i, 0);
                    roomModel.setValueAt(
                        roomNumber == newRoomNumber || roomNumber == selectedGuest.getRoomNumber() ? "Yes" : "No", 
                        i, 4
                    );
                }

                JOptionPane.showMessageDialog(this, "Booking modified successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Modification failed: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
        updateRoomTable();
    }

    public void cancelBooking() {
        int selectedBookingRow = bookingTable.getSelectedRow();
        if (selectedBookingRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to cancel", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Find the corresponding guest
        String guestName = (String) bookingTable.getValueAt(selectedBookingRow, 0);
        int roomNumber = (int) bookingTable.getValueAt(selectedBookingRow, 1);
        Guest selectedGuest = guests.stream()
            .filter(g -> g.getName().equals(guestName) && g.getRoomNumber() == roomNumber)
            .findFirst()
            .orElse(null);
        if (selectedGuest == null) {
            JOptionPane.showMessageDialog(this, "Guest not found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Find the room for this guest
        Room selectedRoom = rooms.stream()
            .filter(room -> room.getRoomNumber() == roomNumber)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Room not found"));

        // Calculate total price for refund
        long numDays = ChronoUnit.DAYS.between(selectedGuest.getCheckInDate(), selectedGuest.getCheckOutDate());
        double totalPrice = selectedRoom.getPrice() * numDays;

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to cancel the booking for " + guestName + "?" + 
            "\nRefund price: ₱" + String.format("%.2f", totalPrice), 
            "Confirm Cancellation", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Free the room
                selectedRoom.setOccupied(false);

                // Remove guest
                guests.remove(selectedGuest);

                // Remove from booking table
                DefaultTableModel bookingModel = (DefaultTableModel) bookingTable.getModel();
                bookingModel.removeRow(selectedBookingRow);

                // Remove from guest table
                DefaultTableModel guestModel = (DefaultTableModel) guestTable.getModel();
                for (int i = 0; i < guestModel.getRowCount(); i++) {
                    if (guestModel.getValueAt(i, 0).equals(guestName) && 
                        (int)guestModel.getValueAt(i, 4) == roomNumber) {
                        guestModel.removeRow(i);
                        break;
                    }
                }

                // Update room table
                DefaultTableModel roomModel = (DefaultTableModel) roomTable.getModel();
                for (int i = 0; i < roomModel.getRowCount(); i++) {
                    if ((int) roomModel.getValueAt(i, 0) == roomNumber) {
                        roomModel.setValueAt("No", i, 4);
                        break;
                    }
                }

                JOptionPane.showMessageDialog(this, "Booking cancelled successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, 
                    "Cancellation failed: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
        updateRoomTable();
    }

}

