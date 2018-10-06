import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

public class TextAreaTest
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        JTextArea ta = new JTextArea(10, 20);
        ta.setText("aabbcc sdfksdjgkj aa aa346i46i5aa");
        frame.add(new JScrollPane(ta));
        Highlighter highLighter = ta.getHighlighter();
        String text = ta.getText();
        DefaultHighlighter.DefaultHighlightPainter p = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
        String keyWord = "aa";
        int pos = 0;
        while ((pos = text.indexOf(keyWord, pos)) >= 0)
        {
            try
            {
                highLighter.addHighlight(pos, pos + keyWord.length(), p);
                pos += keyWord.length();
            }
            catch (BadLocationException e)
            {
                e.printStackTrace();
            }
        }
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}