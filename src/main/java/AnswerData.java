import java.util.Collection;

public class AnswerData
{
    private String text;
    private Collection<String> buttons;

    public AnswerData(String text, Collection<String> buttons) {
        this.text = text;
        this.buttons = buttons;
    }

    public AnswerData(String text) {
        this(text, null);
    }

    public String getText() { return text; }

    public Collection<String> getButtons() { return buttons; }
}
