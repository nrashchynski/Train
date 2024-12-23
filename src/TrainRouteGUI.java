import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainRouteGUI extends JFrame {
    private TrainRouteManager manager;
    private JTextArea outputArea;
    private JTextField departureField, destinationField, trainNumberField, departureTimeField, totalSeatsField, coupeSeatsField, platzkartSeatsField, luxurySeatsField;
    private JComboBox<String> actionComboBox;

    public TrainRouteGUI() {
        manager = new TrainRouteManager();

        // Настройка окна
        setTitle("Train Route Manager");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        JPanel actionPanel = new JPanel();
        actionComboBox = new JComboBox<>(new String[]{
                "Choose Action",
                "1. Add Route",
                "2. Display Routes",
                "3. Find Route by Index",
                "4. Find Routes by Departure",
                "5. Find Routes by Destination",
                "6. Find Routes by Train Number",
                "7. Delete Route by Index",
                "8. Exit"
        });
        actionComboBox.setSelectedIndex(0);
        actionComboBox.addActionListener(new ActionHandler());
        actionPanel.add(actionComboBox);

        JPanel inputPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Departure:"));
        departureField = new JTextField();
        inputPanel.add(departureField);

        inputPanel.add(new JLabel("Destination:"));
        destinationField = new JTextField();
        inputPanel.add(destinationField);

        inputPanel.add(new JLabel("Train Number:"));
        trainNumberField = new JTextField();
        inputPanel.add(trainNumberField);

        inputPanel.add(new JLabel("Departure Time:"));
        departureTimeField = new JTextField();
        inputPanel.add(departureTimeField);

        inputPanel.add(new JLabel("Total Seats:"));
        totalSeatsField = new JTextField();
        inputPanel.add(totalSeatsField);

        inputPanel.add(new JLabel("Coupe Seats:"));
        coupeSeatsField = new JTextField();
        inputPanel.add(coupeSeatsField);

        inputPanel.add(new JLabel("Platzkart Seats:"));
        platzkartSeatsField = new JTextField();
        inputPanel.add(platzkartSeatsField);

        inputPanel.add(new JLabel("Luxury Seats:"));
        luxurySeatsField = new JTextField();
        inputPanel.add(luxurySeatsField);

        JButton addButton = new JButton("Add Route");
        addButton.addActionListener(new AddRouteAction());
        inputPanel.add(addButton);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        JPanel statusPanel = new JPanel();
        JLabel statusLabel = new JLabel("Ready");
        statusPanel.add(statusLabel);

        add(actionPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);
    }

    private class ActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedAction = (String) actionComboBox.getSelectedItem();
            if (selectedAction == null || selectedAction.equals("Choose Action")) {
                return;
            }

            switch (selectedAction) {
                case "1. Add Route":
                    departureField.setText("");
                    destinationField.setText("");
                    trainNumberField.setText("");
                    departureTimeField.setText("");
                    totalSeatsField.setText("");
                    coupeSeatsField.setText("");
                    platzkartSeatsField.setText("");
                    luxurySeatsField.setText("");
                    break;
                case "2. Display Routes":
                    try {
                        outputArea.setText("");
                        manager.displayAllRoutes(outputArea);
                    } catch (IOException | ClassNotFoundException ex) {
                        outputArea.append("Error displaying routes: " + ex.getMessage() + "\n");
                    }
                    break;
                case "3. Find Route by Index":
                    String indexInput = JOptionPane.showInputDialog("Enter index:");
                    if (indexInput != null && !indexInput.isEmpty()) {
                        try {
                            int index = Integer.parseInt(indexInput);
                            manager.findRouteByIndex(index, outputArea);
                        } catch (NumberFormatException ex) {
                            outputArea.append("Invalid index format.\n");
                        } catch (IOException | ClassNotFoundException ex) {
                            outputArea.append("Error finding route: " + ex.getMessage() + "\n");
                        }
                    }
                    break;
                case "4. Find Routes by Departure":
                    String departure = JOptionPane.showInputDialog("Enter departure:");
                    if (departure != null) {
                        try {
                            manager.findRoutesByDeparture(departure, outputArea);
                        } catch (IOException | ClassNotFoundException ex) {
                            outputArea.append("Error finding routes: " + ex.getMessage() + "\n");
                        }
                    }
                    break;
                case "5. Find Routes by Destination":
                    String destination = JOptionPane.showInputDialog("Enter destination:");
                    if (destination != null) {
                        try {
                            manager.findRoutesByDestination(destination, outputArea);
                        } catch (IOException | ClassNotFoundException ex) {
                            outputArea.append("Error finding routes: " + ex.getMessage() + "\n");
                        }
                    }
                    break;
                case "6. Find Routes by Train Number":
                    String trainNumber = JOptionPane.showInputDialog("Enter train number:");
                    if (trainNumber != null) {
                        try {
                            manager.findRoutesByTrainNumber(trainNumber, outputArea);
                        } catch (IOException | ClassNotFoundException ex) {
                            outputArea.append("Error finding routes: " + ex.getMessage() + "\n");
                        }
                    }
                    break;
                case "7. Delete Route by Index":
                    String deleteIndexInput = JOptionPane.showInputDialog("Enter index to delete:");
                    if (deleteIndexInput != null && !deleteIndexInput.isEmpty()) {
                        try {
                            int index = Integer.parseInt(deleteIndexInput);
                            manager.deleteRouteByIndex(index, outputArea);
                        } catch (NumberFormatException ex) {
                            outputArea.append("Invalid index format.\n");
                        }
                    }
                    break;
                case "8. Exit":
                    System.exit(0);
                    break;
                default:
                    outputArea.append("Invalid choice.\n");
            }
        }
    }

    private class AddRouteAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String departure = departureField.getText();
                String destination = destinationField.getText();
                String trainNumber = trainNumberField.getText();
                String departureTime = departureTimeField.getText();
                int totalSeats = Integer.parseInt(totalSeatsField.getText());
                int coupeSeats = Integer.parseInt(coupeSeatsField.getText());
                int platzkartSeats = Integer.parseInt(platzkartSeatsField.getText());
                int luxurySeats = Integer.parseInt(luxurySeatsField.getText());

                TrainRoute route = new TrainRoute(departure, destination, trainNumber, departureTime, totalSeats, coupeSeats, platzkartSeats, luxurySeats);
                manager.addRoute(route, outputArea);
            } catch (IOException ex) {
                outputArea.append("Error adding route: " + ex.getMessage() + "\n");
            } catch (NumberFormatException ex) {
                outputArea.append("Invalid input format.\n");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TrainRouteGUI gui = new TrainRouteGUI();
            gui.setVisible(true);
        });
    }

    static class TrainRouteManager {
        private static final String FILE_NAME = "train_routes.dat";
        private java.util.List<Long> index = new ArrayList<>();
        private Map<String, java.util.List<Long>> departureIndex = new HashMap<>();
        private Map<String, java.util.List<Long>> destinationIndex = new HashMap<>();
        private Map<String, java.util.List<Long>> trainNumberIndex = new HashMap<>();

        private void printToOutput(String message, JTextArea outputArea) {
            if (outputArea != null) {
                outputArea.append(message + "\n");
            } else {
                System.out.println(message);
            }
        }

        public void addRoute(TrainRoute route, JTextArea outputArea) throws IOException {
            try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "rw");
                 ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(raf.getFD()))) {
                long position = raf.length();
                raf.seek(position);
                oos.writeObject(route);
                index.add(position);

                departureIndex.computeIfAbsent(route.getDeparturePoint(), k -> new ArrayList<>()).add(position);
                destinationIndex.computeIfAbsent(route.getDestinationPoint(), k -> new ArrayList<>()).add(position);
                trainNumberIndex.computeIfAbsent(route.getTrainNumber(), k -> new ArrayList<>()).add(position);

                printToOutput("Route added successfully.", outputArea);
            }
        }

        public void displayAllRoutes(JTextArea outputArea) throws IOException, ClassNotFoundException {
            try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "r");
                 ObjectInputStream ois = new ObjectInputStream(new FileInputStream(raf.getFD()))) {
                for (Long pos : index) {
                    raf.seek(pos);
                    TrainRoute route = (TrainRoute) ois.readObject();
                    printToOutput(route.toString(), outputArea);
                }
            }
        }

        public void findRouteByIndex(int index, JTextArea outputArea) throws IOException, ClassNotFoundException {
            if (index < 0 || index >= this.index.size()) {
                printToOutput("Index out of bounds.", outputArea);
                return;
            }
            try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "r");
                 ObjectInputStream ois = new ObjectInputStream(new FileInputStream(raf.getFD()))) {
                raf.seek(this.index.get(index));
                TrainRoute route = (TrainRoute) ois.readObject();
                printToOutput(route.toString(), outputArea);
            }
        }

        public void deleteRouteByIndex(int index, JTextArea outputArea) {
            if (index < 0 || index >= this.index.size()) {
                printToOutput("Index out of bounds.", outputArea);
                return;
            }
            this.index.remove(index);
            printToOutput("Route at index " + index + " deleted from index.", outputArea);
        }

        public void findRoutesByDeparture(String departure, JTextArea outputArea) throws IOException, ClassNotFoundException {
            java.util.List<Long> positions = departureIndex.get(departure);
            if (positions != null) {
                try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "r");
                     ObjectInputStream ois = new ObjectInputStream(new FileInputStream(raf.getFD()))) {
                    for (Long pos : positions) {
                        raf.seek(pos);
                        TrainRoute route = (TrainRoute) ois.readObject();
                        printToOutput(route.toString(), outputArea);
                    }
                }
            } else {
                printToOutput("No routes found for departure: " + departure, outputArea);
            }
        }

        public void findRoutesByDestination(String destination, JTextArea outputArea) throws IOException, ClassNotFoundException {
            java.util.List<Long> positions = destinationIndex.get(destination);
            if (positions != null) {
                try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "r");
                     ObjectInputStream ois = new ObjectInputStream(new FileInputStream(raf.getFD()))) {
                    for (Long pos : positions) {
                        raf.seek(pos);
                        TrainRoute route = (TrainRoute) ois.readObject();
                        printToOutput(route.toString(), outputArea);
                    }
                }
            } else {
                printToOutput("No routes found for destination: " + destination, outputArea);
            }
        }

        public void findRoutesByTrainNumber(String trainNumber, JTextArea outputArea) throws IOException, ClassNotFoundException {
            List<Long> positions = trainNumberIndex.get(trainNumber);
            if (positions != null) {
                try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "r");
                     ObjectInputStream ois = new ObjectInputStream(new FileInputStream(raf.getFD()))) {
                    for (Long pos : positions) {
                        raf.seek(pos);
                        TrainRoute route = (TrainRoute) ois.readObject();
                        printToOutput(route.toString(), outputArea);
                    }
                }
            } else {
                printToOutput("No routes found for train number: " + trainNumber, outputArea);
            }
        }
    }
}