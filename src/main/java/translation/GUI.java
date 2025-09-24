package translation;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class GUI {

    public static void main(String[] args) {
        Translator translator = new JSONTranslator();
        CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
        LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();

        SwingUtilities.invokeLater(() -> {

            // Convert codes -> names for languages
            List<String> languageNames = translator.getLanguageCodes().stream()
                    .map(languageCodeConverter::fromLanguageCode)
                    .collect(Collectors.toList());

            // Language selection panel (shows full names now)
            JPanel languagePanel = new JPanel();
            JComboBox<String> languageCombo = new JComboBox<>(languageNames.toArray(new String[0]));
            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageCombo);

            // Convert codes -> names for countries
            List<String> countryNames = translator.getCountryCodes().stream()
                    .map(countryCodeConverter::fromCountryCode)
                    .collect(Collectors.toList());

            // Country selection panel (shows full names now)
            JPanel countryPanel = new JPanel();
            JList<String> countryList = new JList<>(countryNames.toArray(new String[0]));
            JScrollPane listScroller = new JScrollPane(countryList);
            listScroller.setPreferredSize(new Dimension(250, 80));
            countryPanel.add(new JLabel("Country:"));
            countryPanel.add(listScroller);

            // Translation result panel
            JPanel translationPanel = new JPanel();
            JLabel resultLabel = new JLabel("Translation: ");
            translationPanel.add(resultLabel);

            // Listener for country selection
            countryList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    String languageNameSelected = (String) languageCombo.getSelectedItem();
                    String countryNameSelected = countryList.getSelectedValue();

                    if (languageNameSelected != null && countryNameSelected != null) {
                        String languageCode = languageCodeConverter.fromLanguage(languageNameSelected);
                        String countryCode = countryCodeConverter.fromCountry(countryNameSelected);
                        String translatedText = translator.translate(countryCode, languageCode);
                        resultLabel.setText("Translation: " + translatedText);
                    }
                }
            });

            // Listener for language selection
            languageCombo.addActionListener(e -> {
                String languageNameSelected = (String) languageCombo.getSelectedItem();
                String countryNameSelected = countryList.getSelectedValue();

                if (languageNameSelected != null && countryNameSelected != null) {
                    String languageCode = languageCodeConverter.fromLanguage(languageNameSelected);
                    String countryCode = countryCodeConverter.fromCountry(countryNameSelected);
                    String translatedText = translator.translate(countryCode, languageCode);
                    resultLabel.setText("Translation: " + translatedText);
                }
            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.add(languagePanel);
            mainPanel.add(translationPanel);
            mainPanel.add(countryPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        });
    }
}
