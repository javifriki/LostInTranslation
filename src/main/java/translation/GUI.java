package translation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


// TODO Task D: Update the GUI for the program to align with UI shown in the README example.
//            Currently, the program only uses the CanadaTranslator and the user has
//            to manually enter the language code they want to use for the translation.
//            See the examples package for some code snippets that may be useful when updating
//            the GUI.
public class GUI {

    public static void main(String[] args) {
        Translator translator = new JSONTranslator();
        CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
        LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();

        SwingUtilities.invokeLater(() -> {

            JPanel languagePanel = new JPanel();
            JComboBox<String> languageCombo = new JComboBox<>(translator.getLanguageCodes().toArray(new String[0]));
            languagePanel.add(new JLabel("Language:"));
            languagePanel.add(languageCombo);

            JPanel countryPanel = new JPanel();
            JList<String> countryList = new JList<>(translator.getCountryCodes().toArray(new String[0]));
            JScrollPane listScroller = new JScrollPane(countryList);
            listScroller.setPreferredSize(new Dimension(250, 80));
            countryPanel.add(new JLabel("Country:"));
            countryPanel.add(listScroller);

            JPanel translationPanel = new JPanel();
            JLabel resultLabelText = new JLabel("Translation:");
            translationPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            translationPanel.add(resultLabel);

            // add JList listener
            countryList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    String languageSelected = (String) languageCombo.getSelectedItem();
                    String countrySelected = (String) countryList.getSelectedValue();

                    if (languageSelected != null && countrySelected != null) {
                        String translatedText = translator.translate(countrySelected, languageSelected);
                        resultLabelText.setText("Translation: " + translatedText);
                    }
                }
            });

            // add ComboBox listener
            languageCombo.addActionListener(e -> {
                String languageSelected = (String) languageCombo.getSelectedItem();
                String countrySelected = (String) countryList.getSelectedValue();

                if (languageSelected != null && countrySelected != null) {
                    String translatedText = translator.translate(countrySelected, languageSelected);
                    resultLabelText.setText("Translation: " + translatedText);
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
