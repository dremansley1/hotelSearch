import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.json.JSONObject;

public class hotelSearch extends JPanel implements ActionListener {

    JLabel title;
    JComboBox hotelLocation;
    JLabel promptlabel;
    JLabel wlabel;
    JButton searchBtn;
    JLabel resultslabel;
    JLabel loadingLabel;
    JLabel informationlabel;
    JPanel resultslist;
    JPanel weatherContainer;

    String[] hotelLocationItems = {
        "Cardiff",
        "London",
        "Manchester",
        "Swansea",
        "Birmingham"
    };

    public hotelSearch() {

        JFrame frame = new JFrame("Hotel Search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // components setup
        title = new JLabel("HOTEL SEARCH");
        hotelLocation = new JComboBox < String > (hotelLocationItems);
        promptlabel = new JLabel("Where would you like to stay?");
        searchBtn = new JButton("Search");
        resultslabel = new JLabel("");
        loadingLabel = new JLabel("");
        informationlabel = new JLabel("");
        resultslist = new JPanel();
        weatherContainer = new JPanel();
        wlabel = new JLabel("");

        frame.setPreferredSize(new Dimension(1000, 800));

        frame.add(title);
        frame.add(hotelLocation);
        frame.add(promptlabel);
        frame.add(searchBtn);
        frame.add(resultslabel);
        frame.add(informationlabel);
        frame.add(loadingLabel);
        frame.add(resultslist);
        frame.add(weatherContainer);
        frame.add(wlabel);

        title.setBounds(330, 15, 150, 25);
        hotelLocation.setBounds(190, 65, 415, 30);
        promptlabel.setBounds(10, 65, 275, 30);
        searchBtn.setBounds(625, 65, 100, 30);
        resultslabel.setBounds(20, 125, 200, 25);
        informationlabel.setBounds(600, 125, 185, 25);
        loadingLabel.setBounds(400, 200, 200, 200);
        resultslist.setBounds(20, 165, 500, 600);
        weatherContainer.setBounds(600, 165, 260, 300);
        wlabel.setBounds(600, 165, 260, 300);
        searchBtn.addActionListener(this);
        frame.pack();
        frame.setVisible(true);
    }


    public void displayHotels(ResultSet search_results) throws IOException {
        try {
            resultslist.removeAll();
            resultslist.updateUI();
            if (!search_results.next()) {

                resultslist.add(new JLabel("Sorry! No hotels were found in this location!"));
                resultslist.validate();
                resultslist.repaint();

            } else {
                do {

                    URL img = new URL(search_results.getString(3));
                    ImageIcon image = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(125, 80, Image.SCALE_DEFAULT));
                    JLabel imgLabel = new JLabel("", image, JLabel.CENTER);
                    resultslist.add(imgLabel);

                    resultslist.add(new JLabel("      " + search_results.getString(2)));
                    resultslist.add(new JLabel("      Price:  £" + search_results.getString(5)));
                    resultslist.add(new JLabel("      Rating: " + search_results.getString(6)));
                    resultslist.validate();
                    resultslist.repaint();

                } while (search_results.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayWeather(JSONObject response) throws MalformedURLException {
        weatherContainer.removeAll();
        weatherContainer.updateUI();
        Object temp, temp_min, temp_max;
        System.out.println(response);
        String weather = response.getJSONArray("weather").getJSONObject(0).getString("description");
        JSONObject mainObj = response.getJSONObject("main");
        temp = mainObj.get("temp");
        temp_min = mainObj.get("temp_min");
        temp_max = mainObj.get("temp_min");
        weatherContainer.add(new JLabel("Current Weather: \n\n" + weather));
        weatherContainer.add(new JLabel("Current Temperature: \n\n" + temp));
        weatherContainer.add(new JLabel("Min Temperature: \n\n" + temp_min));
        weatherContainer.add(new JLabel("Max Temperature: \n\n" + temp_max));
        weatherContainer.validate();
        weatherContainer.repaint();
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == searchBtn) {

            String location = hotelLocation.getSelectedItem().toString();
            findHotels hotels = new findHotels();

            try {
                displayHotels(hotels.runQuery(location));
                ShowWeather fetchWeather = new ShowWeather();
                displayWeather(fetchWeather.weather(location));

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            informationlabel.setText("Current weather in " + location);
            resultslabel.setText("Results for Hotels in " + location);

        }
    }

    public static void main(String[] args) {
        hotelSearch guiLayout = new hotelSearch();
    }

}