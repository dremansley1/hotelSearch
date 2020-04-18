import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.sql.ResultSet;
import java.sql.SQLException;


public class hotelSearch extends JPanel implements ActionListener{

        JLabel title;
        JComboBox hotelLocation;
        JLabel jcomp3;
        JButton searchBtn;
        JLabel resultslabel;
        JLabel informationlabel;
        JPanel resultslist;
        JPanel informationContainer;

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
    

        title = new JLabel("HOTEL SEARCH");
        hotelLocation = new JComboBox < String > (hotelLocationItems);
        jcomp3 = new JLabel("Where would you like to stay?");
        searchBtn = new JButton("Search");
        resultslabel = new JLabel("");
        informationlabel = new JLabel("");
        resultslist = new JPanel();
        informationContainer = new JPanel();

        frame.setPreferredSize(new Dimension(800,800));

        //add components
        frame.add(title);
        frame.add(hotelLocation);
        frame.add(jcomp3);
        frame.add(searchBtn);
        frame.add(resultslabel);
        frame.add(informationlabel);
        frame.add(resultslist);
        frame.add(informationContainer);

        //set component bounds (only needed by Absolute Positioning)
        title.setBounds(330, 15, 150, 25);
        hotelLocation.setBounds(190, 65, 415, 30);
        jcomp3.setBounds(10, 65, 275, 30);
        searchBtn.setBounds(625, 65, 100, 30);
        resultslabel.setBounds(20, 125, 200, 25);
        informationlabel.setBounds(510, 125, 185, 25);
        resultslist.setBounds(20, 165, 465, 300);
        informationContainer.setBounds(505, 165, 260, 300);

        searchBtn.addActionListener(this);
        frame.pack();
        frame.setVisible(true);
    }
    
    public void displayResults(ResultSet search_results) {
    	 try {
    		 resultslist.removeAll();
    		 resultslist.updateUI();
				if (!search_results.next()) {
					resultslist.add(new JLabel("Sorry! No hotels were found in this location!"));
			    	resultslist.validate();
			    	resultslist.repaint();

				} else {
				    do {
				    	resultslist.add(new JLabel("Hotel Name: " + search_results.getString(2)));
				    	resultslist.add(new JLabel("Image URL: " + search_results.getString(3)));
				    	resultslist.add(new JLabel("Hotel Rating: " + search_results.getString(5)));
				    	resultslist.validate();
				    	resultslist.repaint();
				    	
				    } while (search_results.next());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }

    public void actionPerformed(ActionEvent event){
        if(event.getSource() == searchBtn) {
            String location = hotelLocation.getSelectedItem().toString();
            findHotels hotels = new findHotels();
            displayResults(hotels.runQuery(location));
            informationlabel.setText("Information about " + location);
            resultslabel.setText("Results for Hotels in " + location);
        }
    }

    public static void main(String[] args) {
      hotelSearch guiLayout = new hotelSearch();
    }

}