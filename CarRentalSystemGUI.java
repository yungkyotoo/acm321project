package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CarRentalSystemGUI {
    private JFrame frame;
    private CarService carService;
    private BookingService bookingService;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CarRentalSystemGUI window = new CarRentalSystemGUI();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public CarRentalSystemGUI() {
        carService = new CarService(); 
        bookingService = new BookingService(); 
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Car Rental System");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new GridLayout(3, 1));

        JButton viewCarsButton = new JButton("View Available Cars");
        JButton bookCarButton = new JButton("Book a Car");
        JButton viewBookingsButton = new JButton("View All Bookings");

        mainPanel.add(viewCarsButton);
        mainPanel.add(bookCarButton);
        mainPanel.add(viewBookingsButton);

        viewCarsButton.addActionListener(e -> showAvailableCars());
        bookCarButton.addActionListener(e -> bookCar());
        viewBookingsButton.addActionListener(e -> showAllBookings());
    }

    private void showAvailableCars() {
        List<Car> availableCars = carService.getAvailableCars();

        JFrame carFrame = new JFrame("Available Cars");
        carFrame.setSize(600, 400);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Car ID");
        model.addColumn("Details");

        for (Car car : availableCars) {
            model.addRow(new Object[]{car.getCarId(), car.getDetails()});
        }

        JTable carTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(carTable);
        carFrame.add(scrollPane, BorderLayout.CENTER);

        carFrame.setVisible(true);
    }

    private void bookCar() {
        JFrame bookingFrame = new JFrame("Book a Car");
        bookingFrame.setSize(400, 300);
        bookingFrame.setLayout(new GridLayout(4, 2));

        JLabel carIdLabel = new JLabel("Enter Car ID:");
        JTextField carIdField = new JTextField();
        JLabel nameLabel = new JLabel("Enter Your Name:");
        JTextField nameField = new JTextField();
        JLabel daysLabel = new JLabel("Enter Rental Days:");
        JTextField daysField = new JTextField();
        JButton bookButton = new JButton("Book");

        bookingFrame.add(carIdLabel);
        bookingFrame.add(carIdField);
        bookingFrame.add(nameLabel);
        bookingFrame.add(nameField);
        bookingFrame.add(daysLabel);
        bookingFrame.add(daysField);
        bookingFrame.add(new JLabel());
        bookingFrame.add(bookButton);

        bookButton.addActionListener(e -> {
            try {
                int carId = Integer.parseInt(carIdField.getText());
                String customerName = nameField.getText();
                int rentalDays = Integer.parseInt(daysField.getText());

                if (customerName.isEmpty()) {
                    JOptionPane.showMessageDialog(bookingFrame, "Name cannot be empty.");
                    return;
                }

                Car car = carService.getCarById(carId);
                if (car == null || !car.isAvailable()) {
                    JOptionPane.showMessageDialog(bookingFrame, "Invalid car ID or car not available.");
                } else {
                    Booking booking = bookingService.createBooking(car, customerName, rentalDays);
                    JOptionPane.showMessageDialog(bookingFrame, "Booking successful!\n" + booking.getDetails());
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(bookingFrame, "Please enter valid numbers for Car ID and Rental Days.");
            }
        });

        bookingFrame.setVisible(true);
    }

    private void showAllBookings() {
        JFrame bookingsFrame = new JFrame("All Bookings");
        bookingsFrame.setSize(600, 400);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Booking ID");
        model.addColumn("Car");
        model.addColumn("Customer");
        model.addColumn("Rental Days");
        model.addColumn("Total Price");

        for (Booking booking : bookingService.getAllBookings()) {
            model.addRow(new Object[]{
                    booking.getBookingId(),
                    booking.getCar().getDetails(),
                    booking.getCustomerName(),
                    booking.getRentalDays(),
                    booking.getTotalPrice()
            });
        }

        JTable bookingsTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        bookingsFrame.add(scrollPane, BorderLayout.CENTER);

        bookingsFrame.setVisible(true);
    }
}
