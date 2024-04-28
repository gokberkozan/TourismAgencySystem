package core;

import javax.swing.*;
import java.awt.*;

public class Helper {
    public static int screenCenterPoint(String axis, Dimension size) {
        return switch (axis) {
            case "x" -> (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y" -> (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default -> 0;
        };
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static boolean isFieldEmpty(JEditorPane pane) {
        return pane.getText().trim().isEmpty();
    }

    public static void showMsg(String str) { // The part that prints the results of the operations on the screen
        optionPaneTR();
        String msg;
        String title;
        switch (str) {
            case "fill":
                msg = "Please fill in all fields.";
                title = "Error!";
                break;
            case "done":
                msg = "Transaction Successful";
                title = "Result";
                break;
            case "error":
                msg = "Something went wrong.";
                title = "Error!";
            default:
                msg = str;
                title = "Message";

        }
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String str) {  // Query screen
        optionPaneTR();
        String msg;
        switch (str) {
            case "sure":
                msg = "Are you sure you want to perform this operation?";
                break;
            default:
                msg = str;
        }
        return JOptionPane.showConfirmDialog(null, msg, "Your Final Decision?", JOptionPane.YES_NO_OPTION) == 0;
    }

    public static void optionPaneTR() {
        UIManager.put("OptionPane.okButtonText", "Okay");
        UIManager.put("OptionPane.yesButtonText", "Yes");
        UIManager.put("OptionPane.noButtonText", "No");
    }

    public static void setTheme() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            }
        }
    }
}